
package domain;

public class VideoParser {
    
    public VideoParser() {
        
    }
    
    public String parse(String youtubelink) {
        String splitter = "\\?"+"v=";
        String id = youtubelink.split(splitter)[1]; 
        String embedlink = "http://www.youtube.com/embed/" + id;
        return embedlink;
    }
}
