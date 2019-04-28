package com.jbaldwin.flowist.web.controller

import com.google.gson.Gson
import com.jbaldwin.flowist.domain.Flow
import com.jbaldwin.flowist.domain.FlowStatus
import com.jbaldwin.flowist.service.FlowService
import com.jbaldwin.flowist.support.MockGenerator
import org.spockframework.spring.SpringBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.security.Principal

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class FlowControllerSpec extends Specification {

    @SpringBean
    private FlowService flowService = Mock(FlowService)
    private MockMvc mockMvc
    private Gson gson

    private UUID id = UUID.randomUUID()
    private UUID owner = UUID.randomUUID()
    private Flow mockFlow = MockGenerator.generateMockFlow(id, owner)
    private Flow mockReturnFlow = MockGenerator.generateMockReturnFlow(id, owner)

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FlowController(flowService)).build()
        gson = new Gson()
    }


    def 'GET ALL flows will return no flows when no flows exist'() {
        when: 'a get request is made to FLOWS endpoint'
        def response = mockMvc.perform(get("/flows")).andReturn().response

        then: 'no flows are returned'
        response.status == HttpStatus.OK.value()
    }

    def 'GET flow by id will return flow with correct id'() {
        given: 'a mocked return flow'
        flowService.getFlowById(id) >> ResponseEntity.ok(mockReturnFlow)

        when: 'a get request is made for a flow by id'
        def response = mockMvc.perform(get("/flows/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn().response

        then: 'the mocked flow is returned'
        with(response) {
            status == HttpStatus.OK.value()
            with(gson.toJson(contentAsString)) {
                contains("id")
                contains("owner")
                contains("created")
                contains("updated")
            }
        }
    }

    def 'POST new flow will save flow with all information'() {
        given: 'a mocked flow and principal'
        Principal mockPrincipal = Mock(Principal.class)
        mockPrincipal.getName() >> owner.toString()
        flowService.saveFlow(mockFlow) >> ResponseEntity.ok(mockReturnFlow)

        when: 'the new flow is posted'
        def response = mockMvc.perform(post("/flows")
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(mockFlow)))
                .andReturn().response

        then: 'the mocked flow is saved'
        with(response) {
            status == HttpStatus.OK.value()
            with(gson.toJson(contentAsString)) {
                contains("id")
                contains("owner")
                contains("created")
                contains("updated")
            }
        }
    }

    def 'PUT an updated flow will update the flow with new information'() {
        given: 'an updated flow with new information'
        mockFlow.setFlowStatus(FlowStatus.COMPLETED)
        flowService.updateFlow(mockFlow, id) >> ResponseEntity.ok(mockReturnFlow)

        when: 'the mock flow is posted'
        def response = mockMvc.perform(put("/flows/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(mockFlow)))
                .andReturn().response

        then: 'the mocked flow is saved'
        with(response) {
            status == HttpStatus.OK.value()
            with(gson.toJson(contentAsString)) {
                contains("id")
                contains("owner")
                contains("created")
                contains("updated")
            }
        }
    }

    def 'DELETE a flow will delete the flow by id'() {
        when: 'the delete request is made'
        def response = mockMvc.perform(delete("/flows/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn().response

        then: 'the flow is deleted'
        response.status == HttpStatus.OK.value()
    }

}
