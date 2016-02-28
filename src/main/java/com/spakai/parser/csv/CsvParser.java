package com.spakai.parser.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class CsvParser implements Iterator<Record> {
    
  private List<Record> list;
  
  private int position = 0;

  public CsvParser(String filePath) throws IOException {
   InputStream is = new FileInputStream(new File(filePath));
   BufferedReader br = new BufferedReader(new InputStreamReader(is));
 
   list = 
       br.lines()
       .map(line -> mapToRecord(line))
       .collect(toList());
  }
  
   public Record mapToRecord(String line) {
       String[] p = line.split(",");
       //temp code for 3 member Record
       return new Record(p[0], p[1], p[2]);
   }
  
  @Override
  public Record next() {
      Record record = list.get(position);
      position = position + 1;
      return record;
  }

  @Override
  public boolean hasNext() {
      if (position >= list.size() || list.get(position) == null) {
	      return false;
      } else {
	      return true;
      }
  } 
  
}


  
