package com.example.fran365.social;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.fran365.cart.Cart;
import com.example.fran365.member.Member;
import com.example.fran365.member.MemberRepository;
import com.example.fran365.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

        Optional<Member> os = memberRepository.findByUsername(username);

        social.setUsername(os.toString());
        socialRepository.save(social);

    }

    @Override
    public List<Social> readList(String username) {
        return socialRepository.findAll();
    }

    @Override
    public List<Social> readDetail() {
        return socialRepository.findAll();
    }

    @Override
    public void update(Social social) {

    }

    @Override
    public void delete(Integer id) {
        Optional<Social> sp = socialRepository.findById(id);

        socialRepository.delete(sp.get());
    }
}
