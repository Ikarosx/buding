package cn.budingcc.framework.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/1/26 18:07
 */
@Data
@ToString
public class QueryResponseResult extends ResponseResult {
    QueryResult queryResult;
    
    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }
}
