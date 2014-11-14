package me.wener.telletsj.collect.github;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import me.wener.telletsj.util.IO;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestObject
{

    private FileSystemManager fsm;

    @Before
    public void setup() throws FileSystemException
    {
        fsm = VFS.getManager();
    }

    @Test
    public void testBranch() throws IOException
    {
        FileContent content = fsm.resolveFile("res:me/wener/telletsj/collect/github/branch.json").getContent();
        String json = IO.readString(content.getInputStream());
        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(json);
        JsonElement element = root
                .getAsJsonObject()
                .getAsJsonObject("commit")
                .getAsJsonObject("commit")
                .getAsJsonObject("tree")
                .get("url");
        assert element
                .getAsString()
                .equals("https://api.github.com/repos/wenerme/wener/git/trees/7ae3e3d3096737440276049792219659159a839e");
    }
    @Test
    public void testBlob() throws IOException
    {
        FileContent content = fsm.resolveFile("res:me/wener/telletsj/collect/github/blob.json").getContent();
        String json = IO.readString(content.getInputStream());
        BlobObject blob = new Gson().fromJson(json, BlobObject.class);
        assert blob.getContent().contains("Blog");
        System.out.println(blob.getContent());
    }
    @Test
    @Ignore
    public void testFetch() throws IOException, GitHubException
    {
        GitHubHelper helper = new GitHubHelper(new VFSFetcher(VFS.getManager()));
        GitHubRepo repo = GitHubRepo.parse("github:wenerme/wener");
        System.out.println(helper.fetchAllBranchNode(repo));
    }
}
