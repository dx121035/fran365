/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.board;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.fran365.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    @Value("bucket-va1rkc")
    private String bucketName;

    @Autowired
    AmazonS3 amazonS3;

    @Autowired
    BoardRepository boardRepository;

    @Override
    public void create(Board board, MultipartFile multipartFile,String category)throws IOException {

        File file =new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());
        }

        String fileName= System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));

        file.delete();

        board.setImage(fileName);
        board.setCreateDate(LocalDateTime.now());
        board.setCategory(category);
        boardRepository.save(board);
    }

    @Override
    public Board detail(Integer id) {
        Optional<Board> board =  boardRepository.findById(id);
        return board.get();
    }

    @Override
    public void update(Board board, MultipartFile multipartFile,String category) throws IOException{

        File file =new File(multipartFile.getOriginalFilename());
        //aws s3 multipartFile을 막바로 올릴 수 없다. 따라서 파일을 일단 저장한 후에 그 파일을  aws로 올리고 삭제한다.

        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());
        }

        //역시 보안등의 이유로 uuid를 사용해도 좋지만 이번엔 다른 방법을 사용해보자
        String fileName= System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));

        //파일을 s3로 올리고 서버에 저장했던 파일은 이제 완전히 삭제한다.
        file.delete();

        board.setImage(fileName);
        board.setCategory(category);
        board.setCreateDate(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Override
    public void delete(Integer id) {
        Optional<Board> board =  boardRepository.findById(id);
        boardRepository.delete(board.get());
    }


    @Override
    public Page<Board> getNoticeBoards(String category, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5,Sort.by(sorts));
        return boardRepository.findByCategory(category,pageable);
    }

    @Override
    public Page<Board> getFAQBoards(String category, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5,Sort.by(sorts));
        return boardRepository.findByCategory(category,pageable);
    }

    @Override
    public Page<Board> getAllBoardsByCategories(List<String> categories, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5,Sort.by(sorts));
        return boardRepository.findByCategoryIn(categories,pageable);
    }

    @Override
    public void hit(Board board, Member member) {
        List<Member> a = board.getHitter();
        a.add(member);
        boardRepository.save(member);
    }
}
