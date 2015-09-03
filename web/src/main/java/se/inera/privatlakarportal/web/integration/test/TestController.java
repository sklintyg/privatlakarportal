package se.inera.privatlakarportal.web.integration.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.privatlakarportal.service.RegisterService;

/**
 * Created by pebe on 2015-09-02.
 */
@RestController
@RequestMapping("/api/test")
@Profile({"dev", "testability-api"})
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RegisterService registerService;

    @Autowired
    private PrivatlakareRepository privatlakareRepository;

    public TestController() {
        LOG.debug("TestController");
    }

    @RequestMapping(value = "/registration/remove/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    boolean removePrivatlakare(@PathVariable("id") String personId) {
        return registerService.removePrivatlakare(personId);
    }

    @RequestMapping(value = "/registration/setname/{id}", method = RequestMethod.POST)
    public boolean setNamePrivatlakare(@PathVariable("id") String personId, @RequestBody String name) {
        Privatlakare privatlakare = privatlakareRepository.findByPersonId(personId);
        if (privatlakare == null) {
           LOG.error("Unable to find privatlakare with personId '{}'", personId);
           return false;
        }
        privatlakare.setFullstandigtNamn(name);
        privatlakareRepository.save(privatlakare);
        return true;
    }

}
