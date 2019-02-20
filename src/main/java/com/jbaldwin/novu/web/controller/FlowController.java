package com.jbaldwin.novu.web.controller;

import com.jbaldwin.novu.domain.BaseFlow;
import com.jbaldwin.novu.service.FlowService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class FlowController {

    private final FlowService flowService;

    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

    @GetMapping(value = "/flow/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Optional<BaseFlow> getFlowById(@PathVariable UUID id) {
        return flowService.getFlowById(id);
    }

    @GetMapping(value = "/flow", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Iterable<BaseFlow> getAllFlows() {
        return flowService.getAllFlows();
    }

    @PostMapping(value = "/flow", produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseFlow saveFlow(@RequestBody BaseFlow flow) {
        return flowService.saveFlow(flow);
    }

}
