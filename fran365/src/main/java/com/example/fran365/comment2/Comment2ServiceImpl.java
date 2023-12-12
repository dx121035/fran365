package com.example.fran365.comment2;

import com.example.fran365.comment.Comment;
import com.example.fran365.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class Comment2ServiceImpl implements Comment2Service{

    @Autowired
    private CommentService commentService;
    @Autowired
    private Comment2Repository comment2Repository;

    public void create(Integer id, String content, String image) {

        Comment comment = commentService.readDetail(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Comment2 comment2 = new Comment2();
        comment2.setImage(image);
        comment2.setContent(content);
        comment2.setComment(comment);
        comment2.setUsername(username);
        comment2.setCreateDate(LocalDateTime.now());
        comment2Repository.save(comment2);

    }

    @Override
    public void delete(Integer id) {
        Optional<Comment2> oc = comment2Repository.findById(id);
        comment2Repository.delete(oc.get());
    }
}
