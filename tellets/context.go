package tellets
import (
	"reflect"
	"fmt"
	"github.com/syndtr/goleveldb/leveldb/errors"
)

type Context interface {
	// Add a value to context
	// return false if add failed because this type already exists
	// Can not add Context
	Add(interface{}) bool;
	// Add a value as specified type
	// t could be reflect.Type, func()TheType,(Type)nil
	AddType(v interface{}, t interface{}) bool;
	// Add a value by a func, func can have parameter that this context hold
	// e.g. func(s Server)Reader{return s.Reader} will inject Reader
	AddFunc(interface{}) bool;
	// return false if the type already exists in parent context
	Set(interface{}) bool;
	// Set the value to parameter
	// e.g.
	//  *int will match the type int
	//  *string will match the string
	//  string will panic
	//  Context will always return this
	Get(interface{}) bool;
	// Match all these types
	// values is the same size as types
	// if value at index not exists, the index will add to missing
	// values[index] will be nil
	Match(types []reflect.Type) (values []interface{}, missing []int)
	// Call this function in this context
	// Will auto inject the parameter
	// if f is not function, then return error
	Call(f interface{}) ([]interface{}, error)
	// Find a value by type
	// return value and it's context
	// if context is nil then type not found
	Find(reflect.Type) (interface{}, Context)
}

var _contextType = reflect.TypeOf((*Context)(nil)).Elem()
type context struct {
	parent   *context
	values   map[reflect.Type]interface{}
	children int
	name     string
}
func (c *context)Add(v interface{}) bool {
	t := reflect.TypeOf(v)
	return c.AddType(v, t)
}
func (c *context)AddFunc(f interface{}) bool {
	v := reflect.ValueOf(f)
	if v.Kind() != reflect.Func && v.Type().NumOut() != 1 {
		panic(errors.New("Must use a func(...)T"))
	}

	values, err := c.Call(f)
	if err != nil { panic(err) }
	return c.AddType(values[0], v.Type().Out(0))
}
func (c *context)AddType(v interface{}, vt interface{}) bool {
	var t reflect.Type
	if ty, ok := vt.(reflect.Type); ok {
		t = ty
	}else {
		typeOf := reflect.TypeOf(vt)
		if typeOf.Kind() == reflect.Func {
			if typeOf.NumOut() != 1 {panic(errors.New("The type func must have one return value"))}
			t = typeOf.Out(0)
		}else {
			panic(errors.New(fmt.Sprintf("Can not use %T as type", vt)))
		}
	}

	if !reflect.TypeOf(v).AssignableTo(t) {
		panic(fmt.Sprintf("Add value(%T) type not implement specified type(%v)", v, t))
	}

	if t == _contextType {
		panic("Can not add Context")
	}

	if _, oc := c.Find(t); oc != nil {
		return false
	}
	c.values[t] = v
	return true
}
func (c *context)Set(v interface{}) bool {
	t := reflect.TypeOf(v)
	if _, oc := c.Find(t); oc != c {
		return false
	}
	c.values[t] = v
	return true
}
func (c *context)Get(v interface{}) bool {
	val := reflect.ValueOf(v)
	if val.Kind() != reflect.Ptr {
		panic("Get value from context must use a pointer")
	}
	// Get Context
	if val.Elem().Type() == _contextType {
		val.Elem().Set(reflect.ValueOf(c))
		return true
	}
	if ov, oc := c.Find(val.Elem().Type()); oc != nil {
		val.Elem().Set(reflect.ValueOf(ov))
		return true
	}
	return false
}
func (c *context)Find(t reflect.Type) (interface{}, Context) {
	if v, ok := c.values[t]; ok {
		return v, c
	}else if c.parent != nil {
		return c.parent.Find(t)
	}
	return nil, nil
}
func (c *context)Match(types []reflect.Type) (values []interface{}, missing []int) {
	values = make([]interface{}, len(types))
	missing = make([]int, 0)
	for i, t := range types {
		if v, oc := c.Find(t); oc != nil {
			values[i] = v
		}else {
			missing = append(missing, i)
		}
	}
	return
}
func (c *context)Call(fu interface{}) ([]interface{}, error) {
	f := reflect.ValueOf(fu)
	if f.Kind() != reflect.Func {
		return nil, errors.New("Call only accept function type")
	}
	t := f.Type();
	types := make([]reflect.Type, t.NumIn())
	for i := 0; i < t.NumIn(); i ++ {
		types[i] = t.In(i);
	}

	if args, missing := c.Match(types); len(missing) == 0 {
		values := make([]reflect.Value, len(args))
		for i := 0; i < len(args); i ++ {
			values[i] = reflect.ValueOf(args[i])
		}
		results := f.Call(values)
		returns := make([]interface{}, len(results))

		for i, v := range results {
			returns[i] = v.Interface()
		}
		return returns, nil
	}else {
		msg := "Parameter missing in context: "
		for _, v := range missing {
			msg += fmt.Sprintf("#%d %v ", v, types[v])
		}
		return nil, errors.New(msg)
	}
}
func (c context)ToString() string {
	return c.name
}
func CreateChildContext(c Context, name string) Context {
	ctx := c.(*context)
	ctx.children +=1
	return &context{
		ctx,
		make(map[reflect.Type]interface{}),
		0,
		fmt.Sprintf("%s#%d-%s", ctx.ToString(), ctx.children, name),
	}
}
func NewContext(name string) Context {
	return &context{
		nil,
		make(map[reflect.Type]interface{}),
		0,
		name,
	}
}
