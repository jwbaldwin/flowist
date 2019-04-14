package com.jbaldwin.flowist.web.controller;

import com.jbaldwin.flowist.domain.Log;
import com.jbaldwin.flowist.service.LogService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/flows/{flowId}/logs")
    public List<Log> getAllLogsByFlowId(@PathVariable UUID flowId) {
        return logService.findByFlowId(flowId);
    }

    @PostMapping("/flows/{flowId}/logs")
    public ResponseEntity<Log> saveLog(@PathVariable UUID flowId, @Valid @RequestBody Log log) {
        LOG.debug("Processing log POST request");
        return logService.saveLog(flowId, log);
    }

    @PutMapping("/flows/{flowId}/logs/{logId}")
    public ResponseEntity<Log> updateLog(@PathVariable UUID flowId, @PathVariable UUID logId, @Valid @RequestBody Log log) {
        LOG.info("Processing log PUT request with id: {}", logId);
        return logService.updateLog(flowId, logId, log);
    }

    @DeleteMapping("/flows/{flowId}/logs/{logId}")
    public ResponseEntity<Object> deleteLog(@PathVariable UUID flowId, @PathVariable UUID logId) {
        LOG.info("Processing log DELETE with id: {}", logId);
        return logService.deleteLog(flowId, logId);
    }

}
