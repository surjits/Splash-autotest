package com.splashbi.pageelement;

import com.splashbi.utility.Constant;
import com.splashbi.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public enum ConnectorsPageElement implements InitPageElement{
    CONNECTOR_HOME(getValueFromPropFile("connectors_home")),
    SEARCH_CONNECTOR(getValueFromPropFile("search_connector")),
    ORACLE_EBS(getValueFromPropFile("oracle_EBS")),
    CREATE_CONNECTORS(getValueFromPropFile("create_connector")),
    DB_CONNECTORS_HEADER(getValueFromPropFile("db_connectors_header")),
    VERIFY_CREATE_CONNECTOR(getValueFromPropFile("verify_create_connector")),
    ORACLE_EBS_INFO(getValueFromPropFile("oracle_ebs_info")),
    TEST_CONNECTOR(getValueFromPropFile("test_connector")),
    CONNECTOR_VALIDATE(getValueFromPropFile("connector_success_popup")),

    /*********************Buttons in Create Connector*******************************/
    CONNECTOR_NAME(getValueFromPropFile("connector_name")),
    SYSTEM_TYPE_LIST(getValueFromPropFile("system_list")),
    CONNECTION_TYPE_LIST(getValueFromPropFile("connection_type_list")),
    HOST_NAME(getValueFromPropFile("host_name")),
    SID(getValueFromPropFile("sid")),
    CONNECTION_USER(getValueFromPropFile("connection_user")),
    CONNECTION_PASSWORD(getValueFromPropFile("connection_password")),
    PORT_NUMBER(getValueFromPropFile("port_number")),
    SCHEMA_NAME(getValueFromPropFile("schema_name")),

    CREATE_CONNECTOR_TEST(getValueFromPropFile("db_create_test_connector")),
    CREATE_CONNECTOR_SAVE(getValueFromPropFile("db_create_save_connector")),
    CONNECTOR_SAVE_SUCCESS_POPUP(getValueFromPropFile("create_connector_success_popup")),

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
