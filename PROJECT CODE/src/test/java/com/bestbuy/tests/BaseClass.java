package com.bestbuy.tests;

import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;

public class BaseClass {

    /*
    http://localhost:3030/products
     */
    @BeforeClass
    public static void init(){
        baseURI = "http://localhost";
        port = 3030;
//        basePath = "";
    }
}
