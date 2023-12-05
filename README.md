# 학습 관리 시스템(Learning Management System)

## 1단계 - 레거시 코드 리팩터링
### 질문 삭제하기 리팩터링 요구사항
- QnaService의 deleteQuestion() 메서드에 단위 테스트 가능한 코드를 도메인 모델 객체에 구현한다
- QnaService의 비즈니스 로직을 도메인 모델로 이동하는 리팩터링을 진행할 때 TDD로 구현한다.
- QnaService의 deleteQuestion() 메서드에 대한 단위 테스트는 src/test/java 폴더 nextstep.qna.service.QnaServiceTest이다. 도메인 모델로 로직을 이동한 후에도 QnaServiceTest의 모든 테스트는 통과해야 한다.

## 2단계 - 수강신청(도메인 모델)
### 수강 신청 기능 요구사항
- [x] 과정(Cource)은 기수 단위로 운영하며, 여러 개의 강의(Session)를 가질 수 있다.
- 강의
  - [x] 강의는 시작일과 종료일을 가진다.
  - [x] 강의는 강의 커버 이미지 정보를 가진다.
    - [x] 이미지 크기는 1MB 이하여야 한다.
    - [x] 이미지 타입은 gif, jpg(jpeg 포함), png, svg만 허용한다.
    - [x] 이미지의 width는 300픽셀, height는 200픽셀 이상이여야 하며, width와 height의 비율은 3:2여야 한다.
  - [x] 강의는 무료 강의와 유료 강의로 나뉜다.
    - [x] 무료 강의는 최대 수강 인원 제한이 없다.
    - [x] 유료 강의는 강의 최대 수강 인원을 초과할 수 없다.
    - [ ] 유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.
  - [x] 강의 상태는 준비중, 모집중, 종료 3가지 상태를 가진다.
  - [x] 강의 수강신청은 강의 상태가 모집중일 때만 가능하다.
  - [x] 유료 강의의 경우 결제는 이미 완료한 것으로 가정하고 이후 과정을 구현한다.
    - [x] 결제를 완료한 결제 정보는 payments 모듈을 통해 관리되며, 결제 정보는 Payment 객체에 담겨 반환한다.
