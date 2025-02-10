package com.petmily.controller;

import com.petmily.domain.CartDTO;
import com.petmily.domain.OrderDetailDTO;
import com.petmily.domain.OrderProductDTO;
import com.petmily.pagination.PageMaker;
import com.petmily.pagination.SearchCriteria;
import com.petmily.service.CartService;
import com.petmily.service.OrderDetailService;
import com.petmily.service.OrderProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("api/cart")
@Log4j2
@AllArgsConstructor
public class CartController {

	CartService cservice;
	OrderProductService opservice;
	OrderDetailService odservice;

	// ** 장바구니 목록 페이지
	@GetMapping("/cartList")
	public String cartList(Model model, SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
		cri.setSnoEno();
		
		// 2) Service 처리
		model.addAttribute("banana", cservice.bcriList(cri));
		
		// 3) View 처리 : PageMaker 필요
		pageMaker.setCri(cri);
		pageMaker.setTotalRowsCount(cservice.criTotalCount(cri));
		// => ver01: 전체 rows 갯수
		//    ver02: 검색조건에 해당하는  rows 갯수
		model.addAttribute("pageMaker", pageMaker);

		return "cart/cartList";
	}

	// ** CartInert(장바구니 추가 페이지)
	@GetMapping(value = "/cartInsert")
	public String cartInsert() {
		return "cart/cartInsert";
	}
	
	@PostMapping("/cartJoin")
    public String insert(@RequestParam String user_id, @RequestParam int product_id, @RequestParam int product_cnt) {
		String uri="cart/cartInsert";
		
		cservice.insert(user_id, product_id, product_cnt);
        
		return uri;
    }

