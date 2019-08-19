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
package se.inera.intyg.privatlakarportal.web.controller.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;

/**
 * Created by pebe on 2015-08-28.
 */
@ApiModel(description = "Response-object för config-tjänst")
public class GetConfigResponse {

    @ApiModelProperty(name = "webcertUrl", dataType = "String")
    private String webcertUrl;

    @ApiModelProperty(name = "webcertStartUrl", dataType = "String")
    private String webcertStartUrl;

    @ApiModelProperty(name = "befattningar", dataType = "Map<String, String>")
    private Map<String, String> befattningar;

    @ApiModelProperty(name = "vardformer", dataType = "Map<String, String>")
    private Map<String, String> vardformer;

    @ApiModelProperty(name = "verksamhetstyper", dataType = "Map<String, String>")
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
