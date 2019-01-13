package com.jbaldwin.novu.service;

import com.jbaldwin.novu.repository.FlowRepository;
import org.springframework.stereotype.Service;

@Service
public class FlowService {

    private final FlowRepository flowRepository;

    public FlowService(FlowRepository flowRepository) {
        this.flowRepository = flowRepository;
    }

}
