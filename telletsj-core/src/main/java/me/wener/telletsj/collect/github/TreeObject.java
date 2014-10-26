package me.wener.telletsj.collect.github;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TreeObject extends BaseObject
{
    private String sha;
    private String url;
    private List<TreeNode> tree;
    private Boolean truncated;
}
