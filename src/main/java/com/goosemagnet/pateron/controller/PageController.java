package com.goosemagnet.pateron.controller;

import com.goosemagnet.pateron.model.CreatorPage;
import com.goosemagnet.pateron.model.CreatorPost;
import com.goosemagnet.pateron.persistence.PageRepository;
import com.goosemagnet.pateron.persistence.PostRepository;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@Value
@RestController
@RequestMapping("/pages")
public class PageController {

    PageRepository pageRepository;
    PostRepository postRepository;

    @GetMapping
    public Page<CreatorPage> getAllPages(Pageable pageable) {
        return pageRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CreatorPage getPageById(@PathVariable("id") long id) {
        return pageRepository.findById(id).orElseThrow(() -> new RuntimeException("Page with id: " + id + " not found"));
    }

    @GetMapping("/{id}/posts")
    public Slice<CreatorPost> getPostsForPage(@PathVariable("id") Long pageId, @RequestParam(required = false, name = "patronId") Long patronId, Pageable pageable) {
        return patronId == null ? postRepository.findAllByPageId(pageId, pageable) : postRepository.getEligiblePosts(patronId, pageId, pageable);
    }
}
