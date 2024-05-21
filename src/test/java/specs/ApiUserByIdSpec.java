package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class ApiUserByIdSpec {
    public static RequestSpecification ApiUserByIdRequestSpec = with()
            .filter(withCustomTemplates())
            .log().method()
            .log().uri()
            .basePath("/api/users");

    public static ResponseSpecification ApiUserByIdResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(200)
            .log(BODY)
            .build();
}
