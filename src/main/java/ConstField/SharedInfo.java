package ConstField;

import UI.DepartmentItem;
import UI.UserItem;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SharedInfo {
	public static final int WIDTH = 850;
	public static final int HEIGHT = 600;
	
	public static final boolean PRINT = true;
	
//	public static Map<Integer, User> map = new HashMap<>();

	public static int MAX_ITEM_PER_PAGE = 8;
	
	public static Color GRAY = new Color(205,205,205);
	public static Color LIGHT_GRAY = new Color(225, 225, 225);
	public static Color GREEN = new Color(46, 204, 113);
	
	public static List<DepartmentItem> NAVY_DEPARTMENT = new ArrayList<>();
	public static List<DepartmentItem> ARMY_DEPARTMENT = new ArrayList<>();
	public static List<DepartmentItem> AIR_FORCE_DEPARTMENT = new ArrayList<>();
	
	public static volatile boolean ADD_DEPARTMENT = false;
	public static int PARENT_ID = 0;
	public static String DEPARTMENT_NAME = null;
	public static String DEPARTMENT_REMARK = null;
	
	public static ApplicationContext AC;
	
	public static List<UserItem> USR = new ArrayList<>();
}
