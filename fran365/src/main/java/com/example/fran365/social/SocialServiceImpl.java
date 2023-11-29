package com.example.fran365.social;

import com.amazonaws.services.s3.AmazonS3;
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

    @Value("seulabucket")
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

        return socialRepository.findAll();
    }

    @Override
    public void update(Social social) {

        socialRepository.save(social);
    }

    @Override
    public void delete(Integer id) {
        Optional<Social> sp = socialRepository.findById(id);

        socialRepository.delete(sp.get());
    }



}
