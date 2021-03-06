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

    public Integer createTestGenre() {
        Integer testId = getMaxId() + 1;
        Genre genre = new Genre(testId, "test1", "test2");
        createNewGenre(genre);
        return testId;
    }

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
        Genre[] genres = baseResponse.getBody().as(Genre[].class);
        verifyResponseBodyIsNotEmpty(genres.length);
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

    @Test(description = "Positive, POST|DELETE Genre")
    public void postDeleteGenrePositive() {
        Integer genreId = getMaxId() + 1;
        Genre genre = new Genre(
                genreId,
                "test_new",
                "test_new"
        );
        verifyResponseStatus(createNewGenre(genre).getStatusCode(), 201);
        verifyResponseStatus(deleteGenreById(genreId).getStatusCode(), 204);
    }

    @Test(description = "Positive, GET Genre by ID")
    public void GetByIdGenrePositive() {
        Integer testId = createTestGenre();
        BaseResponse baseResponse = getGenreById(testId);
        verifyResponseStatus(baseResponse.getStatusCode(), 200);
        deleteGenreById(testId);
    }

    @Test(description = "Negative, GET Genre by ID")
    public void GetByIdGenreNegative() {
        BaseResponse baseResponse = getGenreById(0);
        verifyResponseStatus(baseResponse.getStatusCode(), 404);
    }

    @Test(description = "Positive, PUT  Genre")
    public void putGenrePositive() {
        Integer testId = createTestGenre();
        Genre genre = new Genre(
                testId,
                "test_edited",
                "test2_edited"
        );
        BaseResponse baseResponse = updateExistGenre(genre);
        verifyResponseStatus(baseResponse.getStatusCode(), 200);
        deleteGenreById(testId);
    }

    @Test(description = "Negative, PUT  Genre")
    public void putGenreNegative() {
        Integer testId = createTestGenre();
        Genre genre = new Genre(
                testId + 2,
                "test_edited",
                "test2_edited"
        );
        BaseResponse baseResponse = updateExistGenre(genre);
        verifyResponseStatus(baseResponse.getStatusCode(), 404);
        deleteGenreById(testId);
    }

    @Test(description = "Positive, GET SEARCH Genre")
    public void getSearchGenrePositive() {
        Integer testId = createTestGenre();
        BaseResponse baseResponse = getSearchGenre("test1");
        verifyResponseStatus(baseResponse.getStatusCode(), 200);
        Genre[] genres = baseResponse.getBody().as(Genre[].class);
        verifyResponseBodyIsNotEmpty(genres.length);
        deleteGenreById(testId);
    }

    @Test(description = "Negative, GET SEARCH Genre")
    public void getSearchGenreNegative() {
        BaseResponse baseResponse = getSearchGenre("te");
        verifyResponseStatus(baseResponse.getStatusCode(), 400);
    }

    @Attachment("Response body")
    public String getResponseBody(ResponseBody responseBody) {
        return responseBody.asString();
    }

    @Step("Get all genres")
    public BaseResponse getAllGenres(ListOptions options) {
        LOG.info("Get all genres request with options:  " + options);
        BaseResponse baseResponse = new GenreService().getGenres(options);
        LOG.info("Get all genres response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }

    @Step("Get genre by id")
    public BaseResponse getGenreById(Integer id) {
        BaseResponse baseResponse = new GenreService().getGenre(id);
        LOG.info("Get genre by id response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }

    @Step("Delete genre by id")
    public BaseResponse deleteGenreById(Integer id) {
        BaseResponse baseResponse = new GenreService().deleteGenre(id);
        LOG.info("Delete genre by id response: " + baseResponse.getStatusCode());
        return baseResponse;
    }

    @Step("Create new genre")
    public BaseResponse createNewGenre(Genre genre) {
        LOG.info("Create new genre request:  " + genre);
        BaseResponse baseResponse = new GenreService().createGenre(genre);
        LOG.info("Create new genre response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }

    @Step("Update exist genre")
    public BaseResponse updateExistGenre(Genre genre) {
        LOG.info("Update exist genre request:  " + genre);
        BaseResponse baseResponse = new GenreService().updateGenre(genre);
        LOG.info("Update exist genre response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }

    @Step("Verify response status")
    public void verifyResponseStatus(int actual, int expected) {
        Assert.assertEquals(actual, expected, "Invalid status code.");
    }

    @Step("Verify response is not empty")
    public void verifyResponseBodyIsNotEmpty(Integer length) {
        Assert.assertTrue(length > 0, "Genre response body is empty.");
    }

    @Step("Get MAX genre id")
    public Integer getMaxId() {
        ListOptions listOptions = new ListOptions(
                "desc",
                1,
                true,
                1,
                "genreId"
        );
        BaseResponse baseResponse = new GenreService().getGenres(listOptions);
        LOG.info("Get MAX Id response: " + baseResponse.getBodyAsString());
        Genre[] genres = baseResponse.getBody().as(Genre[].class);
        return genres[0].genreId;
    }

    @Step("Get search genre ")
    public BaseResponse getSearchGenre(String query) {
        BaseResponse baseResponse = new GenreService().getSearchGenres(query);
        LOG.info("Get search genre response: " + getResponseBody(baseResponse.getBody()));
        return baseResponse;
    }


}
