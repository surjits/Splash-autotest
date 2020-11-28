package com.splashbi.pageelement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;

public enum DomainPageElement implements InitPageElement {
	SHOW_ALL_DOMAINS(getValueFromPropFile("show_all_domains")),
	CREATE_DOMAIN(getValueFromPropFile("createdomain")),
	DOMAIN_NAME(getValueFromPropFile("domain_name")),
	BUSINESS_APP_LIST(getValueFromPropFile("business_application_list")),
	LIST_OF_BUSINESSAPPS(getValueFromPropFile("list_of_businessapps")),
	//BUSINESS_APP_NAME(getValueFromPropFile("business_application_name")),
	CREATE_FOLDER_CHECKBOX(getValueFromPropFile("create_folder_checkbox")),
	CREATE_FOLDER(getValueFromPropFile("create_folder")),
	FOLDER_NAME(getValueFromPropFile("folder_name")),
	DB_CONNECTOR_LIST(getValueFromPropFile("dbconnector_list_dropdown")),
	CONNECTOR_LIST(getValueFromPropFile("connector_list")),
	//DB_CONNECTOR_NAME(getValueFromPropFile("dbconnector_name")),
	SAVE_BUTTON(getValueFromPropFile("save_button")),
	DOMAIN_HEADER(getValueFromPropFile("domain_name_header")),
	TABLE_SEARCH_BOX(getValueFromPropFile("table_searchbox")),
	TABLE_LIST(getValueFromPropFile("table_list")),
	VIEWS_CHECKBOX(getValueFromPropFile("views_checkbox")),

	TABLE_SEARCH_ICON(getValueFromPropFile("table_search_icon")),
	TABLE_DROP_LOC(getValueFromPropFile("table_drop_loc")),
	FILTER_DROP_LOC(getValueFromPropFile("table_drop_loc")),
	SAVE_FILTER(getValueFromPropFile("save_filter")),
	SEARCH_DOMAIN(getValueFromPropFile("search_domain")),
	LOAD_JOINS(getValueFromPropFile("load_joins")),
	MASTER_COL_SEARCH_BUTTON(getValueFromPropFile("master_col_search_button")),
	CHILD_COL_SEARCH_BUTTON(getValueFromPropFile("child_col_search_button")),
	REVERSE_JOIN(getValueFromPropFile("reverse_join")),
	SAVE_JOIN(getValueFromPropFile("save_join")),
	SAVE_TABLE(getValueFromPropFile("save_table")),

	BACK_TO_TABLE_ICON(getValueFromPropFile("back_to_table_icon")),
	NO_DATA_IN_SUGGESTED_JOIN(getValueFromPropFile("no_data_in_suggested_join")),
	MASS_EDIT_ICON(getValueFromPropFile("mass_edit_icon")),
	SHARE_ICON(getValueFromPropFile("share_icon")),
	VERIFY_SHARE_DOMAIN_WINDOW(getValueFromPropFile("verify_sharedomain_page")),
	SEARCH_AVAILABLE_USERS_TO_SHARE(getValueFromPropFile("search_available_users_to_share")),
	MOVE_RIGHT_ICON(getValueFromPropFile("move_right_icon")),
	EDIT_ENABLE(getValueFromPropFile("edit_enable")),
	SAVE_SHARE(getValueFromPropFile("save_share")),
	MORE_ICON_IN_DOMAIN_WINDOW(getValueFromPropFile("save_share")),
	INFO_DOMAIN(getValueFromPropFile("info_domain")),
	DOMAIN_SHARE_TAB(getValueFromPropFile("domain_share_tab")),
	USERS_EXPAND_ARROW(getValueFromPropFile("users_exapnd_arrow")),
	//Domain LOV Edit elements
	
	DOMAIN_LOV_BUTTON(getValueFromPropFile("domain_lov_button")),
	CREATE_LOV(getValueFromPropFile("create_lov")),
	LOV_NAME_FIELD(getValueFromPropFile("lov_name_field")),
	LOV_TYPE_DROPDOWN(getValueFromPropFile("lov_type_dropdown")),
	LOV_QUERY_BOX(getValueFromPropFile("lov_query_box")),
	LOV_VALIDATE_SQL(getValueFromPropFile("lov_validate_sql")),
	SAVE_AND_NEXT(getValueFromPropFile("save_and_next")),
	SAVE_LOV_BUTTON(getValueFromPropFile("save_lov_button")),
    END(getValueFromPropFile("submit"));
	
	private String loc;
	public String expression;
	WebElement e;
	DomainPageElement(String val){
    	
    	loc = val;
    	
    }
    
    public static String getValueFromPropFile(String key) {
    	String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"DomainPage.properties",key);
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

