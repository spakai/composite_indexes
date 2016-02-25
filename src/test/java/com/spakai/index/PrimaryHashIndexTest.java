package com.spakai.index;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.Equals;
import java.util.concurrent.Future;

public class PrimaryHashIndexTest {

    PrimaryHashIndex<String, String> index;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setup() {
        index = new PrimaryHashIndex<>();
        index.load("5","Local");
    }

    @Test
    public void GetAValueFromIndexThatExists() {
        try {
            assertThat(index.exactMatch("5").get().iterator().next(), is("Local"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void GetAValueFromIndexThatDoesNotExist() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("No match found");

        index.exactMatch("4").get();
    }

}

