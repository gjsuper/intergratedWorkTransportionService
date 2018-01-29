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
public class ArmyInfoPanel extends DisplayDepartmentItemUI implements InitializingBean{

	@Resource
	private CacheService cacheService;

	@Override
	public void afterPropertiesSet() throws Exception {
		init(SharedInfo.ARMY_DEPARTMENT, cacheService.getArmyId("陆军"), "陆军部门信息");
	}
}
