package com.spakai.composite;
import com.spakai.index.Index;
import com.spakai.index.PrimaryHashIndex;
import java.net.URL;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.concurrent.ExecutionException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TwoKeyExactMatchLookupTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    TwoKeyExactMatchLookup twoKeyExactMatchLookup ;
    
    @Before
    public void setup() {
        URL url = this.getClass().getResource("/data.csv");
        System.out.println(url.getPath());
        twoKeyExactMatchLookup = new TwoKeyExactMatchLookup(url.getPath(),2);
    }

    @Test
    public void AsyncGetAValueFromIndexThatExists() throws ExecutionException, InterruptedException {
        assertThat(twoKeyExactMatchLookup.asyncLookup("5","6").get().iterator().next(),is("Local"));
    }
    
     @Test
    public void SyncGetAValueFromIndexThatExists() throws ExecutionException, InterruptedException {
        assertThat(twoKeyExactMatchLookup.syncLookup("5","6").iterator().next(),is("Local"));
    }

    @Test
    public void AsyncGetAValueFromIndexThatDoesNotExistInOneOfTheIndexes() throws ExecutionException, InterruptedException {
      thrown.expect(ExecutionException.class);
      thrown.expectMessage("No match found");    
      twoKeyExactMatchLookup.asyncLookup("6","6").get();
    }

    @Test
    public void AsyncGetAValueFromIndexThatDoesNotExistDuringSetIntersection() throws ExecutionException, InterruptedException {
      thrown.expect(ExecutionException.class);
      thrown.expectMessage("No match found");    
      twoKeyExactMatchLookup.asyncLookup("5","7").get();

    }
}

