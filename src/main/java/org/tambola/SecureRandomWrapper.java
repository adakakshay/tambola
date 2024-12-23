package org.tambola;

import java.security.SecureRandom;

public class SecureRandomWrapper {
    private final SecureRandom secureRandom;

    public SecureRandomWrapper() {
        this.secureRandom = new SecureRandom();
    }

    public int nextInt(int bound) {
        return secureRandom.nextInt(bound);
    }
}
