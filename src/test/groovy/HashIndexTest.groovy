import com.spakai.exception.NoMatchException
import com.spakai.index.HashIndex
import com.spakai.index.Index
import spock.lang.Specification

class HashIndexSpecification extends Specification {

    def "If asked to do best match throw UnsupportedOperationException" () {
        setup:
            Index hashIndex = new HashIndex()
        when:
            hashIndex.bestMatch("something")
        then:
            thrown UnsupportedOperationException
    }

    def "First time insert for the specified key" () {
        setup:
            Index hashIndex = new HashIndex()
        when:
            hashIndex.insert("60175559138","Local")
        then:
            notThrown Exception
    }

    def "Second time insert for the specified key" () {
        setup:
            Index hashIndex = new HashIndex()
            hashIndex.insert("60175559138","Local")
        when:
            hashIndex.insert("60175559138","Local2")
        then:
            notThrown Exception
    }

    def "Retrieve value" () {
        setup:
            Index hashIndex = new HashIndex()
            hashIndex.insert("60175559138","LocalX")
            hashIndex.insert("60175559138","LocalY")
            def expectedSet = ["LocalX","LocalY"] as Set
        when:
            Set response = hashIndex.exactMatch("60175559138")
        then:
            response == expectedSet
    }

    def "Try to insert a null value" () {
        setup:
            Index hashIndex = new HashIndex()
        when:
            hashIndex.insert(null,null)
        then:
            thrown NullPointerException
    }

    def "Try to search a null value" () {
        setup:
            Index hashIndex = new HashIndex()
        when:
            hashIndex.exactMatch(null)
        then:
            thrown NullPointerException
    }

    def "No match found" () {
        setup:
            Index hashIndex = new HashIndex()
        when:
            hashIndex.exactMatch("iamnotthere")
        then:
            thrown NoMatchException
    }
}