Feature: ORDS API

  Scenario: Q1
    Given accept type is JSON
    #And path parameter value "US"
    And Query param value "q" and '{"country_id": "US"}'
    When user sends request to "/countries"
    Then status code is 200
    And content type is JSON
    And country_id is "US"
    And country_name is "United States of America"
    And region_id is 2

  Scenario: Q2
    Given accept type is JSON
    And Query param value "q" and '{"department_id":80}'
    When user sends request to "/employees"
    Then status code is 200
    And content type is JSON
    And all job_ids start with "SA"
    And all department_ids are 80
    Then count is 25