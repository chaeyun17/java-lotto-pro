## 5단계 - 로또(수동)

### 기능 요구사항

- 사용자가 수동으로 추첨 번호를 입력할 수 있도록 한다.
- 입력: 구입금액, 수동 구매할 로또 수, 수동으로 구매할 번호

### 프로그래밍 요구사항

- 모든 원시값과 문자열을 포장.
    - 로또 숫자가 int이니 LottoNumber 구현
- 축약 금지
- 예외처리를 통해 에러가 발생하지 않도록 하기
    - 사용자가 잘못된 값을 이력 시 exception으로 에러 처리하기
    - java 8 optional 을 적용해 NullPointerException 발생하지 않도록 하기
- UI 로직을 제외하고, 모두 TDD로 구현한다
- java enum 을 사용
- 일급 콜렉션 사용
- indent 1까지 허용
- 함수 길이 15라인 이하
- 자바 코드 컨벤션 지키기
- else 예약서 사용하지 않기

### 기능 목록

1. 구매금액을 입력받아 금액에 맞게 로또 번호 자동 생성하여 보여준다
    1. 구매 금액을 CLI 로 입력받는다
    2. 수동으로 구매할 로또 수를 입력한다
        - 입력한 수동 로또수가 구매 가능 로또수를 초과하면 '구매가능한 로또 수를 초과하였습니다' 메시지를 출력한다
    3. 수동으로 여러 로또번호를 입력한다
        - 마지막 입력을 마치면 자동구매로 바로 진행하여, 초과 입력을 막는다
        - 입력하는 로또번호가 로또번호에 유효(1~45, 중복 안됨)하지 않으면 '로또 번호는 1~45이며, 중복이 없어야 합니다' 메시지를 출력한다. 그리도 다시 입력 받는다.
    4. 구매 갯수에 맞게 로또 번호들을 자동생성한다
        - 1~45 숫자, 6개 번호, 오름차순 정렬
    5. 구매 갯수와 자동생성한 번호를 보여준다
        - 수동으로 몇장, 자동으로 몇장을 출력한다
        - 수동 번호들과 자동 번호들을 출력한다
3. 지난주 당첨번호를 입력받아 당첨통계(수익률)를 보여준다
    1. 지난주 당첨번호를 입력받는다
    2. 보너스볼을 입력받는다
    3. 지난주 당첨번호로, 자동생성한 번호들의 당첨 내역을 계산한다
    4. 당첨 내역으로 수익률(기준 1.0)을 계산한다
    5. 당첨내역와 수익률을 보여준다
4. 프로그램을 종료한다

#### 객체 설계

- InputView: 입력
    - 구매 금액, 수동 구매 로또 수, 수동 구매 로또 번호들을 입력받아 DTO로 반환한다.
    - 지난주 당첨번호와 보너스볼을 입력아 DTO로 반환한다.
- ResultView: 출력
    - 수동으로 구매한 로또 수와 자동으로 구매한 로또수를 보여준다
    - 수동입력한 로또들과 자동생성한 로또들을 보여준다
        - 로또를 정렬하여 오름차순으로 로또를 보여준다
    - 당첨내역과 수익률을 보여준다.
- LottoGameController: 게임 진행을 위한 객체. 입출력과 도메인 간의 연계 및 게임 진행
    - 구입급액을 입력받아, 구매 가능한 수를 계산한다
    - 수동으로 구매할 로또 수를 입력받을 때, 유효성 체크를 한다.
        - 입력한 수동 로또수가 구매 가능 로또수를 초과하면 '구매가능한 로또 수를 초과하였습니다' 메시지를 출력한다
    - 수동으로 여러 로또번호를 입력받은 것을 유효성체크를 하고, 로또들로 변환한다.
        - 마지막 입력을 마치면 자동구매로 바로 진행하여, 초과 입력을 막는다
        - 입력하는 로또번호가 로또번호에 유효(1~45, 중복 안됨)하지 않으면 '로또 번호는 1~45이며, 중복이 없어야 합니다' 메시지를 출력한다. 그리도 다시 입력 받는다.
    - 수동으로 몇장 자동으로 몇장 구매 내역을 출력한다
    - 수동과 자동으로 발급한 로또번호들을 출력한다.
    - 지난주 당첨 번호와 보너스번호를 입력받아, 당첨로또로 변환한다
    - 당첨 통계를 출력한다
        - 당첨 일치 갯수를 출력한다(당첨금 안내도 같이)
        - 총 수익률을 출력한다.
- Money: 구매금액과 총상금에 사용
    - 숫자를 입력하여 생성할 수 있다.
    - 0이상 정수이어야 한다
- PositiveNumber
    - 0 이상 정슈어야 한다
    - 수동 구매 갯수, 구입가능갯수
- Purchase
    - 구입급액을 입력받아, 구매 가능한 수를 계산한다
