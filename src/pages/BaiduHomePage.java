package pages;

import org.openqa.selenium.By;
import util.BaseFunction;

public class BaiduHomePage extends BaseFunction{
	//����	
	public By tieba = By.name("tj_trtieba");

	/**
	* @description �������
	* 
	*/
	public void clickTieba() {
		click(tieba); 
		waitForElementPresent(10, new BaiduTiebaPage().searchButton);
	}
}
