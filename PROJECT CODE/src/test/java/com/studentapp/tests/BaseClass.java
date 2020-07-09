package com.studentapp.tests;

import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class BaseClass {

    @BeforeClass
    public static void init(){
        baseURI = "http://localhost";
        port = 8085;
        basePath = "/student";
    }

}
