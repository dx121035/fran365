package com.example.fran365.social;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SocialRepository extends JpaRepository<Social,Integer> {
    List<Social> findByUsernameOrderByCreateDateDesc(String username);
    //@Query("SELECT s FROM Social s WHERE s.username = :username OR s.status = '1' ORDER BY s.createDate DESC")
    //List<Social> findPublicPostsOrByUsername(String username);
    @Query("SELECT s FROM Social s WHERE s.status = '1' ORDER BY s.createDate DESC")
    Page<Social> findPageBy(Pageable pageable);


}
