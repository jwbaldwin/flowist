package com.jbaldwin.flowist.service;

import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.domain.FlowStatus;
import com.jbaldwin.flowist.repository.FlowRepository;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FlowService {

    private final FlowRepository flowRepository;

    public FlowService(FlowRepository flowRepository) {
        this.flowRepository = flowRepository;
    }

    public List<Flow> getAllFlows() {
        LOG.info("Fetching all flows");
        return flowRepository.findAll();
    }

    public ResponseEntity<Flow> getFlowById(UUID id) {
        LOG.info("Fetching flow with ID: {}", id);

        return ResponseEntity.ok(flowRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Flow not found with id: " + id.toString())));
    }

    public ResponseEntity<Flow> saveFlow(Flow flow) {
        flow.setFlowStatus(FlowStatus.ACTIVE);

        LOG.info("Saving new flow: {}", flow);
        return ResponseEntity.ok(flowRepository.save(flow));
    }

    public ResponseEntity<Flow> updateFlow(Flow flow, UUID id) {
        LOG.info("Updating flow with ID: {} \n and {}", flow.getId(), flow);

        return flowRepository.findById(id).map(storedFlow -> {
            storedFlow.setActivity(flow.getActivity());
            storedFlow.setTitle(flow.getTitle());
            storedFlow.setContent(flow.getContent());
            storedFlow.setTags(flow.getTags());
            storedFlow.setFlowStatus(flow.getFlowStatus());
            return ResponseEntity.ok(flowRepository.save(storedFlow));
        }).orElseThrow(() -> new ResourceNotFoundException("Flow not found with id: " + id.toString()));
    }

    public ResponseEntity<?> deleteFlowById(UUID id) {
        LOG.info("Deleting flow with ID: {}", id);

        flowRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
