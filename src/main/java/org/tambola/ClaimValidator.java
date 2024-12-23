package org.tambola;

import org.tambola.enums.WinningType;
import org.tambola.factory.WinningPatternFactory;
import org.tambola.pattern.WinningPattern;

import java.util.Set;

public class ClaimValidator {
    public boolean validateClaims(int[][] ticket, WinningType winningType) {
        Set<Integer> announcedNumbers = GameState.getInstance().getAnnouncedNumbers();
        WinningPattern validator = WinningPatternFactory.getValidator(winningType);
        return validator.validate(ticket, announcedNumbers);
    }

    public boolean canClaim(WinningType winningType) {
        GameState gameState = GameState.getInstance();
        return gameState.getAnnouncedCount() >= 5 && !gameState.isPatternClaimed(winningType);
    }
}
