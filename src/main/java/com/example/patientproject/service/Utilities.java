package com.example.patientproject.service;

import java.io.InputStream;

public class Utilities {
    public InputStream loadResource(String resourceName) {
        return this.getClass().getClassLoader().getResourceAsStream(resourceName);
    }
}
