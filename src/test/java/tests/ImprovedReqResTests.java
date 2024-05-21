package tests;

import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ApiUnknownSpec.ApiUnknownRequestSpec;
import static specs.ApiUnknownSpec.ApiUnknownResponseSpec;
import static specs.ApiUserByIdSpec.ApiUserByIdRequestSpec;
import static specs.ApiUserByIdSpec.ApiUserByIdResponseSpec;
import static specs.ApiUserDelaySpec.ApiUserDelayRequestSpec;
import static specs.ApiUserDelaySpec.ApiUserDelayResponseSpec;
import static specs.ApiUserSpec.ApiUserRequestSpec;
import static specs.ApiUserSpec.ApiUserResponseSpec;

@Tag("improved_tests")
@DisplayName("REST API tests for https://reqres.in")
public class ImprovedReqResTests {

    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Create a user via the api/users controller")
    void createUserTest() {
        /*
        1. Perform a POST request to https://reqres.in/api/users with the JSON body: {"name":"morpheus", "job":"leader"}
        2. Get the response:
        {
            "name": "morpheus",
            "job": "leader",
            "id": "37",
            "createdAt": "2024-05-18T10:09:21.329Z"
        }
        3. Check that:
        - the HTTP status is 201;
        - the "name" is "morpheus";
        - the "job" is "leader".
        */

        ApiUsersRequestModel jsonRequestBody = new ApiUsersRequestModel();
        jsonRequestBody.setName("morpheus");
        jsonRequestBody.setJob("leader");

        ApiUsersResponseModel response = step("Perform a POST request", ()->
        given(ApiUserRequestSpec)
                .body(jsonRequestBody)

        .when()
                .post()

        .then()
                .spec(ApiUserResponseSpec)
                .extract().as(ApiUsersResponseModel.class)
        );

        step("Check the response ('name' must be 'morpheus'; 'job' must be 'leader')", () -> {
                    assertEquals("morpheus", response.getName());
                    assertEquals("leader", response.getJob());
                }
        );
    }

    @Test
    @DisplayName("Get a user by the user id via the api/users controller")
    void getUserTest() {
        /*
        1. Perform a GET request to https://reqres.in/api/users/{id}, where {id} = 2
        2. Get the response:
        {
            "data": {
                "id": 2,
                "email": "janet.weaver@reqres.in",
                "first_name": "Janet",
                "last_name": "Weaver",
                "avatar": "https://reqres.in/img/faces/2-image.jpg"
            },
            "support": {
                "url": "https://reqres.in/#support-heading",
                "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
            }
        }
        3. Check that:
        - the HTTP status is 200;
        - the "first_name" is "Janet";
        - the "last_name" is "Weaver".
        */

        String id = "2";

        ApiUsersByIdResponseModel response = step("Perform a GET request", ()->
        given(ApiUserByIdRequestSpec)

        .when()
                .get("/{id}", id)

        .then()
                .spec(ApiUserByIdResponseSpec)
                .extract().as(ApiUsersByIdResponseModel.class)
        );

        step("Check the response (the user name must be Janet Weaver)", () -> {
                    assertEquals("Janet", response.getData().getFirst_name());
                    assertEquals("Weaver", response.getData().getLast_name());
                }
        );
    }

    @Test
    @DisplayName("Get the number of users per page returned by the api/unknown controller")
    void countItemsInDataElementTest() {
        /*
        1. Perform a GET request to https://reqres.in/api/unknown
        2. Get the response:
        {
            "page": 1,
            "per_page": 6,
            "total": 12,
            "total_pages": 2,
            "data": [
                {
                    "id": 1,
                    "name": "cerulean",
                    "year": 2000,
                    "color": "#98B2D1",
                    "pantone_value": "15-4020"
                },
                {
                    "id": 2,
                    "name": "fuchsia rose",
                    "year": 2001,
                    "color": "#C74375",
                    "pantone_value": "17-2031"
                },
                {
                    "id": 3,
                    "name": "true red",
                    "year": 2002,
                    "color": "#BF1932",
                    "pantone_value": "19-1664"
                },
                {
                    "id": 4,
                    "name": "aqua sky",
                    "year": 2003,
                    "color": "#7BC4C4",
                    "pantone_value": "14-4811"
                },
                {
                    "id": 5,
                    "name": "tigerlily",
                    "year": 2004,
                    "color": "#E2583E",
                    "pantone_value": "17-1456"
                },
                {
                    "id": 6,
                    "name": "blue turquoise",
                    "year": 2005,
                    "color": "#53B0AE",
                    "pantone_value": "15-5217"
                }
            ],
            "support": {
                "url": "https://reqres.in/#support-heading",
                "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
            }
        }
        3. Check that "data" contains 6 items.
        */

        ApiUnknownResponseModel response = step("Perform a GET request", ()->
        given(ApiUnknownRequestSpec)

        .when()
                .get()

        .then()
                .spec(ApiUnknownResponseSpec)
                .extract().as(ApiUnknownResponseModel.class)
        );

        step("Check the response ('data' must contain 6 items)", () -> {
                    assertEquals(6, response.getData().size());
                }
        );
    }

    @Test
    @DisplayName("Check colors returned by the api/unknown controller")
    void checkColorInDataElementTest() {
        /*
        1. Perform a GET request to https://reqres.in/api/unknown
        2. Get the response
        3. Check that "data" contains the specified "color"
        */

        ApiUnknownResponseModel response = step("Perform a GET request", ()->
        given(ApiUnknownRequestSpec)

        .when()
                .get()

        .then()
                .spec(ApiUnknownResponseSpec)
                .extract().as(ApiUnknownResponseModel.class)
        );

        step("Check the response ('data' must include colors #98B2D1, #BF1932, and #53B0AE)", () -> {
                    List<String> targetColorList = new ArrayList<>();
                    for (int i = 0; i < response.getData().size(); i++) {
                        targetColorList.add(response.getData().get(i).getColor());
                    }
                    assertThat(targetColorList, hasItems("#98B2D1", "#BF1932", "#53B0AE"));
                }
        );
    }

    @Test
    @DisplayName("Check the time required by the api/user controller to respond")
    void responseTimeTest() {
        /*
        1. Perform a GET request to https://reqres.in/api/users?delay=3
        2. Get the response
        3. Check the time required to get this response < 5000 milliseconds
        */

        ApiUsersDelayResponseModel response = step("Perform a GET request and check the response time", ()->
        given(ApiUserDelayRequestSpec)

        .when()
                .get("?delay=3")

        .then()
                .time(lessThan(5000L))
                .spec(ApiUserDelayResponseSpec)
                .extract().as(ApiUsersDelayResponseModel.class)
        );
    }
}
