# Guessing game

Android app that illustrates the use of a binary tree in a small animal-guessing game.

It is also an MVP architecture example.

### API Overview

```java
while (!board.hasFinished()) {
    String question = board.move();
    board.play(answer);
}
board.hasVictory();
```

###

<img src="game.gif" width="300"/>
