package service.sqlService;

import mapper.SqlMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqlService implements InitializingBean {

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"})
    @Autowired
    private SqlMapper sqlMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(sqlMapper.getAllStudents());
    }
}
