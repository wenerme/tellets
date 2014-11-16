package me.wener.telletsj.data.cq;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.ArticleState;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.Tag;

@Data
@EqualsAndHashCode(exclude = {"tags", "categories", "meta"})
class ArticleVO implements Article, ArticleInfo
{
    private final Set<Tag> tags = Sets.newLinkedHashSet();
    private final Set<String> features = Sets.newLinkedHashSet();
    private final Set<Category> categories = Sets.newLinkedHashSet();
    private final Map<String, String> meta = Maps.newHashMap();
    private final String title;
    private final String link;
    private final String sha;
    private final String content;
    private final String description;
    private final Long timestamp;
    private final ArticleState state;

    public ArticleVO(Article a)
    {
        title = a.getTitle();
        link = a.getLink();
        sha = a.getSha();
        content = a.getContent();
        description = a.getDescription();
        timestamp = a.getTimestamp();
        state = a.getState();
    }

    public ArticleVO(Article a, ArticleInfo i)
    {
        this(a);
        addInfo(i);
    }

    public void clearInfo()
    {
        tags.clear();
        features.clear();
        categories.clear();
        meta.clear();
    }

    public void addInfo(ArticleInfo i)
    {
        tags.addAll(i.getTags());
        features.addAll(i.getFeatures());
        categories.addAll(i.getCategories());
        meta.putAll(i.getMeta());
    }

    public void info(ArticleInfo i)
    {
        clearInfo();
        addInfo(i);
    }
}
