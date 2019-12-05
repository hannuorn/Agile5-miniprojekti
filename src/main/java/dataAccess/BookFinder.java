package dataAccess;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import domain.Book;
import java.util.ArrayList;


public class BookFinder {

    private static final String APPLICATION_NAME = "Agile5-Vinkkikirjasto/1.0";

    private static Book queryGoogleBooks(JsonFactory jsonFactory, String isbn) throws Exception {
        ClientCredentials.errorIfNotSpecified();

        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                .build();

        List bookList = books.volumes().list("isbn: " + isbn + "]");

        Volumes volumes = bookList.execute();
        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            System.out.println("No matches found.");
            return new Book("", "", isbn, "", "");
        }

        ArrayList<Book> foundBooks = new ArrayList();

        for (Volume volume : volumes.getItems()) {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            String authorString = "";
            java.util.List<String> authors = volumeInfo.getAuthors();
            
            if(authors != null && !authors.isEmpty()) {
                for (int i = 0; i < authors.size(); ++i) {
                    if(authors.get(i) != null) {
                    authorString += authors.get(i);
                    authorString += " ";
                    }
                }
            }
            
            foundBooks.add(new Book(authorString, volumeInfo.getTitle(), isbn, "", ""));
        }
        
        if(foundBooks.size() >= 1) {
            return foundBooks.get(0);
        }

        return new Book("", "", isbn, "", "");
    }

    public Book findBookByISBN(String isbn) throws Exception {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Book book = queryGoogleBooks(jsonFactory, isbn);

        return book;
    }

}
