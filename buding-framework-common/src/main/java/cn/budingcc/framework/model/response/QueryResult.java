package cn.budingcc.framework.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/26 17:54
 */
@Data
@ToString
public class QueryResult<T> {
    private List<T> list;
    private long total;
    private long totalPage;
}
