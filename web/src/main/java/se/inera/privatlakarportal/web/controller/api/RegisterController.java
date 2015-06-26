package se.inera.privatlakarportal.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.inera.privatlakarportal.service.RegisterService;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;

/**
 * Created by pebe on 2015-06-25.
 */
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = "application/json")
    public void createRegistration(@RequestBody CreateRegistrationRequest request) {
        registerService.createRegistration(request);
    }

}
