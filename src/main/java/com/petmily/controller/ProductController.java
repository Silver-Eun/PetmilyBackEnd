package com.petmily.controller;

import com.petmily.domain.EventDTO;
import com.petmily.domain.ProductDTO;
import com.petmily.domain.ProductImageDTO;
import com.petmily.domain.PromotionProductDTO;
import com.petmily.pagination.PageMaker;
import com.petmily.pagination.SearchCriteria;
import com.petmily.service.EventService;
import com.petmily.service.ProductImageService;
import com.petmily.service.ProductService;
import com.petmily.service.PromotionProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("api/product")
@Log4j2
@AllArgsConstructor
public class ProductController {

    PromotionProductService pmpservice;
    ProductService pservice;
    ProductImageService piservice;
    EventService eservice;

    @GetMapping("/pmProductList")
    public String promotionProduct(Model model) {
        model.addAttribute("pmptable", pmpservice.selectList());
        log.info("** pmProductList 성공 **");
        return "product/promotionProduct";
    }

    @GetMapping("/pmpInsertForm")
    public String pmpInsertForm(Model model) {
        model.addAttribute("pmptable", pmpservice.selectList());
        log.info("** pmpInsertForm 성공 **");
        return "product/promotionProductInsert";
    } // pmpInsertForm

    // => Insert Service 처리: POST
    @PostMapping(value = "/pmpinsert")
    public String pmpinsert(HttpServletRequest request, PromotionProductDTO dto, Model model)
            throws IllegalStateException, IOException {

        MultipartFile uploadfilef = dto.getUploadfilef();
        String file = "bbb.gif";

        if (uploadfilef != null && !uploadfilef.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\promotion\\";
            String file1 = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file1));

