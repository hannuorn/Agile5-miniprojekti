Feature: User can edit the information of single item
    Scenario: User can the edit the information of a book
    Given There are books stored in the application and single view of book is clicked
    When User cliks edit and inputs new title "Ohjelmistoprojekti" and presses submit
    Then Edited title "Ohjelmistoprojekti" is stored in the application
    