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
            print(hashIndex.toString());
        when:
            def Set<String> response = hashIndex.exactMatch("60175559138")
        then:
            response  == ["LocalX", "LocalY"]
    }

}