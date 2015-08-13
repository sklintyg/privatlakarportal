package se.inera.privatlakarportal.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.inera.privatlakarportal.service.RegisterService;
import se.inera.privatlakarportal.service.dto.HospInformation;
import se.inera.privatlakarportal.service.postnummer.PostnummerService;
import se.inera.privatlakarportal.service.postnummer.model.Omrade;
import se.inera.privatlakarportal.web.controller.api.dto.*;

import java.util.List;

/**
 * Created by pebe on 2015-06-25.
 */
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private PostnummerService postnummerService;

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
    public GetHospInformationResponse getHospInformation() {
        return new GetHospInformationResponse(registerService.getHospInformation());
    }

    @RequestMapping(value = "/registration/omrade/{postnummer}")
    public GetOmradeResponse getOmrade(@PathVariable("postnummer") String postnummer) {
        return new GetOmradeResponse(postnummerService.getOmradeByPostnummer(postnummer));
    }
}
