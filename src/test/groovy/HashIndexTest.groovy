import com.spakai.index.HashIndex
import com.spakai.index.Index
import spock.lang.Specification

class HashIndexSpecification extends Specification {

    def "If asked to do best match throw UnsupportedOperationException" () {
        setup:
            def Index hashIndex = new HashIndex()
        when:
            hashIndex.bestMatch("something")
        then:
            thrown UnsupportedOperationException
    }
}