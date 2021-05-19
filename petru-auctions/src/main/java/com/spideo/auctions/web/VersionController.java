package com.spideo.auctions.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class VersionController {

    @GetMapping("${spring.data.rest.basePath}/version")
    public @ResponseBody String getVersion() {
        return "0.1";
    }

}
