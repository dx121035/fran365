/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.brand;

import java.util.List;

public interface BrandService {
    void create (Brand brand);
    List<Brand> list();
    Brand detail(Integer id);
    void update (Brand brand);
    void delete (Integer id);
}
