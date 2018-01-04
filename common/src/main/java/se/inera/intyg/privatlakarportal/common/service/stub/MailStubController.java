/*
 * Copyright (C) 2018 Inera AB (http://www.inera.se)
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
package se.inera.intyg.privatlakarportal.common.service.stub;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stub/mails")
@Profile({ "dev", "mail-stub" })
public class MailStubController {

    @Autowired
    private MailStubStore mailStore;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> getMails() throws MessagingException, IOException {
        return mailStore.getMails();
    }

    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public Response deleteMailbox() throws IOException, MessagingException {
        mailStore.getMails().clear();
        return Response.ok().build();
    }
}
