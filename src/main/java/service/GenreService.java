package service;

import client.HttpClient;
import entity.ListOptions;
import response.BaseResponse;
import utils.EndpointBuilder;

public class GenreService {

    public BaseResponse getGenre(int genreId) {
        String endpoint = new EndpointBuilder().pathParameter("genre").pathParameter(genreId).get();
        return HttpClient.get(endpoint);
    }

    public BaseResponse getGenres(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("genres");
        if (options.orderType != null) endpoint.queryParam("orderType", options.orderType);
        endpoint
            .queryParam("page", options.page)
            .queryParam("pagination", options.pagination)
            .queryParam("size", options.size);
        if (options.sortBy != null) endpoint.queryParam("sortBy", options.sortBy);
        return HttpClient.get(endpoint.get());
    }

    // TODO properly handle genre entity
    public BaseResponse createGenre(Object genre) {
        String endpoint = new EndpointBuilder().pathParameter("genre").get();
        return HttpClient.post(endpoint, genre.toString());
    }
}
