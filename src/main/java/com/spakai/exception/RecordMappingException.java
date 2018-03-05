
package com.spakai.exception;

public class RecordMappingException extends RuntimeException {
  public RecordMappingException(String message) {
    super(message);
  }
  public RecordMappingException(String message, Throwable cause) {
    super(message, cause);
  }
}
