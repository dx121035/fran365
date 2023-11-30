/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.reply;

public interface ReplyService {
    void create(Integer id, String content);
    Reply detail(Integer id);
    void sendSimpleMessage (String to, String subject, String text);
}
