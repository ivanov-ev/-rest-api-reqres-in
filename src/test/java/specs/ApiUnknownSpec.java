package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;

public class ApiUnknownSpec {
    public static RequestSpecification ApiUnknownRequestSpec = with()
            .filter(withCustomTemplates())
            .log().method()
            .log().uri()
            .basePath("/api/unknown");

    public static ResponseSpecification ApiUnknownResponseSpec = new ResponseSpecBuilder()
            .log(BODY)
            .build();
}
