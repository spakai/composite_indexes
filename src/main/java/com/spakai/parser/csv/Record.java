package com.spakai.parser.csv;

public class  Record {

  private String callingNumber;
  private String calledNumber;
  private String classification;

  public Record(String a, String b, String c) {
    callingNumber = a;
    calledNumber  = b;
    classification = c;
  }


}
