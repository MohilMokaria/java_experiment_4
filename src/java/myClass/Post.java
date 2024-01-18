package myClass;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {

    // Instance variables for the post title, body, and timestamp
    private String title;
    private String body;
    private Timestamp post_time;

    // Default constructor
    public Post() {
    }

    // Parameterized constructor to initialize the Post object with title, body, and timestamp
    public Post(String title, String body, Timestamp post_time) {
        this.title = title;
        this.body = body;
        this.post_time = post_time;
    }

    // Method to get the formatted message time in "HH:mm | dd/MM/yyyy" format
    public String getFormattedMsgTime() {
        if (post_time != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm | dd/MM/yyyy");
            return dateFormat.format(new Date(post_time.getTime()));
        } else {
            return null; // Return null if the timestamp is not set
        }
    }

    // Setter method for the post title
    public void setTitle(String title) {
        this.title = title;
    }

    // Setter method for the post body
    public void setBody(String body) {
        this.body = body;
    }

    // Setter method for the post timestamp
    public void setTime(Timestamp post_time) {
        this.post_time = post_time;
    }

    // Getter method for the post title
    public String getTitle() {
        return this.title;
    }

    // Getter method for the post body
    public String getBody() {
        return this.body;
    }

    // Getter method for the post timestamp
    public Timestamp getTime() {
        return this.post_time;
    }

}
