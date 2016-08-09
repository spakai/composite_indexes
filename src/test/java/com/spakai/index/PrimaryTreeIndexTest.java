package com.spakai.index;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimaryTreeIndexTest {

    PrimaryTreeIndex<String> index;
    ExecutorService pool;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setup() {
        pool = Executors.newFixedThreadPool(1);
        index = new PrimaryTreeIndex<String>(pool);
        index.load("5","Local");
        index.load("62","National");
        index.load("72123","Free");
        index.load("7212","LocalLocal");
        index.load("721","Local");
        
    }

    @Test
    public void GetAExactMatchValueFromIndexThatExists() {
        try {
            assertThat(index.asyncExactMatch("5").get().iterator().next(), is("Local"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     @Test
    public void GetABestMatchValueFromIndexThatExists() {
        try {
            assertThat(index.asyncBestMatch("72125").get().iterator().next(), is("LocalLocal"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
      @Test
    public void GetASyncBestMatchValueFromIndexThatExists() {
        try {
            assertThat(index.syncBestMatch("72125").iterator().next(), is("LocalLocal"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
        @Test
    public void GetABestMatchWhichIsExactMatchFromIndexThatExists() {
        try {
            assertThat(index.asyncBestMatch("62").get().iterator().next(), is("National"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void GetAValueFromIndexThatDoesNotExist() throws Exception {
        thrown.expect(ExecutionException.class);
        thrown.expectMessage("No match found");

        index.asyncExactMatch("4").get();
    }

}

