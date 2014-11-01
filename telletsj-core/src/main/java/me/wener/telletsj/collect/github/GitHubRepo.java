package me.wener.telletsj.collect.github;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import lombok.Data;

@Data
@Immutable
public class GitHubRepo
{
    private static final String REG_INFO_URI = "^(github:)? # 可选的模式前缀\n" +
            "(?<user>\\w+)/(?<repository>\\w+) # 用户名和仓库信息部分 \n" +
            "(:(?<branch>\\w+))? # 可选的分支部分\n" +
            "(/(?<path>.*))? # PATH 不包含前导的/\n" +
            "$";
    private static final Pattern PAT_INFO_URI = Pattern.compile(REG_INFO_URI, Pattern.COMMENTS);
    @Nonnull
    private final String user;
    @Nonnull
    private final String repository;
    @Nonnull
    private final String branch;
    @Nonnull
    private final String path;

    public GitHubRepo(@Nonnull String user, @Nonnull String repository, String branch, String path)
    {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(repository);
        this.user = user;
        this.repository = repository;

        this.branch = branch == null ? "master" : branch;
        this.path = Strings.nullToEmpty(path);
    }

    public static GitHubRepo parse(String uri)
    {
        Matcher matcher = PAT_INFO_URI.matcher(uri);
        Preconditions.checkArgument(matcher.find(), "Wrong GitHub repo uri: %s", uri);

        String user = matcher.group("user");
        String repository = matcher.group("repository");
        String branch = matcher.group("branch");
        String path = matcher.group("path");
        return new GitHubRepo(user, repository, branch, path);
    }

    public String getBranchUrl()
    {
        String url = "https://api.github.com/repos/%s/%s/branches/%s";
        return String.format(url, user, repository, branch);
    }

    @Override
    public String toString()
    {
        String str = "github:%s/%s%s%s";
        return String.format(str, user, repository, ":" + branch, "/" + path);
    }
}
