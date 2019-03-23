package com.jbaldwin.flowist.web.controller;

import com.google.gson.Gson;
import com.jbaldwin.flowist.domain.Flow;
import com.jbaldwin.flowist.domain.FlowStatus;
import com.jbaldwin.flowist.service.FlowService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FlowControllerTest {

    private MockMvc mockMvc;
    Gson gson;

    private Flow mockFlow;
    private UUID id = UUID.randomUUID();
    private UUID owner = UUID.randomUUID();
    private ArrayList<String> tags = new ArrayList<>();

    @MockBean
    private FlowService flowService;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(new FlowController(flowService)).build();

        this.gson = new Gson();

        this.tags.add("mock");
        this.mockFlow = Flow.builder()
                .id(id)
                .owner(owner.toString())
                .activity("researching")
                .title("Mock flow title")
                .content("Mock flow content")
                .tags(tags)
                .created(Instant.now().toString())
                .flowStatus(FlowStatus.ACTIVE)
                .build();
    }

    @Test
    public void getAllFlows() throws Exception {
        this.mockMvc.perform(get("/flows/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("[]"));
    }

    @Test
    public void getFlowById() throws Exception {
        Mockito.when(flowService.getFlowById(this.id)).thenReturn(this.mockFlow);

        MvcResult result = this.mockMvc.perform(get("/flows?id=" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(this.gson.toJson(this.mockFlow)));

    }

    @Test
    public void saveFlow() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(this.owner.toString());
        Mockito.when(flowService.saveFlow(this.mockFlow)).thenReturn(this.mockFlow);

        MvcResult result = this.mockMvc.perform(post("/flows")
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(this.gson.toJson(this.mockFlow)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(this.gson.toJson(this.mockFlow)));
    }

    @Test
    public void updateFlow() throws Exception {
        Mockito.when(flowService.getFlowById(this.id)).thenReturn(this.mockFlow);

        this.mockFlow.setFlowStatus(FlowStatus.COMPLETED);
        Mockito.when(flowService.updateFlow(this.mockFlow, this.id))
                .thenReturn(this.mockFlow);

        MvcResult result = this.mockMvc.perform(put("/flows")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(this.gson.toJson(this.mockFlow)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo(this.gson.toJson(this.mockFlow)));
    }

    @Test
    public void deleteFlow() throws Exception {
        this.mockMvc.perform(delete("/flows?id=" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
    }
}