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
package se.inera.intyg.privatlakarportal.web.controller.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

/**
 * Created by pebe on 2015-08-28.
 */
@Schema(description = "Response-object för config-tjänst")
public class GetConfigResponse {

    @Schema(name = "webcertUrl", type = "String")
    private String webcertUrl;

    @Schema(name = "webcertStartUrl", type = "String")
    private String webcertStartUrl;

    @Schema(name = "befattningar", type = "Map<String, String>")
    private Map<String, String> befattningar;

    @Schema(name = "vardformer", type = "Map<String, String>")
    private Map<String, String> vardformer;

    @Schema(name = "verksamhetstyper", type = "Map<String, String>")
    private Map<String, String> verksamhetstyper;

    public GetConfigResponse(String webcertUrl, String webcertStartUrl, Map<String, String> befattningar,
        Map<String, String> vardformer, Map<String, String> verksamhetstyper) {
        this.webcertUrl = webcertUrl;
        this.webcertStartUrl = webcertStartUrl;
        this.befattningar = befattningar;
        this.vardformer = vardformer;
        this.verksamhetstyper = verksamhetstyper;
    }

    public String getWebcertUrl() {
        return webcertUrl;
    }

    public String getWebcertStartUrl() {
        return webcertStartUrl;
    }

    public Map<String, String> getBefattningar() {
        return befattningar;
    }

    public Map<String, String> getVardformer() {
        return vardformer;
    }

    public Map<String, String> getVerksamhetstyper() {
        return verksamhetstyper;
    }
}
