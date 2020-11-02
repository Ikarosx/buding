package cn.budingcc.ucenter.dao;

import cn.budingcc.framework.domain.ucenter.extension.BdUserRoleExtension;
import cn.budingcc.framework.domain.ucenter.request.UserListRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * @author Ikaros
 * @date 2020/2/11 12:36
 */
@Mapper
public interface BdUserMapper {
    @Select("<script>" +
            "SELECT *,school_name FROM bd_user, bd_school" +
            "<where>" +
            "bd_user.school_id = bd_school.id " +
            "<when test='userListRequest.studentId!=null'>" +
            "AND student_id LIKE '%${userListRequest.studentId}%' " +
            "</when>" +
            "<when test='userListRequest.username!=null'>" +
            "AND username LIKE '%${userListRequest.username}%' " +
            "</when>" +
            "<when test='userListRequest.sex!=null'>" +
            "AND sex=#{userListRequest.sex} " +
            "</when>" +
            "<when test='userListRequest.state!=null'>" +
            "AND bd_user.state = #{userListRequest.state} " +
            "</when>" +
            "</where>" +
            "</script>")
    @Results({
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "userPic", column = "user_pic"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "schoolName", column = "school_name"),
            @Result(property = "schoolId", column = "school_id"),
            @Result(property = "id", column = "id"),
            @Result(property = "bdRoleList", column = "id",
            many = @Many(select = "cn.budingcc.ucenter.dao.BdRoleMapper.listBdRoleByUserId",fetchType = FetchType.LAZY))
    })
    Page<BdUserRoleExtension> listUsersByPage(int page, int size, UserListRequest userListRequest);
}
