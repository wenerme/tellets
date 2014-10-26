package me.wener.telletsj.collect.github;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.concurrent.Immutable;
import lombok.Data;

@Data
@Immutable
public class GitHubRepo
{
    private static final String REG_INFO_URI = "^(github:)? # 可选的模式前缀\n" +
            "(?<user>\\w+)/(?<repository>\\w+) # 用户名和仓库信息部分 \n" +
            "(:(?<branch>\\w+))? # 可选的分支部分\n" +
            "(?<path>.*)?$";
    private static final Pattern PAT_INFO_URI = Pattern.compile(REG_INFO_URI, Pattern.COMMENTS);
    private final String user;
    private final String repository;
    private final String branch;
    private final String path;

    public GitHubRepo(String user, String repository, String branch, String path)
    {
        this.user = user;
        this.repository = repository;
        this.branch = branch;
        this.path = path;
    }

    public static GitHubRepo parse(String uri)
    {
        Matcher matcher = PAT_INFO_URI.matcher(uri);
        Preconditions.checkArgument(matcher.find(), "Wrong GitHub repo info uri: %s", uri);

        String user = matcher.group("user");
        String repository = matcher.group("repository");
        String branch = matcher.group("branch");
        String path = matcher.group("path");
        return new GitHubRepo(user, repository, branch, path);
    }

    public String getBranchUrl()
    {
        String url = "https://api.github.com/repos/%s/%s/branches/%s";
        return String.format(url, user, repository, branch == null ? "master" : branch);
    }

    @Override
    public String toString()
    {
        return "github:" + user + "/" + repository + (branch == null ? "" : ":" + branch) + Strings.nullToEmpty(path);
    }
}
