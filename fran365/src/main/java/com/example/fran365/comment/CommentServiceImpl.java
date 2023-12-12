package com.example.fran365.comment;

import com.example.fran365.social.Social;

import com.example.fran365.social.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SocialRepository socialRepository;

    @Override
    public void create(Integer id, String content) {

        Optional<Social> os = socialRepository.findById(id);
        Social social = os.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Comment c = new Comment();
        c.setContent(content);
        c.setSocial(social);
        c.setUsername(username);
        c.setCreateDate(LocalDateTime.now());
        commentRepository.save(c);

    }

    @Override
    public Comment readDetail(Integer id) {
        Optional<Comment> oc = commentRepository.findById(id);
        return oc.get();
    }


}