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
and run a set union operation on them.





