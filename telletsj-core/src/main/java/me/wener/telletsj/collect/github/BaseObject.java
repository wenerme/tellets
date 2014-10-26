package me.wener.telletsj.collect.github;

import lombok.Data;

@Data
class BaseObject
{
    private String message;

    public void checkError() throws GitHubException
    {
        if (message != null)
            throw new GitHubException(message);
    }

}
