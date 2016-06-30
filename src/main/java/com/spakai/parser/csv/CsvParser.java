package com.spakai.parser.csv;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CsvParser<T> implements Iterable<T> {
    
  private final Class<T> clazz; 
    
  private List<T> list;
  
  public void load(String filePath) throws IOException {
      list = Files.lines( Paths.get(filePath))
              .map(line -> mapToRecord(line))
              .collect(toList());
  }
  
  public CsvParser(Class<T> clazz)  {
      this.clazz = clazz;
  }
  
  public T mapToRecord(String line)  {
      String[] p = line.split(",");
      try {
          return clazz.getDeclaredConstructor(String[].class).newInstance(new Object[] { p });
      } catch (InstantiationException | IllegalAccessException
              | IllegalArgumentException | InvocationTargetException
              | NoSuchMethodException | SecurityException e) {
          return null;
      }
  }
  
  @Override
  public Iterator<T> iterator() {
      return new Iterator<T>() {
          
          private int position = 0;
          
          @Override
          public T next() {
              T record = list.get(position);
              position = position + 1;
              return record;
          }
        
          @Override
          public boolean hasNext() {
              return !(position >= list.size() || list.get(position) == null);
          } 
      };
  }
}
