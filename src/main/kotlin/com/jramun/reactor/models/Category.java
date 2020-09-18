package com.jramun.reactor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Category {
    @Id
    private String id;
    private String name;
    private List<Post> posts = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category addPost(Post post) {
        this.posts.add(post);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
