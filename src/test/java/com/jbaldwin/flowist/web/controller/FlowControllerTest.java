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
import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.domain.FlowStatus;
import com.jbaldwin.flowist.service.FlowService;
import com.jbaldwin.flowist.support.MockGenerator;
import java.security.Principal;
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
public class FlowControllerTest {

    @MockBean
    private FlowService flowService;
    private MockMvc mockMvc;
    private Gson gson;

    private UUID id = UUID.randomUUID();
    private UUID owner = UUID.randomUUID();
    private Flow mockFlow = MockGenerator.generateMockFlow(id, owner);

    @Before
    public void setup() {
        mockMvc = standaloneSetup(new FlowController(flowService)).build();
        gson = new Gson();
    }

    @Test
    public void getAllFlows_willReturnNoFlows_whenNoFlowsExist() throws Exception {
        mockMvc.perform(get("/flows"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json("[]"));
    }

    @Test
    public void getFlowById_willReturnFlowWithCorrectId() throws Exception {
        Mockito.when(flowService.getFlowById(id)).thenReturn(ResponseEntity.ok(mockFlow));

        MvcResult result = mockMvc.perform(get("/flows/" + id)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(gson.toJson(mockFlow)));
    }

    @Test
    public void saveFlow_willSaveFlow_andSetInformation() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(owner.toString());
        Mockito.when(flowService.saveFlow(mockFlow)).thenReturn(ResponseEntity.ok(mockFlow));

        MvcResult result = mockMvc.perform(post("/flows")
            .principal(mockPrincipal)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(mockFlow)))
            .andExpect(status().isOk())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(gson.toJson(mockFlow)));
    }

    @Test
    public void updateFlow_willUpdateFlowById() throws Exception {
        mockFlow.setFlowStatus(FlowStatus.COMPLETED);
        Mockito.when(flowService.updateFlow(mockFlow, id)).thenReturn(ResponseEntity.ok(mockFlow));

        MvcResult result = mockMvc.perform(put("/flows/" + id)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(mockFlow)))
            .andExpect(status().isOk())
            .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(gson.toJson(mockFlow)));
    }

    @Test
    public void deleteFlow_willDeleteFlowWithId() throws Exception {
        mockMvc.perform(delete("/flows/" + id)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk());
    }
}