Feature: User can search items based on title, author or comment

    Scenario: User can search books based on correct title
        Given There are books stored in the application
        When Searched string "Ohjelmistotuotanto" is written in the search field
        Then Listing of searched items shows the title "Ohjelmistotuotanto"

    Scenario: User can search books based on correct author
        Given There are books stored in the application
        When Searched string "Matti" is written in the search field
        Then Listing of searched items shows the title "Ohjelmistotuotanto"

    Scenario: User can search links based on correct comment
        Given There are links stored in the application
        When Searched string "havainnollistava" is written in the search field
        Then Listing of searched items shows the title "Blogiteksti"