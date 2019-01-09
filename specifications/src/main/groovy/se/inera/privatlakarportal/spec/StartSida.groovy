/*
 * Copyright (C) 2019 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.spec

import se.inera.intyg.privatlakarportal.page.ErrorPage
import se.inera.intyg.privatlakarportal.page.StartPage
import se.inera.intyg.privatlakarportal.page.TermsPage

class Startsida {

    public void gåTillStartsidan() {
        Browser.drive {
            to StartPage
        }
    }

    public boolean felsidanVisas() {
        boolean result
        Browser.drive {
            result = at ErrorPage
        }
        return result
    }

    public boolean startsidanVisas() {
        boolean result
        Browser.drive {
            result = at StartPage
        }
        return result
    }

    public void startaRegistrering() {
        Browser.drive {
            page.startaRegistrering()
        }
    }

    public void visaAnvändarvillkor() {
        Browser.drive {
            page.visaAnvändarvillkoren()
        }
    }

    public boolean användarvillkorenVisas() {
        boolean result
        Browser.drive {
            result = at TermsPage
        }
        return result
    }

    public void stängAnvändarvillkoren() {
        Browser.drive {
            page.dismissDialog()
        }
    }

}
