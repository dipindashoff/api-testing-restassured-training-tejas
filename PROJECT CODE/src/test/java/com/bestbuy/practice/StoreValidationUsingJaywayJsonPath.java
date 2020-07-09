package com.bestbuy.practice;

import com.jayway.jsonpath.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

/*
Running JsonSlurper examples with Jayway JsonPath
 */

public class StoreValidationUsingJaywayJsonPath {

    String jsonResponse;

    @BeforeClass
    public void setUp(){
        // RESOURCE URL: http://localhost:3030/stores
        baseURI = "http://localhost";
        port = 3030;
        jsonResponse = when().get("/stores").thenReturn().asString();
    }

    /*
    GET TOTAL COUNT
     */
    @Test
    public void getTotalcount(){
        int total = JsonPath.read(jsonResponse, "$.total");
        System.out.println(total);
    }

    /*
    GET NAME OF 1ST STORE
     */
    @Test
    public void getFirstStoreName(){
        String storeName = JsonPath.read(jsonResponse, "$.data[0].name");
        System.out.println(storeName);
    }

    /*
    GET FIRST SERVICE NAME FROM FIRST STORE
     */
    @Test
    public void getFirstServiceName(){
        String storeName = JsonPath.read(jsonResponse, "$.data[0].services[0].name");
        System.out.println(storeName);
    }
    /*
    GET STORE WITH ZIP CODE
     */
    @Test
    public void getStoreWithZip(){
        List<Map<String, ?>> store = JsonPath.read(jsonResponse, "$.data[?(@.zip == '55901')]");
        System.out.println(store);
    }
    /*
    GET STORE ADDRESS WITH ZIP CODE
     */
    @Test
    public void getStoreAddrWithZip(){
        List<String> storeAddr = JsonPath.read(jsonResponse, "$.data[?(@.zip == '55901')].address");
        System.out.println(storeAddr);
    }
    /*
    GET STORE WITH MAX/MIN ID
     */
    @Test
    public void getStoreWithMaxID(){
//        List<Double> storeAddr = JsonPath.read(jsonResponse, "$.data[*].max()");
//        System.out.println(storeAddr); // throws error
        // Example
        // $.max($.store.book[0].price, $.store.book[1].price, $.store.book[2].price, $.store.book[3].price)
    }

    /*
    GET ZIP OF STORES WITH ID < 10
     */
    @Test
    public void getZip(){
        List<String> zip = JsonPath.read(jsonResponse, "$..data[?(@.id < 10)].zip");
        System.out.println(zip);
    }

    /*
    GET ALL SERVICE NAMES OF ALL THE STORES
     */
    @Test
    public void getAllServiceNames(){
        List<String> zip = JsonPath.read(jsonResponse, "$..services[*].name");
        System.out.println(zip);
    }

}
