package com.studentapp.tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class StudentTests extends BaseClass {

    /*
    Get list of students
     */
    @Test
    public static void testGETList(){
        String str = when().get("http://localhost:8085/student/list").thenReturn().asString();
        System.out.println(str);
        // OR
        when().get("http://localhost:8085/student/list").thenReturn().prettyPrint();
    }

    /*
   Using Query Params & print the response directly
    */
    @Test
    public static void testGETQueryParams(){

        // Add Query Param
        given().queryParam("programme", "Computer Science").queryParam( "limit", 1).when().get(
                "http://localhost:8085/student" +
                        "/list").thenReturn().prettyPrint();

        // OR together using Query Params
        given().queryParams("programme", "Computer Science", "limit", 1).when().get(
                "http://localhost:8085/student" +
                        "/list").thenReturn().prettyPrint();

        // OR using hash map inside query params
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("programme", "Computer Science");
        map.put("limit", 1);

        given().queryParams(map).when().get(
                "http://localhost:8085/student" +
                        "/list").thenReturn().prettyPrint();
    }

    /*
   Using a PATH PARAMETER
    */
    @Test
    public static void testGETPathParams(){
        // http://localhost:8085/student/2
        given().pathParam("id", 2).when().get(
                "http://localhost:8085/student/{id}").thenReturn().prettyPrint();
    }

    /*
    Setup Base Class
     */
    public static void init(){
        baseURI = "http://localhost";
        port = 8085;
        basePath = "/student";
    }

    @Test
    public static void testGETAfterBaseClassSetup(){
        // Change default base URI, base path, port
        init();
        when().get("/2").then().statusCode(200);
        // Reset to default base URI, base path, port
        RestAssured.reset();
        System.out.println("After reset...");
        when().get("/list").then().statusCode(200); // throws a 403
    }

    /*
    Create a Student record using POST and POJO
     */
    @Test
    public static void testPOSTUsingPOJO(){
        init();
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("abc@gmail.com");
        student.setProgramme("Mechanical Engineering");
        student.setCourses(Arrays.asList("Heat and Mass Transfer", "Fluid Mechanics", "Dynamics of Machinery"));

        given().contentType(ContentType.JSON).body(student).when().post().then().statusCode(201);
    }

    /*
    Create a Student record using POST and POJO and fake data
     */
    @Test
    public static void testPOSTUsingPOJOFakerAPI(){
        init();
        Student student = new Student();

        Faker fake = new Faker();
        student.setFirstName(fake.name().firstName());
        student.setLastName(fake.name().lastName());
        student.setEmail(fake.internet().emailAddress());
        student.setProgramme("Mechanical Engineering");
        student.setCourses(Arrays.asList("Heat and Mass Transfer", "Fluid Mechanics", "Dynamics of Machinery"));

        given().contentType(ContentType.JSON).body(student).when().post().then().statusCode(201);
    }

    /*
    Update a Student record using PUT and POJO and fake data
     */
    @Test
    public static void testPUT(){
        init();
        Student student = new Student();

        Faker fake = new Faker();
        student.setFirstName(fake.name().firstName());
        student.setLastName(fake.name().lastName());
        student.setEmail(fake.internet().emailAddress());
        student.setProgramme("Mechanical Engineering");
        student.setCourses(Arrays.asList("Heat and Mass Transfer", "Fluid Mechanic", "Dynamics of Machinery"));

        given().contentType(ContentType.JSON).body(student).when().put("/101").then().statusCode(200);
    }

    /*
    UPDATE partial info of a Student record using PATCH
     */
    @Test
    public static void testPATCHUpdateEmail(){
        init();
        Student student = new Student();

        Faker fake = new Faker();
        student.setEmail(fake.internet().emailAddress()); // Student app supports PUT only for updating email address

        given().contentType(ContentType.JSON).body(student).when().patch("/101").then().statusCode(200);
    }

    /*
    Print the REQUEST Logs
     */
    @Test
    public static void testLogsForRequest(){
        init();
        System.out.println("Log REQUEST Headers...");
        given().log().headers().when().get("/1").prettyPrint();

        System.out.println("Log REQUEST params...");
        given().log().params().params("programme", "Mechanical Engineering", "limit", 1).when().get("/list").prettyPrint();

        System.out.println("Log all only if TC fails...");
        // Adding an existing student should fail the run
        Student student = new Student();
        Faker fake = new Faker();
        student.setFirstName("Deeshu");
        student.setLastName("Dakini");
        student.setEmail("deeshu.iluvu@yahoo.com");
        student.setProgramme("Mechanical Engineering");
        student.setCourses(Arrays.asList("Heat and Mass Transfer", "Fluid Mechanics", "Dynamics of Machinery"));

        // It fails so the Logs are printed
        given().log().ifValidationFails().contentType(ContentType.JSON).body(student).when().post().then().statusCode(201);

    }

    /*
   Print the RESPONSE Logs
    */
    @Test
    public static void testLogsForResponse(){
        init();
//        System.out.println("Log Response Headers...");
//        given().when().get("/list").then().log().headers().statusCode(200);

        // Log only if it fails
        System.out.println("Log Response only if TC fails...");
        given().params("limit", -1).when().get("/list").then().log().ifError().statusCode(200);
//        given().params("limit", -1).when().get("/list").then().log().ifValidationFails().statusCode(200); // ditto
    }




}
