Feature: User can add a book to the application

    Scenario: User can add a book with valid fields as information
            Given Page for entering new book is selected
            When valid writer "Matti" and Title "Ohjelmistotuotanto" and ISBN "111A" and Tag "Ohtu" and Description "Tosi tärkeaa" are entered
            Then Listing of all books shows the title "Ohjelmistotuotanto"
            