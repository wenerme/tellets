package me.wener.telletsj.data.cq;

import me.wener.telletsj.data.DataService;
import me.wener.telletsj.util.inject.BaseModule;

public class CQDataModule extends BaseModule<CQDataModule>
{
    @Override
    protected void configure()
    {
        CQDataService service = new CQDataService();
        bind(DataService.class).toInstance(service);
    }
}
