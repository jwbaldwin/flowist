package com.jbaldwin.flowist.service;

import com.jbaldwin.flowist.domain.Flow;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class TagExtractionService {

    private static String TAG_PREFIX = "#";

    ArrayList<String> extractTags(Flow flow) {
        ArrayList<String> extractedTags = flow.getTags();

        extractedTags.add(extractTagsFromActivity(flow.getActivity()));

        return extractedTags;
    }

    private String extractTagsFromActivity(String activity) {
        return TAG_PREFIX + activity.toLowerCase();
    }

}
