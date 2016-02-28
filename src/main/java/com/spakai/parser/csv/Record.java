package com.spakai.parser.csv;

public class Record {

  private final String callingNumber;
  private final String calledNumber;
  private final String classification;

  public Record(String a, String b, String c) {
    callingNumber = a;
    calledNumber  = b;
    classification = c;
  }
}

