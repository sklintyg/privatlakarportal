package se.inera.privatlakarportal.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(value = "html", method = GET, produces = "text/plain")
    public String getIndexPage() {
        return "/index.html";
    }

}
