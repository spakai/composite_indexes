package com.spakai.parser.csv;

public class Record {

  public final String callingNumber;
  public final String calledNumber;
  public final String classification;

  public Record(String[] p) {
    callingNumber = p[0];
    calledNumber  = p[1];
    classification = p[2];
  }
}

