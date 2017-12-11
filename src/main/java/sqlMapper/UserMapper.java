package sqlMapper;

import domin.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from students")
    List<User> getAllStudents();

    @Select("select * from user where username = #{username} and password = #{password}")
    User getUserByUsernameAndPassword(@Param("username")String username, @Param("password") String password);

    @Select("select * from user where username = #{username}")
    public User getUserByUsername(@Param("username") String username);

    @Options(useGeneratedKeys = true)
    @Insert("insert into user(`username`, `password`, `extendInfo`) values (#{username}, #{password}, #{extendInfo})")
    public void addUser(User user);


}
