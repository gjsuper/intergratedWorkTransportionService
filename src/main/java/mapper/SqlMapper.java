package mapper;

import domin.Students;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SqlMapper {

    @Select("select * from students")
    List<Students> getAllStudents();
}
