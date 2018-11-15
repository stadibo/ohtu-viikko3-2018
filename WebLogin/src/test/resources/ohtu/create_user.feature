Feature: A new user account can be created if a proper unused username and password are given

  Scenario: creation is successful with valid username and password
    Given register new user is selected
    When a valid username "liisa" and password "salainen1" and matching password confirmation are entered
    Then a new user is created

  Scenario: creation fails with too short username and valid password
    Given register new user is selected
    When an invalid username "li" and password "supersecred1" and matching password confirmation are entered
    Then user is not created and error "username should have at least 3 characters" is reported

  Scenario: creation fails with correct username and too short password
    Given register new user is selected
    When a valid username "larsson" and invalid password "juujuu" and matching password confirmation are entered
    Then user is not created and error "password should have at least 8 characters" is reported

  Scenario: creation fails when password and password confirmation do not match
    Given register new user is selected
    When a valid username "henrik" and valid password "megasecred123" and mismatching password confirmation are entered
    Then user is not created and error "password and password confirmation do not match" is reported
