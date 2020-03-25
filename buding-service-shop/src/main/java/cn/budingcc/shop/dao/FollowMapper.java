package cn.budingcc.shop.dao;

import cn.budingcc.framework.domain.shop.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/29 14:52
 */
@Mapper
public interface FollowMapper {
    @Select("SELECT * FROM bd_good AS good, bd_user_good_follow AS follow WHERE follow.user_id = #{userId} AND follow.good_id = good.id")
    List<Good> listFollowsByUserId(String userId);
}
