import entity.Genre;
import entity.ListOptions;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.GenreService;

public class GenreTest {

    private static final Logger LOG = Logger.getLogger(GenreTest.class);

    private Integer testID;


    @Test(description = "Positive, GET all Genre ", priority = 1)
    public void getAllGenrePositive() {
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
        testID = genres[0].genreId + 1;
        Assert.assertEquals(baseResponse.getStatusCode(), 200, "Invalid status code.");
    }

    @Test(description = "Negative, GET all Genre ", priority = 2)
    public void getAllGenreNegative() {
        ListOptions listOptions = new ListOptions(
                "test",
                0,
                true,
                0,
                "test"
        );
        BaseResponse baseResponse = new GenreService().getGenres(listOptions);
        LOG.info("Got response: " + baseResponse.getBodyAsString());
        Assert.assertEquals(baseResponse.getStatusCode(), 400, "Invalid status code.");
    }

    @Test(description = "Positive, POST  Genre ", priority = 3)
    public void postGenrePositive() {
        Genre genre = new Genre(
                testID,
                "test1",
                "test2"
        );
        BaseResponse baseResponse = new GenreService().createGenre(genre);
        LOG.info("Got response: " + baseResponse.getBodyAsString());
        Assert.assertEquals(baseResponse.getStatusCode(), 201, "Invalid status code.");
    }

    @Test(description = "Positive, GET Genre by ID", priority = 4)
    public void GetByIdGenrePositive() {
        BaseResponse baseResponse = new GenreService().getGenre(testID);
        LOG.info("Got response: " + baseResponse.getBodyAsString());
        Assert.assertEquals(baseResponse.getStatusCode(), 200, "Invalid status code.");
    }

    @Test(description = "Positive, Delete Genre ", priority = 5)
    public void deleteGenrePositive() {
        BaseResponse baseResponse = new GenreService().deleteGenre(testID);
        LOG.info("Got response: " + baseResponse.getStatusCode());
        Assert.assertEquals(baseResponse.getStatusCode(), 204, "Invalid status code.");
    }

    @Test(description = "Negative, GET Genre by ID", priority = 6)
    public void GetByIdGenreNegative() {
        BaseResponse baseResponse = new GenreService().getGenre(testID);
        LOG.info("Got response code: " + baseResponse.getStatusCode());
        Assert.assertEquals(baseResponse.getStatusCode(), 404, "Invalid status code.");
    }


}
