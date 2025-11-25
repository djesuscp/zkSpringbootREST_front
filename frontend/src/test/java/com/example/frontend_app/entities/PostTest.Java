package com.example.frontend_app.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PostTest {

    @Test
    public void testGettersAndSetters() {
        Post post = new Post();

        post.setId(1);
        post.setUserId(2);
        post.setPostId(3);
        post.setTitle("Test Title");
        post.setBody("Test Body");

        assertEquals(1, post.getId());
        assertEquals(2, post.getUserId());
        assertEquals(3, post.getPostId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Body", post.getBody());
    }
}
