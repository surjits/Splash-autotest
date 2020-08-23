package com.splashbi.pageelement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;

public enum LoginPageElement implements InitPageElement {
	USER_NAME(getValueFromPropFile("user")),
	PASSWORD(getValueFromPropFile("password")),
	LOGIN(getValueFromPropFile("submit")),
	WARNING(getValueFromPropFile("warning"));
	
	private String loc;
	public String expression;
	WebElement e;
    LoginPageElement(String val){
    	
    	loc = val;
    	
    }
    
    public static String getValueFromPropFile(String key) {
    	String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"LoginPage.properties",key);
        return value;
    }

    @Override
    public By getBy() {
		return ElementLocator.getLocator(loc);
	}
    
    @Override
	 public By getDynamicBy(String val) {
        return null;
          
	 }

	

}
