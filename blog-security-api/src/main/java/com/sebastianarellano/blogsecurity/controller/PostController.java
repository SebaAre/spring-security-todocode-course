package com.sebastianarellano.blogsecurity.controller;

import com.sebastianarellano.blogsecurity.dto.PostCreateRequestDTO;
import com.sebastianarellano.blogsecurity.dto.PostResponseDTO;
import com.sebastianarellano.blogsecurity.dto.PostUpdateRequestDTO;
import com.sebastianarellano.blogsecurity.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    public ResponseEntity<PostResponseDTO> createPost (@Valid @RequestBody PostCreateRequestDTO request){
        PostResponseDTO response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(){
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id){
        PostResponseDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequestDTO request){
        PostResponseDTO response = postService.updatePost(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}