package org.tambola;

import java.util.List;

public class TambolaGameSuiteTest {

    public static void main(String[] args) {
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
        TambolaGame game = new TambolaGame(players, new SecureRandomWrapper());

        game.startGame();
    }
}
