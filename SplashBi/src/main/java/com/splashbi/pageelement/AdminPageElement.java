package com.splashbi.pageelement;

import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public enum AdminPageElement implements InitPageElement {
    CONNECTORS(getValueFromPropFile("connectors")),
    ERP_MAPPING(getValueFromPropFile("erp_mapping")),
    END(getValueFromPropFile("submit"));
    private String loc;
    public String expression;
    WebElement e;
    AdminPageElement(String val){

        loc = val;

    }
    public static String getValueFromPropFile(String key) {
        String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"AdminPage.properties",key);
        return value;
    }
    @Override
    public By getBy() {
        return ElementLocator.getLocator(loc);
    }

    @Override
    public org.openqa.selenium.By getDynamicBy(String val) {
        return null;

    }
}
