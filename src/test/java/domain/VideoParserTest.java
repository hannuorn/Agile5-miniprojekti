
package domain;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class VideoParserTest {
    
    VideoParser videoParser;
    
    @Before
    public void setUp() {
        videoParser = new VideoParser();
    }
    
    @Test
    public void canParseCorrectly() {
        String embed = videoParser.parse("https://www.youtube.com/watch?v=vYquumk4nWw");
        assertEquals(embed, "http://www.youtube.com/embed/vYquumk4nWw");
    }
}
