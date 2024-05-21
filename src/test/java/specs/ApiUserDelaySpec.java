package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.STATUS;

public class ApiUserDelaySpec {
    public static RequestSpecification ApiUserDelayRequestSpec = with()
            .filter(withCustomTemplates())
            .log().method()
            .log().uri()
            .basePath("/api/users");

    public static ResponseSpecification ApiUserDelayResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(200)
            .build();
}
