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

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceImplTest extends TestCase {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    public void subscriptionInUseShouldReturnTrueIfSubscriptionAdaptionOrRequired() {
        ReflectionTestUtils.setField(subscriptionService, "subscriptionAdaptation", true);
        ReflectionTestUtils.setField(subscriptionService, "subscriptionRequired", true);

        assertTrue(subscriptionService.isSubscriptionInUse());
    }

    @Test
    public void subscriptionAdaptationAndNotRequiredShouldReturnTrue() {
        ReflectionTestUtils.setField(subscriptionService, "subscriptionAdaptation", true);
        ReflectionTestUtils.setField(subscriptionService, "subscriptionRequired", false);

        assertTrue(subscriptionService.isSubscriptionAdaptationAndNotRequired());
    }

    @Test
    public void subscriptionAdaptationAndNotRequiredShouldReturnFalse() {
        ReflectionTestUtils.setField(subscriptionService, "subscriptionAdaptation", true);
        ReflectionTestUtils.setField(subscriptionService, "subscriptionRequired", true);

        assertFalse(subscriptionService.isSubscriptionAdaptationAndNotRequired());
    }

}