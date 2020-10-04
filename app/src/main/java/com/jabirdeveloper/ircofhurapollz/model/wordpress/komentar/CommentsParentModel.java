package com.jabirdeveloper.ircofhurapollz.model.wordpress.komentar;

import com.jabirdeveloper.ircofhurapollz.model.wordpress.AuthorAvatar;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.Rendered;

import java.util.List;

public class CommentsParentModel {

    private int id;

    private int post;

    private int parent;

    private int author;

    private String authorName;

    private String date;

    private String dateGmt;

    private Rendered content;

    private String link;

    private AuthorAvatar authorAvatar;

    private List<CommentsChildModel> childList;

    public CommentsParentModel() {
    }

    public CommentsParentModel(int id, int post, int parent, int author, String authorName, String date, String dateGmt, Rendered content, String link, AuthorAvatar authorAvatar, List<CommentsChildModel> childList) {
        this.id = id;
        this.post = post;
        this.parent = parent;
        this.author = author;
        this.authorName = authorName;
        this.date = date;
        this.dateGmt = dateGmt;
        this.content = content;
        this.link = link;
        this.authorAvatar = authorAvatar;
        this.childList = childList;
    }

    public int getId() {
        return id;
    }

    public int getPost() {
        return post;
    }

    public int getParent() {
        return parent;
    }

    public int getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDate() {
        return date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public Rendered getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public AuthorAvatar getAuthorAvatar() {
        return authorAvatar;
    }

    public List<CommentsChildModel> getChildList() {
        return childList;
    }
}
