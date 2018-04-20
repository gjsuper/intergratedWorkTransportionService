package sqlMapper;

import DataStruct.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface MessageMapper {

    @Insert("insert into message(src_id, dst_id, data) values(#{src_id}, #{dst_id}, #{data})")
    void addMessage(Message message);

    @Select("select * from message where dst_id = #{dst_id} and id > #{id}")
    List<Message> getMessagesById(@Param("dst_id") int dst_id, @Param("id") int id);

    @Select("select * from message where dst_id = ? and time > ?")
    List<Message> getMessagesByTime(@Param("dst_id") int id, @Param("time")Timestamp timestamp);

}
