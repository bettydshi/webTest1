package util;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
	public static String getProperty(String key, String filePath) {
		Properties p = new Properties();
		try {		
     		InputStream ips = new FileInputStream(filePath);
			BufferedReader bf = new BufferedReader(new  InputStreamReader(ips,"UTF-8"));//�����ȡproperties�ļ��в����������������
			p.load(bf);
		}catch (Exception e) {
			Log4jUtil.error(e);
		}		
		return p.getProperty(key);		
	}
	
	/**
	 * ֱ�ӵȴ�
	 * @author betty.shi
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ���һ��Ԫ��
	 * @author betty.shi
	 */
	public static void click(WebElement element) {
		element.click();
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
	 * ��ʼ��chromedriver
	 * @throws IOException
	 * @author betty.shi
	 */
	public static void initialize() {
		String chDriver = getProperty("driver.path.chrome", ".\\resource\\env.properties");
		String chrome = getProperty("chrome.path", ".\\resource\\env.properties");
		System.setProperty("webdriver.chrome.driver", chDriver);
		System.setProperty("webdriver.chrome.bin", chrome);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
		
	/**
	 * �˳�browser
	 * @author betty.shi
	 */
	public static void quitBrowser() {
		driver.quit();
	}

	/**
	 * open url
	 * @author betty.shi
	 */
	public static void openAnURL(String url) {
		try {
			driver.get(url);
		} catch(Exception e) {
			Log4jUtil.error(e);
		}		
	}

	/**
	 * get element
	 * @author betty.shi
	 */
	public static WebElement getElement(By locator) {
		return driver.findElement(locator);
	}
	
	/**
	 * get elements
	 * @author betty.shi
	 */
	public static List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	/**
	 * ���һ��Ԫ��
	 * @author betty.shi
	 */
	public static void click(By locator) {
		getElement(locator).click();
	}

	/**
	 * �����������ֵ
	 * @author betty.shi
	 */
	public static void setText(By locator, String text) {
		getElement(locator).clear();
		getElement(locator).sendKeys(text);
	}
	
	/**
	 * ����������ϴ��ļ�
	 * @author betty.shi
	 * @parameter location ѡ����,file �ļ�·��
	 */
	public static void setFile(By locator, String pathName) {
		String absolutePath=getAbsolutePath(new File(pathName));
		driver.findElement(locator).sendKeys(absolutePath);
	}
	
	/**
	 * ��һ��Ԫ����ѡȡֵΪxxx��Ԫ��
	 * @param locatorΪ��һ��Ԫ�صĶ�λ
	 * @author betty.shi
	 */
	public static WebElement findElementByText(By locator, String text) {
		List<WebElement> elements = getElements(locator);
		WebElement element = null;
		for (WebElement e : elements) {
			if (e.getText().equals(text)) {
				element = e;
			}
		}
		return element;

	}

    /**
     * ��һ��Ԫ����ѡȡֵΪxxx��Ԫ�ز���������ֵ
     * @author betty.shi
     */
    public static int findElementByTextReturnIndex(By locator, String text) {
    	List<WebElement> elements = getElements(locator);
		int index = 0 ;
		for (int i=0; i<elements.size(); i++) {
			if (elements.get(i).getText().contains(text)) {
				 index = i;
			}
		}
		return index;
    }
    
	/**
	 * ��һ��Ԫ����ѡȡֵ����xxx��Ԫ��
	 * @param locatorΪ��һ��Ԫ�صĶ�λ
	 * @author betty.shi
	 */
	public static WebElement findElementByContainsText(By locator, String text) {
		List<WebElement> elements = getElements(locator);
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
     * ���¼��̻س���
     * @author betty.shi
     */
    public static void pressEnterKey(By locator) {
    	getElement(locator).sendKeys(Keys.ENTER);
    }
    
    /**
     * �ж�Ԫ���Ƿ����
     * @author betty.shi
     */
    public static boolean doesWebElementExist(By locator) {
    	try 
    	{ 
    		getElement(locator); 
    		return true; 
            } 
    	catch (Exception e) { 
    		return false; 
            } 
    } 
    
    /**
     * ���ڼ�n����
     * @author sara.zhou
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
    
	/**
     * ��ȡString���͵�����
     * @author sara.zhou
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
      * @author sara.zhou
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
      * @author sara.zhou
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
     * ����Ƶ�ĳԪ����
     * @author aimee.yang
     */ 
    
    public static void mouseOver(By locator) {   	
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locator)).perform();		
	}
    
    /**
     * �������·����þ���·��
     * @author aimee.yang
     */ 
    
    public static String getAbsolutePath(File file) {  
		 String fileAbsolutePath = file.getAbsolutePath();
		 return fileAbsolutePath;
    }
    
    /**
     * @description ˢ��ҳ��
     * @author aimee.yang
     */ 
    
    public static void refreshPage() {  
    	driver.navigate().refresh();
    }
}
