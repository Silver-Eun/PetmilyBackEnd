package com.petmily.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends ProductDTO{

	private int review_id;
	private int product_id;
	private String review_writer;
	private String review_title;
	private int review_point;
	private int review_count;
	private String review_content;
	private Date review_regdate;
	private String review_image1;
	private String review_image2;
	
	private boolean reply_check;
	
	// Join과 Update 폼으로부터 전달받은 데이터 
	private MultipartFile[] uploadfile1;
	private MultipartFile uploadfile2;
	
	private String user_id;
	private int order_key;
}
