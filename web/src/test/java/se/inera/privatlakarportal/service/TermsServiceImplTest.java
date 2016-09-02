package se.inera.privatlakarportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.inera.privatlakarportal.integration.terms.services.dto.Terms;
import se.inera.privatlakarportal.persistence.model.MedgivandeText;
import se.inera.privatlakarportal.persistence.repository.MedgivandeTextRepository;

/**
 * Created by pebe on 2015-09-11.
 */
@RunWith(MockitoJUnitRunner.class)
public class TermsServiceImplTest {

    @Mock
    private MedgivandeTextRepository medgivandeTextRepository;

    @InjectMocks
    private TermsServiceImpl termsService;

    @Test
    public void testGetTerms() {

        MedgivandeText medgivandeText = new MedgivandeText();
        medgivandeText.setVersion(1L);
        medgivandeText.setMedgivandeText("Testtext");
        medgivandeText.setDatum(LocalDate.parse("2015-09-01").atStartOfDay());
        when(medgivandeTextRepository.findLatest()).thenReturn(medgivandeText);

        Terms response = termsService.getTerms();
        assertEquals(LocalDate.parse("2015-09-01").atStartOfDay(), response.getDate());
        assertEquals("Testtext", response.getText());
        assertEquals(1L, response.getVersion());
    }
}
