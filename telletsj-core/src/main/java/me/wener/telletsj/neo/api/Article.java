package me.wener.telletsj.neo.api;

import java.util.Collection;
import java.util.Date;

public interface Article
{
    String title();

    String link();

    Date date();

    String state();

    Collection<String> categories();

    Collection<String> tags();

    String sha();

    String summary();

    String content();

    String source();

    Article title(String val);

    Article link(String val);

    Article date(Date val);

    Article state(String val);

    Article categories(Collection<String> val);

    Article tags(Collection<String> val);

    Article sha(String val);

    Article summary(String val);

    Article content(String val);

    Article source(String val);
}
