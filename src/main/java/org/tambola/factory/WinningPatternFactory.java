package org.tambola.factory;

import org.tambola.enums.WinningType;
import org.tambola.pattern.BottomRowWinningPattern;
import org.tambola.pattern.EarlyFiveWinningPattern;
import org.tambola.pattern.FullHouseWinningPattern;
import org.tambola.pattern.MiddleRowWinningPattern;
import org.tambola.pattern.TopRowWinningPattern;
import org.tambola.pattern.WinningPattern;

import java.util.HashMap;
import java.util.Map;

public class WinningPatternFactory {
    private static final Map<WinningType, WinningPattern> PATTERN_MAP = new HashMap<>();

    static {
        PATTERN_MAP.put(WinningType.TOP_ROW, new TopRowWinningPattern());
        PATTERN_MAP.put(WinningType.MIDDLE_ROW, new MiddleRowWinningPattern());
        PATTERN_MAP.put(WinningType.BOTTOM_ROW, new BottomRowWinningPattern());
        PATTERN_MAP.put(WinningType.FULL_HOUSE, new FullHouseWinningPattern());
        PATTERN_MAP.put(WinningType.EARLY_FIVE, new EarlyFiveWinningPattern());
    }

    public static WinningPattern getValidator(WinningType gameType) {
        WinningPattern pattern = PATTERN_MAP.get(gameType);
        if (pattern == null) {
            throw new IllegalArgumentException("Invalid game type");
        }
        return pattern;
    }
}