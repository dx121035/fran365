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
    public void create(Integer id, String content) {

        Board board = boardService.detail(id);
        board.setStatus("답변완료");
        Reply c = new Reply();
        c.setContent(content);
        c.setBoard(board);
        c.setCreateDate(LocalDateTime.now());
        replyRepository.save(c);
    }

    @Override
    public Reply detail(Integer id) {
        Optional<Reply> reply = replyRepository.findById(id);
        return reply.get();
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("seula724@naver.com"); // 실제 네이버 이메일 주소
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }
}