package me.wener.telletsj.collect.github;

import java.io.IOException;

public class GitHubException extends IOException
{
    public GitHubException()
    {
    }

    public GitHubException(String message)
    {
        super(message);
    }

    public GitHubException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GitHubException(Throwable cause)
    {
        super(cause);
    }
}
