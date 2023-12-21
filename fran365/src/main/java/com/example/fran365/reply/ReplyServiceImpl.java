/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.reply;


import com.example.fran365.board.Board;
import com.example.fran365.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public void create(Integer id, String content,String writer) {

        Board board = boardService.detail(id);
        if (!board.getCategory().equals("공지") && !board.getCategory().equals("FAQ")) {
            board.setStatus("답변완료");
        }
        Reply c = new Reply();
        c.setContent(content);
        c.setBoard(board);
        c.setWriter(writer);
        c.setCreateDate(LocalDateTime.now());
        replyRepository.save(c);
    }

    @Override
    public void noticeCreate(Integer id, String content, String writer) {
        Board board = boardService.detail(id);
        if (!board.getCategory().equals("공지") && !board.getCategory().equals("FAQ")) {
            board.setStatus("답변완료");
        }
        Reply c = new Reply();
        c.setContent(content);
        c.setBoard(board);
        c.setWriter(writer);
        c.setCreateDate(LocalDateTime.now());
        replyRepository.save(c);
    }

    @Override
    public Reply detail(Integer id) {
        Optional<Reply> reply = replyRepository.findById(id);
        return reply.get();
    }

    @Override
    public Reply noticeDetail(Integer id) {
        Optional<Reply> reply = replyRepository.findById(id);
        return reply.get();
    }

}