package com.jbaldwin.flowist.service;

import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.domain.FlowStatus;
import com.jbaldwin.flowist.repository.FlowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FlowService {

    private final FlowRepository flowRepository;

    public FlowService(FlowRepository flowRepository) {
        this.flowRepository = flowRepository;
    }

    public List<Flow> getAllFlows() {
        log.info("Fetching all flows");
        return flowRepository.findAll();
    }

    public Flow getFlowById(UUID id) {
        log.info("Fetching flow with ID: {}", id);

        return flowRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flow not found with id: " + id.toString()));
    }

    public Flow saveFlow(Flow flow) {
        flow.setFlowStatus(FlowStatus.ACTIVE);

        log.info("Saving new flow: {}", flow);

        return flowRepository.save(flow);
    }

    public Flow updateFlow(Flow flow, UUID id) {
        log.info("Updating flow with ID: {} \n and {}", flow.getId(), flow);

       Flow updatedFlow = getFlowById(id);
        updatedFlow.setActivity(flow.getActivity());
        updatedFlow.setTitle(flow.getTitle());
        updatedFlow.setContent(flow.getContent());
        updatedFlow.setTags(flow.getTags());
        updatedFlow.setFlowStatus(flow.getFlowStatus());

        return flowRepository.save(updatedFlow);
    }

    public void deleteFlowById(UUID id) {
        log.info("Deleting flow with ID: {}", id);

        flowRepository.deleteById(id);
    }


}
