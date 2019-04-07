package com.jbaldwin.flowist.support;

import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.domain.FlowStatus;
import com.jbaldwin.flowist.domain.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class MockGenerator {

    public static Flow generateMockFlow(UUID id, UUID owner) {
        ArrayList<String> tags = new ArrayList<>(Collections.singleton("mock"));
        return Flow.builder()
            .id(id)
            .owner(owner.toString())
            .activity("researching")
            .title("Mock flow title")
            .content("Mock flow content")
            .tags(tags)
            .flowStatus(FlowStatus.ACTIVE)
            .build();
    }

    public static Log generateMockLog(UUID id, UUID flowId, UUID owner) {
        return Log.builder()
            .id(id)
            .content("This is a test log")
            .flow(generateMockFlow(flowId, owner))
            .build();
    }

}
