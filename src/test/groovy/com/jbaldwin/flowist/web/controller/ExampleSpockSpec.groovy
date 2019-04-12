package com.jbaldwin.flowist;

class ExampleSpockSpec extends UnitTestSpec {

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    void setup() {
        mockMvc = webApContextWetup(wac).build()
    }

    def 'testing the spock test'() {
        given: 'some testing thing'
        def testVar = 1

        when: 'something happens'
        def response = mockMvc.perform(get('/test')).andReturn().response

        then: 'the result'
        response.contentType = 'application/json'
        reponse.content = 'hello,wor.d'
    }
}