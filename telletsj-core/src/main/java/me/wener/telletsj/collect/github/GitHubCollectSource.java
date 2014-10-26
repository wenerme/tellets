package me.wener.telletsj.collect.github;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import me.wener.telletsj.collect.AbstractCollectSource;
import me.wener.telletsj.collect.CollectionException;
import me.wener.telletsj.collect.SourceContent;

public class GitHubCollectSource extends AbstractCollectSource
{
    @Getter
    private final GitHubHelper helper;
    private final GitHubRepo repo;

    public GitHubCollectSource(GitHubHelper helper, GitHubRepo repo)
    {
        this.helper = helper;
        this.repo = repo;
    }

    @Override
    public Iterable<SourceContent> collect() throws CollectionException
    {
        List<SourceContent> contents = Lists.newArrayList();
        for (TreeNode node : helper.fetchTreeNode(repo))
        {
            if (node.getType() == TreeNode.TreeNodeType.blob)
            {
                contents.add(new GitHubSourceContent(this, node));
            }
        }
        return contents;
    }
}
