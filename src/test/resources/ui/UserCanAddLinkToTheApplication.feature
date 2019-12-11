Feature: User can add a link to the application

    Scenario: User can add a link with valid fields as information
            Given Page for entering new link is selected
            When writer "Matti" and Title "Kurssimateriaali" and URL "https://ohjelmistotuotanto-hy.github.io/" and Description "Ohjeita" and isVideo "disabled" for link are entered
            Then Listing of all books shows the title "Kurssimateriaali"