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
    public void GetAValueFromIndexThatExists() {
        String s = "60175559138";
        index.insert(s);
        assertThat(index.search(s), is(true));
    }  
    
    @Test
    public void BestMatchSearch() {
        
        String s = "6017555";
        index.insert(s);
        
        TrieNode node = index.bestSearch("60175559138");
        assertThat(node.c, is (s));
    }  
}

