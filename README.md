#Motivation
While working on index_search, the book that provided theory on which I wrote my code,  talked about composite indexes.

Let 's say we have the following table

| Calling Number  | Called Number | Rate      |
| ------------- | --------------- |-----------|
| 05            | 05              | Local     |
| 05            | 06              | National  |

and we have the following query 

select Rate from table where Calling number = 05 and Called Number = 06;

to do this we need two indexes, one for Calling Number -> Rate , one for Called Number ->Rate , we get the results of these lookups
and run a set intersection operation on them.


#Code design
The two lookups are independent and only when both results arrive , we do a set intersection operation which should translate to something like this

```
    public CompletableFuture<Set<V>> lookup(K callingNumber, K calledNumber) {
        CompletableFuture<Set<V>> calling = callingNumberIndex.bestMatch(callingNumber);
        CompletableFuture<Set<V>> called = calledNumberIndex.bestMatch(calledNumber);
        return calling.thenCombineAsync(called, (s1, s2) -> intersection(s1, s2));
    }
```
    
#Issues
Benchmarking tests show that best match search is slow. After refactoring the best match code to use TreeMap and floorEntry ( with help from StackOverFlow members),
my benchmark test went from 6000ms to best search 500 records to 41 ms to best search 50000 records. the exact search which basically uses a Hashmap with get() is at 19ms. 

I was able to get faster lookups using Tries.





