package com.jbaldwin.flowist

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [Flowist])
@ActiveProfiles(["boot-run", "test"])
class UnitTestSpec extends Specification {

}