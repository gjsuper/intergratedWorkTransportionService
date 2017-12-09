package sqlMapper;

import domin.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from students")
    List<User> getAllStudents();

    @Select("select * from user where username = #{username} and password = #{password}")
    User getUserByUsername(String username, String password);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user(usename, password) values (#{username}, #{password})")
    int addUser(String username, String password);
}
