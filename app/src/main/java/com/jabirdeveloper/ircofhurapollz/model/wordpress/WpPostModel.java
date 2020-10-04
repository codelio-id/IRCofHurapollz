package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WpPostModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;

    @SerializedName("guid")
    @Expose
    private Rendered guid;

    @SerializedName("modified")
    @Expose
    private String modified;

    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("title")
    @Expose
    private Rendered title;

    @SerializedName("content")
    @Expose
    private Rendered content;

    @SerializedName("excerpt")
    @Expose
    private Rendered excerpt;

    @SerializedName("author")
    @Expose
    private int author;

    @SerializedName("featured_media")
    @Expose
    private int featuredMedia;

    @SerializedName("comment_status")
    @Expose
    private String commentStatus;

    @SerializedName("ping_status")
    @Expose
    private String pingStatus;

    @SerializedName("sticky")
    @Expose
    private boolean sticky;

    @SerializedName("template")
    @Expose
    private String template;

    @SerializedName("format")
    @Expose
    private String format;

    @SerializedName("categories")
    @Expose
    private List<Integer> categories;

    @SerializedName("tags")
    @Expose
    private List<Integer> tags;

    @SerializedName("jetpack_featured_media_url")
    @Expose
    private String featuredMediaUrl;

    @SerializedName("_links")
    @Expose
    private Links links;

    public WpPostModel() {
    }

    public WpPostModel(int id, String date, String dateGmt, Rendered guid, String modified, String modifiedGmt, String slug, String status, String type, String link, Rendered title, Rendered content, Rendered excerpt, int author, int featuredMedia, String commentStatus, String pingStatus, boolean sticky, String template, String format, List<Integer> categories, List<Integer> tags, String featuredMediaUrl, Links links) {
        this.id = id;
        this.date = date;
        this.dateGmt = dateGmt;
        this.guid = guid;
        this.modified = modified;
        this.modifiedGmt = modifiedGmt;
        this.slug = slug;
        this.status = status;
        this.type = type;
        this.link = link;
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.author = author;
        this.featuredMedia = featuredMedia;
        this.commentStatus = commentStatus;
        this.pingStatus = pingStatus;
        this.sticky = sticky;
        this.template = template;
        this.format = format;
        this.categories = categories;
        this.tags = tags;
        this.featuredMediaUrl = featuredMediaUrl;
        this.links = links;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public Rendered getGuid() {
        return guid;
    }

    public String getModified() {
        return modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    public Rendered getTitle() {
        return title;
    }

    public Rendered getContent() {
        return content;
    }

    public Rendered getExcerpt() {
        return excerpt;
    }

    public int getAuthor() {
        return author;
    }

    public int getFeaturedMedia() {
        return featuredMedia;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public boolean isSticky() {
        return sticky;
    }

    public String getTemplate() {
        return template;
    }

    public String getFormat() {
        return format;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public String getFeaturedMediaUrl() {
        return featuredMediaUrl;
    }

    public Links getLinks() {
        return links;
    }
}
