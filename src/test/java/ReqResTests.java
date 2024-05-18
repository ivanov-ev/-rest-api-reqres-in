import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqResTests {

    @BeforeAll
    public static void configureRestAssured() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void CreateUserTest() {
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
        String jsonRequestBody = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(jsonRequestBody)
                .contentType(JSON)
                .log().method()
                .log().uri()
                .log().body()

        .when()
                .post("/api/users")

        .then()
                .log().status()
                .statusCode(201)
                .log().body()
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void GetUserTest() {
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

        given()
                .log().method()
                .log().uri()

        .when()
                .get("/api/users/{id}", id)

        .then()
                .log().status()
                .statusCode(200)
                .log().body()
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }


    @Test
    void CountItemsInDataElementTest() {
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
        given()
                .log().method()
                .log().uri()

        .when()
                .get("api/unknown")

        .then()
                .log().body()
                .body("data", hasSize(6));
    }

    @Test
    void CheckColorInDataElementTest() {
        /*
        1. Perform a GET request to https://reqres.in/api/unknown
        2. Get the response
        3. Check that "data" contains the specified "color"
        */
        given()
                .log().method()
                .log().uri()

        .when()
                .get("api/unknown")

        .then()
                .log().body()
                .body("data.color", hasItems("#98B2D1", "#BF1932", "#53B0AE"));
    }

    @Test
    void ResponseTimeTest() {
        /*
        1. Perform a GET request to https://reqres.in/api/users?delay=3
        2. Get the response
        3. Check the time required to get this response < 5000 milliseconds
        */

        given()
                .log().method()
                .log().uri()

        .when()
                .get("api/users?delay=3")

        .then()
                .time(lessThan(5000L));
    }
}
