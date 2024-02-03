# PROGRAPHY-AOS
PROGRAPHY-AOS

# 아키텍처
## MVI
- 기본적으로 viewModel에서 stateHolder의 책임을 지게 설계.
- state holder의 동작 방식은 외부에서 사용자의 intent가 들어오면 내부 시스템의 동작인 event를 변경하고 각 event에 맞는 action을 수행하는 방식으로 구성.
- 각 action은 현 viewModelState와 usecase를 이용하여 새로운 view model state를 발행.
- 각 action을 스트림으로 구성.
- 각 action을 merge하여 하나의 ui state를 발행하는 flow를 구성.
- 즉 view model 사용 방식은 크게 2가지. 구독하여 uiState 관측 or intent, event를 전달하여 의도한 새로운 uiState 발행 요청.

## 모듈화
- 기본적으로 domain layer를 순수 계층의 관점으로 프로젝트 설계.

### domain 모듈
- repository 구현체를 주입 받은 usecase에서 비즈니스 로직 과정을 거친 결과 반환.
- repository 구현체는 domain 모듈에서 뚫은 dataSource 인터페이스로 구현한 구현체를 주입 받아서 구성.

### network, database 모듈
- domain에 의존성을 걸고 domain에서 필요한 dataSource를 구현.

### feature
- 각 피쳐들은 domain에 의존성을 걸고 각 usecase를 이용하여 필요한 viewmodel, ui 들을 구성.

### app
- app에서는 각 피쳐에 필요한 구현체들을 조립하여 사용.
