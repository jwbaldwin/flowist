package com.jbaldwin.flowist.service;

import com.jbaldwin.flowist.domain.Log;
import com.jbaldwin.flowist.repository.FlowRepository;
import com.jbaldwin.flowist.repository.LogRepository;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogService {

    private final LogRepository logRepository;
    private final FlowRepository flowRepository;

    public LogService(LogRepository logRepository, FlowRepository flowRepository) {
        this.logRepository = logRepository;
        this.flowRepository = flowRepository;
    }

    public List<Log> findByFlowId(UUID flowId) {
        LOG.info("Fetching all logs for id: {}", flowId);
        return logRepository.findByFlowId(flowId);
    }

    public ResponseEntity<Log> saveLog(UUID flowId, Log log) {
        LOG.info("Saving log attached to flow with id: {}", flowId);
        return flowRepository.findById(flowId).map(flow -> {
            log.setFlow(flow);
            return ResponseEntity.ok(logRepository.save(log));
        }).orElseThrow(() -> new ResourceNotFoundException("Flow not found with id: " + flowId.toString()));
    }

    public ResponseEntity<Log> updateLog(UUID flowId, UUID logId, Log log) {
        LOG.info("Updating log attached to flow with id: {}", flowId);
        if (!flowRepository.existsById(flowId)) {
            throw new ResourceNotFoundException("Flow not found with id: " + flowId.toString());
        }
        return logRepository.findById(logId).map(storedLog -> {
            log.setContent(storedLog.getContent());
            return ResponseEntity.ok(logRepository.save(log));
        }).orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + logId.toString()));
    }

    public ResponseEntity<Object> deleteLog(UUID flowId, UUID logId) {
        LOG.info("Delete log with id: {}", logId);
        return logRepository.findByIdAndFlowId(logId, flowId).map(log -> {
            logRepository.delete(log);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + logId.toString()));
    }
}
