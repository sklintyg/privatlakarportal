/*
 * Copyright (C) 2022 Inera AB (http://www.inera.se)
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.privatlakarportal.persistence.model.Privatlakare;
import se.inera.intyg.privatlakarportal.persistence.repository.PrivatlakareRepository;
import se.inera.intyg.privatlakarportal.service.monitoring.MonitoringLogService;

@ExtendWith(MockitoExtension.class)
class EraseServiceImplTest {

    @Mock
    private PrivatlakareRepository privatlakareRepository;
    @Mock
    private MonitoringLogService monitoringLogService;

    @InjectMocks
    private EraseServiceImpl eraseService;

    private static final String HSA_ID = "SE23100000175-5TEST";
    private static final Privatlakare PRIVATE_PRACTTIONER = new Privatlakare();

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(eraseService, "erasePrivatePractitioner", true);
    }

    @Test
    public void shouldMonitorlogWhenPrivatePractitionerIsErased() {
        doReturn(PRIVATE_PRACTTIONER).when(privatlakareRepository).findByHsaId(HSA_ID);

        eraseService.erasePrivatePractitioner(HSA_ID);

        verify(monitoringLogService).logUserErased(HSA_ID);
    }

    @Test
    public void shouldEraseWhenPrivatePractitionerIsFound() {
        doReturn(PRIVATE_PRACTTIONER).when(privatlakareRepository).findByHsaId(HSA_ID);

        eraseService.erasePrivatePractitioner(HSA_ID);

        verify(privatlakareRepository).delete(PRIVATE_PRACTTIONER);
    }

    @Test
    public void shouldThrowExceptionWhenDeletePrivatePractitionerFailure() {
        doReturn(PRIVATE_PRACTTIONER).when(privatlakareRepository).findByHsaId(HSA_ID);
        doThrow(new RuntimeException()).when(privatlakareRepository).delete(PRIVATE_PRACTTIONER);

        assertThrows(RuntimeException.class, () -> eraseService.erasePrivatePractitioner(HSA_ID));
    }

    @Test
    public void shouldNotEraseWhenNoPrivatePractitionerFound() {
        doReturn(null).when(privatlakareRepository).findByHsaId(HSA_ID);

        eraseService.erasePrivatePractitioner(HSA_ID);

        verifyNoMoreInteractions(privatlakareRepository);
    }

    @Test
    public void shouldNotMonitorLogWhenNoPrivatePractitionerFound() {
        doReturn(null).when(privatlakareRepository).findByHsaId(HSA_ID);

        eraseService.erasePrivatePractitioner(HSA_ID);

        verifyNoInteractions(monitoringLogService);
    }

    @Test
    public void shouldNotEraseWhenEraseConfigInactivated() {
        ReflectionTestUtils.setField(eraseService, "erasePrivatePractitioner", false);
        doReturn(PRIVATE_PRACTTIONER).when(privatlakareRepository).findByHsaId(HSA_ID);

        eraseService.erasePrivatePractitioner(HSA_ID);

        verifyNoMoreInteractions(privatlakareRepository);
    }

    @Test
    public void shouldNotMonitorLogWhenEraseConfigInactivated() {
        ReflectionTestUtils.setField(eraseService, "erasePrivatePractitioner", false);
        doReturn(PRIVATE_PRACTTIONER).when(privatlakareRepository).findByHsaId(HSA_ID);

        eraseService.erasePrivatePractitioner(HSA_ID);

        verifyNoInteractions(monitoringLogService);
    }

}
