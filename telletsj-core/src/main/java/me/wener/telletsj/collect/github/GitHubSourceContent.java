package me.wener.telletsj.collect.github;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import java.io.File;
import java.net.URL;
import me.wener.telletsj.collect.SourceContent;

public class GitHubSourceContent extends SourceContent
{
    private final GitHubCollectSource source;
    private final TreeNode node;

    public GitHubSourceContent(GitHubCollectSource source, TreeNode node)
    {
        this.source = source;
        this.node = node;
        Preconditions.checkArgument(node.getType() == TreeNode.TreeNodeType.blob);
        setHash(node.getSha());
        setFilename(new File(node.getPath()).getName());
    }

    @Override
    protected void fillContent0() throws GitHubException
    {
        setContent(source.getHelper().fetchBlobNode(node).getContent());
    }
}
