package service.MysqlService;

import sqlMapper.StudentsMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlService implements InitializingBean {

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"})
    @Autowired
    private StudentsMapper sqlMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(sqlMapper.getAllStudents());
    }
}
