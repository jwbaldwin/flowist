package com.jbaldwin.flowist.web.controller

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jbaldwin.flowist.domain.Log
import com.jbaldwin.flowist.service.LogService
import com.jbaldwin.flowist.support.MockGenerator
import org.spockframework.spring.SpringBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class LogControllerSpec extends Specification {

    @SpringBean
    private LogService logService = Mock(LogService)
    private MockMvc mockMvc
    private Gson gson

    private UUID id = UUID.randomUUID()
    private UUID flowId = UUID.randomUUID()
    private UUID owner = UUID.randomUUID()
    private Log mockLog = MockGenerator.generateMockLog(id, flowId, owner)

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LogController(logService)).build()
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    def 'GET ALL logs by flow id will return no logs when none exist'() {
        given: 'a mocked set of logs'
        List<Log> mockList = new ArrayList<>()
        mockList.add(mockLog)
        logService.findByFlowId(flowId) >> mockList

        when: 'a get request is made with flow id for logs'
        def response = mockMvc.perform(get("/flows/" + flowId + "/logs")).andReturn().response

        then: 'the list of logs is returned'
        with(response) {
            status == HttpStatus.OK.value()
            contentAsString == gson.toJson(mockList)
        }
    }

    def 'POST new log will save log with all information'() {
        given: 'a mocked log'
        logService.saveLog(_, _ as Log) >> ResponseEntity.ok(mockLog)

        when: 'a new log is posted'
        def response = mockMvc.perform(post("/flows/" + flowId + "/logs")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(mockLog)))
                .andReturn().response

        then: 'the mocked flow is saved'
        with(response) {
            status == HttpStatus.OK.value()
            contentAsString == gson.toJson(mockLog)
        }
    }

    def 'PUT an updated log will update the log with the new information'() {
        given: 'an updated log with new information'
        mockLog.setContent("UPDATED")
        logService.updateLog(flowId, id, _) >> ResponseEntity.ok(mockLog)

        when: 'the updated log is put'
        def response = mockMvc.perform(put("/flows/" + flowId + "/logs/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(mockLog)))
                .andReturn().response

        then: 'the mocked flow is saved'
        with(response) {
            status == HttpStatus.OK.value()
            contentAsString == gson.toJson(mockLog)
        }
    }

    def 'DELETE a log will delete the log by id'() {
        given: 'a log should be deleted'
        logService.deleteLog(flowId, id) >> ResponseEntity.ok().build()

        when: 'the delete request is made'
        def response = mockMvc.perform(delete("/flows/" + flowId + "/logs/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn().response

        then: 'the flow is deleted'
        response.status == HttpStatus.OK.value()
    }

}
