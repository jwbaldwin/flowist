package com.jbaldwin.novu.service;

import com.jbaldwin.novu.domain.BaseFlow;
import com.jbaldwin.novu.domain.FlowStatus;
import com.jbaldwin.novu.repository.FlowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FlowService {

    private final FlowRepository flowRepository;

    public FlowService(FlowRepository flowRepository) {
        this.flowRepository = flowRepository;
    }

    public Optional<BaseFlow> getFlowById(UUID id) {
        log.info("Finding flow with ID: {}", id);

        return flowRepository.findById(id);
    }

    public Iterable<BaseFlow> getAllFlows() {
        log.info("Finding all flows");

        return flowRepository.findAll();
    }

    public BaseFlow saveFlow(BaseFlow flow) {
        flow.setFlowStatus(FlowStatus.ACTIVE);
        flow.setCreated(Instant.now().toString());

        log.info("Saving new flow: {}", flow);

        return flowRepository.save(flow);
    }
}
