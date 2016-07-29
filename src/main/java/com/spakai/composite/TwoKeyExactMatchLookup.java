package com.spakai.composite;

import com.spakai.exception.NoMatchException;
import com.spakai.index.Index;
import com.spakai.index.PrimaryHashIndex;
import com.spakai.parser.csv.CsvParser;
import com.spakai.parser.csv.Record;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwoKeyExactMatchLookup {

    private final ExecutorService pool;
    
    private final Index<String> callingNumberIndex;
    
    private final Index<String> calledNumberIndex;
    
    public TwoKeyExactMatchLookup(String dataFilePath, int threadPoolSize) {
         pool = Executors.newFixedThreadPool(threadPoolSize);
         callingNumberIndex = new PrimaryHashIndex<String> (pool);
         calledNumberIndex = new PrimaryHashIndex<String> (pool);
         
         loadDataIntoIndexes(callingNumberIndex, calledNumberIndex, dataFilePath);
         
    }
    
    private void loadDataIntoIndexes(Index<String> index1, 
            Index<String> index2, String filePath) {
        
         CsvParser<Record> parser = new CsvParser<Record>(Record.class);
         
         try {
            parser.load(filePath);
         } catch (Exception e) {
             // TODO
             e.printStackTrace();
         }
         
          for(Record record : parser) {
              callingNumberIndex.load(record.callingNumber, record.classification);
          } 
          
          for(Record record : parser) {
              calledNumberIndex.load(record.calledNumber, record.classification);
          }
        
    }
    
    public CompletableFuture<Set<String>> lookup(String callingNumber, String calledNumber) {
        CompletableFuture<Set<String>> calling = callingNumberIndex.asyncExactMatch(callingNumber);
        CompletableFuture<Set<String>> called = calledNumberIndex.asyncExactMatch(calledNumber);
        return calling.thenCombineAsync(called, (s1, s2) -> intersection(s1, s2));
    }
    
    public Set<String> intersection(Set<String> s1, Set<String> s2) {
        Set<String> set;
        set = new HashSet<>(s1);
        set.retainAll(s2);

        if (set.isEmpty()) {
            throw new NoMatchException("No match found");
        }

        return set;
    }
}
