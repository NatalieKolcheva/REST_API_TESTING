package client;

import config.ServiceConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import response.BaseResponse;

import static io.restassured.RestAssured.given;

public class HttpClient {

    public static BaseResponse get(String endpoint) {
        return HttpClient.sendRequest(Method.GET, endpoint);
    }

    public static BaseResponse post(String endpoint, String body) {
        return HttpClient.sendRequest(Method.POST, endpoint, body);
    }

    public static BaseResponse put(String endpoint, String body) {
        return HttpClient.sendRequest(Method.PUT, endpoint, body);
    }

    public static BaseResponse delete(String endpoint) {
        return HttpClient.sendRequest(Method.DELETE, endpoint);
    }

    private static BaseResponse sendRequest(Method method, String endpoint) {
        return HttpClient.sendRequest(method, endpoint, null);
    }

    private static BaseResponse sendRequest(Method method, String endpoint, String body) {
        String url = ServiceConfig.HOST + endpoint;
        RequestSpecification spec = given();
        if (body != null) {
            spec.contentType(ContentType.JSON);
            spec.body(body);
        }
        Response rawResponse = spec.request(method, url);
        return new BaseResponse(rawResponse);
    }
}
