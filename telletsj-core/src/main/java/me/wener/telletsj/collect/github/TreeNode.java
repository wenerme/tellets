package me.wener.telletsj.collect.github;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
class TreeNode extends BaseObject
{
    private String path;
    private String url;
    private Integer size;
    private String mode;
    private String sha;
    private TreeNodeType type;

    public enum TreeNodeType
    {
        blob, tree
    }
}
