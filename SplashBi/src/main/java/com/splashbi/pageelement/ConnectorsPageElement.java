package com.splashbi.pageelement;

import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public enum ConnectorsPageElement implements InitPageElement{

    ORACLE_EBS(getValueFromPropFile("oracle_EBS")),
    ORACLE_EBS_INFO(getValueFromPropFile("oracle_ebs_info")),
    TEST_CONNECTOR(getValueFromPropFile("test_connector")),
    CONNECTOR_VALIDATE(getValueFromPropFile("connector_success_popup")),
    END(getValueFromPropFile("end"));
    private String loc;
    public String expression;
    WebElement e;
    ConnectorsPageElement(String val){

        loc = val;

    }
    public static String getValueFromPropFile(String key) {
        String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"ConnectorsPage.properties",key);
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
