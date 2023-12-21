/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.reply;

public interface ReplyService {
    void create(Integer id, String content,String writer);

    void noticeCreate(Integer id, String content,String writer);
    Reply detail(Integer id);

    Reply noticeDetail(Integer id);
}
