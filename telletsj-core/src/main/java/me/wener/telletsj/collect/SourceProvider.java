package me.wener.telletsj.collect;

import java.net.URI;
import java.util.Set;

public interface SourceProvider
{
    /**
     * @return 该 Provider 支持的 URI scheme
     */
    Set<String> getSchemes();

    /**
     * @return 该URI对应的收集源
     * @throws IllegalArgumentException      URI 格式或信息错误
     * @throws UnsupportedOperationException 不支持的URI Scheme
     */
    CollectSource getSource(URI uri) throws IllegalArgumentException, UnsupportedOperationException;
}
