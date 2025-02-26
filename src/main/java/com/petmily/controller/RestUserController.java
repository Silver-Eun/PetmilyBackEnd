package com.petmily.controller;


import com.petmily.domain.UserDTO;
import com.petmily.mapperInterface.UserMapper;
import com.petmily.service.EmailService;
import com.petmily.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.SecureRandom;
import java.util.Random;

@RestController
@RequestMapping("/api/rsuser")
@Log4j2
@RequiredArgsConstructor
public class RestUserController {

    private final UserService service;
    private final EmailService eservice;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/Login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody UserDTO dto) {
        ResponseEntity<UserDTO> result = null;

        String id = dto.getUser_id();
        String password = dto.getUser_password();

        // 2) 서비스 처리
        dto = service.selectOne(dto);
        log.info("dto =" + dto);
        log.info("dto.getUser_id =" + dto.getUser_id());
        log.info("dto.getUser_name =" + dto.getUser_name());

        // DB에 저장된 암호화된 비밀번호
        String encryptedPassword = dto.getUser_password();

        // 클라이언트에서 받은 비밀번호를 암호화
        String encodedPassword = passwordEncoder.encode(password);

        if (dto != null && id.equals(dto.getUser_id()) && passwordEncoder.matches(password, encryptedPassword)) {
            HttpSession session = request.getSession(); // 세션 가져오기
            session.setAttribute("loginID", dto.getUser_id());  // 세션에 로그인 정보 저장
            session.setAttribute("loginName", dto.getUser_name());

            // 세션 쿠키 생성
            ResponseCookie cookie = ResponseCookie.from("JSESSIONID", session.getId())
                    .path("/")
                    .httpOnly(true)
                    .secure(true) // HTTPS 환경에서만 전송
                    .sameSite("None")  // SameSite=None
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            // 로그인된 사용자 정보를 ResponseBody에 담아서 반환
            final UserDTO userDTO = UserDTO.builder()
                    .user_id(dto.getUser_id())
                    .user_name(dto.getUser_name())
                    .user_email(dto.getUser_email())
                    .user_phone(dto.getUser_phone())
                    .user_birthday(dto.getUser_birthday())
                    .zipcode(dto.getZipcode())
                    .addr(dto.getAddr())
                    .addr_detail(dto.getAddr_detail())
                    .build();

            result = ResponseEntity.status(HttpStatus.OK).body(userDTO);  // 로그인 성공
            log.info("로그인 성공 => " + HttpStatus.OK);

            log.info("Session loginID: " + session.getAttribute("loginID"));
            log.info("Session loginName: " + session.getAttribute("loginName"));
        } else {
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // 로그인 실패
            log.info("로그인 실패 => " + HttpStatus.UNAUTHORIZED);
        }
        return result;
    }


    @PostMapping(value = "/Signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO dto) {

        try {
            // 회원가입 시 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(dto.getUser_password());
            dto.setUser_password(encodedPassword);

            // Service 처리
            if (service.insert(dto) > 0) {
                return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패");
            }
        } catch (Exception e) {
            log.error("회원가입 중 에러 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생");
        }
    }

    @GetMapping(value = "/idcheck")
    public ResponseEntity<String> checkIfId(@RequestParam("user_id") String user_id) {
        try {
            int count = mapper.checkUserId(user_id);

            if (count > 0) {
                return ResponseEntity.ok("F"); // 이미 사용 중인 아이디
            } else {
                return ResponseEntity.ok("T"); // 사용 가능한 아이디
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("아이디 확인에 실패했습니다.");
        }
    }

    @PostMapping(value = "/Findid")
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

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();

    private String generateTemporaryPassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return stringBuilder.toString();
    }

    @PostMapping(value = "/Findpw/{userId}")
    public ResponseEntity<String> findUserPw(@PathVariable String userId, @RequestBody UserDTO dto) {
        String useremail = dto.getUser_email();
        String foundUserPW = service.foundUserPw(userId, useremail);

        if (foundUserPW != null) {
            // 임시 비밀번호 생성
            String temporaryPassword = generateTemporaryPassword(10); // 예를 들어 10자리의 임시 비밀번호 생성

            log.info("foundUserPW =" + foundUserPW);
            log.info("temporaryPassword =" + temporaryPassword);

            // 임시 비밀번호를 암호화
            String encryptedTemporaryPassword = passwordEncoder.encode(temporaryPassword);

            // DB에 암호화된 임시 비밀번호 업데이트
            boolean updated = service.randompw(userId, encryptedTemporaryPassword);

            log.info("encryptedTemporaryPassword = " + encryptedTemporaryPassword);
            log.info("updated =" + updated);

            if (updated) {
                // 사용자의 이메일로 임시 비밀번호 전송
                eservice.sendTemporaryPasswordEmail(useremail, temporaryPassword);
                return ResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("임시 비밀번호 업데이트에 실패했습니다.");
            }
        } else {
            // 해당 사용자를 찾을 수 없을 때 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자 정보를 찾을 수 없습니다.");
        }
    }

    @DeleteMapping(value = "/selfDelete/{user_id}")
    public ResponseEntity<?> delete(@PathVariable("user_id") String userId, UserDTO dto) {
        dto.setUser_id(userId);
        if (service.delete(dto) > 0) {
            log.info("회원 삭제 성공 => " + HttpStatus.OK);
            return new ResponseEntity<String>("회원 삭제 성공", HttpStatus.OK);
        } else {
            log.info("회원 삭제 실패 => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<String>("삭제 실패", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(value = "/userlist")
    public ResponseEntity<?> userlist(HttpServletRequest request, UserDTO dto) {
        HttpSession session = request.getSession();
        String loggedInUserID = (String) session.getAttribute("loginID");

        if (loggedInUserID != null) {
            // 로그인된 사용자일 때의 처리
            UserDTO id = service.selectOne(dto);

            if (id != null) {
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 회원입니다.");
            }
        } else {
            // 로그인되지 않은 상태일 때의 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요.");
        }
    }

    @PostMapping(value = "/update/{user_id}")
    public ResponseEntity<String> update(@PathVariable("user_id") String userId, @RequestBody UserDTO dto) {
        dto.setUser_id(userId);

        int updateResult = service.update(dto);

        if (updateResult > 0) {
            log.info("** update HttpStatus.OK => " + HttpStatus.OK);
            return new ResponseEntity<>("회원정보 수정 성공", HttpStatus.OK);
        } else {
            log.info("** update HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<>("회원정보 수정 실패", HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping(value = "/pwupdate/{user_id}")
    public ResponseEntity<String> pwupdate(HttpServletRequest request, @PathVariable("user_id") String userId, @RequestBody UserDTO dto) {
        dto.setUser_id(userId);

        // 비밀번호 암호화
        String newPassword = dto.getUser_password();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(newPassword);

        // 암호화된 비밀번호를 DTO에 설정
        dto.setUser_password(encryptedPassword);

        int pwupdateResult = service.pwupdate(dto);
        log.info("pwupdateResult =" + pwupdateResult);
        if (pwupdateResult > 0) {
            log.info("** pwupdate HttpStatus.OK => " + HttpStatus.OK);
            return new ResponseEntity<>("비밀번호 수정 성공", HttpStatus.OK);
        } else {
            log.info("** pwupdate HttpStatus.BAD_GATEWAY => " + HttpStatus.BAD_GATEWAY);
            return new ResponseEntity<>("비밀번호 수정 실패", HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping(value = "/checkPassword/{user_id}")
    public ResponseEntity<String> checkPassword(@PathVariable("user_id") String user_id, @RequestBody UserDTO dto) {
        //입력받은 비밀번호
        String inputPassword = dto.getUser_password();

        dto.setUser_id(user_id);

        //데이터베이스에서 가져온 비밀번호
        String storedEncryptedPassword = mapper.checkUserPw(user_id);
        log.info("userId =" + user_id);
        log.info("inputPassword =" + inputPassword);
        log.info("storedEncryptedPassword =" + storedEncryptedPassword);
        if (storedEncryptedPassword != null) {
            // 비밀번호 비교
            if (passwordEncoder.matches(inputPassword, storedEncryptedPassword)) {
                // 비밀번호 일치
                return new ResponseEntity<>("비밀번호 확인 성공", HttpStatus.OK);
            } else {
                // 비밀번호 불일치
                return new ResponseEntity<>("비밀번호 확인 실패", HttpStatus.BAD_REQUEST);
            }
        } else {
            // 사용자가 존재하지 않을 경우 처리
            return new ResponseEntity<>("사용자가 존재하지 않습니다", HttpStatus.NOT_FOUND);
        }
    }

}
