package com.splashbi.pageelement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.splashbi.pageobject.DomainPage;
import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;

public enum DynamicPageElement implements InitPageElement {
//****************Domain Page***********************************
	TABLE_TO_DRAG(getValueFromPropFile("table_to_drag")),
	DOMAIN_TABLE(getValueFromPropFile("domain_table")),
    DOMAIN_FILTER_TAB(getValueFromPropFile("domain_filter_tab")),
    DOMAIN_NAME_BUTTON(getValueFromPropFile("domain_name_button")),
    BUSINESS_APP_NAME(getValueFromPropFile("business_app")),
    DB_CONNECTOR(getValueFromPropFile("db_connector")),
    FILTER_TAB(getValueFromPropFile("filter_tab")),
    JOIN_TAB(getValueFromPropFile("join_tab")),
    SGGESTED_JOINS(getValueFromPropFile("suggested_joins")),
    TABLE_JOIN_SAVE(getValueFromPropFile("table_join_save")),
    CREATE_JOIN(getValueFromPropFile("table_join_save")),
    SELECT_ALL_JOIN_TABLE(getValueFromPropFile("table_join_save")),
    MASTER_COL_DATA(getValueFromPropFile("master_column_data")),
    ADD_FILTER(getValueFromPropFile("add_filter")),
    FILTER_NAME_TO_DRAG(getValueFromPropFile("filter_name_to_drag")),
    FILTER_NAME(getValueFromPropFile("filter_name")),
    FREE_TEXT_FOR_FILTER(getValueFromPropFile("freetext_for_filter_button")),
    INPUT_FREE_TEXT_AREA(getValueFromPropFile("free_text_area")),
    DOMAIN_EDIT(getValueFromPropFile("domain_edit")),
    TABLE_EDIT(getValueFromPropFile("domain_table_edit")),
    CLICK_DOMAIN(getValueFromPropFile("click_domain")),
    EDIT_DOMAIN(getValueFromPropFile("domain_edit")),
    EDIT_DOMAIN_TABLE(getValueFromPropFile("domain_table_edit")),
	LOV_TYPE_VALUE(getValueFromPropFile("lov_type")),
	VERIFY_LOV(getValueFromPropFile("verify_lov")),

//*********************************REPORT PAGE****************************************
    DOMAIN_NAME_IN_REPORT(getValueFromPropFile("click_domain_in_report")),
    EXPAND_DOMAIN_TABLE(getValueFromPropFile("expand_domain_table")),
    SELECT_ALL_CHECKBOX(getValueFromPropFile("selectall_checkbox")),
    SELECT_PROPERTY_COLUMN(getValueFromPropFile("select_property_column")),
    DRAG_COLUMN(getValueFromPropFile("drag_column")),
    VERIFY_REPORT_CREATION(getValueFromPropFile("verify_report_creation")),
	EXPAND(getValueFromPropFile("expandLink"));
	
	private String loc;
	public String expression;
	WebElement e;
	static Logger logger = Logger.getLogger(DynamicPageElement.class);
	DynamicPageElement(String val){
    	
    	loc = val;
    	
    }
    
    public static String getValueFromPropFile(String key) {
    	String val=null;
    	try {
    		val = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"DynamicElement.properties",key);
    	}catch(Exception e) {
    		logger.error("Didn't find any key/value for given key", e);
    	}
    	return val;
       
    }
	 @Override
	    public By getBy() {
			return ElementLocator.getLocator(loc);
		}
	 @Override
	 public By getDynamicBy(String val) {
		 return ElementLocator.getDynamicLocator(loc, val);
	 }

}
