/**
 * Copyright (C) 2016 Inera AB (http://www.inera.se)
 *
 * This file is part of privatlakarportal (https://github.com/sklintyg/privatlakarportal).
 *
 * privatlakarportal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * privatlakarportal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.inera.intyg.privatlakarportal.web.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.infra.dynamiclink.model.DynamicLink;
import se.inera.intyg.infra.dynamiclink.service.DynamicLinkService;
import se.inera.intyg.privatlakarportal.common.integration.kodverk.Befattningar;
import se.inera.intyg.privatlakarportal.common.integration.kodverk.Vardformer;
import se.inera.intyg.privatlakarportal.common.integration.kodverk.Verksamhetstyper;
import se.inera.intyg.privatlakarportal.web.controller.api.dto.GetConfigResponse;

import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by pebe on 2015-08-28.
 */
@Api(value = "/config", description = "Config-api för Privatläkarportalen", produces = MediaType.APPLICATION_JSON)
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private Environment env;

    @Autowired
    private DynamicLinkService dynamicLinkService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "getConfig")
    public GetConfigResponse getConfig() {
        return new GetConfigResponse(
                env.getProperty("webcert.host.url"),
                env.getProperty("webcert.start.url"),
                Befattningar.getBefattningar(),
                Vardformer.getVardformer(),
                Verksamhetstyper.getVerksamhetstyper());
    }

    @RequestMapping(value = "/links", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "/links")
    public Map<String, DynamicLink> getLinks() {
        return dynamicLinkService.getAllAsMap();
    }
}