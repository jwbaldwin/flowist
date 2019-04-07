package com.jbaldwin.flowist.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.google.gson.Gson;
import com.jbaldwin.flowist.domain.Log;
import com.jbaldwin.flowist.service.LogService;
import com.jbaldwin.flowist.support.MockGenerator;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LogControllerTest {

    @MockBean
    private LogService logService;
    private MockMvc mockMvc;
    private Gson gson;

    private UUID id = UUID.randomUUID();
    private UUID flowId = UUID.randomUUID();
    private UUID owner = UUID.randomUUID();
    private Log mockLog = MockGenerator.generateMockLog(id, flowId, owner);

    @Before

    public void setup() {
        mockMvc = standaloneSetup(new LogController(logService)).build();
        gson = new Gson();
    }

    @Test
    public void getAllLogsByFlowId_willReturnNoLogs_whenNoLogsExist() throws Exception {
        mockMvc.perform(get("/flows/" + flowId + "/logs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json("[]"));
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