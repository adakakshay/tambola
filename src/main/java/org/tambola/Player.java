package org.tambola;

public class Player {
    private final String playerId;
    private final int[][] ticket;

    public Player(String playerId, int[][] ticket) {
        this.playerId = playerId;
        this.ticket = ticket;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int[][] getTicket() {
        return ticket;
    }
}
