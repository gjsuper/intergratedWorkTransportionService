package UI;


import ConstField.SharedInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import service.dataFetchService.CacheService;

import javax.annotation.Resource;

/**
 * ��ʾ���в���
 * @author Administrator
 *
 */

@Service
public class AirForceInfoPanel extends DisplayDepartmentItemUI implements InitializingBean{

	@Resource
	private CacheService cacheService;

	@Override
	public void afterPropertiesSet() throws Exception {
		init(SharedInfo.AIR_FORCE_DEPARTMENT, cacheService.getArmyId("空军"), "空军部门信息");
	}
}
