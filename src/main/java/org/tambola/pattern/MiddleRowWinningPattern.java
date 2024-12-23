package org.tambola.pattern;

import java.util.Set;

public class MiddleRowWinningPattern implements WinningPattern {
    @Override
    public boolean validate(int[][] ticket, Set<Integer> announcedNumbers) {
        for (int number : ticket[1]) {
            if (number != 0 && !announcedNumbers.contains(number)) {
                return false;
            }
        }
        return true;
    }
}