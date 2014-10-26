package me.wener.telletsj.collect.github;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.DatatypeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
class BlobObject extends BaseObject
{
    private String content;
    private String sha;
    private String url;
    private String encoding;
    private Integer size;
    private transient String rawContent = null;

    public String getContent()
    {
        return getContent(StandardCharsets.UTF_8);
    }

    public String getContent(Charset charset)
    {
        if (rawContent != null)
            return rawContent;

        if ("base64".equals(encoding))
        {
            byte[] bytes = DatatypeConverter.parseBase64Binary(content);
            rawContent = new String(bytes, charset);
        } else
            rawContent = content;

        return rawContent;
    }

}
