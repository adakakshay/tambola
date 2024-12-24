package org.tambola;

import org.tambola.enums.WinningType;
import org.tambola.processor.ClaimProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TambolaGame {
    private final GameState gameState = GameState.getInstance();
    private final ClaimProcessor claimProcessor = new ClaimProcessor();
    private final List<Player> players;
    private final Scanner scanner = new Scanner(System.in);
    private final SecureRandomWrapper secureRandomWrapper;


    public TambolaGame(List<Player> players, SecureRandomWrapper secureRandomWrapper) {
        this.players = players;
        this.secureRandomWrapper = secureRandomWrapper;
    }

    public void startGame() {
        System.out.println("Choose announcement mode:");
        System.out.println("1. Random Announcements");
        System.out.println("2. Input Announcements");

        int choice = -1;
        while (choice != 1 && choice != 2) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    Thread dealer = new Thread(this::randomAnnounceNumbers);
                    dealer.start();
                } else if (choice == 2) {
                    Thread dealer = new Thread(this::inputAnnounceNumbers);
                    dealer.start();
                } else {
                    System.out.println("Invalid choice. Enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
            }
        }
    }

    public void randomAnnounceNumbers() {

        Set<Integer> announced = new HashSet<>();

        while (announced.size() < 90) { // Assuming Tambola has numbers 1-90
            int number = secureRandomWrapper.nextInt(90) + 1;
            if (!announced.contains(number)) {
                announced.add(number);
                gameState.announceNumber(number);
                System.out.println("Dealer announced: " + number);

                if (gameState.getAnnouncedCount() >= 5) {
                    handleClaims();
                }

                try {
                    Thread.sleep(1000); // We have to pause between announcements
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("Game Over. All numbers announced!");
    }

    public void inputAnnounceNumbers() {
        System.out.println("Enter numbers to announce (type 'done' to finish):");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int number = Integer.parseInt(input);
                if (number < 1 || number > 90) {
                    System.out.println("Invalid number. Enter a number between 1 and 90.");
                } else if (gameState.isNumberAnnounced(number)) {
                    System.out.println("Number already announced: " + number);
                } else {
                    gameState.announceNumber(number);
                    System.out.println("Dealer announced: " + number);

                    if (gameState.getAnnouncedCount() >= 5) {
                        handleClaims();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 90 or 'done'.");
            }
        }
    }

    private void handleClaims() {
        System.out.println("Waiting for claims...");
        System.out.println("Enter player ID and winning type to submit a claim, or type 'skip' to proceed.");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("skip")) {
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length == 2) {
                String playerId = parts[0];
                try {
                    WinningType winningType = WinningType.fromString(parts[1].toUpperCase());
                    submitPlayerClaim(playerId, winningType);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid winning type. Valid types: TOP_ROW, MIDDLE_ROW, BOTTOM_ROW, FULL_HOUSE, EARLY_FIVE");
                }
            } else {
                System.out.println("Invalid input. Format: <playerId> <winningType>");
            }
        }
    }

    public void submitPlayerClaim(String playerId, WinningType winningType) {
        claimProcessor.submitClaim(playerId, () -> {
            ClaimValidator validator = new ClaimValidator();
            if (validator.canClaim(winningType)) {
                boolean claimAccepted = validator.validateClaims(getPlayer(playerId).getTicket(), winningType);
                if (claimAccepted) {
                    boolean claimed = gameState.claimPattern(winningType, playerId);
                    if (claimed) {
                        System.out.println(playerId + " claims " + winningType + ": Accepted");
                    } else {
                        System.out.println(playerId + " claims " + winningType + ": Already Claimed by " + gameState.getPatternClaimer(winningType));
                    }
                } else {
                    System.out.println(playerId + " claims " + winningType + ": Rejected");
                }
            } else {
                System.out.println(playerId + " claims " + winningType + ": Cannot Claim Now");
            }
        });
    }

    private Player getPlayer(String playerId) {
        return players.stream().filter(player -> player.getPlayerId().equals(playerId)).findFirst().orElseThrow(()
                -> new IllegalArgumentException("Player not found"));
    }

    public GameState getGameState() {
        return gameState;
    }
}
