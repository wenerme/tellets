package me.wener.telletsj.data.cq;

import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.impl.ArticleBuilder;

class ArticleVOBuilder extends ArticleBuilder
{
    @Override
    public Article build()
    {
        return new ArticleVO(super.build());
    }
}
