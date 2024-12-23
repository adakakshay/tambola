package org.tambola.pattern;

import java.util.Set;

public class FullHouseWinningPattern implements WinningPattern {
    @Override
    public boolean validate(int[][] ticket, Set<Integer> announcedNumbers) {
        for (int[] row : ticket) {
            for (int number : row) {
                if (number != 0 && !announcedNumbers.contains(number)) {
                    return false;
                }
            }
        }
        return true;
    }
}
