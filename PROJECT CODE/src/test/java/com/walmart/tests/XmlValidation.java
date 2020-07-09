package com.walmart.tests;

import io.restassured.internal.path.xml.NodeChildrenImpl;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.XmlPath.*;

public class XmlValidation {

    /*
    Resource URL:
    http://api.walmartlabs.com/v1/search?query=cookies&format=xml&apiKey=75e3u4sgb2khg673cbv2gjup
     */

    ValidatableResponse validatableResponse;
    String xmlResponse;

    @BeforeClass
    public void init(){
        baseURI = "http://api.walmartlabs.com";
        basePath = "/v1";

        // Setup query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("query", "cookies");
        queryParams.put("format", "xml");
        queryParams.put("apiKey", "75e3u4sgb2khg673cbv2gjup");

        // Setup validatableResponse
        validatableResponse = given().queryParams(queryParams).when().get("/search").then();

        // Setup xmlResponse
        xmlResponse = given().queryParams(queryParams).when().get("/search").asString();
    }

    /*
    Get numItems
     */
    @Test
    public void getNumItems(){
        int numItems = validatableResponse.extract().path("searchresponse.numItems");
        System.out.println(numItems);
    }
    /*
    Get name of first item
     */
    @Test
    public void getNameOfFirstItem(){
        String name = validatableResponse.extract().path("searchresponse.items.item[0].name");
        System.out.println(name);
    }
    /*
    Get the gift options for the first product (extract output as one string)
     */
    @Test
    public void getGiftOptionsOfFirstItem(){
        // To extract output as one string
        String result = with(xmlResponse).getString("searchresponse.items.item[0].giftOptions");
        System.out.println(result); // empty [not error - data is blank]
    }
    /*
    Print the total size of items[]
     */
    @Test
    public void getSize(){
//        int size = validatableResponse.extract().path("searchresponse.items.size()"); // 1 incorrect
//        System.out.println(size);

        // NOTE: Use NodeChildrenImpl object's size() method to get size.
        NodeChildrenImpl childrenObj = validatableResponse.extract().path("searchresponse.items.item"); // 10
    }
    /*
    Get all the names in items[] (use with() & its getList() methods)
     */
    @Test
    public void getNameList(){
        List<String> nameList = with(xmlResponse).getList("searchresponse.items.item.name");
        System.out.println(nameList);
    }
    /*
    Get sale price of an item
     */
    @Test
    public void getSalePriceOfAnItem(){
        List<String> salePrice = with(xmlResponse).getList("searchresponse.items.item.find{it.itemId=='50259424'}" +
                ".salePrice");
        System.out.println(salePrice); //[6.98]
    }
    /*
    Deep search in XML path
    Search anywhere items with name OREO & get the sale price
     */
    @Test
    public void getSalePriceOfAllItemsWithName(){
        List<String> salePrice = with(xmlResponse).getList("**.findAll{it.name==~/OREO.*/}" +
                ".salePrice");
        System.out.println(salePrice); //[3.56, 3.56]
    }

}
