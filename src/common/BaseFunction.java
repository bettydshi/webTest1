package common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * The common methods.
 * 
 * @author betty.shi
 * 
 */
public class BaseFunction {

	public static WebDriver driver;
	
	/**
	 * �������ļ���ȡֵ
	 * @return
	 * @throws IOException
	 * @author betty.shi
	 */
	public static String getProperty(String key, String filePath) throws IOException {
		Properties p = new Properties();
		InputStream ips = new FileInputStream(filePath);
		BufferedReader bf = new BufferedReader(new  InputStreamReader(ips,"UTF-8"));//�����ȡproperties�ļ��в����������������
		p.load(bf);
		return p.getProperty(key);
		
	}

	/**
	 * ��ʼ��chromedriver
	 * @throws IOException
	 * @author betty.shi
	 */
	public static void initialize() throws IOException {
		String chDriver = getProperty("driver.path.chrome", ".\\resource\\env.properties");
		String chrome = getProperty("chrome.path", ".\\resource\\env.properties");
		System.setProperty("webdriver.chrome.driver", chDriver);
		System.setProperty("webdriver.chrome.bin", chrome);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	/**
	 * open url
	 * @author betty.shi
	 */
	public static void openAnURL(String url) {
		driver.get(url);
	}

	/**
	 * ���һ��Ԫ��
	 * @author betty.shi
	 */
	public static void click(WebElement element) {
		element.click();
	}

	/**
	 * ���һ��Ԫ��
	 * @author betty.shi
	 */
	public static void click(By locator) {
		driver.findElement(locator).click();
	}

	/**
	 * �����������ֵ
	 * @author betty.shi
	 */
	public static void setText(WebElement webElement, String text) {
		webElement.clear();
		webElement.sendKeys(text);
	}

	/**
	 * �����������ֵ
	 * @author betty.shi
	 */
	public static void setText(By locator, String text) {
		driver.findElement(locator).sendKeys(text);
	}

	/**
	 * ��һ��Ԫ����ѡȡֵΪxxx��Ԫ��
	 * @author betty.shi
	 */
	public static WebElement findElementByText(By locator, String text) {
		List<WebElement> elements = driver.findElements(locator);
		WebElement element = null;
		for (WebElement e : elements) {
			if (e.getText().equals(text)) {
				element = e;
			}
		}
		return element;

	}

	/**
	 * ��һ��Ԫ����ѡȡֵ����xxx��Ԫ��
	 * @author betty.shi
	 */
	public static WebElement findElementByContainsText(By locator, String text) {
		List<WebElement> elements = driver.findElements(locator);
		WebElement element = null;
		for (WebElement e : elements) {
			if (e.getText().contains(text)) {
				element = e;
			}
		}
		return element;

	}
	
	/**
	 * �ȴ�ֱ��ĳԪ�صĳ���
	 * @author betty.shi
	 */
	public static boolean waitForElementPresent(int timeout, By locator) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOfElementLocated(locator));		
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * �ȴ�ֱ��ĳԪ�صĳ���
	 * @author betty.shi
	 */
	public static boolean waitForElementPresent(int timeout, WebElement element) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOf(element));
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * �ȴ�ֱ������ĳ�ı���Ԫ�صĳ���
	 * @author betty.shi
	 */
	public static boolean waitForElementContainsTextPresent(int timeout, By locator,String text) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOf(findElementByContainsText(locator, text)));		
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * �ȴ�ĳ��λ�õ�Ԫ�ص��ı�Ϊxxx
	 * @author betty.shi
	 */
	public static boolean waitForTextToBePresentInElementLocated(int timeout, By locator,String text) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));		
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * �ȴ�ֱ��ĳԪ����ʧ
	 * @author betty.shi
	 */
	public static boolean waitForElementInvisibility(int timeout, By locator) {
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * �ȴ�ֱ��ĳԪ�ؿ��Ե��
	 * @author betty.shi
	 */
	public static boolean waitForElementClickable(int timeout, By locator) {
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * ֱ�ӵȴ�
	 * @author betty.shi
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Log4jUtil.error(e);
		}

	}	
	
	public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
	
	/**
	 * ��������ֻ���
	 * @author betty.shi
	 */
    public static String getTel() {
    	String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    
    /**
     * ���¼��̻س���
     * @author betty.shi
     */
    public static void pressEnterKey(WebElement element) {
    	element.sendKeys(Keys.ENTER);
    }
    
    /**
     * �ж�Ԫ���Ƿ����
     * @author betty.shi
     */
    public static boolean doesWebElementExist(By locator) {
    	try 
    	{ 
    		driver.findElement(locator); 
    		return true; 
            } 
    	catch (Exception e) { 
    		return false; 
            } 
    } 
    
    /**
     * ����������ֵ
     * @author betty.shi
     */
    public static int findElementByTextReturnIndex(By locator, String text) {
    	List<WebElement> elements = driver.findElements(locator);
		int index = 0 ;
		for (int i=0; i<elements.size(); i++) {
			if (elements.get(i).getText().contains(text)) {
				 index = i;
			}
		}
		return index;
    }
    
    /**
     * ��ȡString���͵�����
     */
    public static String getCurentDateString() {
    	//��ȡ��ǰ����
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	//��ȡString���͵�����
    	String currentDateString=dateFormat.format(date); 
    	return currentDateString;
    }
    
    /**
     * ��ȡString���͵�ʱ��
     */
    public static String getCurentTimeString() {
    	//��ȡ��ǰʱ��
    	Date date = new Date();
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    	//��ȡString���͵�ʱ��
    	String currentTimeString = timeFormat.format(date);
    	return currentTimeString;
    }
    
    /**
     * ��ȡString���͵�����ʱ��
     */
    public static String getCurentDateTimeString() {
    	//��ȡ��ǰʱ��
    	Date date = new Date();
    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    	//��ȡString���͵�ʱ��
    	String dateTimeString = dateTimeFormat.format(date);
    	return dateTimeString;
    }
    
    /**
     * ���ڼ�n����
     * @throws ParseException 
     */
    public static String getDatePlusMonths(String date,int n) throws ParseException {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date dt=dateFormat.parse(date);
    	Calendar rightNow=Calendar.getInstance();
    	rightNow.setTime(dt);
    	rightNow.add(Calendar.MONTH, n);
    	Date dateTime=rightNow.getTime();
    	String datePlusMonthsString = dateFormat.format(dateTime);
    	return datePlusMonthsString;
    }
    
}
