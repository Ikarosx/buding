package cn.budingcc.framework.domain.shop.request;

import cn.budingcc.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/1/28 16:21
 */
@Data
@ToString
public class GoodListRequest extends RequestData {
    private Integer state;
    private String userId;
    private String sort;
    private String directCategoryId;
}
