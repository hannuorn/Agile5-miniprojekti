package domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LinkTest {
    
    Link link;
    String url;
    
    @Before
    public void setUp() {
        url = "https://dev.to/napicellatwit/consistency-models-52l";
        link = new Link("Nicola Apicella", "Consistency models", url, "Hieno ohje.");
    }
    
    @Test
    public void constructorCreatesNewLink() {
        String info = link.getInfo();
        
        String expected = "Kirjoittaja: Nicola Apicella" + "<br/>"
                + "Otsikko: Consistency models" + "<br/>"
                + "Tyyppi: " + "Linkki" + "<br/>"
                + "URL: <a href=\"" + url + "\" target=\"_blank\">" + url + "</a><br/>"
                + "Kommentti: Hieno ohje.";
        
        assertEquals(info, expected);
    }
    
    @Test
    public void linkHasCorrectType() {
        assertEquals(ItemType.LINK, link.getType());
    }
}
