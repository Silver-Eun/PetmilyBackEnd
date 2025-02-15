package com.petmily.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends PromotionProductDTO {
	private int product_id;
	private int promotion_id;
	private String product_kind;
	private String product_category;
	private String product_name;
	private String product_description;
	private int product_price;
	private int product_stock;
	private int product_sales;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date product_regdate;
	
	private String product_mainimagepath;
	private MultipartFile uploadfilef;
	private String product_detailimagepath;
	private MultipartFile uploadfilef2;
	private double product_rating;
	private String product_origin;
	private int review_cnt;
}
