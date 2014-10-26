package me.wener.telletsj.collect.github;

import java.io.IOException;

public interface Fetcher
{
    String fetch(String url) throws IOException;
}
