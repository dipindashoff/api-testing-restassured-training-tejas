package com.bestbuy.tests;

import com.jayway.jsonpath.JsonPath;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class JsonPathJaywayProductsExamples extends BaseClass{

    static String jsonResponse;

    /*
    Get ROOT element
    Operator: $
    The root element to query
     */
    @Test
    public static void getRoot(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        Map<String, ?> rootElement = JsonPath.read(jsonResponse, "$");
        System.out.println(rootElement.toString());
    }

    /*
    Get total count from response
    Operator: .<name>   Dot-notated child
     */
    @Test
    public static void getTotalFromResponse(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        int totalCount = JsonPath.read(jsonResponse, "$.total");
        System.out.println(totalCount);
    }

    /*
    Get all data elements from response
    Operator: .<name>   Dot-notated child
     */
    @Test
    public static void getAllDataElementsFromResponse(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        List<HashMap<String, Object>> dataElements = JsonPath.read(jsonResponse, "$.data[*]");
        dataElements.forEach(System.out::println);
    }

    /*
    Get FIRST element of data from response
    Operator: .<name>   Dot-notated child
     */
    @Test
    public static void getFirstDataElement(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        HashMap<String, Object> firstElement = JsonPath.read(jsonResponse, "$.data[0]");
        System.out.println(firstElement.toString());
    }

    /*
    Get LAST element of data from response
    Operator: .<name>   Dot-notated child
     */
    @Test
    public static void getlastDataElement(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        HashMap<String, Object> firstElement = JsonPath.read(jsonResponse, "$.data[-1]");
        System.out.println(firstElement.toString());
    }

    /*
    Get all IDs element of data from response
    Operator: .<name>   Dot-notated child & * wild card operator
     */
    @Test
    public static void getAllIDsInData(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        List<Integer> listOfIDs = JsonPath.read(jsonResponse, "$.data[*].id");
        System.out.println(listOfIDs);
    }

    /*
    Get IDs of all elements
     */
    @Test
    public static void getIDOfAllElements(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        List<Integer> listOfIDs = JsonPath.read(jsonResponse, "$..id");
        System.out.println(listOfIDs);
    }

    /*
   Get name of products whose price is less than 5
    */
    @Test
    public static void getNameOfProductsWithPriceLessThan5(){

        jsonResponse = when().get("/products").thenReturn().asString();
//        System.out.println(jsonResponse);

        List<String> listOfNamesPriceLessThan5 = JsonPath.read(jsonResponse, "$..[?(@.price<5)].name");
        System.out.println(listOfNamesPriceLessThan5);
    }


}
