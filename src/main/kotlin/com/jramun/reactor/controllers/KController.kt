package com.jramun.reactor.controllers

import com.jramun.reactor.models.Category
import com.jramun.reactor.models.Post
import com.jramun.reactor.models.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromStream
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import sun.jvm.hotspot.oops.Instance
import java.time.Duration
import java.util.function.Consumer
import java.util.stream.Stream


@RestController
@RequestMapping("/api/kotlin")
class KController {

    var categories = mutableListOf(
            Category("category-1"),
            Category("category-2"),
            Category("category-3")
    )
    var posts = mutableListOf(
            Post("post-1", Category("category-1")),
            Post("post-2", Category("category-1")).addTag(Tag("tag-1")).addTag(Tag("tag-2")),
            Post("post-3", Category("category-1")).addTag(Tag("tag-2")),
            Post("post-4", Category("category-2")).addTag(Tag("tag-2")),
            Post("post-5", Category("category-2")).addTag(Tag("tag-1")),
            Post("post-6", Category("category-3")).addTag(Tag("tag-1"))
    )
    var tags = mutableListOf(
            Tag("tag-1"),
            Tag("tag-2")
    )

    @GetMapping("/scenario-1")
    fun scenario1() {
        Flux.fromIterable(posts)
                .flatMap { p: Post -> Flux.just(p.category) }
                .map { category: Category -> category.name }
                .subscribe { t: String? -> println(t) }
    }

    @GetMapping("/scenario-2")
    fun scenario2() {
        Flux.fromIterable(posts).groupBy { post: Post? -> post?.category }.subscribe(Consumer { t -> println(t.key()?.name) });
    }

    @GetMapping("/scenario-3")
    fun scenario3(): Flux<Tuple2<Post, Category>> {
        return Flux.fromIterable(posts).zipWith(Flux.fromIterable(categories)).log();
    }

    @GetMapping("/scenario-4")
    fun scenario4(): Flux<Post> {
        return Flux.fromIterable(posts).mergeWith(Flux.fromIterable(posts)).log();
    }

    @GetMapping("/scenario-5")
    fun scenario5(): Mono<MutableList<Post>> {
        return Flux.fromIterable(posts).collectList().log()
    }

    @GetMapping("/scenario-6")
    fun scenario6(): Mono<Tuple2<MutableList<Post>, MutableList<Post>>> {
        return Flux.fromIterable(posts).collectList().zipWith(Flux.fromIterable(posts).collectList())
    }

    @GetMapping("/scenario-7")
    fun scenario7(): Mono<Tuple2<MutableList<Post>, MutableList<Post>>> {
        return Flux.fromIterable(posts).collectList().zipWith(Flux.fromIterable(posts).collectList())
    }

    @GetMapping("/scenario-8")
    fun scenario8(): Flux<Category> {
        val categories1 = Flux.fromIterable(categories)
        val categories2 = Flux.fromIterable(categories)
        return Flux.merge(categories1, categories2)
    }

    @GetMapping("/scenario-9")
    fun scenario9(): Flux<Tuple2<Category, Category>> {
        val categories1 = Flux.fromIterable(categories)
        val categories2 = Flux.fromIterable(categories)
        return Flux.zip(categories1, categories2)
    }

    @GetMapping("/scenario-10")
    fun scenario10(): Flux<Category> {
        val categories1 = Flux.fromIterable(categories)
        val categories2 = Flux.fromIterable(categories)
        return Flux.concat(categories1, categories2)
    }

    @GetMapping("/scenario-11")
    fun scenario11(): Flux<Category> {
        return Flux.fromIterable(categories).doOnSubscribe { t -> print(t) }.filter { t -> t.name == "category-2" };
    }

    @GetMapping("/scenario-12")
    fun scenario12(): Flux<Long> {
        val flux = fromStream(Stream.generate { Category("s1") })
        val fluxInterval = Flux.interval(Duration.ofSeconds(1))
        return fluxInterval.log().delayElements(Duration.ofSeconds(1))
    }
}
