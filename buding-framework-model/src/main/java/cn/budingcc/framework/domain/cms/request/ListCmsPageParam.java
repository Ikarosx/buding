package cn.budingcc.framework.domain.cms.request;

import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/3/1 9:32
 */
@Data
@ToString
public class ListCmsPageParam {
    private String pageName;
    private String pageAlias;
}
