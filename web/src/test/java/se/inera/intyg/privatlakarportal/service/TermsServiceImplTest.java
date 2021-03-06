/*
 * Copyright (C) 2021 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import se.inera.intyg.privatlakarportal.integration.terms.services.dto.Terms;
import se.inera.intyg.privatlakarportal.persistence.model.MedgivandeText;
import se.inera.intyg.privatlakarportal.persistence.repository.MedgivandeTextRepository;

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
