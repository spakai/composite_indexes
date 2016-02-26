
package com.spakai.exception;

import java.lang.RuntimeException;

public class NoMatchException extends RuntimeException {
  public NoMatchException(String message) {
    super(message);
  }
}
