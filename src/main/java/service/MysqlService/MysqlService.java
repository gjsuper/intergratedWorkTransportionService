package service.MysqlService;

import sqlMapper.StudentsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MysqlService {

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"})
    @Resource
    private StudentsMapper sqlMapper;
}
