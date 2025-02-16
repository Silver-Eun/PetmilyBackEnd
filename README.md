## Petmily 📅 개발 기간 2023.05.23 ~ 2023.12.27  

### 📚 과정  
:ledger: KDT 4기 프로젝트 기반 풀스택 프로젝트  

### 👥 팀 구성  
:family: 4명  

### 🚀 기술 스택  
**Back-End** :computer:  
- Java 8 
- Spring Boot 2.7.17  
- Spring Security  
- MyBatis
- MySQL  
- Maven
- Lombok  

**Front-End** 🎨  
- React

**DevOps** ⚙
- 전자정부프레임워크
- Visual Studio Coed

---

### 📂 본인파트

#### 🛍 사용자 기능 (FE & BE)  
- **상품상세페이지, 회원정보수정 페이지 FE 구성** (BE는 3차에서 다른인원에게 재배분)
- **장바구니, 주문페이지, 주문상세페이지, 마이페이지, 주문내역페이지 BE / FE 구성**
 
#### 🔧 관리자 기능  
- **관리자페이지 중 장바구니, 주문관리 부분 구성**  
- **장바구니, 주문, 주문상세 테이블 설계**   

---

### 🔄 리팩토링 및 배포 (2024.11.04 ~ 2025.02.16)  

### 👥 팀 구성  
:family: 1명(기존 팀이 아닌 단독으로 진행)

### ✅ 기술 스택 변경  
- **개발 환경 변경**: 전자정부프레임워크 → IntelliJ  
- **Java 업그레이드**: Java 8 → Java 17  
- **Spring Boot 업그레이드**: Spring Boot 2.7.17 → Spring Boot 3.2.2  
- **빌드 시스템 변경**: Maven → Gradle
- **데이터베이스 매핑 기술 변경**: MyBatis → **JPA (Hibernate)**
- **템플릿 엔진 변경**: JSP → Thymeleaf  
- **환경 변수 설정 변경**: `application.properties` → `application.yml`

### ✅ 기능 개선  
- **관리자 페이지 회원, 게시판 부분 UX 개선**
  - 관리자페이지내 ‘회원’ 클릭시 페이지 이동 →  **하단 드롭다운 메뉴 방식 적용**
  - 관리자페이지내 ‘게시판’ 클릭시 공지사항목록이 기본으로 노출 →  **하단 드롭다운 메뉴 방식 적용**
- **회원 기능 보안 강화**  
  - 관리자페이지내에서 회원가입시 **비밀번호 암호화 기능 누락 수정**  
- **게시판 기능 추가**  
  - 관리자페이지내 FAQ에서 누락된 **글쓰기 및 수정 기능 구현**  
- **API 구조 정리**  
  - 내부 API 경로에 `/api` prefix 추가 → **프론트엔드(React) API와 명확하게 구분**
 
### ✅ 배포
- **백엔드 배포**
  - 관리자페이지 및 API는 Cloudtype에 배포
- **프론트엔드 배포**
  - 프론트엔드는 Netlify에 배포
---

### 📎 배포 링크  
- **Petmily**: [https://petmilyreal.netlify.app/](https://petmilyreal.netlify.app/)  
- **Petmily 관리자페이지**: [https://port-0-petmilyreal-1272llwrbm1kq.sel5.cloudtype.app/api/home](https://port-0-petmilyreal-1272llwrbm1kq.sel5.cloudtype.app/api/home)
