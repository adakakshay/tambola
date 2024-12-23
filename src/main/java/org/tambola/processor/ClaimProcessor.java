package org.tambola.processor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ClaimProcessor {
    private final Queue<ClaimTask> claimQueue = new ConcurrentLinkedQueue<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void submitClaim(String playerId, Runnable claimTask) {
        lock.lock(); // Lock to ensure thread-safe addition and processing
        try {
            claimQueue.add(new ClaimTask(playerId, claimTask));
            processClaims();
        } finally {
            lock.unlock();
        }
    }

    private void processClaims() {
        while (!claimQueue.isEmpty()) {
            ClaimTask claimTask = claimQueue.poll();
            if (claimTask != null) {
                claimTask.getTask().run();
            }
        }
    }
}