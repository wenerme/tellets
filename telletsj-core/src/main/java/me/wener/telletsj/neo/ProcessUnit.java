package me.wener.telletsj.neo;

import java.util.Map;
import lombok.Data;
import me.wener.telletsj.neo.api.Metadata;

@Data
public class ProcessUnit
{
    private String originContent;
    private String processedContent;
    private Map<String, Object> option;
    private Metadata meta;
}
