package com.spakai.parser.csv

import spock.lang.Specification

class TestRecord {

    public String callingNumber
    public String calledNumber
    public String classification

    def Record(String[] p) {
        callingNumber = p[0]
        calledNumber  = p[1]
        classification = p[2]
    }
}

class CsvParserSpecification extends Specification {

    def "File not found" () {
        setup:
            CsvParser<TestRecord> parser = new CsvParser<>()
        when:
            parser.load("dummy.csv")
        then:
            thrown IOException
    }
}