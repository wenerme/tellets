package me.wener.telletsj.process;

public class ProcessException extends Exception
{
    public ProcessException()
    {
    }

    public ProcessException(String message)
    {
        super(message);
    }

    public ProcessException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProcessException(Throwable cause)
    {
        super(cause);
    }
}
