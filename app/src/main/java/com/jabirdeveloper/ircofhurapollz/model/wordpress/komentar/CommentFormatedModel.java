package com.jabirdeveloper.ircofhurapollz.model.wordpress.komentar;

import com.jabirdeveloper.ircofhurapollz.model.wordpress.CommentsModel;

import java.util.List;

public class CommentFormatedModel {

    private List<CommentsModel> parent;

    private List<CommentsModel> childParent;

    private List<CommentsModel> cpChild;

    public CommentFormatedModel() {
    }

    public CommentFormatedModel(List<CommentsModel> parent, List<CommentsModel> childParent, List<CommentsModel> cpChild) {
        this.parent = parent;
        this.childParent = childParent;
        this.cpChild = cpChild;
    }

    public List<CommentsModel> getParent() {
        return parent;
    }

    public List<CommentsModel> getChildParent() {
        return childParent;
    }

    public List<CommentsModel> getCpChild() {
        return cpChild;
    }
}
