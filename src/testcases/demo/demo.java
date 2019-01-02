package testcases.demo;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import actions.module1.BaiduHomePage;
import common.BaseTestCase;
import elements.module2.BaiduTiebaPageElements;


public class demo extends BaseTestCase{
	
	/**
	 * @TestCase  �򿪰ٶ���ҳ��������
	 */	
	@Test
	public void openTieba() {
		testName="�򿪰ٶ���ҳ��������";
		new BaiduHomePage().clickTieba();
		//����
		assertTrue(new BaiduTiebaPageElements().getSearchButton().isDisplayed());
		
	}
}
