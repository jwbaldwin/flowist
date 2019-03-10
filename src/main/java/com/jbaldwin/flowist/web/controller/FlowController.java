package com.jbaldwin.flowist.web.controller;

import com.jbaldwin.flowist.domain.BaseFlow;
import com.jbaldwin.flowist.service.FlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
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
