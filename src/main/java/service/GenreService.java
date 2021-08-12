package service;

import client.HttpClient;
import com.google.gson.Gson;
import entity.Genre;
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

    public BaseResponse createGenre(Genre genre) {
        String endpoint = new EndpointBuilder().pathParameter("genre").get();
        String bodyJson = new Gson().toJson(genre);
        return HttpClient.post(endpoint, bodyJson);
    }

    public BaseResponse deleteGenre(int genreId) {
        String endpoint = new EndpointBuilder().pathParameter("genre").pathParameter(genreId).get();
        return HttpClient.delete(endpoint);
    }
}
