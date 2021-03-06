package entity;

import com.google.gson.Gson;

public class Genre {

    public Integer genreId;
    public String genreName;
    public String genreDescription;

    public Genre() {
    }

    public Genre(Integer genreId, String genreName, String genreDescription) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.genreDescription = genreDescription;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
