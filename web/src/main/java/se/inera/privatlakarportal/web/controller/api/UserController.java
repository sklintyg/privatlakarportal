package se.inera.privatlakarportal.web.controller.api;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import se.inera.privatlakarportal.service.UserService;
import se.inera.privatlakarportal.web.controller.api.dto.*;

/**
 * Created by pebe on 2015-08-21.
 */
@Api(value = "/user", description = "REST API för användarservice", produces = MediaType.APPLICATION_JSON)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "getUser")
    public GetUserResponse getUser() {
        return new GetUserResponse(userService.getUserWithStatus());
    }

}
