package testcases.demo;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import pages.BaiduHomePage;
import pages.BaiduTiebaPage;
import util.BaseTestCase;


public class demo extends BaseTestCase{
	
	/**
	 * @TestCase  �򿪰ٶ���ҳ��������
	 */	
	@Test
	public void openTieba() {
		testName="�򿪰ٶ���ҳ��������";
		new BaiduHomePage().clickTieba();
		//����
		assertTrue(getElement(new BaiduTiebaPage().searchButton).isDisplayed());
		
	}
}
