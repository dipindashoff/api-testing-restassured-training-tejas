package com.bestbuy.practice;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class ProductsValidationUsingJsonSlurper {

    ValidatableResponse validatableResponse;

    @BeforeClass
    public void init(){
        // RESOURCE URL: http://localhost:3030/products
        baseURI = "http://localhost";
        port = 3030;

        validatableResponse = when().get("/products").then();
    }
    /*
    GET total
     */
    @Test
    public void getTotal(){
       int total = validatableResponse.extract().path("total");
        System.out.println(total);
    }

    /*
    GET all elements of data
     */
    @Test
    public void getDataElements(){
        List<Map<String, ?>> data = validatableResponse.extract().path("data");
        System.out.println(data);
    }
    /*
    GET FIRST ITEM BY INDEX FROM DATA LIST
     */
    @Test
    public void getFirstData(){
        Map<String, ?> data = validatableResponse.extract().path("data[0]");
        System.out.println(data);
    }
    /*
    GET LAST ELEMENT BY INDEX FROM LIST
     */
    @Test
    public void getLastData(){
        Map<String, ?> data = validatableResponse.extract().path("data[-1]");
        System.out.println(data);
    }
    /*
   GET ALL IDs INSIDE DATA LIST
    */
    @Test
    public void getAllIDsInData(){
        List<Integer> ids = validatableResponse.extract().path("data.findAll{it.id}.id");
        System.out.println(ids);
    }
    /*
   GET ALL IDs IN THE JSON
    */
    @Test
    public void getAllIDs(){
        // Note: This does not pull all the IDs
        // It will get all IDs of  (like the example above) or all IDs of categories
        List<String> ids = validatableResponse.extract().path("data.categories.findAll{it.id}.id");
        System.out.println(ids);
    }
    /*
    GET NAME OF PRODUCTS OF PRICE LESS THAN 5
     */
    @Test
    public void getNames(){
        List<String> names = validatableResponse.extract().path("data.findAll{it.price > 5}.name");
        System.out.println(names);
    }
}
