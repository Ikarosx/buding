package cn.budingcc.framework.domain.cms.request;

import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/3/1 9:28
 */
@Data
@ToString
public class ListCmsSiteServerParam {
    private String ip;
    private String serverPort;
    private String serverDomain;
    private String serverName;
    
}
