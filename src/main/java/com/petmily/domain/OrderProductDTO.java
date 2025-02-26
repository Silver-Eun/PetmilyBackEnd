package com.petmily.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO extends ProductDTO {
	private int order_key;
	private String user_id;
	private int order_total_price;
	private int product_review;
	
	//private LocalDateTime order_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date order_date;
	
	private String pay_method;
	private String order_name;
	private String order_email;
	private String order_tel;
	private String order_zipcode;
	private String order_addr;
	private String order_addr_detail;
	private String order_req;
	private String orderItems;
}
