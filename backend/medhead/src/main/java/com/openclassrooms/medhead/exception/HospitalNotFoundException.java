package com.openclassrooms.medhead.exception;

public class HospitalNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    // Message par défaut
    private static final String DEFAULT_MESSAGE = "No available hospital found";


    public HospitalNotFoundException(String message) {
        super(message);
    }
    
    // Constructeur avec message par défaut
    public HospitalNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}