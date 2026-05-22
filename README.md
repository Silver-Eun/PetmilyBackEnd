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

### 🔄 리팩토링, 기능 개선 및 배포 (2024.11.04 ~ 2025.02.16)  

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
### 🔄 트러블 슈팅 및 성능 최적화 (2026.03.26, 2026.05.22) 
- **초기 접속 지연(Cold Start) 및 DB 커넥션 병목 해결**
  - **이슈:** 사이트 최초 접속 시 백엔드 API 응답 지연으로 인해 프론트엔드에서 레이아웃 시프트(Layout Shift) 및 이미지 렌더링 지연이 1초 이상 발생
  - **원인 파악:** Cloudtype 무료 티어의 서버 Sleep 문제뿐만 아니라, 오랫동안 요청이 없을 시 DB 커넥션 풀(Connection Pool)이 휴면 상태에 빠져 재연결에 긴 시간이 소요되는 것을 확인
  - **해결:** UptimeRobot을 활용하여 5분마다 단순 Health Check가 아닌 실제 메인 홈 API(/api/rsproduct/promotionInfoList)를 주기적으로 호출하도록 설정.
  - **결과:** 서버 상시 가동은 물론, DB 커넥션 풀 활성화 및 버퍼 풀 캐싱(Cache Warm-up)을 유지하여 초기 API 응답 속도를 3초 대에서 0.5초대 이내로 단축하고 UX를 크게 개선함
---
### 📎 배포 링크  
- **Petmily**: [https://petmilyreal.netlify.app/](https://petmilyreal.netlify.app/)  
- **Petmily 관리자페이지**: [https://port-0-petmilyreal-1272llwrbm1kq.sel5.cloudtype.app/api/home](https://port-0-petmilyreal-1272llwrbm1kq.sel5.cloudtype.app/api/home)
