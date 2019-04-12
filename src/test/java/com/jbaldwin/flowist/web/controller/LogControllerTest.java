package com.jbaldwin.flowist.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.jbaldwin.flowist.domain.Log;
import com.jbaldwin.flowist.service.LogService;
import com.jbaldwin.flowist.support.MockGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
//@RunWith(MockitoJUnitRunner.class)
public class LogControllerTest {

    @Mock
    private LogService logService;
    @InjectMocks
    private LogController logController;

    private MockMvc mockMvc;
    private Gson gson;

    private UUID id = UUID.randomUUID();
    private UUID flowId = UUID.randomUUID();
    private UUID owner = UUID.randomUUID();
    private Log mockLog = MockGenerator.generateMockLog(id, flowId, owner);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(logController)
            .build();
        gson = new Gson();
    }

    @Test
    public void getAllLogsByFlowId_willReturnNoLogs_whenNoLogsExist() throws Exception {
        List<Log> mockList = new ArrayList<>();
        mockList.add(mockLog);
        Mockito.when(logService.findByFlowId(flowId)).thenReturn(mockList);

        mockMvc.perform(get("/flows/" + flowId + "/logs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(1)));

        verify(logService, times(1)).findByFlowId(flowId);
        verifyNoMoreInteractions(logService);
    }

    @Test
    public void saveLog_willSaveLog_andSetInformation() throws Exception {
        Mockito.when(logService.saveLog(flowId, mockLog)).thenReturn(ResponseEntity.ok(mockLog));

        MvcResult result = mockMvc.perform(post("/flows/" + flowId + "/logs")
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(mockLog)))
            .andExpect(status().isOk())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(gson.toJson(mockLog)));
        verify(logService, times(1)).findByFlowId(flowId);
        verify(logService, times(1)).saveLog(flowId, mockLog);
        verifyNoMoreInteractions(logService);
    }

    @Test
    public void updateLog() throws Exception {
        mockLog.setContent("UPDATED");
        Mockito.when(logService.updateLog(flowId, id, mockLog)).thenReturn(ResponseEntity.ok(mockLog));

        MvcResult result = mockMvc.perform(put("/flows/" + flowId + "/logs/" + id)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(mockLog)))
            .andExpect(status().isOk())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(gson.toJson(mockLog)));
    }

    @Test
    public void deleteLog_willDeleteLogWithFlowId() throws Exception {
        Mockito.when(logService.deleteLog(flowId, id)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/flows/" + flowId + "/logs/" + id)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk());
    }
}