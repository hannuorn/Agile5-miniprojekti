package domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LinkTest {
    
    Link link;
    
    @Before
    public void setUp() {
        link = new Link("Nicola Apicella", "Consistency models", "https://dev.to/napicellatwit/consistency-models-52l", "Hieno ohje.");
    }
    
    @Test
    public void constructorCreatesNewLink() {
        String info = link.getInfo();
        
        String expected = "Kirjoittaja: Nicola Apicella" + "<br/>"
                + "Otsikko: Consistency models" + "<br/>"
                + "Tyyppi: " + "Linkki" + "<br/>"
                + "URL: https://dev.to/napicellatwit/consistency-models-52l" + "<br/>"
                + "Kommentti: Hieno ohje.";
        
        assertEquals(info, expected);
    }
    
    @Test
    public void linkHasCorrectType() {
        assertEquals(ItemType.LINK, link.getType());
    }
}
