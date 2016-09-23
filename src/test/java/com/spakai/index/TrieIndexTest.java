package com.spakai.index;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TrieIndexTest {

    TrieIndex index;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        index = new TrieIndex();
    }

    @Test
    public void BestMatchSearch() {
        
        index.insert("0060175559138");
        index.insert("006017555");
        index.insert("006017");
        index.insert("0060");
        assertThat(index.bestSearch("0060175552020"), is ("006017555"));
    }  
}