- ErrorCode
    - 에러 코드가 주어지면 정해진 에러 메시지 문자열를 반환한다.
- LottoNumber
    - 번호가 1~45 범위 이내인지 체크한다
- LottoNumbers
    - 로또 번호들
- LottoTicket
    - 로또 번호 6개로 로또 티켓을 생성한다
    - 로또 번호 6개는 중복이 없어야 한다
- LottoTickets
    - N개의 로또 티켓들로 생성한다
    - 로또 사이즈를 확인할 수 있다
    - 로또 티켓을 추가한다
- LottoTicketGenerator
    - 입력한 갯수에 맞는 로또 티켓들을 자동 생성한다
- WinningLotto
    - 당첨 번호 6개와 보너스 번호 1개를 저장한다
    - 로또 6자리와 당첨번호가 중복이 없도록 한다
    - 로또티켓과 비교하여 당첨순위를 반환한다
- Rank
    - 일치갯수와 보너스번호 일치여부를 입력받아 당첨순위를 생성한다
    - 당첨 순위에 해당하는 일치 개수를 반환한다
    - 당첨 순위에 해당하는 당첨금을 반환한다
    - 일치개수는 0~6 사이여야 한다
- LottoStatics
    - 로또티켓들과 당첨번호로 당첨순위들정보(RankCounts)를 계산한다
    - RankCounts와 구매금액을 통해서 수익률을 계산한다
- LottoStaticsResult
    - 순위집계와 수익률 데이터
- RankCounts
    - 순위집계 데이터

---

## 4단계 - 로또(2등)

### 기능 요구사항

- 보너스볼(추가번호)를 입력받아 2등을 추가한다
- 당첨통계에도 2등을 추가

### 프로그래밍 요구사항

- UI 로직을 제외하고, 모두 TDD로 구현한다
- java enum 을 사용
- 일급 콜렉션 사용
- indent 1까지 허용
- 함수 길이 15라인 이하
- 자바 코드 컨벤션 지키기
- else 예약서 사용하지 않기

### 기능 목록

- 보너스볼 입력
- 2위(5개 일치,보너스볼 일치) 순위 추가
- 당첨 통계에 2위 추가

#### 객체 설계

- InputView: 입력
    - 구매 금액을 입력받는다
    - 지난주 당첨번호를 입력받는다
    - 보너스볼을 입력받는다
- ResultView: 출력
    - 구매 갯수와 자동생성한 번호를 보여준다
    - 당첨내역과 수익률을 보여준다
- LottoController
    - 입력과 출력 실행
    - 로또 프로그램 진행
- LottoNumberGenerator
    - 로또 번호 자동 생성한다
- LottoNumber
    - 로또 번호 상태 저장
    - 제약사항: 1~45 숫자, 6개 번호, 오름차순 정렬, 중복 없음
- LottoNumbers
    - 로또 번호들을 상태 저장
    - 로또 번호 추가
- LottoRank
    - 해당 로또 번호의 순위 계산
- LottoRanks
    - 해당 로또 번호들의 순위 갯수 집계: 1등-0개, 2등-1개, 3등-2개, 4등-3개
    - 총 당첨금 계산
- Profit
    - 주문금액과 총 당첨금을 통해 수익률 계산

---

## 3단계 - 로또(자동)

### 기능 요구사항

- [x] 로또 구입 금액에 따라 로또들을 발급해야 한다
- [x] 로또 1장 가격은 1000원
- [x] 입출력은 console
- [x] 입력은 구입금액과 지난당첨번호
- [x] 출력은 로또 번호들과 당첨 통계(일치 갯수, 수익률)
- [x] 수익률은 소수점 2자리까지 표시

#### 로또 앱 사용 시나리오

1. 구입 금액 입력 요청
2. 구매 갯수와 로또 번호들 출력
3. 지난주 당첨 번호 입력
4. 당첨 몇개인지, 수익률 출력

### 프로그래밍 요구사항

- [x] **TDD 로 구현하기**. UI 로직 제외
- [x] 비지니스 로직과 UI 로직 분리: 클래스로 분리
- [x] indent depth 1까지 허용
- [x] 함수 라인 수 15라인 이하
- [x] 한 함수에는 한 가지 일만 하기
- [x] 모든 로직에 단위테스트 구현하기
- [x] 자바 코드 컨벤션 지키기
    - https://google.github.io/styleguide/javaguide.html
    - https://myeonguni.tistory.com/1596
- [x] else 예약어 사용 금지: if 문에서 바로 return 하기

### 힌트

- 로또 자동 생성 `Collections.shuffle()`
- 정렬 `Collections.sort()`
- 값 존재 여부 `ArrayList의 contains()`

### 기능 목록

