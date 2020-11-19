package com.splashbi.pageelement;

import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public enum DashboardPageElement implements InitPageElement {
    VERIFY_DASHBOARD_HOME(getValueFromPropFile("verify_dashboard_home")),
    CREATE_DROPDOWN(getValueFromPropFile("create_dropdown_button")),
    CREATE_CHART(getValueFromPropFile("create_chart")),
    VERIFY_CREATE_VIEW(getValueFromPropFile("create_view")),
    DOMAIN_SEARCH(getValueFromPropFile("domain_search_field")),
    VERIFY_NEWCHART_TAB(getValueFromPropFile("verify_new_chart_tab")),
    VERIFY_UNTITLED_CHART_TAB(getValueFromPropFile("verify_untitle_chart")),
    TARGET_DROP_LOC_IN_DIMENSION(getValueFromPropFile("target_area_in_dimension")),
    TARGET_DROP_LOC_IN_MEASURES(getValueFromPropFile("target_area_in_measures")),
    SAVE_UNTITLE_CHART(getValueFromPropFile("save_untitle_chart")),
    ENTER_CHART_NAME(getValueFromPropFile("enter_chart_name")),
    SAVE_TITLED_CHART(getValueFromPropFile("save_titled_chart")),
    EXPAND(getValueFromPropFile("expandLink"));

    private String loc;
    public String expression;
    WebElement e;
    DashboardPageElement(String val){

        loc = val;

    }

    public static String getValueFromPropFile(String key) {
        String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"DashboardPage.properties",key);
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
