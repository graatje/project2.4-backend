package Forum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.ArrayList;

@Entity
public class ForumThread {

    private @Id @GeneratedValue Long id;

    private String author;
    private String title;

    @Lob
    private String content;

    @Lob
    private ArrayList<ForumPost> replies = new ArrayList<>();


    public ForumThread(){}

    public ForumThread(String author, String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<ForumPost> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<ForumPost> replies) {
        this.replies = replies;
    }
}
