package tellets
import (
	"fmt"
	"reflect"
)
type HookType string



// Call sequence
// Before -> Parent Before -> Parent After -> After
type Hook interface {
	// Hook before all
	HookBefore(HookType, ...interface{})
	// Hook after all
	HookAfter(HookType, ...interface{})
	// Call hook in this context
	DoHook(HookType, Context)
	// Will not affect parent hook
	UnHook(HookType, ...interface{})
}

type hook struct {
	parent   *hook
	before   map[HookType][]interface{}
	after    map[HookType][]interface{}
	name     string
	children int
}
func (h *hook)HookBefore(t HookType, hook ...interface{}) {
	h.checkFunc(hook...)
	if s, ok := h.before[t]; !ok {
		s = make([]interface{}, 0)
		h.before[t] = s
	}
	h.before[t] = append(append(make([]interface{}, 0), hook...), h.before[t]...)
}
func (h *hook)DoHook(t HookType, ctx Context) {
	if s, ok := h.before[t]; ok {
		for _, f := range s {
			if _, err := ctx.Call(f); err != nil {
				panic(err)
			}
		}
	}
	if h.parent != nil {
		h.parent.DoHook(t, ctx)
	}
	if s, ok := h.after[t]; ok {
		for _, f := range s {
			if _, err := ctx.Call(f); err != nil {
				panic(err)
			}
		}
	}
}
func (h *hook)UnHook(t HookType, hook ...interface{}) {
	if s, ok := h.before[t]; ok {
		a := make([]interface{}, 0)
		for _, f := range s {
			for _, fh := range hook {
				if f != fh {
					a = append(a, f)
				}
			}
		}
		h.before[t] = a
	}
	if s, ok := h.after[t]; ok {
		a := make([]interface{}, 0)
		for _, f := range s {
			for _, fh := range hook {
				if f != fh {
					a = append(a, f)
				}
			}
		}
		h.after[t] = a
	}
}
func (h *hook)HookAfter(t HookType, hook ...interface{}) {
	h.checkFunc(hook...)
	if s, ok := h.after[t]; !ok {
		s = make([]interface{}, 0)
		h.after[t] = s
	}
	h.after[t] = append(h.after[t], hook...)
}

func (h *hook)checkFunc(hook ...interface{}) {
	for _, f := range hook {
		if reflect.TypeOf(f).Kind() != reflect.Func {
			panic("Hook must be func")
		}
	}
}

func (h hook)ToString() string {
	return h.name
}
func CreateChildHook(h Hook, name string) Hook {
	ho := h.(*hook)
	ho.children +=1
	return &hook{
		ho,
		make(map[HookType][]interface{}),
		make(map[HookType][]interface{}),
		fmt.Sprintf("%s#%d-%s", ho.ToString(), ho.children, name),
		ho.children,
	}
}

func NewHook(name string) Hook {
	return &hook{
		nil,
		make(map[HookType][]interface{}),
		make(map[HookType][]interface{}),
		name,
		0,
	}
}