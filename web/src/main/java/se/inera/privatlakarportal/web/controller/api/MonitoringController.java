package se.inera.privatlakarportal.web.controller.api;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import se.inera.privatlakarportal.service.monitoring.HealthCheckServiceImpl;
import se.inera.privatlakarportal.service.monitoring.dto.HealthStatus;

@Api(value = "/monitoring", description = "Healthcheck-api för Privatläkarportalen", produces = MediaType.APPLICATION_JSON)
@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    @Autowired
    private HealthCheckServiceImpl healthCheck;

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public Response getDBStatus() {
        HealthStatus status = healthCheck.checkDB();
        String xml = buildXMLResponse(status);
        return Response.ok(xml).build();
    }

    @RequestMapping(value = "/hsa", method = RequestMethod.GET)
    public Response getHsaStatus() {
        HealthStatus status = healthCheck.checkHSA();
        String xml = buildXMLResponse(status);
        return Response.ok(xml).build();
    }

    @RequestMapping(value = "/uptime", method = RequestMethod.GET)
    public Response getUpTimeStatus() {
        HealthStatus status = healthCheck.checkUptime();
        String xml = buildXMLResponse(status);
        return Response.ok(xml).build();
    }

    @RequestMapping(value = "/usernumber", method = RequestMethod.GET)
    public Response getUpNumberOfUsers() {
        HealthStatus status = healthCheck.checkNbrOfUsers();
        String xml = buildXMLResponse(status);
        return Response.ok(xml).build();
    }

    private String buildXMLResponse(HealthStatus status) {
        return buildXMLResponse(status.isOk(), status.getMeasurement());
    }

    private String buildXMLResponse(boolean ok, long time) {
        StringBuilder sb = new StringBuilder();
        sb.append("<pingdom_http_custom_check>");
        sb.append("<status>" + (ok ? "OK" : "FAIL") + "</status>");
        sb.append("<response_time>" + time + "</response_time>");
        sb.append("</pingdom_http_custom_check>");
        return sb.toString();
    }
}
