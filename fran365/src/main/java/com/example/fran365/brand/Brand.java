/*name:sunghee kim
date:2023/11/23
mail: inew3w@gmail.com
*/

package com.example.fran365.brand;

import com.example.fran365.sales.Sales;
import com.example.fran365.stock.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition ="LONGTEXT")
    private String content;

    private String address1; //category

    private String address2;

    private String tel; //점주 가게 번호

    private String phone; //핸드폰 번호

    private String time; //영업 시간

    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE)
    private List<Sales> salesList;

    private LocalDateTime createDate;

    @JsonIgnore
    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE)
    private List<Stock> stockList;

}
