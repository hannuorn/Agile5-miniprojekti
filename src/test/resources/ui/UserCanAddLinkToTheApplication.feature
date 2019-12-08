Feature: User can add a link to the application

    Scenario: User can add a link with valid fields as information
            Given Page for entering new link is selected
            When valid writer "Matti" and Title "Kurssimateriaali" and URL "https://ohjelmistotuotanto-hy.github.io/" and Description "Ohjeita" and isVideo "0" for link are entered
            Then Listing of all books shows the title "Kurssimateriaali"

    Scenario: A link with invalid information cannot be added
            Given Page for entering new link is selected
            When valid writer "Matti" and Title "Kurssimateriaali" and URL "y" and Description "Ohjeita" and isVideo "1" for link are entered
            Then Listing of all books does not show the title "Kurssimateriaali"