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
package se.inera.intyg.privatlakarportal.common.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by pebe on 2015-09-10.
 */
@Service
public class DateHelperServiceImpl implements DateHelperService {

    /*
     * Used for mocking now() in unit tests
     */
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
