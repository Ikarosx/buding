package cn.budingcc.framework.domain.shop.request;

import cn.budingcc.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/2/23 20:59
 */
@Data
@ToString
public class GoodSearchParam extends RequestData {
    private String sort;
    private String directCategoryId;
    private String keyword;
}
