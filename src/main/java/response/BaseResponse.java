package response;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class BaseResponse {
    private final Response response;

    public BaseResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return this.response.getStatusCode();
    }

    public String getHeader(String header) {
        return this.response.getHeader(header);
    }

    public ResponseBody getBody() {
        return this.response.body();
    }

    public String getBodyAsString() {
        return this.response.body().asString();
    }
}
