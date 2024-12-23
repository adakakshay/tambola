package org.tambola.pattern;

import java.util.Set;

public class BottomRowWinningPattern implements WinningPattern {
    @Override
    public boolean validate(int[][] ticket, Set<Integer> announcedNumbers) {
        for (int number : ticket[2]) {
            if (number != 0 && !announcedNumbers.contains(number)) {
                return false;
            }
        }
        return true;
    }
}