package com.splashbi.pageelement.admin;

import com.splashbi.pageelement.ElementLocator;
import com.splashbi.pageelement.InitPageElement;
import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public enum ERPMappingPageElement implements InitPageElement {
    ERP_MAPPING_HOME(getValueFromPropFile("erp_mapping_home")),
    ORACLE_EBUSINESS_SUIT(getValueFromPropFile("oracle_ebusiness_suit")),
    ORACLE_EBUSINESS_SUIT_HOME(getValueFromPropFile("oracle_ebusiness_suit_home")),
    SEARCH_USER_MAPPING(getValueFromPropFile("search_user_mapping")),
    USER_MAPPING_SEARCHED_LIST(getValueFromPropFile("searched_user_mapping")),
    CREATE_ERP_MAPPING(getValueFromPropFile("create_erp_mapping")),
    CREATE_ERP_MAPPING_HOME(getValueFromPropFile("create_erp_mapping_home")),
    SPLASHBI_EMP_NAME_LIST(getValueFromPropFile("splashbi_emp_name_list")),
    AUTHENTICATION_METHOD_LIST(getValueFromPropFile("authentication_method_list")),
    EBS_CONNECTION_LIST(getValueFromPropFile("ebs_connection_list")),
    SEARCH_EBS_USER(getValueFromPropFile("search_ebs_user")),
    SELECT_USER_MAPPING(getValueFromPropFile("select_user_mapping")),
    SEARCH_AND_SELECT_EBS_USER_WINDOW(getValueFromPropFile("serach_and_select_ebs_user_window")),
    EBS_USER_LIST(getValueFromPropFile("ebs_user_list")),
    SAVE_ERP_MAPPING(getValueFromPropFile("save_erp_mapping")),
    DELETE_USER_MAPPING(getValueFromPropFile("delete_usermapping")),
    ROLES_RESPONSIBILITY_TAB(getValueFromPropFile("roles_responsibility_tab")),
    IMPORT_RESPONSIBILITIES_WINDOW(getValueFromPropFile("import_responsibilities_window")),
    IMPORT_EBS_RESPONSIBILITIES(getValueFromPropFile("import_ebs_responsibilities")),
    IMPORT_EBS_CONNECTION_LIST(getValueFromPropFile("import_ebs_connection_list")),
    ADD_USER_TO_RESONSIBILITY_CHECKBOX(getValueFromPropFile("add_user_to_responsibility_checkbox")),
    SEARCH_RESPONSIBILITY(getValueFromPropFile("search_responsibility")),
    FIRST_AVAILABLE_ROLE_RESPONSIBILITY_TO_IMPORT(getValueFromPropFile("first_available_role_responsibility_to_import")),
    FIRST_AVAILABLE_ROLE_RESPONSIBILITY_TO_ADD(getValueFromPropFile("first_available_responsibility_to_add")),
    MOVE_TO_RIGHT_ARROW(getValueFromPropFile("move_to_right_arrow")),
    RIGHT_ARROW_TO_MOVE(getValueFromPropFile("right_arrow_to_move")),
    VERIFY_FIRSTROW_IN_SELECTED_ROLE(getValueFromPropFile("first_row_in_selected_role_to_import")),
    VERIFY_FIRSTROW_IN_SELECTED_ROLE_To_ADD(getValueFromPropFile("first_row_in_selected_role_to_add")),
    SAVE_IMPORT_RESPONSIBILITY(getValueFromPropFile("save_import_responsibility")),
    SAVE_USER_RESPONSIBILITY(getValueFromPropFile("save_user_responsibility")),
    SEARCH_USER_IN_ROLE_RESPONSIBILITY_TAB(getValueFromPropFile("search_user_in_role_responsibility_tab")),
    ADD_RESPONSIBILITY_ACTION(getValueFromPropFile("add_responsibility_action")),
    ROLES_RESPONSIBILITY_WINDOW(getValueFromPropFile("roles_responsibility_window")),
    AVAILABLE_ROLE_RESPONSIBILITY(getValueFromPropFile("available_role_responsibility")),
    WARNING_POPUP(getValueFromPropFile("warning_popup"));

    private String loc;
    public String expression;
    WebElement e;
    ERPMappingPageElement(String val){

        loc = val;

    }
    public static String getValueFromPropFile(String key) {
        String value = Utility.getValueFromPropertyFile(Constant.OR_PATH+"/"+"ERPMappingPage.properties",key);
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
