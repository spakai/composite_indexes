package com.spakai.composite;

import com.spakai.index.Index;
import com.spakai.index.PrimaryHashIndex;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TwoKeyLookupTest {

    Index<String, String> callingNumberIndex;
    Index<String, String> calledNumberIndex;
    TwoKeyLookup<String,String> twoKeyLookup;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setup() {
        callingNumberIndex = new PrimaryHashIndex<>();
        calledNumberIndex = new PrimaryHashIndex<>();
        callingNumberIndex.load("5","Local");
        callingNumberIndex.load("7","Local");
        calledNumberIndex.load("6","Local");
        calledNumberIndex.load("7","National");
        twoKeyLookup = new TwoKeyLookup<>(callingNumberIndex, calledNumberIndex);

    }

    @Test
    public void GetAValueFromIndexThatExists() throws ExecutionException, InterruptedException {
        assertThat(twoKeyLookup.lookup("5","6").get().iterator().next(),is("Local"));

    }

    @Test
    public void GetAValueFromIndexThatDoesNotExistInOneOfTheIndexes() throws ExecutionException, InterruptedException {
      thrown.expect(Exception.class);                                                             
      thrown.expectMessage("No match found");    
      twoKeyLookup.lookup("6","6").get();

    }

    @Test
    public void GetAValueFromIndexThatDoesNotExistDuringSetIntersection() throws ExecutionException, InterruptedException {
      thrown.expect(Exception.class);                                                             
      thrown.expectMessage("No match found");    
      twoKeyLookup.lookup("7","7").get();

    }


}

