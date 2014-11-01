package me.wener.telletsj.process.processor;

import com.google.common.collect.Sets;
import com.google.common.io.Files;
import java.util.Collections;
import java.util.Set;
import lombok.Getter;
import me.wener.telletsj.process.MetaProcessor;

public abstract class ExtensionDetectProcessor implements MetaProcessor
{
    @Getter
    private final Set<String> extensions = Sets.newHashSet();

    protected ExtensionDetectProcessor(String... ext)
    {
        Collections.addAll(extensions, ext);
    }

    protected ExtensionDetectProcessor()
    {
    }

    @Override
    public boolean canProcess(String filename)
    {
        return extensions.contains(Files.getFileExtension(filename));
    }
}
