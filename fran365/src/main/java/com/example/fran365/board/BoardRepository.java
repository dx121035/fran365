/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByCategory(String category, Pageable pageable);

    List<Board> findByCategory(String category);
    
    List<Board> findByCategoryIn(List<String> categories, Sort sort );

    List<Board> findByCategory(String category, Sort sort);
    
    List<Board> findByStatus(String status);
    
    List<Board> findByUsernameAndCategoryNotIn(String username, List<String> excludedCategories, Sort sort);
}
