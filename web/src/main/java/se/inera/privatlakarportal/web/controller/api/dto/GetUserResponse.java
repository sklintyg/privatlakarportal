package se.inera.privatlakarportal.web.controller.api.dto;

/**
 * Created by pebe on 2015-08-21.
 */
public class GetUserResponse {

    private String name;

    public GetUserResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
