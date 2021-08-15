import com.google.gson.Gson;
import entity.Genre;
import entity.ListOptions;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.ResponseBody;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.GenreService;

public class GenreTest {

    private static final Logger LOG = Logger.getLogger(GenreTest.class);


    @Test(description = "Positive, GET all Genre")
    public void getAllGenrePositive() {
        ListOptions listOptions = new ListOptions(
                "asc",
                1,
                true,
                2,
                "genreId"
        );
        BaseResponse baseResponse = getAllGenres(listOptions);
        verifyResponseStatus(baseResponse.getStatusCode(), 200);
    }

    @Test(description = "Negative, GET all Genre")
    public void getAllGenreNegative() {
        ListOptions listOptions = new ListOptions(
                "asc",
                1,
                true,
                1,
                "test"
        );
        BaseResponse baseResponse = getAllGenres(listOptions);
        verifyResponseStatus(baseResponse.getStatusCode(), 400);
    }

    @Test(description = "Positive, POST  Genre")
    public void postGenrePositive() {
        Genre genre = new Genre(
                getMaxId() + 1,
                "test1",
                "test2"
        );
        BaseResponse baseResponse = createNewGenre(genre);
        verifyResponseStatus(baseResponse.getStatusCode(), 201);
    }

    @Test(description = "Positive, GET Genre by ID")
    public void GetByIdGenrePositive() {
        BaseResponse baseResponse = getGenreById(getMaxId());
        verifyResponseStatus(baseResponse.getStatusCode(), 200);
    }

    @Test(description = "Negative, GET Genre by ID")
    public void GetByIdGenreNegative() {
        BaseResponse baseResponse = getGenreById(0);
        verifyResponseStatus(baseResponse.getStatusCode(), 404);
    }

    @Test(description = "Positive, Delete Genre")
    public void deleteGenrePositive() {
        BaseResponse baseResponse = deleteGenreById(getMaxId());
        verifyResponseStatus(baseResponse.getStatusCode(), 204);
    }


    @Attachment("Request body")
    public String getRequestBody(Object requestBody) {
        return new Gson().toJson(requestBody);
    }

    @Attachment("Response body")
    public String getResponseBody(ResponseBody responseBody) {
        return responseBody.asString();
    }

    @Step("Get all genres")
    public BaseResponse getAllGenres(ListOptions options) {
        LOG.info("Send request with options:  " + getRequestBody(options));
        BaseResponse baseResponse = new GenreService().getGenres(options);
        LOG.info("Got response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }

    @Step("Get genre by id")
    public BaseResponse getGenreById(Integer id) {
        BaseResponse baseResponse = new GenreService().getGenre(id);
        LOG.info("Got response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }

    @Step("Delete genre by id")
    public BaseResponse deleteGenreById(Integer id) {
        BaseResponse baseResponse = new GenreService().deleteGenre(id);
        LOG.info("Got response: " + baseResponse.getStatusCode());
        return baseResponse;
    }

    @Step("Create new genre")
    public BaseResponse createNewGenre(Genre genre) {
        LOG.info("Send request with options:  " + getRequestBody(genre));
        BaseResponse baseResponse = new GenreService().createGenre(genre);
        LOG.info("Got response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }

    @Step("Verify response status")
    public void verifyResponseStatus(int actual, int expected) {
        Assert.assertEquals(actual, expected, "Invalid status code.");
    }

    @Step("Get MAX Id")
    public Integer getMaxId() {
        ListOptions listOptions = new ListOptions(
                "desc",
                1,
                true,
                1,
                "genreId"
        );
        BaseResponse baseResponse = new GenreService().getGenres(listOptions);
        LOG.info("Got response: " + baseResponse.getBodyAsString());
        Genre[] genres = baseResponse.getBody().as(Genre[].class);
        return genres[0].genreId;
    }

}
