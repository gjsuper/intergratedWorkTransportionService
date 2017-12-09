package sqlMapper;

import domin.Students;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentsMapper {

    @Select("select * from students")
    List<Students> getAllStudents();
}
