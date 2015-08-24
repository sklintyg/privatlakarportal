package se.inera.privatlakarportal.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.inera.privatlakarportal.service.RegisterService;
import se.inera.privatlakarportal.service.model.CreateRegistrationResponseStatus;
import se.inera.privatlakarportal.service.model.RegistrationWithHospInformation;
import se.inera.privatlakarportal.service.model.SaveRegistrationResponseStatus;
import se.inera.privatlakarportal.service.postnummer.PostnummerService;
import se.inera.privatlakarportal.web.controller.api.dto.*;

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
        RegistrationWithHospInformation registrationWithHospInformation = registerService.getRegistration();
        return new GetRegistrationResponse(registrationWithHospInformation.getRegistration(), registrationWithHospInformation.getHospInformation());
    }

    @RequestMapping(value = "/registration/create", method = RequestMethod.POST, consumes = "application/json")
    public CreateRegistrationResponse createRegistration(@RequestBody CreateRegistrationRequest request) {
        CreateRegistrationResponseStatus status = registerService.createRegistration(request.getRegistration());
        return new CreateRegistrationResponse(status);
    }

    @RequestMapping(value = "/registration/save", method = RequestMethod.POST, consumes = "application/json")
    public SaveRegistrationResponse createRegistration(@RequestBody SaveRegistrationRequest request) {
        SaveRegistrationResponseStatus status = registerService.saveRegistration(request.getRegistration());
        return new SaveRegistrationResponse(status);
    }

    @RequestMapping(value = "/registration/hospInformation")
    public GetHospInformationResponse getHospInformation() {
        return new GetHospInformationResponse(registerService.getHospInformation());
    }

    @RequestMapping(value = "/registration/omrade/{postnummer}")
    public GetOmradeResponse getOmrade(@PathVariable("postnummer") String postnummer) {
        return new GetOmradeResponse(postnummerService.getOmradeByPostnummer(postnummer));
    }

    @RequestMapping(value = "/registration/remove/{id}")
    public boolean removePrivatlakare(@PathVariable("id") String personId) {
        return registerService.removePrivatlakare(personId);
    }
}
