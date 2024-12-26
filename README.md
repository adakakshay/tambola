## Overview
This repository contains a Java-based implementation of a Tambola (Housie) game. The system allows multiple players to claim winning patterns dynamically as numbers are announced by the dealer. The dealer can announce numbers randomly or through manual input. The game is designed with modern software design principles, including concurrency handling and design patterns.

## Features
```sh
  Winning Patterns:

  Top Row
  
  Middle Row
  
  Bottom Row
  
  Full House
  
  Early Five
```

Dealer Modes:
```sh
  
  Random Announcements: Numbers are announced automatically by the dealer.
  
  Manual Announcements: Dealer inputs numbers manually.
```

Concurrency Handling:
```sh

  Multiple players can claim winning patterns concurrently.
```

Claims are processed in a thread-safe manner.

Design Patterns Used:
```sh

  Singleton Pattern: For managing game state.
  
  Factory Pattern: For generating validators for different winning patterns.
  
  Strategy Pattern: For validating specific winning patterns.
```

How to Run

Clone the Repository:
```sh
  git clone <repository-url>
  cd tambola-game
  
  There is class TambolaGameSuiteTest - run the main method
```
