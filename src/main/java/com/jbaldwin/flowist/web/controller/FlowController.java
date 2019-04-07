package com.jbaldwin.flowist.web.controller;

import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.service.FlowService;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FlowController {

    private final FlowService flowService;

    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

    @GetMapping(value = "/flows", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Flow> getAllFlows() {
        return flowService.getAllFlows();
    }

    @PostMapping(value = "/flows", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Flow> saveFlow(@Valid @RequestBody Flow flow, Principal principal) {
        LOG.debug("Processing flow POST request");
        flow.setOwner(principal.getName());
        return flowService.saveFlow(flow);
    }

    @GetMapping(value = "/flows/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Flow> getFlow(@PathVariable UUID id) {
        LOG.debug("Processing flow GET request with id: {}", id);
        return flowService.getFlowById(id);
    }

    @PutMapping(value = "/flows/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Flow> updateFlow(@Valid @RequestBody Flow flow, @PathVariable UUID id) {
        LOG.info("Processing flow PUT request with id: {}", id);
        return flowService.updateFlow(flow, id);
    }

    @DeleteMapping(value = "/flows/{id}")
    public ResponseEntity<?> deleteFlow(@PathVariable UUID id) {
        LOG.info("Processing flow DELETE with id: {}", id);
        return flowService.deleteFlowById(id);
    }

}
