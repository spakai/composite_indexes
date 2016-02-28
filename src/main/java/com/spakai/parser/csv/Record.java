package com.spakai.parser.csv;

public class Record {

  public final String callingNumber;
  public final String calledNumber;
  public final String classification;

  public Record(String a, String b, String c) {
    callingNumber = a;
    calledNumber  = b;
    classification = c;
  }
  
  
  
}

