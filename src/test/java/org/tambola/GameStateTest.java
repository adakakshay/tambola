package org.tambola;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    public void testGameStateAnnouncements() {
        GameState gameState = GameState.getInstance();
        gameState.announceNumber(5);
        assertTrue(gameState.isNumberAnnounced(5));
        assertFalse(gameState.isNumberAnnounced(10));
        assertEquals(1, gameState.getAnnouncedCount());
    }
}