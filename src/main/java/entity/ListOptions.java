package entity;

import com.google.gson.Gson;

public class ListOptions {
    public String orderType; // should be moved into Enum, since only 2 values exist: "asc", "desc"
    public int page;
    public boolean pagination;
    public int size;
    public String sortBy;



    public ListOptions(String orderType, int page, boolean pagination, int size, String sortBy) {
        this.orderType = orderType;
        this.page = page;
        this.pagination = pagination;
        this.size = size;
        this.sortBy = sortBy;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
