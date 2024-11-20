package com.petmily.controller;

import com.petmily.domain.UserDTO;
import com.petmily.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("user")
@Log4j2
@AllArgsConstructor
public class UserController {

    UserService service;
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "/Loginf")
    public String Loginf(Model model) {
        model.addAttribute("banana", service.selectList());
        log.info("Loginf성공");
        return "user/Loginf";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session, Model model, RedirectAttributes rttr) {

        session.invalidate();
        rttr.addFlashAttribute("message", "로그아웃 성공");
        return "redirect:home";
    } //logout

    @GetMapping(value = "/newpwf")
    public void newpwf() {
        // viewName 생략
    }

    // => password 만 수정
//    @PostMapping(value = "/changePassword")
//    public String changePassword(HttpServletRequest request, Model model, UserDTO dto) {
//
//        log.info("** update 성공 **");
//        model.addAttribute("banana", dto);
//        if (service.update(dto) > 0) {
//            log.info("** update 성공 **");
//        } else {
//            log.info("** update 실패 **");
//        }
//
//        return "/home";
//    }

    @PostMapping(value = "/Login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(HttpSession session, @RequestBody UserDTO dto) {
        try {
            // 입력된 비밀번호
            String inputPassword = dto.getUser_password();

            // DB에서 유저 정보 가져오기
            UserDTO storedUser = service.selectOne(dto);

            if (storedUser != null && passwordEncoder.matches(inputPassword, storedUser.getUser_password())) {
                // 세션에 로그인 정보 저장
                session.setAttribute("loginID", storedUser.getUser_id());
                session.setAttribute("loginName", storedUser.getUser_name());

                // 성공 응답 생성
                UserDTO responseDto = UserDTO.builder()
                        .user_id(storedUser.getUser_id())
                        .user_name(storedUser.getUser_name())
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(responseDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");
            }
        } catch (Exception e) {
            log.error("** 로그인 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("시스템 오류 발생! 잠시 후 다시 시도하세요.");
        }
    }


    @GetMapping(value = "/Joinf")
    public void Joinf() {
        // viewName 생략 -> 요청명이 viewName 이 됨
    }

//    @PostMapping(value = "/join")
//    public ResponseEntity<String> join(UserDTO dto) {
//        try {
//            // Service 처리
//            if (service.insert(dto) > 0) {
//                return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패 다시 시도하세요");
//            }
//        } catch (Exception e) {
//            log.error("** 회원가입 중 에러 발생: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("시스템 오류 발생! 잠시 후 다시 시도하세요");
//        }
//    }

    @PostMapping(value = "/join")
    public ResponseEntity<String> join(UserDTO dto) {
        try {
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(dto.getUser_password());
            dto.setUser_password(encodedPassword);

            if (service.insert(dto) > 0) {
                return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패 다시 시도하세요");
            }
        } catch (Exception e) {
            log.error("** 회원가입 중 에러 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("시스템 오류 발생! 잠시 후 다시 시도하세요");
        }
    }


    @GetMapping(value = "/Findidf")
    public void Findidf() {
    }

    @PostMapping("/findid")
    public ResponseEntity<UserDTO> findUserId(HttpSession session, @RequestBody UserDTO dto) {
        ResponseEntity<UserDTO> result = null;

        String username = dto.getUser_name();
        String useremail = dto.getUser_email();
        String foundUserID = service.foundUserId(username, useremail); // 해당 메서드로 사용자 아이디 찾기

        UserDTO userDTO = new UserDTO();
        if (foundUserID != null) {
            userDTO.setUser_id(foundUserID);
            return ResponseEntity.ok(userDTO);
        } else {
            // 해당 사용자를 찾을 수 없을 때 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(value = "/Findpwf")
    public void Findpwf() {
    }

    @PostMapping("/findpw")
    public ResponseEntity<UserDTO> findUserPw(HttpSession session, @RequestBody UserDTO dto) {
        ResponseEntity<UserDTO> result = null;

        String userid = dto.getUser_id();
        String useremail = dto.getUser_email();
        String foundUserPW = service.foundUserPw(userid, useremail); // 해당 메서드로 사용자 아이디 찾기


        log.info("dto =" + dto);
        log.info("id =" + userid);
        log.info("useremail =" + useremail);
        UserDTO userDTO = new UserDTO();
        if (foundUserPW != null) {
            userDTO.setUser_password(foundUserPW);
            return ResponseEntity.ok(userDTO);
        } else {
            // 해당 사용자를 찾을 수 없을 때 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/UserList")
    public void userList(Model model) {
        model.addAttribute("banana", service.selectList());

    }

    @GetMapping("/Updatef/{ii}")
    public String Updatef(@PathVariable("ii") String id, UserDTO dto, Model model) {
        dto.setUser_id(id);
        model.addAttribute("banana", service.selectOne(dto));
        log.info("**update 성공 **");
        return "user/Updatef";
    }

    @PostMapping(value = "/Userupdate")
    public void UserUpdate(HttpServletRequest request, UserDTO dto,
                           HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (service.update(dto) > 0) {
            log.info("** update 성공 **");
            out.println("<script>");
            out.println("alert('회원정보 수정 성공');");
            // home 페이지로 이동
            out.println("location.href='/home';");
            out.println("</script>");
        } else {
            log.info("** update 실패 **");
            out.println("<script>");
            out.println("alert('회원정보 수정 실패');");
            // home 페이지로 이동
            out.println("location.href='/home';");
            out.println("</script>");
        }
        out.close();
    }


    @DeleteMapping("/UserDelete/{ii}")
    public ResponseEntity<?> pmpDelete(@PathVariable("ii") String id, UserDTO dto) {
        dto.setUser_id(id);
        if (service.delete(dto) > 0) {
            log.info("** delete HttpStatus.OK => " + HttpStatus.OK);
            return new ResponseEntity<String>("** 삭제 성공 **", HttpStatus.OK);
        } else {
            log.info("** delete HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<String>("** 삭제 실패, Data_NotFound **", HttpStatus.BAD_GATEWAY);
        }
    }

    // 비밀번호 변경
//    @PostMapping(value = "/changePassword")
//    public String changePassword(HttpServletRequest request, Model model,
//                                 @RequestParam String currentPassword,
//                                 @RequestParam String newPassword) {
//
//        log.info("** 비밀번호 변경 요청 **");
//
//        // 현재 로그인된 사용자 정보 가져오기
//        HttpSession session = request.getSession();
//        String userId = (String) session.getAttribute("loginID");  // 로그인된 사용자 ID 가져오기
//
//        if (userId == null) {
//            log.error("사용자 미로그인 상태입니다.");
//            return "redirect:/user/Loginf";  // 로그인 페이지로 리디렉션
//        }
//
//        // 사용자 정보 조회
//        UserDTO user = service.selectOne(UserDTO.builder().user_id(userId).build());
//
//        if (user == null) {
//            log.error("사용자 정보 조회 실패");
//            return "redirect:/user/Loginf";  // 사용자 정보가 없으면 로그인 페이지로 리디렉션
//        }
//
//        // 입력된 현재 비밀번호와 저장된 비밀번호 비교
//        if (!passwordEncoder.matches(currentPassword, user.getUser_password())) {
//            log.error("현재 비밀번호 불일치");
//            return "redirect:/user/Updatef/" + userId + "?error=currentPasswordMismatch";  // 비밀번호 불일치 처리
//        }
//
//        // 새 비밀번호 암호화
//        String encodedNewPassword = passwordEncoder.encode(newPassword);
//        user.setUser_password(encodedNewPassword);  // 새로운 비밀번호 설정
//
//        // 비밀번호 업데이트 처리
//        int updateResult = service.update(user);
//        if (updateResult > 0) {
//            log.info("비밀번호 변경 성공");
//            return "redirect:/" ;
//        } else {
//            log.error("비밀번호 변경 실패");
//            return "redirect:/" ;
//        }
//    }
}
