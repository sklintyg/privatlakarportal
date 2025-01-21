/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.common.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import se.inera.intyg.privatlakarportal.persistence.model.LegitimeradYrkesgrupp;
import se.inera.intyg.privatlakarportal.persistence.model.Privatlakare;

/**
 * Created by pebe on 2015-09-07.
 */
@RunWith(MockitoJUnitRunner.class)
public class PrivatlakareUtilsTest {

    @Test
    public void testLakare() {
        Privatlakare privatlakare = new Privatlakare();
        Set<LegitimeradYrkesgrupp> legitimeradYrkesgrupper = new HashSet<LegitimeradYrkesgrupp>();
        legitimeradYrkesgrupper.add(new LegitimeradYrkesgrupp(privatlakare, "Extra", "E"));
        legitimeradYrkesgrupper.add(new LegitimeradYrkesgrupp(privatlakare, "Läkare", "LK"));
        legitimeradYrkesgrupper.add(new LegitimeradYrkesgrupp(privatlakare, "Mer", "M"));
        privatlakare.setLegitimeradeYrkesgrupper(legitimeradYrkesgrupper);

        assertTrue(PrivatlakareUtils.hasLakareLegitimation(privatlakare));
    }

    @Test
    public void testEjLakare() {
        Privatlakare privatlakare = new Privatlakare();
        Set<LegitimeradYrkesgrupp> legitimeradYrkesgrupper = new HashSet<LegitimeradYrkesgrupp>();
        legitimeradYrkesgrupper.add(new LegitimeradYrkesgrupp(privatlakare, "Extra", "E"));
        legitimeradYrkesgrupper.add(new LegitimeradYrkesgrupp(privatlakare, "Mer", "M"));
        privatlakare.setLegitimeradeYrkesgrupper(legitimeradYrkesgrupper);

        assertFalse(PrivatlakareUtils.hasLakareLegitimation(privatlakare));
    }

}
