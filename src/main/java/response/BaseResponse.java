package response;

import io.restassured.response.Response;

public class BaseResponse {
    private Response response;

    public BaseResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return this.response.getStatusCode();
    }

    public String getHeader(String header) {
        return this.response.getHeader(header);
    }

    public String getBody() {
        return this.response.body().asString();
    }
}
