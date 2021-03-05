Feature: First letter
  As a user,
  I always want to see the first letter of word,
  so it gives me a starting direction of my guesses.

Scenario: User starts round
  When user starts a new round
  Then show the first letter


Feature: Start new game
  As a user,
  I want to start a game,
  So i can practice my Lingo skills.

Scenario: User starts game
  When user starts new game
  Then start new round

Feature: Continue game
  As a user,
  I want to continue a previous game,
  So i can play multiple games at ounce.

Scenario: User continues game
  Given user start game
  When user wants to continue a game
  And game is not finished
  Then user continues to play previous game

Feature: Make a guess
  As a user,
  I want to make a guess to the word,
  So i can score points.

# <word>,<guess> en <feedback>
Scenario Outline: User guesses word
  Examples:
    |  |
