package com.example.fran365.social;

import com.example.fran365.member.Member;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SocialService {

    void create(Social social);

    List<Social> readList();

    //List<Social> readDetail();

    void update(Integer id, String content, String status);

    void delete(Integer id);

    void updateStatus(Integer postId, String status);

    void like(Social social, Member member);


    Page<Social> getList(int page);

}
