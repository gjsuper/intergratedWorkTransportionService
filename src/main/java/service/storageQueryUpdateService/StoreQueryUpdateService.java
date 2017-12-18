package service.storageQueryUpdateService;

import domin.ConstantField;
import domin.KafkaDataStruct;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StoreQueryUpdateService {

    @Resource
    private StorageService storageService;

    @Resource
    private QueryService queryService;

    @Resource
    private UpdateService updateService;

    public void dealStoreQueryUpdate(KafkaDataStruct kafkaDataStruct) {

        byte[] data = kafkaDataStruct.getData();

        if(data[1] == ConstantField.STORAGE_REQUEST) {
            storageService.dealStorageSignal(kafkaDataStruct);
        } else if(data[1] == ConstantField.QUERY_REQUEST) {
            queryService.dealQuerySignal(kafkaDataStruct);
        } else if(data[1] == ConstantField.UPDATE_REQUEST) {
            updateService.dealUpdateSignal(kafkaDataStruct);
        }
    }

}
