package com.cerv1no.ecommerce.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String s) {
        super(s);
    }
}
