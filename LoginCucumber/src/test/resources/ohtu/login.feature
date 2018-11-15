Feature: User can log in with valid username/password-combination

    Scenario: user can login with correct password
       Given command login is selected
       When  username "pekka" and password "akkep" are entered
       Then  system will respond with "logged in"

    Scenario: user can not login with incorrect password
       Given command login is selected
       When username "pekka" and password "keppa" are entered
       Then system will respond with "komento (new tai login):"

    Scenario: nonexistent user can not login to
       Given command login is selected
       When username "tester" and password "hmm" are entered
       Then system will respond with "komento (new tai login):"