package org.tambola;

import org.tambola.enums.WinningType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Singleton Pattern Implementation done here for design pattern
class GameState {
    private static final GameState INSTANCE = new GameState();
    private final Set<Integer> announcedNumbers = Collections.synchronizedSet(new HashSet<>());
    protected final Map<WinningType, String> claimedPatterns = new ConcurrentHashMap<>(); //To store the claimed patterns

    private GameState() {}

    public static GameState getInstance() {
        return INSTANCE;
    }

    public void announceNumber(int number) {
        announcedNumbers.add(number);
    }

    public boolean isNumberAnnounced(int number) {
        return announcedNumbers.contains(number);
    }

    public Set<Integer> getAnnouncedNumbers() {
        return new HashSet<>(announcedNumbers);
    }

    public int getAnnouncedCount() {
        return announcedNumbers.size();
    }

    public boolean isPatternClaimed(WinningType pattern) {
        return claimedPatterns.containsKey(pattern);
    }

    public synchronized boolean claimPattern(WinningType pattern, String playerId) {
        if (!isPatternClaimed(pattern)) {
            claimedPatterns.put(pattern, playerId);
            return true;
        }
        return false;
    }

    public String getPatternClaimer(WinningType pattern) {
        return claimedPatterns.get(pattern);
    }

    //Testing purpose
    public void reset() {
        announcedNumbers.clear();
        claimedPatterns.clear();
    }
}