package sqlMapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StorageMapper {

    @Select("select * from storage where `key` = #{key}")
    String getBykey(@Param("key") String key);

    @Insert("insert into storage values(#{key}, #{value})")
    void add(@Param("key") String key, @Param("value") String value);

    @Insert("update storage set `value`= #{values} where `key`= #{key})")
    void update(@Param("key") String key, @Param("value") String value);
}
