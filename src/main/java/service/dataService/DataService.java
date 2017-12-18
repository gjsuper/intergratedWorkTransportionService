package service.dataService;

import domin.ConstantField;
import domin.KafkaDataStruct;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataService {

    @Resource
    private ShortMessageService shortMessageService;

    @Resource
    private FileService fileService;

    @Resource
    private VideoService videoService;

    @Resource
    private RealTimeDataService realTimeDataService;


    public void dealData(KafkaDataStruct kafkaDataStruct) {

        byte[] data = kafkaDataStruct.getData();

        if(data[1] == ConstantField.VIDEO) {
            videoService.dealVideoData(kafkaDataStruct);
        } else if(data[1] == ConstantField.SHORT_MESSAGE) {
            shortMessageService.dealShortMessage(kafkaDataStruct);
        } else if(data[1] == ConstantField.REAL_TIME_DATA) {
            realTimeDataService.dealRealTimeData(kafkaDataStruct);
        } else if(data[1] == ConstantField.FILE) {
            fileService.dealFileData(kafkaDataStruct);
        }
    }
}
