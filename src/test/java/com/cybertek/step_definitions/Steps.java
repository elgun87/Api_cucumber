package com.cybertek.step_definitions;

import com.cybertek.base.Hr_Base_URL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Steps {
    RequestSpecification request;
    Response response;
    JsonPath jsonPath;
    List<Map> listmap;

    @Given("accept type is JSON")
    public void accept_type_is_json() {
      request = given().contentType(ContentType.JSON);
    }

    @Given("path parameter value {string}")
    public void path_parameter_value(String country_initials) {
        request.queryParams("q","{\"country_id\":\""+country_initials+"\"}");
    }

    @When("user sends request to {string}")
    public void user_sends_request_to(String end_point) {
        response = request.log().all().get(Hr_Base_URL.HR_BASE_URL + ""+end_point+"");
    }

    @Then("status code is {int}")
    public void status_code_is(Integer stausCode) {
      assertEquals(200,response.statusCode());
    }

    @Then("content type is JSON")
    public void content_type_is_json() {
        assertEquals("application/json",response.contentType());
    }

    @Then("country_id is {string}")
    public void country_id_is(String string) {
        jsonPath = response.jsonPath();
        assertEquals("US",jsonPath.getString("items[0].country_id"));
    }

    @Then("country_name is {string}")
    public void country_name_is(String string) {
        assertEquals("United States of America",jsonPath.getString("items[0].country_name"));
    }

    @Then("region_id is {int}")
    public void region_id_is(Integer region_id) {
        assertEquals(region_id,jsonPath.get("items[0].region_id"));
    }


    @Given("Query param value {string} and {string}")
    public void query_param_value_and(String q, String param2) {
       request.queryParams(q,param2);

    }

    @Then("all job_ids start with {string}")
    public void all_job_ids_start_with(String string) {
        listmap = response.path("items");
        listmap.stream().forEach(e -> {
          String job_id =(String) e.get("job_id");
          assertTrue(job_id.startsWith("SA"));

        });

    }

    @Then("all department_ids are {int}")
    public void all_department_ids_are(Integer int1) {
        listmap.stream().forEach(e-> assertEquals(80,e.get("department_id")));
    }

    @Then("count is {int}")
    public void count_is(int count) {
        assertEquals(count,listmap.size());
    }
}
