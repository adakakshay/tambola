package org.tambola.pattern;

import java.util.Set;

public interface WinningPattern {
    boolean validate(int[][] ticket, Set<Integer> announcedNumbers); //This would be strategy pattern
}