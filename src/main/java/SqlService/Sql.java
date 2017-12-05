package SqlService;

import mapper.SqlMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sql implements InitializingBean {

    @Autowired
    private SqlMapper sqlMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(sqlMapper.getAllStudents());
    }
}
