package com.spakai.composite;
import com.spakai.index.Index;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.ExecutionException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TwoKeyLookupTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    TwoKeyLookup<String,String> twoKeyLookup = new TwoKeyLookup<>(5);
    Index<String, String> callingNumberIndex;
    Index<String, String> calledNumberIndex;

    @Before
    public void setup() {
        callingNumberIndex = twoKeyLookup.getIndexOne();
        calledNumberIndex = twoKeyLookup.getIndexTwo();
        callingNumberIndex.load("5","Local");
        callingNumberIndex.load("7","Local");
        calledNumberIndex.load("6","Local");
        calledNumberIndex.load("7","National");
    }

    @Test
    public void GetAValueFromIndexThatExists() throws ExecutionException, InterruptedException {
        assertThat(twoKeyLookup.lookup("5","6").get().iterator().next(),is("Local"));
    }

    @Test
    public void GetAValueFromIndexThatDoesNotExistInOneOfTheIndexes() throws ExecutionException, InterruptedException {
      thrown.expect(ExecutionException.class);
      thrown.expectMessage("No match found");    
      twoKeyLookup.lookup("6","6").get();

    }

    @Test
    public void GetAValueFromIndexThatDoesNotExistDuringSetIntersection() throws ExecutionException, InterruptedException {
      thrown.expect(ExecutionException.class);
      thrown.expectMessage("No match found");    
      twoKeyLookup.lookup("7","7").get();

    }


}

