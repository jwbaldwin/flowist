package com.jbaldwin.flowist.web.controller;

import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.service.FlowService;
import com.jbaldwin.flowist.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/flows")
public class LogController {

        private final LogService logService;

        public LogController(LogService logService) {
            this.logService = logService;
        }

        @GetMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
        public List<Flow> getAllFlows() {
            return logService.getAllFlows();
        }

        @PostMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
        public ResponseEntity<Flow> saveFlow(@Valid @RequestBody Flow flow, Principal principal){
            log.debug("Processing POST request for log");
            flow.setOwner(principal.getName());
            return ResponseEntity.ok(flowService.saveFlow(flow));
        }

        @GetMapping(value= "/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
        public ResponseEntity<Flow> getFlow(@PathVariable UUID id){
            log.debug("Processing GET request with id: {}", id);
            return ResponseEntity.ok(flowService.getFlowById(id));
        }

        @PutMapping(value= "/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
        public ResponseEntity<Flow> updateFlow(@Valid @RequestBody Flow flow, @PathVariable UUID id) {
            log.info("Processing PUT request with id: {}", id);
            return ResponseEntity.ok(flowService.updateFlow(flow, id));
        }

        @DeleteMapping(value= "/{id}")
        public ResponseEntity<?> deleteFlow(@PathVariable UUID id){
            flowService.deleteFlowById(id);
            return ResponseEntity.ok("Deleted entity with id: " + id);
        }

    }

}
