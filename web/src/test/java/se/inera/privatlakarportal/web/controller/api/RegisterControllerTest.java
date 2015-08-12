package se.inera.privatlakarportal.web.controller.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.RegisterService;
import se.inera.privatlakarportal.service.RegisterServiceImpl;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationRequest;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationResponse;
import se.inera.privatlakarportal.web.controller.api.dto.CreateRegistrationResponseStatus;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {
    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController = new RegisterController();

    @Test
    public void testCreateRegistration() {
        CreateRegistrationRequest request = new CreateRegistrationRequest();
        registerController.createRegistration(request);

        verify(registerService).createRegistration(request.getRegistration());
    }
}