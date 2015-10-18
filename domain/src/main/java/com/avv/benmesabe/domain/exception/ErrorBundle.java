package com.avv.benmesabe.domain.exception;

/**
 * Created by angelvazquez on 18/10/15.
 */
public interface ErrorBundle {

    Exception getException();

    String getErrorMessage();
}
