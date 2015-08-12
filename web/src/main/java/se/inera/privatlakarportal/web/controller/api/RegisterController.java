package se.inera.privatlakarportal.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.inera.privatlakarportal.service.RegisterService;
import se.inera.privatlakarportal.service.dto.HospInformation;
import se.inera.privatlakarportal.web.controller.api.dto.*;

/**
 * Created by pebe on 2015-06-25.
 */
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/registration")
    public GetRegistrationResponse getRegistration() {
        return new GetRegistrationResponse(registerService.getRegistration());
    }

    @RequestMapping(value = "/registration/create", method = RequestMethod.POST, consumes = "application/json")
    public CreateRegistrationResponse createRegistration(@RequestBody CreateRegistrationRequest request) {
        CreateRegistrationResponseStatus status = registerService.createRegistration(request.getRegistration());
        return new CreateRegistrationResponse(status);
    }

    @RequestMapping(value = "/registration/hospInformation")
    public HospInformation getHospInformation() {
        return registerService.getHospInformation();
    }


}
