package common;

import common.BaseFunction;
import listener.GenerateReport;
import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

/**
 * The common cases.
 * 
 * @author betty.shi
 * 
 */
public class BaseTestCase extends BaseFunction implements ITest{
	
	public String testName;
	
	/**
	 * 1.��ʼ�� 2. login 
	 * @throws IOException
	 * @author betty.shi
	 */
	@BeforeMethod
	public static void setUp() throws IOException {
        try {
		initialize();
		login(getProperty("login.account", ".\\resource\\env.properties"), getProperty("login.password", ".\\resource\\env.properties"));
        }
        catch(Exception e) {
        	Log4jUtil.error(e);
        }
	}

	/**
	 * ��¼
	 * @throws IOException
	 * @author betty.shi
	 */
	public static void login(String account, String password) throws IOException {
		openAnURL(getProperty("login.url", ".\\resource\\env.properties"));
		sleep(2000);
		//ʡ��login����
	}


	@AfterMethod
	public static void tearDown() throws InterruptedException {
		try {
		logout();
		quitBrowser();
		}
		catch(Exception e) {
			Log4jUtil.error(e);
		}
	}

	/**
	 * �ǳ�
	 * @author betty.shi
	 */
	public static void logout() {
		sleep(2000);
		//ʡ��logout����
	}

	/**
	 * �˳�browser
	 * @author betty.shi
	 */
	public static void quitBrowser() {
		driver.quit();
	}
	/**
	 * ʵ��ITest�ӿڣ���дgetTestName��������GenerateReport���У�����ִ��ʧ�ܻ���������ʱ��ӡtestName
	 * @author betty.shi
	 */
	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return testName;
	}
	
	@AfterSuite
	public static void sendEmail() {
		try {
			String userName = ""; // ����������  
	        String password = ""; // ����������  
	        String smtpHost = ""; // �ʼ�������    
  
	        String to = ""; // �ռ��ˣ�����ռ����԰�Ƕ��ŷָ�  
	        String cc = ""; // ���ͣ���������԰�Ƕ��ŷָ�  
	        String subject = ""; // ����
	        String body = GenerateReport.returnResult(); // ���ģ�������html��ʽ��Ӵ  
	        // ������·�����������Ҳ����  
	        List<String> ReportPath = Arrays.asList(GenerateReport.returnReportPath());
	        List<String> screenshotPath = GenerateReport.returnScreenshotPath();
	        List<String> attachments = new ArrayList<String>();
	        attachments.addAll(ReportPath);
	        attachments.addAll(screenshotPath);
	        
	        SendEmail email = SendEmail.entity(smtpHost, userName, password, to, cc, subject, body, attachments);  
	  
	        email.send(); // ���ͣ�     
		}
		catch(Exception e) {
			Log4jUtil.error(e);
		}
	}
	
}