1. 구매금액을 입력받아 금액에 맞게 로또 번호 자동 생성하여 보여준다
    1. 구매 금액을 CLI 로 입력받는다
    2. 구매 금액에 맞게 구매 갯수를 계산한다
    3. 구매 갯수에 맞게 로또 번호들을 자동생성한다
        1. 1~45 숫자, 6개 번호, 오름차순 정렬
    4. 구매 갯수와 자동생성한 번호를 보여준다
2. 지난주 당첨번호를 입력받아 당첨통계(수익률)를 보여준다
    1. 지난주 당첨번호를 입력받는다
    2. 지난주 당첨번호로, 자동생성한 번호들의 당첨 내역을 계산한다
    3. 당첨 내역으로 수익률(기준 1.0)을 계산한다
    4. 당첨내역와 수익률을 보여준다
3. 프로그램을 종료한다

#### 객체 설계

- InputView: 입력
    - 구매 금액을 입력받는다
    - 지난주 당첨번호를 입력받는다
- ResultView: 출력
    - 구매 갯수와 자동생성한 번호를 보여준다
    - 당첨내역과 수익률을 보여준다
- LottoController
    - 입력과 출력 실행
    - 로또 프로그램 진행
- LottoNumberGenerator
    - 로또 번호 자동 생성한다
- LottoNumber
    - 로또 번호 상태 저장
    - 제약사항: 1~45 숫자, 6개 번호, 오름차순 정렬, 중복 없음
- LottoNumbers
    - 로또 번호들을 상태 저장
    - 로또 번호 추가
- LottoRank
    - 해당 로또 번호의 순위 계산
- LottoRanks
    - 해당 로또 번호들의 순위 갯수 집계: 1등-0개, 2등-1개, 3등-2개, 4등-3개
- LottoPrize
    - 해당 순위의 당첨금 계산: 4등-5000원
- LottoPrizes
    - 여러 순위들의 총 당첨금 계산: 총 5000원
- Profit
    - 주문금액과 총 당첨금을 통해 수익률 계산
    - 제약사항: 소수점 두번째자리

----

## 2단계 - 문자열 덧셈 계산기

### 기능 요구사항

- 쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리한 각 숫자의 합을 반환 (예: “” => 0, "1,2" => 3, "1,2,3" => 6, “1,2:3” => 6)
- 앞의 기본 구분자(쉼표, 콜론)외에 커스텀 구분자를 지정할 수 있다. 커스텀 구분자는 문자열 앞부분의 “//”와 “\n” 사이에 위치하는 문자를 커스텀 구분자로 사용한다. 예를 들어 “//;\n1;2;3”과 같이
  값을 입력할 경우 커스텀 구분자는 세미콜론(;)이며, 결과 값은 6이 반환되어야 한다.
- 문자열 계산기에 숫자 이외의 값 또는 음수를 전달하는 경우 RuntimeException 예외를 throw한다.

### 프로그래밍 요구사항

- indent(들여쓰기) depth를 2단계에서 1단계로 줄여라.
- depth의 경우 if문을 사용하는 경우 1단계의 depth가 증가한다. if문 안에 while문을 사용한다면 depth가 2단계가 된다.
- 메소드의 크기가 최대 10라인을 넘지 않도록 구현한다.
- method가 한 가지 일만 하도록 최대한 작게 만들어라.
- else를 사용하지 마라.

### 기능 목록

1. 입력: 문자열을 입력받는다
2. 구분자 파악: 입력값의 구분자를 파악한다. 콤마 or 콜론 or 커스텀
3. 숫자 파싱: 구분자를 통해 문자열을 파싱하여, 숫자를 추출한다.
4. 파싱한 숫자 검증: 숫자 이외의 값 또는 음수 체크
5. 연산: 숫자들을 모두 합 연산한다.

### 도메인 설계

기능(행위)를 묶어서 객체들을 생성

1. Parser
    1. 구분자 파싱
    2. 구분자를 통한 숫자 파싱
2. 덧셈기 Adder
    1. 양의 정수들을 모두 합하고 결과값을 반환
3. 문자열 덧셈 계산기
    1. 사용자로부터 문자열을 입력받아, 파싱하여 모두 합친 값을 반환
    2. Parser, Adder 객체를 활용하여 기능 수행

--- 

# 로또

## 진행 방법

* 로또 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

--- 

## 참고

### TDD 과정

- 실패하는 테스트를 구현한다.
- 테스트가 성공하도록 프로덕션 코드를 구현한다.
- 프로덕션 코드와 테스트 코드를 리팩토링한다.

### TDD 원칙

- 원칙 1 - 실패하는 단위 테스트를 작성할 때까지 프로덕션 코드(production code)를 작성하지 않는다.
- 원칙 2 - 컴파일은 실패하지 않으면서 실행이 실패하는 정도로만 단위 테스트를 작성한다.
- 원칙 3 - 현재 실패하는 테스트를 통과할 정도로만 실제 코드를 작성한다.




