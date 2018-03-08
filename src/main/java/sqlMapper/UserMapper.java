package sqlMapper;

import DataStruct.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserMapper {

    @Select("select * from user where id = #{id} and password = #{password}")
    User getUserByIdAndPassword(@Param("id")String id, @Param("password") String password);

    @Select("select * from user where name = #{name}")
    User getUserByUsername(@Param("name") String name);

    @Select("select * from user where id = #{id}")
    User getUserById(int id);

    @Options(useGeneratedKeys = true)
    @Insert("insert into user(name, password, army, department, ip) values (#{name}, #{password}, #{army}, #{department}, #{ip})")
    void addUser(User user);

    @Update("update user setObject status = 1, ip = #{ip} where name = #{name}, port = #{port}")
    int setLoginInfo(@Param("name") String name, @Param("ip") String ip, @Param("port") int port);

    @Update("update user setObject status = 0 where id = #{id}")
    int setLogoutInfo(@Param("name") String id);

    @Update("update user setObject password = #{password} where id = #{id}")
    void setPassword(@Param("password") String password, @Param("id") String id);

    @Delete("delete from user where id = #{id}")
    int deleteUserById(int id);

    @Select("select * from user where army in (select army from usr where id = #{id})")
    List<User> getUsersByArmy(int id);

    @Select("select * from user where department = #{department}")
    List<User> getUsersByDepartment(int department);

    @Delete("delete from user where department = #{department}")
    void deleteUserByDepartment(int department);

    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select ip, port from user where status = 1")
    List<User> getOnlineUsers();

}
