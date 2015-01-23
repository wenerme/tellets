package me.wener.telletsj.neo.api;

import java.util.Map;
import me.wener.telletsj.neo.MetadataImpl;

public interface Metadata
{
    <T> T metadata(String key);

    MetadataImpl metadata(String key, Object meta);

    /**
     * 只可获取不能设置
     *
     * @return 返回作为标示的信息
     */
    String id();

    String sha1();

    Metadata sha1(String sha1);

    String title();

    Metadata title(String title);

    String link();

    Metadata link(String link);

    Long timestamp();

    Metadata timestamp(Long timestamp);

    Map<String, Object> asMap();
}
