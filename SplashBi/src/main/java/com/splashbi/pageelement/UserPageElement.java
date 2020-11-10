package com.splashbi.pageelement;

import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public enum UserPageElement implements InitPageElement {
    SEARCH_USER(getValueFromPropFile("search_user")),
    CREATE_USER(getValueFromPropFile("create_user")),
    USER_NAME(getValueFromPropFile("username")),
    FIRST_NAME(getValueFromPropFile("first_name")),
    LAST_NAME(getValueFromPropFile("last_name")),
    LETME_CREATE_PASSWORD(getValueFromPropFile("letme_create_password")),
    ENTER_PASSWORD(getValueFromPropFile("enter_password")),
    EMAIL(getValueFromPropFile("email")),
    SAVE_NEXT(getValueFromPropFile("save_next")),
    VALIDATE_SUCCESS(getValueFromPropFile("validate_success")),
    END(getValueFromPropFile("end"));
    private String loc;
    public String expression;
    WebElement e;
    UserPageElement(String val){

        loc = val;

    }
    public static String getValueFromPropFile(String key) {
        String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"UsersPage.properties",key);
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
