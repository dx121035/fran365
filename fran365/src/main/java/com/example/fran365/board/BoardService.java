/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.board;

import com.example.fran365.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    void create (Board board, String category) throws IOException;
    Board detail(Integer id);

    Board readDetail(Integer id);

    void update (Board board, String category) throws IOException;
    void delete (Integer id);
    Page <Board> getNoticeBoards(String category,int page);
    List<Board> readFAQList(String category);
    Page <Board> getFAQBoards(String category,int page);
    List<Board> getAllBoardsByCategories(List<String> categories);
    void hit (Board board, Member member);



}
