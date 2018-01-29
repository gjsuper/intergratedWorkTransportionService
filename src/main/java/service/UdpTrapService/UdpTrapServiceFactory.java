package service.UdpTrapService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UdpTrapServiceFactory {
	@Resource
	private RegisterService registerService;
	@Resource
	private LoginService loginService;
	@Resource
	private SMService sMService;
	@Resource
	private SyncContactService syncContactService;
	@Resource
	private SyncDepartmentService syncDepartmentService;
	@Resource
	private LogoutService logoutService;
	@Resource
	private ChangePasswordService changePasswordService;
	@Resource
	private SyncSMService syncSMService;
	@Resource
	private FileService fileService;
	@Resource
	private CloseAccountService closeAccountService;
	
	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public SMService getProcessSM() {
		return sMService;
	}

	public void setProcessSM(SMService processSM) {
		this.sMService = processSM;
	}

	public SyncContactService getSyncContactService() {
		return syncContactService;
	}

	public void setSyncContactService(SyncContactService syncContactService) {
		this.syncContactService = syncContactService;
	}

	public SyncDepartmentService getSyncDepartmentService() {
		return syncDepartmentService;
	}

	public void setSyncDepartmentService(SyncDepartmentService syncDepartmentService) {
		this.syncDepartmentService = syncDepartmentService;
	}

	public LogoutService getLogoutService() {
		return logoutService;
	}

	public void setLogoutService(LogoutService logoutService) {
		this.logoutService = logoutService;
	}

	public ChangePasswordService getChangePasswordService() {
		return changePasswordService;
	}

	public void setChangePasswordService(ChangePasswordService changePasswordService) {
		this.changePasswordService = changePasswordService;
	}

	public SyncSMService getSyncSMService() {
		return syncSMService;
	}

	public void setSyncSMService(SyncSMService syncSMService) {
		this.syncSMService = syncSMService;
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public CloseAccountService getCloseAccountService() {
		return closeAccountService;
	}

	public void setCloseAccountService(CloseAccountService closeAccountService) {
		this.closeAccountService = closeAccountService;
	}
	
}
