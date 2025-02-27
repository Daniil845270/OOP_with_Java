package edu.uob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import edu.uob.OXOMoveException.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;

// implemented the horizontal and vertical win checking with variable win treshold -> check the actual variable win threshold with edge cases
// test the case where you have 2 in a row, then space/other players mark, then 3 in a row (depending on the win threshold)

class ExampleControllerTests {
  private OXOModel model;
  private OXOController controller;

  // Make a new "standard" (3x3) board before running each test case (i.e. this method runs before every `@Test` method)
  // In order to test boards of different sizes, winning thresholds or number of players, create a separate test file (without this method in it !)
  @BeforeEach
  void setup() {
    model = new OXOModel(3, 3, 3);
    model.addPlayer(new OXOPlayer('X'));
    model.addPlayer(new OXOPlayer('O'));
    controller = new OXOController(model);
  }

  // This next method is a utility function that can be used by any of the test methods to _safely_ send a command to the controller
  void sendCommandToController(String command) {
      // Try to send a command to the server - call will timeout if it takes too long (in case the server enters an infinite loop)
      // Note: this is ugly code and includes syntax that you haven't encountered yet
      String timeoutComment = "Controller took too long to respond (probably stuck in an infinite loop)";
      assertTimeoutPreemptively(Duration.ofMillis(1000), ()-> controller.handleIncomingCommand(command), timeoutComment);
  }

  // Test simple move taking and cell claiming functionality
  @Test
  void testBasicMoveTaking() throws OXOMoveException {
    // Find out which player is going to make the first move
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    // Make a move
    sendCommandToController("a1");
    // Check that A1 (cell [0,0] on the board) is now "owned" by the first player
    String failedTestComment = "Cell a1 wasn't claimed by the first player";
    assertEquals(firstMovingPlayer, controller.gameModel.getCellOwner(0, 0), failedTestComment);
  }

