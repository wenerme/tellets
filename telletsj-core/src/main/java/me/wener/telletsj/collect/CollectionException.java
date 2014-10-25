package me.wener.telletsj.collect;

public class CollectionException extends Exception
{
    public CollectionException()
    {
    }

    public CollectionException(String message)
    {
        super(message);
    }

    public CollectionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CollectionException(Throwable cause)
    {
        super(cause);
    }
}
