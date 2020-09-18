package com.jramun.reactor.repos

import com.jramun.reactor.models.Category
import com.jramun.reactor.models.Post
import com.jramun.reactor.models.Tag
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CategoryRepo : ReactiveMongoRepository<Category, String> {
}

interface TagRepo : ReactiveMongoRepository<Tag, String> {
}

interface PostRepo : ReactiveMongoRepository<Post, String> {
}