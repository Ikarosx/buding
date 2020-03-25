package cn.budingcc.framework.domain.shop.extension;

import cn.budingcc.framework.domain.shop.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/16 8:45
 */
@Data
@ToString
public class CategoryNode extends Category {
    private List<CategoryNode> children;
}
