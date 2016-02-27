package com.spakai.parser.csv;

import java.util.Iterator;
import java.util.List;

public class CsvParser implements Iterator<Record> {

  private List<Record> list;

  public CsvParser(String path) {

  }

  public Record next() {
      return null;

  }

  public boolean hasNext() {
      return false;
  } 
}