	// ** CartInert(장바구니 추가) 비동기 Post
	@PostMapping(value = "/cartInsertP",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> cartInsert(@RequestParam String user_id, @RequestParam int product_id, @RequestParam int product_cnt) {
		
		ResponseEntity<?> result = null;
		
		cservice.insert(user_id, product_id, product_cnt);

		return result;
	}
	
	// ** cdelete(장바구니 상품 삭제)
	@DeleteMapping("/cdelete/{ii}/{jj}")
	public ResponseEntity<?> cdelete(@PathVariable("ii") String user_id, @PathVariable("jj") int product_id,
			CartDTO dto) {
		dto.setUser_id(user_id);
		dto.setProduct_id(product_id);
		if (cservice.delete(dto) > 0) {
			log.info("** cdelete HttpStatus.OK => " + HttpStatus.OK);
			return new ResponseEntity<String>("삭제 성공", HttpStatus.OK);
		} else {
			log.info("** cdelete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
			return new ResponseEntity<String>("삭제 실패", HttpStatus.BAD_GATEWAY);
		}
	}

// ====================================================
	
	// ** 주문내역 목록 페이지
	@GetMapping("/orderProduct")
	public String orderProductList(Model model, SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
		cri.setSnoEno();
		
		// 2) Service 처리
		model.addAttribute("banana", opservice.bcriList(cri));
		
		// 3) View 처리 : PageMaker 필요
		pageMaker.setCri(cri);
		pageMaker.setTotalRowsCount(opservice.criTotalCount(cri)); 
		// => ver01: 전체 rows 갯수
		//    ver02: 검색조건에 해당하는  rows 갯수
		model.addAttribute("pageMaker", pageMaker);

		return "cart/orderProduct";
	}
	
	// ** OrderProductInsert(주문내역 추가 페이지)
	@GetMapping(value = "/orderProductInsert")
	public String orderProductInsert() {

		return "cart/orderProductInsert";
	}
	
	// ** orderProductInsert(주문내역 추가) Post
	@PostMapping(value="/orderProductJoin")
	public String orderProductJoin(OrderProductDTO dto, Model model)  {
		// 1. 요청분석 & Service
		String uri="cart/orderProduct";
		
		// 2. Service 처리
		if ( opservice.insert(dto) > 0 ) {
			model.addAttribute("message", "주문내역 추가 성공");
		}else {
			model.addAttribute("message", "주문내역 상품 추가 실패");
			uri="cart/orderProductInsert";
		}
		
		// 3. View 
		return uri;
	}

	// ** orderProductInsert(주문내역 추가) 비동기 Post
	@PostMapping(value = "/orderProductInsertP",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> orderProductInsert(OrderProductDTO dto) {
		
		ResponseEntity<?> result = null;
		
		// => Service 처리
		if (opservice.insert(dto) > 0) {
			result = ResponseEntity.status(HttpStatus.OK).body("주문내역 추가 성공");
			log.info("** orderProduct HttpStatus.OK => " + HttpStatus.OK);
		} else {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("주문내역 추가 실패");
			log.info("** orderProduct HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
		}
		return result;
	}

	// ** opdetail(주문내역 세부내역 페이지)
	@GetMapping(value ="/opdetail")
	public String opdetail(HttpServletRequest request, Model model, OrderProductDTO dto) {
		model.addAttribute("apple", opservice.selectOne(dto));
		
		if ( "U".equals(request.getParameter("jCode")) )
			 return "cart/orderProductUpdate";
		else return "cart/orderProductDetail";
	}
	
	// ** orderProductUpdate(주문내역 수정) Post
	@PostMapping(value="/orderProductUpdate")
	public String orderProductUpdate(OrderProductDTO dto, Model model)  {
		// => 처리결과에 따른 화면 출력을 위해서 dto의 값을 Attribute에 보관
		model.addAttribute("apple", dto);
		String uri="cart/orderProductDetail";
		
		// 2. Service 처리
		if ( opservice.update(dto) > 0 ) {
			model.addAttribute("message", "주문내역 수정 성공");
		}else {
			model.addAttribute("message", "주문내역 수정 실패");
			uri="cart/orderProductInsert";
		}
		
		// 3. View 
		return uri;
	}

	// ** orderProductUpdate(주문내역 수정) 비동기 Post
	@PostMapping(value = "/orderProductUpdateP",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> orderProductUpdate(OrderProductDTO dto) {
		
		ResponseEntity<?> result = null;
		
		// => Service 처리
		if (opservice.update(dto) > 0) {
			result = ResponseEntity.status(HttpStatus.OK).body("주문내역 수정 성공");
			log.info("** orderProduct HttpStatus.OK => " + HttpStatus.OK);
		} else {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("주문내역 수정 실패");
			log.info("** orderProduct HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
		}
		return result;
	}
	
	// ** opdelete(주문내역 삭제)
	@DeleteMapping("/opdelete/{ii}")
	public ResponseEntity<?> opdelete(@PathVariable("ii") int order_key, OrderProductDTO dto) {
		dto.setOrder_key(order_key);
		if (opservice.delete(dto) > 0) {
			log.info("** opdelete HttpStatus.OK => " + HttpStatus.OK);
			return new ResponseEntity<String>("** 삭제 성공 **", HttpStatus.OK);
		} else {
			log.info("** opdelete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
			return new ResponseEntity<String>("** 삭제 실패, Data_NotFound **", HttpStatus.BAD_GATEWAY);
		}
	}

// ====================================================
	
	// ** 주문내역 목록 페이지네이션
	@GetMapping("/orderDetail")
	public String orderDetailList(Model model, SearchCriteria cri, PageMaker pageMaker) {
		// 1) Criteria 처리
		cri.setSnoEno();
		
		// 2) Service 처리
		model.addAttribute("banana", odservice.bcriList(cri));
		
		// 3) View 처리 : PageMaker 필요
		pageMaker.setCri(cri);
		pageMaker.setTotalRowsCount(odservice.criTotalCount(cri)); 
		// => ver01: 전체 rows 갯수
		//    ver02: 검색조건에 해당하는  rows 갯수
		model.addAttribute("pageMaker", pageMaker);

		return "cart/orderDetail";
	}
	
	// ** OrderDetailInsert(주문상세내역 추가 페이지)
	@GetMapping(value = "/orderDetailInsert")
	public String orderDetailInsert() {

		return "cart/orderDetailInsert";
	}
	
	// ** orderDetailInsert(주문상세내역 추가) Post
	@PostMapping(value="/orderDetailJoin")
	public String orderDetailJoin(OrderDetailDTO dto, Model model)  {
		// 1. 요청분석 & Service
		String uri="cart/orderDetail";
		
		// 2. Service 처리
		if ( odservice.insert(dto) > 0 ) {
			model.addAttribute("message", "장바구니에 상품 추가 성공");
		}else {
			model.addAttribute("message", "장바구니에 상품 추가 실패");
			uri="cart/orderDetailInsert";
		}
		
		// 3. View 
		return uri;
	}

	// ** orderDetailInsert(주문내역 추가) 비동기 Post
	@PostMapping(value = "/orderDetailInsertP",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> orderDetailInsert(OrderDetailDTO dto) {
		
		ResponseEntity<?> result = null;
		
		// => Service 처리
		if (odservice.insert(dto) > 0) {
			result = ResponseEntity.status(HttpStatus.OK).body("주문상세내역 추가 성공");
			log.info("** orderDetail HttpStatus.OK => " + HttpStatus.OK);
		} else {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("주문상세내역 추가 실패");
			log.info("** orderDetail HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
		}
		return result;
	}
	
	// ** oddetail(주문상세내역 세부내역 페이지)
	@GetMapping(value ="/oddetail")
	public String oddetail(HttpServletRequest request, Model model, OrderDetailDTO dto) {
		model.addAttribute("apple", odservice.selectOne(dto));
		
		if ( "U".equals(request.getParameter("jCode")) )
			 return "cart/orderDetailUpdate";
		else return "cart/orderDetailDetail";
	}
	
	// ** orderProductUpdate(주문내역 수정) Post
	@PostMapping(value="/orderDetailUpdate")
	public String orderDetailUpdate(OrderDetailDTO dto, Model model)  {
		// => 처리결과에 따른 화면 출력을 위해서 dto의 값을 Attribute에 보관
		model.addAttribute("apple", dto);
		String uri="cart/orderDetailDetail";
		
		// 2. Service 처리
		if ( odservice.update(dto) > 0 ) {
			model.addAttribute("message", "주문내역 수정 성공");
		}else {
			model.addAttribute("message", "주문내역 수정 실패");
			uri="cart/orderProductInsert";
		}
		
		// 3. View 
		return uri;
	}

	// ** orderProductUpdate(주문내역 수정) 비동기 Post
	@PostMapping(value = "/orderDetailUpdateP",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> orderDetailUpdate(OrderDetailDTO dto) {
		
		ResponseEntity<?> result = null;
		
		// => Service 처리
		if (odservice.update(dto) > 0) {
			result = ResponseEntity.status(HttpStatus.OK).body("주문상세내역 수정 성공");
			log.info("** orderProduct HttpStatus.OK => " + HttpStatus.OK);
		} else {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("주문상세내역 수정 실패");
			log.info("** orderProduct HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
		}
		return result;
	}

	// ** oddelete(주문상세내역 삭제)
	@DeleteMapping("/oddelete/{ii}")
	public ResponseEntity<?> oddelete(@PathVariable("ii") int order_key, OrderDetailDTO dto) {
		dto.setOrder_key(order_key);
		if (odservice.delete(dto) > 0) {
			log.info("** oddelete HttpStatus.OK => " + HttpStatus.OK);
			return new ResponseEntity<String>("** 삭제 성공 **", HttpStatus.OK);
		} else {
			log.info("** oddelete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
			return new ResponseEntity<String>("** 삭제 실패, Data_NotFound **", HttpStatus.BAD_GATEWAY);
		}
	}

}
