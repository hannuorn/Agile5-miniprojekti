Feature: As a user I want to add a book to the application

    Scenario: book's title is listed when requested
        Given new book with title is added
        Then system returns correct title
    
    