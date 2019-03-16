package com.jbaldwin.flowist.web.controller;

import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.service.FlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/flows")
public class FlowController {

    private final FlowService flowService;

    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Flow> getFlow(@RequestParam UUID id){
        log.debug("Processing GET request with id: {}", id);
        return ResponseEntity.ok(flowService.getFlowById(id));
    }

    @GetMapping(value ="/all", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Flow> getAllFlows() {
        return flowService.getAllFlows();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Flow> saveFlow(@Valid @RequestBody Flow flow){
        log.debug("Processing POST request");
        return ResponseEntity.ok(flowService.saveFlow(flow));
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Flow> updateFlow(@Valid @RequestBody Flow flow, @RequestParam UUID id) {
        log.info("Processing PUT request with id: {}", id);
        return ResponseEntity.ok(flowService.updateFlow(flow, id));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFlow(@RequestParam UUID id){
        flowService.deleteFlowById(id);
        return ResponseEntity.ok("Deleted entity with id: " + id);
    }

}
