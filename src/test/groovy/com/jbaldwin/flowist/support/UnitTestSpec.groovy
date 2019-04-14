package com.jbaldwin.flowist.support

import com.jbaldwin.flowist.FlowistApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [FlowistApplication])
@ActiveProfiles(["boot-run", "test"])
abstract class UnitTestSpec extends Specification {}