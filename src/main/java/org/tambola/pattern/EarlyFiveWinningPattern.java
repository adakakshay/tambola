package org.tambola.pattern;

import java.util.Set;

public class EarlyFiveWinningPattern implements WinningPattern {
    @Override
    public boolean validate(int[][] ticket, Set<Integer> announcedNumbers) {
        int count = 0;
        for (int[] row : ticket) {
            for (int number : row) {
                if (number != 0 && announcedNumbers.contains(number)) {
                    count++;
                    if (count == 5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
