package com.jbaldwin.flowist.web.controller


import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class IndexControllerSpec extends Specification {

    private MockMvc mockMvc

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).build()
    }

    def 'GET test should return default message'() {
        when: 'a get request is made to TEST endpoint'
        def response = mockMvc.perform(get("/test")).andReturn().response

        then: 'the status is 200'
        response.status == HttpStatus.OK.value()

        and: 'the content is the test string'
        response.contentAsString == 'This is the api!'
    }
}