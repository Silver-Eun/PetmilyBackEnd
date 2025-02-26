package com.petmily.controller;

import com.petmily.domain.FaqDTO;
import com.petmily.domain.InquiryDTO;
import com.petmily.domain.NoticeDTO;
import com.petmily.domain.ReviewDTO;
import com.petmily.pagination.Criteria;
import com.petmily.pagination.PageMaker;
import com.petmily.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "api/board", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class BoardController {

	BoardService boardService;

	// --------------------SHOW NOTICE LIST--------------------
	
	// Notice Paging List
	@GetMapping(value = "/noticePagingList")
	public String noticePagingList(Model model, Criteria cri, PageMaker pageMaker) {
		cri.setSnoEno();
		model.addAttribute("notice", boardService.getNoticePagedList(cri));

		pageMaker.setCri(cri);
		pageMaker.setTotalRowsCount(boardService.noticeTotalCount());
		model.addAttribute("pageMaker", pageMaker);

		return "board/noticePagingList";
	}
	// --------------------SHOW NOTICE LIST END--------------------

	
	// --------------------SHOW INQUIRY LIST--------------------
	
	// Inquiry Paging List
		@GetMapping(value = "/inquiryPagingList")
		public String inquiryPagingList(Model model, Criteria cri, PageMaker pageMaker) {
			cri.setSnoEno();
			model.addAttribute("inquiry", boardService.getInquiryPagedList(cri));

			pageMaker.setCri(cri);
			pageMaker.setTotalRowsCount(boardService.inquiryTotalCount());
			model.addAttribute("pageMaker", pageMaker);

			return "board/inquiryPagingList";
		}
	// --------------------SHOW INQUIRY LIST END--------------------

		
	// --------------------SHOW REVIEW LIST--------------------	

	// Review Paging List
	@GetMapping(value = "/reviewPagingList")
	public String reviewPagingList(Model model, Criteria cri, PageMaker pageMaker) {
		cri.setSnoEno();
		model.addAttribute("review", boardService.getReviewPagedList(cri));

		pageMaker.setCri(cri);
		pageMaker.setTotalRowsCount(boardService.reviewTotalCount());
		model.addAttribute("pageMaker", pageMaker);

		return "board/reviewPagingList";
	}
	// --------------------SHOW REVIEW LIST END--------------------	
	
	
	// --------------------SHOW REVIEW LIST--------------------	

	// Faq Paging List
		@GetMapping(value = "/faqPagingList")
		public String faqPagingList(Model model, Criteria cri, PageMaker pageMaker) {
			cri.setSnoEno();
			model.addAttribute("faq", boardService.getFaqPagedList(cri));

			pageMaker.setCri(cri);
			pageMaker.setTotalRowsCount(boardService.faqTotalCount());
			model.addAttribute("pageMaker", pageMaker);

			return "board/faqPagingList";
		}
	// --------------------SHOW REVIEW LIST END--------------------	

	// --------------------NOTICE CRUD--------------------

	// Notice Detail
	@GetMapping(value = "/noticeDetail")
	public String selectNotice(Model model, NoticeDTO dto) {

		model.addAttribute("notice", boardService.getNotice(dto));

		return "board/noticeDetail";
	}

	// Notice UPDATE FORM
	@GetMapping(value = "/noticeUpdateForm")
	public String updateNotice(Model model, NoticeDTO dto) {
		model.addAttribute("notice", boardService.getNotice(dto));

		return "board/noticeUpdateForm";
	}

	// Notice UPDATE
	@PostMapping(value = "/noticeUpdate")
	public String update(Model model, NoticeDTO dto, RedirectAttributes rttr) {
		String uri = "redirect:noticeDetail?notice_id=" + dto.getNotice_id();

		if (boardService.updateNotice(dto) > 0) {
			rttr.addFlashAttribute("notice", dto);
		} else {
			model.addAttribute("notice", dto);
			uri = "board/noticeUpdate";
		}

		return uri;
	}

	// Notice INSERT FORM
	@GetMapping(value = "/noticeInsertForm")
	public String noticeInsert() {

		return "board/noticeInsertForm";
	}

	// Notice INSERT
	// AXIOS 사용하여 비동기 방식으로 NOTICE 글작성

	// Notice DELETE
	@GetMapping(value = "/noticeDelete")
	public String delete(NoticeDTO dto, Model model, RedirectAttributes rttr) {
		String uri = "redirect:noticeList";

		if (boardService.deleteNotice(dto) > 0) {
			rttr.addFlashAttribute("message", "글 삭제 성공");
		} else {
			rttr.addFlashAttribute("message", "글 삭제 실패");
		}
		return uri;
	}

	// --------------------NOTICE CRUD END--------------------

	// --------------------INQUIRY CRUD--------------------

	// Inquiry Detail
	@GetMapping(value = "/inquiryDetail")
	public String selectInquiry(Model model, InquiryDTO dto) {

		model.addAttribute("inquiry", boardService.getInquiry(dto));

		return "board/inquiryDetail";
	}

	// Inquiry UPDATE FORM
	@GetMapping(value = "/inquiryUpdateForm")
	public String updateInquiry(Model model, InquiryDTO dto) {

		model.addAttribute("inquiry", boardService.getInquiry(dto));

		return "board/inquiryUpdateForm";
	}

	// --------------------INQUIRY CRUD END--------------------

	// --------------------FAQ CRUD--------------------

	// Faq Detail
	@GetMapping(value = "/faqDetail")
	public String selectFaq(Model model, FaqDTO dto) {

		model.addAttribute("faq", boardService.getFaq(dto));

		return "board/faqDetail";
	}

	// Faq UPDATE FORM
	@GetMapping(value = "/faqUpdateForm")
	public String updateFaq(Model model, FaqDTO dto) {

		model.addAttribute("faq", boardService.getFaq(dto));

		return "board/faqUpdateForm";
	}

	// Faq INSERT FORM
	@GetMapping(value = "/faqInsertForm")
	public String faqInsert() {

		return "board/faqInsertForm";
	}

	// --------------------FAQ CRUD END--------------------

	// --------------------REVIEW CRUD--------------------

	// Review Detail
	@GetMapping(value = "/reviewDetail")
	public String selectReview(Model model, ReviewDTO dto) {
		model.addAttribute("review", boardService.getReview(dto));
		model.addAttribute("reply", boardService.getReplyList(dto));

		return "board/reviewDetail";
	}

	// Review UPDATE FORM
	@GetMapping(value = "/reviewUpdateForm")
	public String updateReview(Model model, ReviewDTO dto) {

		model.addAttribute("review", boardService.getReview(dto));

		return "board/reviewUpdateForm";
	}

	// --------------------REVIEW CRUD END--------------------
}
