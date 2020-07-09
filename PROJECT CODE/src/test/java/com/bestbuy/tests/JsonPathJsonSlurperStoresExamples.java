package com.bestbuy.tests;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class JsonPathJsonSlurperStoresExamples extends BaseClass {

    private static ValidatableResponse validatableResponse;

    @BeforeClass
    public static void setUp(){
        validatableResponse = when().get("/stores").then();
    }

    /*
    Get TOTAL
     */
    @Test
    public void getTotalCount(){
        int total = validatableResponse.extract().path("total");
        System.out.println(total);
    }

    /*
    Get name of the 1st store
     */
    @Test
    public void getFirstStoreName(){
        String name = validatableResponse.extract().path("data[0].name");
        System.out.println(name); //Minnetonka
    }

    /*
    Get first service name from first data element
     */
    @Test
    public void getFirstServiceNameFromFirstStore(){
        String name = validatableResponse.extract().path("data[0].services[0].name");
        System.out.println(name); //Geek Squad Services
    }

    /*
    Get all the info of store with zip code 55901
     */
    @Test
    public void findStoreWithZip(){
        Map<String, ?> map = validatableResponse.extract().path("data.find{it.zip == '55901'}");
        map.forEach((k,v)-> System.out.println(k+" : "+v));
    }

    /*
    Get address of store with zip code 55901
     */
    @Test
    public void findStoreAddressWithZip(){
        String address = validatableResponse.extract().path("data.find{it.zip == '55901'}.address");
        System.out.println(address);
    }

    /*
    Get info of store with max/min id
     */
    @Test
    public void findStoreOfMaxId(){
        Map<String, ?> maxId = validatableResponse.extract().path("data.max{it.id}");
        maxId.forEach((k,v)-> System.out.println(k+" : "+v));
        System.out.println(); // OR
//        System.out.println(map);
        Map<String, ?> minId = validatableResponse.extract().path("data.min{it.id}");
        minId.forEach((k,v)-> System.out.println(k+" : "+v));
    }

    /*
    Get all stores with Id < 10 (& print their zip codes)
     */
    @Test
    public void findAllStoresOfId(){

        // How to determine the output type?
        // Use the online code editor to test the query & determine the return type from the output
        List<Map<String, ?>> stores = validatableResponse.extract().path("data.findAll{it.id <10}");
        stores.forEach(System.out::println);
        // For a simple output, get only the zipcodes
        List<String> zipCodes = validatableResponse.extract().path("data.findAll{it.id <10}.zip");
        System.out.println(zipCodes);
    }

    /*
    Get all the service names of all the stores
     */
    @Test
    public void findAllServiceNames(){
        List<List<String>> storeNames = validatableResponse.extract().path("data.services.findAll{it.name}.name");
        System.out.println(storeNames);
    }

}
