package com.example.fran365.social;

import java.util.List;
import java.util.Optional;

public interface SocialService {

    void create(Social social);

    List<Social> readList();

    List<Social> readDetail();

    void update(Integer id, String content, String status);

    void delete(Integer id);

    void updateStatus(Integer postId, String status);

}
