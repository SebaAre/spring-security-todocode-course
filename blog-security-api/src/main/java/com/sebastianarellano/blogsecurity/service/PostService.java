package com.sebastianarellano.blogsecurity.service;

import com.sebastianarellano.blogsecurity.dto.PostCreateRequestDTO;
import com.sebastianarellano.blogsecurity.dto.PostResponseDTO;
import com.sebastianarellano.blogsecurity.dto.PostUpdateRequestDTO;
import com.sebastianarellano.blogsecurity.entity.Author;
import com.sebastianarellano.blogsecurity.entity.Post;
import com.sebastianarellano.blogsecurity.repository.AuthorRepository;
import com.sebastianarellano.blogsecurity.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostResponseDTO createPost(PostCreateRequestDTO request){
        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setAuthor(author);

        Post savedPost = postRepository.save(post);

        return convertToDTO(savedPost);
    }

    public List<PostResponseDTO> getAllPosts(){
        return postRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO getPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return convertToDTO(post);
    }

    public PostResponseDTO updatePost(Long id, PostUpdateRequestDTO request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(request.title());
        post.setContent(request.content());

        Post updatedPost = postRepository.save(post);

        return convertToDTO(updatedPost);
    }

    public void deletePost(Long id){
        if (!postRepository.existsById(id)){
            throw new RuntimeException("Post not found");
        }
        postRepository.deleteById(id);
    }


    private PostResponseDTO convertToDTO(Post post){
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getId(),
                post.getAuthor().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
