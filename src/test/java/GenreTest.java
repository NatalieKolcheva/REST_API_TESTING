import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.GenreService;

public class GenreTest {

    private static final Logger LOG = Logger.getLogger(GenreTest.class);

    @Test (description = "Positive, GET Genre by ID")
    public void testGetByIdPositive() {
        BaseResponse baseResponse = new GenreService().getGenre(9856);
        LOG.info("Got response: " + baseResponse.getBody());
        Assert.assertEquals(baseResponse.getStatusCode(), 200, "Invalid status code.");
    }

    @Test (description = "Negative, GET Genre by ID")
    public void testGetByIdNegative() {
        BaseResponse baseResponse = new GenreService().getGenre(1010);
        LOG.info("Got response code: " + baseResponse.getStatusCode());
        Assert.assertEquals(baseResponse.getStatusCode(), 404, "Invalid status code.");
    }
}
