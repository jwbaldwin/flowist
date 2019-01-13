package com.jbaldwin.novu.web.controller;

import com.jbaldwin.novu.service.FlowService;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/flows")
public class FlowController {

    private final FlowService flowService;

    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

}
