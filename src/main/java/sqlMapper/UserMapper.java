package sqlMapper;

import domin.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from students")
    List<User> getAllStudents();

    @Select("select * from user where username = #{username} and password = #{password}")
    User getUserByUsernameAndPassword(@Param("username")String username, @Param("password") String password);

    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);

    @Options(useGeneratedKeys = true)
    @Insert("insert into user(`username`, `password`, `extendInfo`) values (#{username}, #{password}, #{extendInfo})")
    void addUser(User user);

    @Update("update user set status = #{status}, ip = #{ip}, port = #{port} where username = #{username}")
    void setUserOnline(@Param("username") String username, int status, @Param("ip") String ip, @Param("port") int port);

    @Update("update user set status = #{status} where username = #{username}")
    void setUserStatus(@Param("username") String username, int status);
}
