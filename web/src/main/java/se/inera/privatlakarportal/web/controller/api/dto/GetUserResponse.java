package se.inera.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import se.inera.privatlakarportal.service.model.User;

/**
 * Created by pebe on 2015-08-21.
 */
@ApiModel(description = "Response-obect för GetUser")
public class GetUserResponse {

    @ApiModelProperty(name = "user", dataType = "User")
    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
