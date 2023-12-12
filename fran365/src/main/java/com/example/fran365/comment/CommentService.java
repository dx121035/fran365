package com.example.fran365.comment;

public interface CommentService {
    void create(Integer id, String content);

    Comment readDetail(Integer id);

    void delete(Integer id);
}
