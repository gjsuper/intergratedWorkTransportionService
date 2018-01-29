package sqlMapper;

import DataStruct.Department;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface DepartmentMapper {

    @Select("select * from department where id in (select department from user where name = #{name}) " +
            "or id in (select army from user where name = #{name}) order by id")
    List<Department> getArmyAndDepByUsername(@Param("name") String name);

    @Select("select * from department")
    List<Department> getAllDepartments();

    @Insert("insert into department(name, parent_id, remark) values(#{name}, #{parent_id}, #{remark})")
    void addDepartment(@Param("name") String name, @Param("parent_id") int id, @Param("remark") String remark);

    @Select("select * from department where id in(select parent_id from department where id = #{id})")
    Department getDepartmentByChildId(int id);

    @Select("select * from department where parent_id in (select id from department where name = #{name})")
    List<Department> getDepartmentByFatherName(String name);

    @Select("select max(id) from department")
    int getMaxId();

    @Select("select id from department where name = #{name}")
    int getIdByName(String name);

    void deleteDepartmentById(@Param("ids") List<Integer> ids);
}
