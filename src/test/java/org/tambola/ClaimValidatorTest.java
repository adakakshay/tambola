package org.tambola;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tambola.enums.WinningType;
import org.tambola.factory.WinningPatternFactory;
import org.tambola.pattern.WinningPattern;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClaimValidatorTest {

    private ClaimValidator claimValidator;
    private GameState gameState;

    @BeforeEach
    public void setUp() {
        claimValidator = new ClaimValidator();
        gameState = GameState.getInstance();
        gameState.reset();
    }

    @Test
    public void testValidateClaims() {
        int[][] ticket = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}
        };
        WinningType winningType = WinningType.TOP_ROW;

        WinningPattern winningPattern = mock(WinningPattern.class);
        when(winningPattern.validate(any(), any())).thenReturn(true);
        WinningPatternFactory.setValidator(winningType, winningPattern);

        gameState.announceNumber(1);
        gameState.announceNumber(2);
        gameState.announceNumber(3);
        gameState.announceNumber(4);
        gameState.announceNumber(5);

        System.out.println("Announced Numbers: " + gameState.getAnnouncedNumbers());
        System.out.println("Ticket: " + Arrays.deepToString(ticket));

        boolean result = claimValidator.validateClaims(ticket, winningType);
        verify(winningPattern).validate(ticket, gameState.getAnnouncedNumbers());
        assertTrue(result);
    }

    @Test
    public void testCanClaim() {
        WinningType winningType = WinningType.TOP_ROW;

        gameState.announceNumber(1);
        gameState.announceNumber(2);
        gameState.announceNumber(3);
        gameState.announceNumber(4);
        gameState.announceNumber(5);

        assertTrue(claimValidator.canClaim(winningType));


        gameState.claimPattern(winningType, "Player1");

        assertFalse(claimValidator.canClaim(winningType));
    }
}