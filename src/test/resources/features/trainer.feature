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
  Given user sees a part of "<word">
  When user guesses "<gues>"
  Then user gets "<feedback>"

  Examples:
    | word  | guess | feedback                                                    |
    | h.... | hi    | invalid, invalid, invalid, invalid, invalid                 |
    | h.... | horde | correct, incorrect, incorrect, incorrect, different place   |
    | h.... | heros | correct, correct, incorrect, incorrect, incorrect           |
    | he... | hello | correct, correct, correct, correct, correct                 |

Scenario: increase points
  Given users guessed the word correctly
  Then increase score by "5 * (5 – amount of guesses) + 5"

#exception
Scenario: reached limit
  Given user guessed more then "5" times
  When user guesses again
  Then tell user guess is invalid

#exception
Scenario: guessed correctly
  Given user guessed the word correctly
  When user makes another guess
  Then tell user guess is invalid

Feature: Start new round
  As a user,
  I want to start a new round,
  To continue my game

Scenario: start new round
  Given a new game starts
  And a previous round did not fail
  Then start a new round

#exception
Scenario: no game started
  When user starts a new round
  But there's no game
  Then user cannot start a new round

#exception
Scenario:
  Given word is not guessed yet
  When user want to start a new round
  Then user cannot start a new round
