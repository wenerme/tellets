package me.wener.telletsj.collect.github;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.List;

public class GitHubHelper
{
    private static final Gson gson = new Gson();
    private final URLFetcher fetcher;
    private final JsonParser parser = new JsonParser();


    public GitHubHelper(URLFetcher fetcher) {this.fetcher = fetcher;}

    public BlobObject fetchBlobNode(TreeNode node) throws GitHubException
    {
        Preconditions.checkArgument(node.getType() == TreeNode.TreeNodeType.blob, "Node type must be blob");
        node.checkError();
        return fetchAsObject(node.getUrl(), BlobObject.class);
    }

    public List<TreeNode> fetchAllBranchNode(GitHubRepo repo) throws GitHubException
    {
        String url = repo.getBranchUrl();
        String json = fetchUrl(url);
        JsonElement root = parser.parse(json);
        checkError(root);
        String branchTreeUrl = getBranchTreeUrl(root) + "?recursive=1";
        return fetchAsObject(branchTreeUrl, TreeObject.class).getTree();
    }

    public List<TreeNode> fetchTreeNode(GitHubRepo repo) throws GitHubException
    {
        List<TreeNode> all = fetchAllBranchNode(repo);
        if (Strings.isNullOrEmpty(repo.getPath()))
            return all;

        List<TreeNode> match = Lists.newArrayList();
        for (TreeNode node : all)
            if (node.getPath().startsWith(repo.getPath()))
                match.add(node);

        return match;
    }

    private void checkError(JsonElement root) throws GitHubException
    {
        JsonPrimitive msgElement = root.getAsJsonObject().getAsJsonPrimitive("message");
        if (msgElement != null)
            throw new GitHubException(msgElement.getAsString());
    }

    private String getBranchTreeUrl(JsonElement root)
    {
        return root
                .getAsJsonObject()
                .getAsJsonObject("commit")
                .getAsJsonObject("commit")
                .getAsJsonObject("tree")
                .get("url")
                .getAsString();
    }

    public String fetchUrl(String url)
    {
        return fetcher.fetch(url);
    }

    public <T extends BaseObject> T fetchAsObject(String url, Class<T> type) throws GitHubException
    {
        T o = gson.fromJson(fetchUrl(url), type);
        o.checkError();
        return o;
    }
}
