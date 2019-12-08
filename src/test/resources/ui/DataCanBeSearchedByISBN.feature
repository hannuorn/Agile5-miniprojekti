Feature: The details of the book can be searched by ISBN

    Scenario: The information will be pre filled when ISBN is added
            Given Page for entering new book is selected
            When valid isbn "9789526753485" is added to text box and search button is clicked
            Then addition form shows the author "Sofi Oksanen"