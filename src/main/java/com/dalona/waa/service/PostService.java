package com.dalona.waa.service;

import com.dalona.waa.domain.Post;
import com.dalona.waa.dto.requestDto.CreatePostDto;
import com.dalona.waa.dto.responseDto.PostResDto;
import com.dalona.waa.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResDto createPost(CreatePostDto createPostDto) {
        Post post = createPostDto.toEntity();
        return new PostResDto(post);
    }
}
