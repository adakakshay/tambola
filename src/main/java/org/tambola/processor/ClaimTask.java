package org.tambola.processor;

public class ClaimTask {
    private final String playerId;
    private final Runnable task;

    public ClaimTask(String playerId, Runnable task) {
        this.playerId = playerId;
        this.task = task;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Runnable getTask() {
        return task;
    }
}