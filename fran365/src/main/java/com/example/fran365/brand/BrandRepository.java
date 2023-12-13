/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.brand;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Brand findByUsername(String username);
    
    @Query("SELECT b.address1, COUNT(b) AS count FROM Brand b GROUP BY b.address1 ORDER BY count DESC")
    List<Object[]> findTop4Address1(Pageable pageable);
}
