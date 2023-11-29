package com.example.fran365.social;

import java.util.List;
import java.util.Optional;

public interface SocialService {

    void create(Social social);

    List<Social> readList();

    List<Social> readDetail();

    void update(Social social);

    void delete(Integer id);

}
