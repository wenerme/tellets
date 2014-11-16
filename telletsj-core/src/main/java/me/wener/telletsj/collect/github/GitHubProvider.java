package me.wener.telletsj.collect.github;

import com.google.common.collect.ImmutableSet;
import java.net.URI;
import java.util.Set;
import javax.inject.Inject;
import me.wener.telletsj.collect.CollectSource;
import me.wener.telletsj.collect.SourceProvider;
import me.wener.telletsj.util.inject.Enabled;
import org.apache.commons.vfs2.FileSystemManager;

/**
 * URI 格式: github:username/repo/path/to/file
 */
@Enabled
public class GitHubProvider implements SourceProvider
{
    private final static Set<String> SCHEMES = ImmutableSet.of("github");
    private GitHubHelper helper;

    @Inject
    protected void init(FileSystemManager fsm)
    {
        helper = new GitHubHelper(new VFSFetcher(fsm));
    }

    @Override
    public Set<String> getSchemes()
    {
        return SCHEMES;
    }

    @Override
    public CollectSource getSource(URI uri) throws IllegalArgumentException, UnsupportedOperationException
    {
        GitHubRepo repo = GitHubRepo.parse(uri.toString());

        return new GitHubCollectSource(helper, repo);
    }
}
