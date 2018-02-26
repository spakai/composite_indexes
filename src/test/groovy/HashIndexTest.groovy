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

    def "First time insert for the specified key" () {
        setup:
            def Index hashIndex = new HashIndex()
        when:
            hashIndex.insert("60175559138","Local");
        then:
            notThrown Exception
    }

    def "Second time insert for the specified key" () {
        setup:
            def Index hashIndex = new HashIndex()
            hashIndex.insert("60175559138","Local")
        when:
            hashIndex.insert("60175559138","Local2")
        then:
            notThrown Exception
    }

    def "Retrieve value" () {
        setup:
            def Index hashIndex = new HashIndex();
            hashIndex.insert("60175559138","LocalX")
            hashIndex.insert("60175559138","LocalY")
            def expectedSet = ["LocalX","LocalY"] as Set
        when:
            def Set response = hashIndex.exactMatch("60175559138")
        then:
            response == expectedSet
    }

    def "Insert null" () {
        setup:
            def Index hashIndex = new HashIndex();
        when:
            hashIndex.insert(null,null);
        then:
            thrown NullPointerException
    }
}