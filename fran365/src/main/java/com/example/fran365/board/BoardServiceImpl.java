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

    @Autowired
    BoardRepository boardRepository;

    @Override
    public void create(Board board,String category)throws IOException {

        if (!board.getCategory().equals("공지") && !board.getCategory().equals("FAQ")) {
            board.setStatus("답변대기");
        }
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
    public Board readDetail(Integer id) {
        Optional<Board> board =  boardRepository.findById(id);
        return board.get();
    }

    @Override
    public void update(Board board, String category) throws IOException{

        board.setCategory(category);
        board.setCreateDate(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Override
    public void delete(Integer id) {
        Optional<Board> board = boardRepository.findById(id);
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
    public List<Board> readFAQList(String category) {
        return boardRepository.findByCategory(category);
    }

    @Override
    public Page<Board> getFAQBoards(String category, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5,Sort.by(sorts));
        return boardRepository.findByCategory(category,pageable);
    }

    @Override
    public List<Board> getAllBoardsByCategories(List<String> categories) {
        List<Sort.Order> sorts = List.of(Sort.Order.desc("createDate"));
        return boardRepository.findByCategoryIn(categories, Sort.by(sorts));
    }

    @Override
    public void hit(Board board, Member member) {
        List<Member> a = board.getHitter();
        a.add(member);
        boardRepository.save(board);
    }

}
