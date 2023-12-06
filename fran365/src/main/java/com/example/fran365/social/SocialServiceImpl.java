package com.example.fran365.social;

import com.amazonaws.services.s3.AmazonS3;
import com.example.fran365.board.Board;
import com.example.fran365.cart.CartController;
import com.example.fran365.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SocialServiceImpl implements SocialService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("ucket-va1rkc")
    private String bucketName;

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void create(Social social) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        social.setUsername(username);
        social.setCreateDate(LocalDateTime.now());
        social.setStatus("1");
        social.setContent(social.getContent());
        socialRepository.save(social);

    }

    @Override
    public List<Social> readList() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return socialRepository.findByUsername(username);
    }

    @Override
    public List<Social> readDetail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return socialRepository.findPublicPostsOrByUsername(username);
    }

    @Override
    public void delete(Integer id) {
        Optional<Social> sp = socialRepository.findById(id);

        socialRepository.delete(sp.get());
    }

    @Override
    public void updateStatus(Integer postId, String status) {


        Optional<Social> os = socialRepository.findById(postId);
        Social social = os.get();

        social.setStatus(status);
        socialRepository.save(social);
    }

    @Override
    public void update(Integer id, String content, String username, String status) {

        Optional<Social> os = socialRepository.findById(id);
        Social social = os.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        username = auth.getName();

        social.setContent(String.valueOf(content));
        social.setStatus(status);
        social.setUsername(username);

        socialRepository.save(social);



    }


}
