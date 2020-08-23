package com.splashbi.pageelement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;

public enum ReportPageElement implements InitPageElement{
	
	CREATE_DROPDOWN(getValueFromPropFile("create_dropdown_button")),
	CREATE_REPORT(getValueFromPropFile("create_report_button")),
	CREATE_REPORT_SET(getValueFromPropFile("create_reportset_button")),
	DOMAIN_SEARCH_FIELD(getValueFromPropFile("domain_search_field")),
	MOVE_TO_RIGHT(getValueFromPropFile("move_to_right")),
	ENTER_REPORT_NAME(getValueFromPropFile("input_report_name")),
	SAVE_REPORT(getValueFromPropFile("save_report")),
	SAVE_REPORT_NAME(getValueFromPropFile("save_reportname")),
	VERIFY_REPORT_CREATED(getValueFromPropFile("verify_report_creation")),
	REPORT_FILTER_CRITERIA(getValueFromPropFile("report_filter_criteria")),
	ADD_COL_PROPERTY(getValueFromPropFile("add_column_property")),
	DROP_PLACE(getValueFromPropFile("drop_place")),
	SAVE_REPORT_FILTER(getValueFromPropFile("save_report_filter")),
	RUN_REPORT(getValueFromPropFile("run_report")),
	OPTION_MORE(getValueFromPropFile("more_option")),
    SUBMIT_REPORT(getValueFromPropFile("submit")),
	SUBMIT_SUCCESS(getValueFromPropFile("verify_success")),
	SQL_SELECT_CONTENT(getValueFromPropFile("sql_select_content")),
	VIEW_SQL(getValueFromPropFile("view_sql"));
	
	private String loc;
	public String expression;
	WebElement e;
	ReportPageElement(String val){
    	
    	loc = val;
    	
    }
    
    public static String getValueFromPropFile(String key) {
    	String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"ReportPage.properties",key);
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

