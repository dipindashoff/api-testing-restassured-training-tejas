package com.walmart.tests;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class JsonValidation {

    /*
    Resource URL:
    http://api.walmartlabs.com/v1/search?query=cookies&format=json&apiKey=75e3u4sgb2khg673cbv2gjup
     */

    ValidatableResponse validatableResponse;
    @BeforeClass
    public void init(){
        baseURI = "http://api.walmartlabs.com";
        basePath = "/v1";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("query", "cookies");
        queryParams.put("format", "json");
        queryParams.put("apiKey", "75e3u4sgb2khg673cbv2gjup");
        validatableResponse = given().queryParams(queryParams).when().get("/search").then();
    }

    /*
    Get numItems
     */
    @Test
    public void getNumItems(){
       int numItems = validatableResponse.extract().path("numItems");
        System.out.println(numItems);
    }
    /*
    Get name of first item
     */
    @Test
    public void getNameOfFirstItem(){
        String name = validatableResponse.extract().path("items[0].name");
        System.out.println(name);
    }
    /*
    Get the gift options for the first product
     */
    @Test
    public void getGiftOptionsOfFirstItem(){
        Map<String, ?> map = validatableResponse.extract().path("items[0].giftOptions");
        System.out.println(map);
    }
    /*
    Print the total size of items[]
     */
    @Test
    public void getSize(){
        int size = validatableResponse.extract().path("items.size()");
        System.out.println(size);
    }
    /*
    Get all the names in items[]
     */
    @Test
    public void getNameList(){
//        List<String> nameList = validatableResponse.extract().path("items.findAll{it.name}.name"); // correct
        List<String> nameList = validatableResponse.extract().path("items.name"); // ditto
        System.out.println(nameList);
    }
    /*
    Get item of name
     */
    @Test
    public void getItemOfName(){
        Map<String, ?> item = validatableResponse.extract().path("items.find{it.name='Archway Cookies, Soft " +
                "Oatmeal Raisin " +
                "Classic " +
                "Cookies, 9.25 Oz'}");
        System.out.println(item);
    }
    /*
    Get sale price of an item
     */
    @Test
    public void getSalePriceOfAnItem(){
        Float salePrice = validatableResponse.extract().path("items.find{it.name='Newtons Soft & fruit Chewy Fig " +
                "Cookies, 10 oz Pack'}.salePrice");
        System.out.println(salePrice);
    }
    /*
    Get the Names which have salePrice less than 3
     */
    @Test
    public void getAllNamesOfLowSalePrice(){
        List<String> nameList = validatableResponse.extract().path("items.findAll{it.salePrice < 3}.name");
        System.out.println(nameList);
    }
    /*
    Get the msrp of items that Start with name = New
     */
    @Test
    public void getMsrpOfItemsWithStartName(){
        List<Float> msrpList = validatableResponse.extract().path("items.findAll{it.name ==~/New.*/}.msrp");
//        List<Float> msrpList = validatableResponse.extract().path("items.findAll{it.name==~/New.*/}.msrp");
        System.out.println(msrpList);
    }
    /*
    Get the saleprice of items that End with name = Pack (case-sensitive)
     */
    @Test
    public void getMsrpOfItemsWithEndName(){
        List<Float> msrpList = validatableResponse.extract().path("items.findAll{it.name==~/.*Pack/}.salePrice");
        System.out.println(msrpList);
    }
}
