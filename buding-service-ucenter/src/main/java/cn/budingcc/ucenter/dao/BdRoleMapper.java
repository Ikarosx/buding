package cn.budingcc.ucenter.dao;

import cn.budingcc.framework.domain.ucenter.BdRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/11 18:02
 */
@Mapper
public interface BdRoleMapper {
    @Select("SELECT * FROM bd_role WHERE id in (SELECT role_id from bd_user_role WHERE user_id = #{userId})")
    @Results({
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "roleCode", column = "role_code"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    List<BdRole> listBdRoleByUserId(String userId);
}
