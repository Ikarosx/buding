package cn.budingcc.gateway.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/17 8:40
 */
@Data
public class TokenInfo {
    private boolean active;
    private String client_id;
    private String[] scope;
    private String user_name;
    private String[] aud;
    private Date exp;
    private String[] authorities;
}