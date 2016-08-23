package se.inera.privatlakarportal.web.controller.api.dto;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
