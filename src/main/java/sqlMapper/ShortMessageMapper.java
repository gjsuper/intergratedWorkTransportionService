package sqlMapper;

import domin.ShortMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShortMessageMapper {

    @Insert("insert into shortmessage (recviver, sender, message, status, transType, extendInfo, srcPort, dstPort) values(#{receiver}, #{sender}," +
            "#{message}, #{status}, #{transType}, #{extendInfo}, #{srcPort}, #{dstPort})")
    void addShortMessage(ShortMessage shortMessage);

    @Select("select message from shortmessage where sender = #{sender} and receiver = #{receiver} and status = #{status}")
    List<String> getMessgesByStatus(@Param("sender") String sender, @Param("recviver") String receiver, @Param("status") int status);

    @Select("select message from shortmessage where sender = #{sender} and receiver = #{receiver}")
    List<String> getMessges(@Param("sender") String sender, @Param("recviver") String receiver);
}
