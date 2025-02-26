package com.petmily.controller;

import com.petmily.domain.*;
import com.petmily.service.EventService;
import com.petmily.service.ProductImageService;
import com.petmily.service.ProductService;
import com.petmily.service.PromotionProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rsproduct")
@Log4j2
@RequiredArgsConstructor
public class RestProductController {

	private final PromotionProductService pmpservice;
	private final ProductService pservice;
	private final ProductImageService piservice;
	private final EventService eservice;


	@GetMapping("/checkdata")
	public String hello() {
		log.info("** React Connect Test 중 **");
		return "~~~ SpringBoot & React 안녕 !!!~~~";
	} //checkdata
	
	@GetMapping("/productDetail/{id}")
    public ResponseEntity<ProductDTO> productDetail(@PathVariable("id") int id, ProductDTO dto) {
        dto.setProduct_id(id);
        dto = pservice.selectOne(dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    } //productDetail
	
	@GetMapping("/productImage/{id}")
    public ResponseEntity<List<ProductImageDTO>> productImage(@PathVariable("id") int id) {
		
        List<ProductImageDTO> productImageList = piservice.selectListByID(id);
        return new ResponseEntity<>(productImageList, HttpStatus.OK);
    } //productImage
	
	@GetMapping("/promotionInfoList")
    public ResponseEntity<List<PromotionProductDTO>> promotionInfoList() {
		
        List<PromotionProductDTO> promotionInfoList = pmpservice.selectPromotionInfoList();
        return new ResponseEntity<>(promotionInfoList, HttpStatus.OK);
    } //promotionProductList
	
	@GetMapping("/promotionProductList/{id}")
    public ResponseEntity<List<ProductDTO>> promotionProductList(@PathVariable("id") int id) {
        List<ProductDTO> promotionProductList = pservice.selectPromotionList(id);
        return new ResponseEntity<>(promotionProductList, HttpStatus.OK);
    } //promotionProductList
	
	@GetMapping("/productList/{param1}/{param2}")
    public ResponseEntity<List<ProductDTO>> productList(
    		@PathVariable("param1") String kind, @PathVariable("param2") String category) {
        List<ProductDTO> productList = pservice.selectedList(kind, category);
        System.out.println("param1"+kind);
        System.out.println("param2"+category);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    } //productList
	
	@GetMapping("/newProductList")
    public ResponseEntity<List<ProductDTO>> newProductList() {
        List<ProductDTO> newProductList = pservice.selectThisMonthList();
        return new ResponseEntity<>(newProductList, HttpStatus.OK);
    } //newProductList
	
	@GetMapping("/popularProductList")
    public ResponseEntity<List<ProductDTO>> popularProductList() {
        List<ProductDTO> popularProductList = pservice.selectPopularList();
        return new ResponseEntity<>(popularProductList, HttpStatus.OK);
    } //popularProductList
	
	@GetMapping("/discountedProductList")
    public ResponseEntity<List<ProductDTO>> discountedProductList() {
        List<ProductDTO> discountedProductList = pservice.selectDiscountedList();
        return new ResponseEntity<>(discountedProductList, HttpStatus.OK);
    } //discountedProductList
	
	@GetMapping("/searchedProductList/{searchKeyword}")
    public ResponseEntity<List<ProductDTO>> searchedProductList(
    		@PathVariable("searchKeyword") String searchKeyword) {
        List<ProductDTO> selectSearchedList = pservice.selectSearchedList(searchKeyword);
        return new ResponseEntity<>(selectSearchedList, HttpStatus.OK);
    } //searchedProductList
	
	@GetMapping("/eventList")
    public ResponseEntity<List<EventDTO>> eventList() {
        List<EventDTO> eventList = eservice.selectList();
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }
	
	@GetMapping("/review/list/{id}")
	public ResponseEntity<?> pReviewList(@PathVariable("id") int id) {
		ResponseEntity<?> result = null;

		List<ReviewDTO> list = pservice.pReviewList(id);
				
		if (list != null) {
			result = ResponseEntity.status(HttpStatus.OK).body(list);
			log.info("Review List HttpStatus => " + HttpStatus.OK);
		} else {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
			log.info("Review List HttpStatus => " + HttpStatus.BAD_GATEWAY);
		}

		return result;
	}
	
	@GetMapping("/inquiry/list/{id}")
	public ResponseEntity<?> pinquiryList(@PathVariable("id") int id) {
		ResponseEntity<?> result = null;

		List<InquiryDTO> list = pservice.pinquiryList(id);
				
		if (list != null) {
			result = ResponseEntity.status(HttpStatus.OK).body(list);
			log.info("Inquiry List HttpStatus => " + HttpStatus.OK);
		} else {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
			log.info("Inquiry List HttpStatus => " + HttpStatus.BAD_GATEWAY);
		}

		return result;
	}
}


