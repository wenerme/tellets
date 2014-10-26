package me.wener.telletsj.collect.github;

import me.wener.telletsj.collect.CollectionException;

public class GitHubException extends CollectionException
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
