package me.wener.telletsj.process;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import java.util.List;

public class DefaultProcessService implements ProcessService
{
    private final List<MetaProcessor> processors = Lists.newCopyOnWriteArrayList();

    @Override
    public ContentInfo tryProcess(String filename, Supplier<String> content) throws ProcessException
    {
        MultiProcessException e = new MultiProcessException();
        for (MetaProcessor processor : processors)
        {
            if (processor.canProcess(filename))
            {
                try
                {
                    return processor.process(content.get());
                } catch (ProcessException ex)
                {
                    e.getExceptions().add(ex);
                }
            }
        }
        if (e.getExceptions().size() == 0)
            return null;

        throw e;
    }

    @Override
    public void register(MetaProcessor processor)
    {
        processors.add(processor);
    }

}
