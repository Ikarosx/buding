package cn.budingcc.framework.domain.cms.request;

import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/3/1 9:24
 */
@Data
@ToString
public class ListCmsSiteParam {
    private String siteName;
    private String serverPort;
    private String serverDomain;
}
