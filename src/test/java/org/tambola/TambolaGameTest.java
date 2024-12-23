package org.tambola;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.tambola.enums.WinningType;
import org.tambola.pattern.FullHouseWinningPattern;
import org.tambola.pattern.TopRowWinningPattern;
import org.tambola.pattern.WinningPattern;
import org.tambola.processor.ClaimProcessor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

class TambolaGameTest {

    @BeforeEach
    public void setup() {
        GameState gameState = GameState.getInstance();
        gameState.reset();
    }

    @Test
    public void testWinningPatternValidation() {
        GameState gameState = GameState.getInstance();
        gameState.announceNumber(1);
        gameState.announceNumber(2);
        gameState.announceNumber(3);
        gameState.announceNumber(4);
        gameState.announceNumber(5);

        int[][] ticket = {
                {1, 2, 3, 4, 5},
                {0, 0, 0, 0, 0},
                {7, 0, 0, 0, 0}
        };

        WinningPattern topRow = new TopRowWinningPattern();
        assertTrue(topRow.validate(ticket, gameState.getAnnouncedNumbers()));

        WinningPattern fullHouse = new FullHouseWinningPattern();
        assertFalse(fullHouse.validate(ticket, gameState.getAnnouncedNumbers()));
    }

    @Test
    public void testClaimValidator() {
        GameState gameState = GameState.getInstance();
        for (int i = 1; i <= 5; i++) {
            gameState.announceNumber(i);
        }

        int[][] ticket = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}
        };

        ClaimValidator validator = new ClaimValidator();
        assertTrue(validator.validateClaims(ticket, WinningType.TOP_ROW));
        assertFalse(validator.validateClaims(ticket, WinningType.MIDDLE_ROW));
    }

    @Test
    public void testClaimProcessor() {
        ClaimProcessor processor = new ClaimProcessor();
        List<String> results = Collections.synchronizedList(new ArrayList<>());

        Runnable task1 = () -> results.add("Claim 1 processed");
        Runnable task2 = () -> results.add("Claim 2 processed");

        processor.submitClaim("Player1", task1);
        processor.submitClaim("Player2", task2);

        assertEquals(2, results.size());
        assertTrue(results.contains("Claim 1 processed"));
        assertTrue(results.contains("Claim 2 processed"));
    }

    @Test
    public void testRandomAnnouncementMode() throws Exception {
        InputStream originalIn = System.in;

        String simulatedInput = "skip\nPlayer1 Toprow\nPlayer2 toprow";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        try {

            Player player1 = new Player("Player1", new int[][]{
                    {1, 2, 3, 4, 5},
                    {6, 7, 8, 9, 10},
                    {11, 12, 13, 14, 15}
            });

            Player player2 = new Player("Player2", new int[][]{
                    {16, 17, 18, 19, 20},
                    {21, 22, 23, 24, 25},
                    {26, 27, 28, 29, 30}
            });

            List<Player> players = List.of(player1, player2);
            SecureRandomWrapper mockSecureRandom = Mockito.mock(SecureRandomWrapper.class);


            when(mockSecureRandom.nextInt(90)).thenReturn(42).thenReturn(0)
                    .thenReturn(1)
                    .thenReturn(3)
                    .thenReturn(4)
                    .thenReturn(2);


            TambolaGame game = new TambolaGame(players);
            game.setSecureRandomWrapper(mockSecureRandom);


            Thread gameThread = new Thread(game::randomAnnounceNumbers);
            gameThread.start();


            game.submitPlayerClaim("Player1", WinningType.TOP_ROW);
            game.submitPlayerClaim("Player2", WinningType.FULL_HOUSE);


            gameThread.join(20000);


            assertTrue(GameState.getInstance().isPatternClaimed(WinningType.TOP_ROW));
            assertEquals("Player1", GameState.getInstance().getPatternClaimer(WinningType.TOP_ROW));
        } finally {

            System.setIn(originalIn);
        }
    }

    @Test
    public void testInputAnnouncementMode() {
        String simulatedInput = "1\n2\n3\ndone\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {

            Player player1 = new Player("Player1", new int[][]{
                    {1, 2, 3, 4, 5},
                    {6, 7, 8, 9, 10},
                    {11, 12, 13, 14, 15}
            });
            List<Player> players = List.of(player1);
            TambolaGame game = new TambolaGame(players);


            game.inputAnnounceNumbers();

            assertTrue(GameState.getInstance().isNumberAnnounced(1));
            assertTrue(GameState.getInstance().isNumberAnnounced(2));
            assertTrue(GameState.getInstance().isNumberAnnounced(3));
            assertEquals(3, GameState.getInstance().getAnnouncedCount());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testClaimAfterRandomAnnouncement() {
        Player player1 = new Player("Player1", new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}
        });

        List<Player> players = List.of(player1);
        TambolaGame game = new TambolaGame(players);


        GameState.getInstance().announceNumber(1);
        GameState.getInstance().announceNumber(2);
        GameState.getInstance().announceNumber(3);
        GameState.getInstance().announceNumber(4);
        GameState.getInstance().announceNumber(5);


        game.submitPlayerClaim("Player1", WinningType.TOP_ROW);


        assertTrue(GameState.getInstance().isPatternClaimed(WinningType.TOP_ROW));
        assertEquals("Player1", GameState.getInstance().getPatternClaimer(WinningType.TOP_ROW));
    }

    @Test
    public void testClaimAfterInputAnnouncement() {
        Player player1 = new Player("Player1", new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}
        });

        List<Player> players = List.of(player1);
        TambolaGame game = new TambolaGame(players);


        GameState.getInstance().announceNumber(1);
        GameState.getInstance().announceNumber(2);
        GameState.getInstance().announceNumber(3);
        GameState.getInstance().announceNumber(4);
        GameState.getInstance().announceNumber(5);


        game.submitPlayerClaim("Player1", WinningType.TOP_ROW);


        assertTrue(GameState.getInstance().isPatternClaimed(WinningType.TOP_ROW));
        assertEquals("Player1", GameState.getInstance().getPatternClaimer(WinningType.TOP_ROW));
    }
}