            file = uploadfilef.getOriginalFilename();
        }

        dto.setPromotion_image(file);

        if (pmpservice.insert(dto) > 0) {
            log.info("** pmpinsert 성공 **");
        } else {
            log.info("** pmpinsert 실패 **");
        }

        return "redirect:/api/home";
    } // pmpinsert

    @GetMapping("/pmpUpdateForm/{ii}")
    public String pmpUpdateForm(@PathVariable("ii") int id, PromotionProductDTO dto, Model model) {
        dto.setPromotion_id(id);
        model.addAttribute("pmptable", pmpservice.selectOne(dto));
        log.info("** pmpUpdateForm 성공 **");
        return "product/promotionProductUpdate";
    } // pUpdateForm

    @PostMapping(value = "/pmpupdate")
    public String pmpUpdate(HttpServletRequest request, PromotionProductDTO dto, Model model) throws IOException {
        // => 처리결과에 따른 화면 출력을 위해서 dto 의 값을 Attribute에 보관
        model.addAttribute("pmptable", dto);

        MultipartFile uploadfilef = dto.getUploadfilef();
        if (uploadfilef != null && !uploadfilef.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\promotion\\";
            String file1 = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file1));

            String file = uploadfilef.getOriginalFilename();
            dto.setPromotion_image(file);
        } else {
            PromotionProductDTO existingEvent = pmpservice.selectOne(dto);
            dto.setPromotion_image(existingEvent.getPromotion_image());
        }

        // => Service 처리
        if (pmpservice.update(dto) > 0) {
            log.info("** pmpupdate 성공 **");
        } else {
            log.info("** pmpupdate 실패 **");
        }

        return "redirect:/api/home";
    } // pmpUpdate

    @DeleteMapping("/pmpDelete/{ii}")
    public ResponseEntity<?> pmpDelete(@PathVariable("ii") int id, PromotionProductDTO dto) {
        dto.setPromotion_id(id);
        if (pmpservice.delete(dto) > 0) {
            log.info("** pmpdelete HttpStatus.OK => " + HttpStatus.OK);
            return new ResponseEntity<String>("삭제 성공", HttpStatus.OK);
        } else {
            log.info("** pmpdelete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<String>("삭제 실패", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/productList")
    public String productList(Model model, SearchCriteria cri, PageMaker pageMaker) {
        cri.setSnoEno();
        model.addAttribute("ptable", pservice.searchCri(cri));

        pageMaker.setCri(cri);
        pageMaker.setTotalRowsCount(pservice.searchTotalCount(cri));
        model.addAttribute("pageMaker", pageMaker);

        log.info("** productList 성공 **");
        return "product/product";
    }

    @GetMapping("/pInsertForm")
    public String pInsertForm(Model model) {
        model.addAttribute("pmptable", pmpservice.selectList());
        model.addAttribute("ptable", pservice.selectList());
        log.info("** pInsertForm 성공 **");
        return "product/productInsert";
    } // pInsertForm

    // => Insert Service 처리: POST
    @PostMapping(value = "/pinsert")
    public String pinsert(HttpServletRequest request, ProductDTO dto, Model model)
            throws IllegalStateException, IOException {

        // 썸네일이미지
        MultipartFile uploadfilef = dto.getUploadfilef();
        String mainImageFile = "bbb.gif";

        if (uploadfilef != null && !uploadfilef.isEmpty()) {

            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\products\\";
            String file = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file));

            mainImageFile = uploadfilef.getOriginalFilename();
        }

        dto.setProduct_mainimagepath(mainImageFile);

        // 상세이미지
        MultipartFile uploadfilef2 = dto.getUploadfilef2();
        String detailImageFile = "productDetail1.jpg";

        if (uploadfilef2 != null && !uploadfilef2.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file = realPath + uploadfilef2.getOriginalFilename();
            uploadfilef2.transferTo(new File(file));

            // react 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\products\\";
            String file2 = realPath2 + uploadfilef2.getOriginalFilename();
            uploadfilef2.transferTo(new File(file2));

            detailImageFile = uploadfilef2.getOriginalFilename();
        }

        dto.setProduct_detailimagepath(detailImageFile);

        if (pservice.insert(dto) > 0) {
            log.info("** pinsert 성공 **");
        } else {
            log.info("** pinsert 실패 **");
        }

        return "redirect:/api/home";
    } // pinsert

    @GetMapping("/pUpdateForm/{ii}")
    public String pUpdateForm(@PathVariable("ii") int id, ProductDTO dto, Model model) {
        dto.setProduct_id(id);
        model.addAttribute("pmptable", pmpservice.selectList());
        model.addAttribute("ptable", pservice.selectOne(dto));
        log.info("** pUpdateForm 성공 **");
        return "product/productUpdate";
    } // pUpdateForm

    @PostMapping(value = "/pupdate")
    public String pUpdate(HttpServletRequest request, ProductDTO dto, Model model) throws IOException {
        // => 처리결과에 따른 화면 출력을 위해서 dto 의 값을 Attribute에 보관
        model.addAttribute("ptable", dto);

        // 기본이미지
        MultipartFile uploadfilef = dto.getUploadfilef();
        if (uploadfilef != null && !uploadfilef.isEmpty()) {

            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\products\\";
            String file = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file));

            String mainImageFile = uploadfilef.getOriginalFilename();
            dto.setProduct_mainimagepath(mainImageFile);

        } else {
            ProductDTO existingEvent = pservice.selectOne(dto);
            dto.setProduct_mainimagepath(existingEvent.getProduct_mainimagepath());
        }

        // 상세이미지
        MultipartFile uploadfilef2 = dto.getUploadfilef2();
        if (uploadfilef2 != null && !uploadfilef2.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef2.getOriginalFilename();
            uploadfilef2.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\products\\";
            String file = realPath + uploadfilef2.getOriginalFilename();
            uploadfilef2.transferTo(new File(file));

            String detailImageFile = uploadfilef2.getOriginalFilename();
            dto.setProduct_detailimagepath(detailImageFile);
        } else {
            ProductDTO existingEvent = pservice.selectOne(dto);
            dto.setProduct_detailimagepath(existingEvent.getProduct_detailimagepath());
        }

        // => Service 처리
        if (pservice.update(dto) > 0) {
            log.info("** pupdate 성공 **");
        } else {
            log.info("** pupdate 실패 **");
        }

        return "redirect:/api/home";
    } // pUpdate

    @DeleteMapping("/pDelete/{ii}")
    public ResponseEntity<?> pDelete(@PathVariable("ii") int id, ProductDTO dto) {
        dto.setProduct_id(id);
        if (pservice.delete(dto) > 0) {
            log.info("** pdelete HttpStatus.OK => " + HttpStatus.OK);
            return new ResponseEntity<String>("** 삭제 성공 **", HttpStatus.OK);
        } else {
            log.info("** pdelete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<String>("** 삭제 실패, Data_NotFound **", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/pImageList")
    public String pImageList(Model model, SearchCriteria cri, PageMaker pageMaker) {
        cri.setSnoEno();
        model.addAttribute("pitable", piservice.searchCri(cri));

        pageMaker.setCri(cri);
        pageMaker.setTotalRowsCount(piservice.searchTotalCount(cri));
        model.addAttribute("pageMaker", pageMaker);

        log.info("** pImageList 성공 **");
        return "product/productImage";
    }

    @GetMapping("/pImageList/{ii}")
    public String pImageListID(@PathVariable("ii") int id, Model model) {
        model.addAttribute("pitable", piservice.selectListByID(id));
        log.info("** pImageListID 성공 **");
        return "product/productImage";
    } // pImageListID

    @GetMapping("/piInsertForm")
    public String piInsertForm(Model model) {
        model.addAttribute("pitable", piservice.selectList());
        log.info("** piInsertForm 성공 **");
        return "product/productImageInsert";
    } // piInsertForm

    // => Insert Service 처리: POST
    @PostMapping(value = "/piinsert")
    public String piinsert(HttpServletRequest request, ProductImageDTO dto, Model model)
            throws IllegalStateException, IOException {

        MultipartFile uploadfilef = dto.getUploadfilef();
        String file = "bbb.gif";

        if (uploadfilef != null && !uploadfilef.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\products\\";
            String file1 = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file1));

            file = uploadfilef.getOriginalFilename();
        }

        dto.setProduct_imagepath(file);

        if (piservice.insert(dto) > 0) {
            log.info("** piinsert 성공 **");
        } else {
            log.info("** piinsert 실패 **");
        }

        return "redirect:/api/home";
    } // piinsert

    @GetMapping("/piUpdateForm/{ii}")
    public String piUpdateForm(@PathVariable("ii") int id, ProductImageDTO dto, Model model) {
        dto.setPimage_id(id);
        model.addAttribute("pitable", piservice.selectOne(dto));
        log.info("** piUpdateForm 성공 **");
        return "product/productImageUpdate";
    } // pUpdateForm

    @PostMapping(value = "/piupdate")
    public String piUpdate(HttpServletRequest request, ProductImageDTO dto, Model model) throws IOException {
        // => 처리결과에 따른 화면 출력을 위해서 dto 의 값을 Attribute에 보관
        model.addAttribute("pitable", dto);

        MultipartFile uploadfilef = dto.getUploadfilef();
        if (uploadfilef != null && !uploadfilef.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\products\\";
            String file1 = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file1));

            String file = uploadfilef.getOriginalFilename();
            dto.setProduct_imagepath(file);
        } else {
            ProductImageDTO existingEvent = piservice.selectOne(dto);
            dto.setProduct_imagepath(existingEvent.getProduct_imagepath());
        }

        // => Service 처리
        if (piservice.update(dto) > 0) {
            log.info("** piupdate 성공 **");
        } else {
            log.info("** piupdate 실패 **");
        }

        return "redirect:/api/home";
    } // piUpdate

    @DeleteMapping("/piDelete/{ii}")
    public ResponseEntity<?> piDelete(@PathVariable("ii") int id, ProductImageDTO dto) {
        dto.setPimage_id(id);
        if (piservice.delete(dto) > 0) {
            log.info("** piDelete HttpStatus.OK => " + HttpStatus.OK);
            return new ResponseEntity<String>("** 삭제 성공 **", HttpStatus.OK);
        } else {
            log.info("** piDelete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<String>("** 삭제 실패, Data_NotFound **", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/eventList")
    public String eventList(Model model) {
        model.addAttribute("etable", eservice.selectList());
        log.info("** eventList 성공 **");
        return "product/event";
    }

    @GetMapping("/eInsertForm")
    public String eInsertForm(Model model) {
        model.addAttribute("etable", eservice.selectList());
        log.info("** eInsertForm 성공 **");
        return "product/eventInsert";
    } // eInsertForm

    // => Insert Service 처리: POST
    @PostMapping(value = "/einsert")
    public String einsert(HttpServletRequest request, EventDTO dto, Model model)
            throws IllegalStateException, IOException {

        MultipartFile uploadfilef = dto.getUploadfilef();
        String file = "bbb.gif";

        if (uploadfilef != null && !uploadfilef.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\event\\";
            String file1 = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file1));

            file = uploadfilef.getOriginalFilename();
        }

        dto.setEvent_imagepath(file);

        if (eservice.insert(dto) > 0) {
            log.info("** einsert 성공 **");
        } else {
            log.info("** einsert 실패 **");
        }

        return "redirect:/api/home";
    } // einsert

    @GetMapping("/eUpdateForm/{ii}")
    public String eUpdateForm(@PathVariable("ii") int id, EventDTO dto, Model model) {
        dto.setEvent_id(id);
        model.addAttribute("etable", eservice.selectOne(dto));
        log.info("** eUpdateForm 성공 **");
        return "product/eventUpdate";
    } // pUpdateForm

    @PostMapping(value = "/eupdate")
    public String eUpdate(HttpServletRequest request, EventDTO dto, Model model) throws IOException {
        model.addAttribute("etable", dto);

        MultipartFile uploadfilef = dto.getUploadfilef();
        if (uploadfilef != null && !uploadfilef.isEmpty()) {
            // 스프링부트 폴더에 저장
            String realPath2 = "C:\\Team119\\Petmily\\src\\main\\webapp\\resources\\uploadImages\\";
            String file2 = realPath2 + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file2));

            // react 폴더에 저장
            String realPath = "C:\\Team119\\Petmily\\src\\main\\119\\public\\Images\\event\\";
            String file1 = realPath + uploadfilef.getOriginalFilename();
            uploadfilef.transferTo(new File(file1));

            String file = uploadfilef.getOriginalFilename();
            dto.setEvent_imagepath(file);
        } else {
            EventDTO existingEvent = eservice.selectOne(dto);
            dto.setEvent_imagepath(existingEvent.getEvent_imagepath());
        }

        // => Service 처리
        if (eservice.update(dto) > 0) {
            log.info("** eupdate 성공 **");
        } else {
            log.info("** eupdate 실패 **");
        }

        return "redirect:/api/home";
    } // eUpdate

    @DeleteMapping("/eDelete/{ii}")
    public ResponseEntity<?> eDelete(@PathVariable("ii") int id, EventDTO dto) {
        dto.setEvent_id(id);
        if (eservice.delete(dto) > 0) {
            log.info("** eDelete HttpStatus.OK => " + HttpStatus.OK);
            return new ResponseEntity<String>("** 삭제 성공 **", HttpStatus.OK);
        } else {
            log.info("** eDelete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<String>("** 삭제 실패, Data_NotFound **", HttpStatus.BAD_GATEWAY);
        }
    } // eDelete

}
