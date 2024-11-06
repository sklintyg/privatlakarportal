package se.inera.intyg.privatlakarportal.web.controller.api.testability;


import static se.inera.intyg.privatlakarportal.config.WebSecurityConfig.TESTABILITY_PROFILE;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.privatlakarportal.auth.FakeElegCredentials;

@RestController
@RequiredArgsConstructor
@Profile(TESTABILITY_PROFILE)
@RequestMapping("/api/testability")
public class TestabilityController {

    private final FakeLoginService fakeAuthService;

    @PostMapping("/fake")
    public void login(@RequestBody FakeElegCredentials fakeCredentials, final HttpServletRequest request) {
        fakeAuthService.login(fakeCredentials, request);
    }

    @PostMapping("/logout")
    public void logout(final HttpServletRequest request) {
        fakeAuthService.logout(request.getSession(false));
    }

}