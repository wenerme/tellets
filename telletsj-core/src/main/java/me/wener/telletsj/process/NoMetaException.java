package me.wener.telletsj.process;

public class NoMetaException extends ProcessException
{
    public NoMetaException()
    {
    }

    public NoMetaException(String message)
    {
        super(message);
    }

    public NoMetaException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoMetaException(Throwable cause)
    {
        super(cause);
    }
}