  // Test out basic win detection
  @Test
  void testBasicWin() throws OXOMoveException {
    // Find out which player is going to make the first move (they should be the eventual winner)
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    // Make a bunch of moves for the two players
    sendCommandToController("a1"); // First player
    sendCommandToController("b1"); // Second player
    sendCommandToController("a2"); // First player
    sendCommandToController("b2"); // Second player
    sendCommandToController("a3"); // First player

    // a1, a2, a3 should be a win for the first player (since players alternate between moves)
    // Let's check to see whether the first moving player is indeed the winner
    String failedTestComment = "Winner was expected to be " + firstMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(firstMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testWinDiagTopLeftX() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    // Make a bunch of moves for the two players
    sendCommandToController("a1"); // First player
    sendCommandToController("b1"); // Second player
    sendCommandToController("a2"); // First player
    sendCommandToController("a3"); // Second player
    sendCommandToController("b2"); // First player
    sendCommandToController("c2"); // Second player
    sendCommandToController("c1"); // First player
    sendCommandToController("b3"); // Second player
    sendCommandToController("c3"); // First player

    String failedTestComment = "testWinDiagTopLeftX was expected to be " + firstMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(firstMovingPlayer, model.getWinner(), failedTestComment);
  }


  @Test
  void testWinDiagBottomLeftO() throws OXOMoveException {
    OXOPlayer secondMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber() + 1);
    // Make a bunch of moves for the two players
    sendCommandToController("b1");
    sendCommandToController("c1");
    sendCommandToController("c2");
    sendCommandToController("b2");
    sendCommandToController("c3");
    sendCommandToController("a3");

    String failedTestComment = "testWinDiagBottomLeftO winner was expected to be " + secondMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(secondMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testWinBottomAcrossX() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    // Make a bunch of moves for the two players
    sendCommandToController("c1");
    sendCommandToController("b1");
    sendCommandToController("c2");
    sendCommandToController("b2");
    sendCommandToController("c3");

    String failedTestComment = "testWinBottomAcrossX was expected to be " + firstMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(firstMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testWinMiddleAcrossO() throws OXOMoveException {
    OXOPlayer secondMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber() + 1);
    // Make a bunch of moves for the two players
    sendCommandToController("a1");
    sendCommandToController("b1");
    sendCommandToController("c1");
    sendCommandToController("b2");
    sendCommandToController("c2");
    sendCommandToController("b3");

    String failedTestComment = "testWinMiddleAcrossO was expected to be " + secondMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(secondMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testWinTopAcrossX() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    // Make a bunch of moves for the two players
    sendCommandToController("a1");
    sendCommandToController("b1");
    sendCommandToController("a2");
    sendCommandToController("b2");
    sendCommandToController("a3");

    String failedTestComment = "testWinTopAcrossX was expected to be " + firstMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(firstMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testWinDownLeftX() throws OXOMoveException {
    OXOPlayer firstMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber());
    // Make a bunch of moves for the two players
    sendCommandToController("a1");
    sendCommandToController("a2");
    sendCommandToController("b1");
    sendCommandToController("b2");
    sendCommandToController("c1");

    String failedTestComment = "testWinDownLeftX was expected to be " + firstMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(firstMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testWinDownRightO() throws OXOMoveException {
    OXOPlayer secondMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber() + 1);
    // Make a bunch of moves for the two players
    sendCommandToController("a2");
    sendCommandToController("a3");
    sendCommandToController("a1");
    sendCommandToController("b3");
    sendCommandToController("b2");
    sendCommandToController("c3");

    String failedTestComment = "testWinDownRightO was expected to be " + secondMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(secondMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testWinMiddleRightO() throws OXOMoveException {
    OXOPlayer secondMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber() + 1);
    // Make a bunch of moves for the two players
    sendCommandToController("a1");
    sendCommandToController("b2");
    sendCommandToController("c1");
//    sendCommandToController("b2");
    sendCommandToController("a2");
    sendCommandToController("b3");
    sendCommandToController("c2");

    String failedTestComment = "testWinMiddleRightO was expected to be " + secondMovingPlayer.getPlayingLetter() + " but wasn't";
    assertEquals(secondMovingPlayer, model.getWinner(), failedTestComment);
  }

  @Test
  void testStalemate() throws OXOMoveException {
    OXOPlayer secondMovingPlayer = model.getPlayerByNumber(model.getCurrentPlayerNumber() + 1);
    // Make a bunch of moves for the two players
    sendCommandToController("a1");
    sendCommandToController("b2");
    sendCommandToController("a3");
    sendCommandToController("a2");
    sendCommandToController("c2");
    sendCommandToController("b1");
    sendCommandToController("b3");
    sendCommandToController("c3");
    sendCommandToController("c1");

    String failedTestComment = "stalemate test failed";
    assertEquals(true, model.isGameDrawn(), failedTestComment);
  }

  // test exceptions when I write them
  // test the basic detections  rules with a grid greater than 3X3 (may test the limit of 9x9)


  // Example of how to test for the throwing of exceptions
  @Test
  void testInvalidIdentifierException() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierLengthException for command `abc123`";
    // The next lins is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierLengthException.class, ()-> sendCommandToController("abc123"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierLengthTooShort1() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierLengthException for command ``";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierLengthException.class, ()-> sendCommandToController(""), failedTestComment);
  }

  @Test
  void testInvalidIdentifierLengthTooShort2() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierLengthException for command `?`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierLengthException.class, ()-> sendCommandToController("?"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierLengthTooShort3() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierLengthException for command `???`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierLengthException.class, ()-> sendCommandToController("???"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierCharacter() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierCharacterException for command `??`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierCharacterException.class, ()-> sendCommandToController("??"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierCharacter2() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierCharacterException for command `?1`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierCharacterException.class, ()-> sendCommandToController("?1"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierCharacter3() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierCharacterException for command `a?`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierCharacterException.class, ()-> sendCommandToController("a?"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierCharacter4() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierCharacterException for command `a/`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierCharacterException.class, ()-> sendCommandToController("a/"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierCharacter5() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierCharacterException for command `a:`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierCharacterException.class, ()-> sendCommandToController("a:"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierCharacter6() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierCharacterException for command ``3`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierCharacterException.class, ()-> sendCommandToController("`3"), failedTestComment);
  }

  @Test
  void testInvalidIdentifierCharacter7() throws OXOMoveException {
    // Check that the controller throws a suitable exception when it gets an invalid command
    String failedTestComment = "Controller failed to throw an InvalidIdentifierCharacterException for command `{3`";
    // The next lines is a bit ugly, but it is the easiest way to test exceptions (soz)
    assertThrows(InvalidIdentifierCharacterException.class, ()-> sendCommandToController("{3"), failedTestComment);
  }
}
