package com.jramun.reactor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

@Document
public class Post {
    @Id
    private String id;
    private String name;
    private Category category;
    private List<Tag> tags = new ArrayList<>();

    public Post(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Post addTag(Tag tag) {
        this.tags.add(tag);
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
