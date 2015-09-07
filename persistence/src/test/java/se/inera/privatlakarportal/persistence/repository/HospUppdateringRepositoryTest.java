package se.inera.privatlakarportal.persistence.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import se.inera.privatlakarportal.persistence.config.PersistenceConfig;
import se.inera.privatlakarportal.persistence.config.PersistenceConfigTest;
import se.inera.privatlakarportal.persistence.model.HospUppdatering;
import se.inera.privatlakarportal.persistence.model.Privatlakare;
import se.inera.privatlakarportal.persistence.repository.util.PrivatelakareTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = {PersistenceConfigTest.class,PersistenceConfig.class})
@ActiveProfiles({"dev"})
public class HospUppdateringRepositoryTest {

    @Autowired
    private HospUppdateringRepository hospUppdateringRepository;

    @Test
    public void testFind() {
        HospUppdatering hospUppdatering = new HospUppdatering();
        hospUppdatering.setSenasteHospUppdatering(new LocalDateTime());

        HospUppdatering saved = hospUppdateringRepository.save(hospUppdatering);
        HospUppdatering read = hospUppdateringRepository.findSingle();

        assertEquals(saved.getSenasteHospUppdatering(), read.getSenasteHospUppdatering());
    }
}
