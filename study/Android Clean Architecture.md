# Android Clean Architecture
### Clean Architecture란?
> 클린 아키텍처는 Uncle Bob이 2012년 엔터프라이즈 아키텍처에서 논의 되던 내용을 집약시킨 개념입니다.
### Clean Architecture 구조 및 특징
![클린 아키텍처 구조](https://miro.medium.com/max/875/1*_HxTmyiDQNCfACBZle67rw.jpeg)
> 클린 아키텍처의 구조는 위와 같이 총 4가지 계층으로 되어 있습니다.  
이렇게 계층을 나누는 이유는 계층을 분리하여 관심사를 분리시키기 위해서이며 이런 아키텍처가 동작하기 위해서는 의존성 규칙을 지켜야 합니다.
한마디로 각 분리된 클래스가 한가지 역할만 하고 서로 의존을 어떻게 할지 규칙이 정해져있고 지켜야한다는 말입니다.  

> 여기서 의존성 규칙은 모든 소스코드 의존성은 반드시 외부에서 내부로, 고수준 정책을 향해야 합니다.
> (위 그림에서는 원 안쪽으로 갈수록 의존성이 낮아집니다.) 
> 즉 안드로이드를 예로들면 비즈니스 로직을 담당하는 ViewModel과 같은 코드들이 DB 또는 Web 같이 구체적인 세부 사항에 의존하지 않아야 합니다.
> 이를 통해 비즈니스 로직(고수준 정책)은 세부 사항들(저수준 정책)의 변경에 영향을 받지 않도록 할 수 있습니다.

+ 엔티티(Entities) : 가장 일반적인 비즈니스 규칙을 캡슐화하고 DTO(Data Transfer Object)도 포함하는 전사적 비즈니스 규칙입니다. 외부가 변경되면 이러한 규칙이 변경 될 가능성이 가장 적습니다.
+ 유스케이스(Use cases) : Intereactor라고도 하며 소프트웨어의 애플리케이션 별 비즈니스 규칙을 나타냅니다.이 계층은 데이터베이스, 공통 프레임 워크 및 UI에 대한 변경으로부터 격리됩니다.
+ 인터페이스 어댑터(Interface Adapters (Presenters)) : 인터페이스 어댑터는 데이터를 Entity 및 UseCase의 편리한 형식(Format) 에서 데이터베이스 및 웹에 적용 할 수있는 형식으로 변환합니다. 이 계층에는 MVP의 Presenter, MVVM의 ViewModel 및 게이트웨이 (= Repositories)가 포함됩니다. 즉 순수한 비즈니스 로직만을 담당하는 역할을 하게 됩니다.
+ 프레임워크와 드라이버(Frameworks & Drivers (Web, DB)) : 프레임워크와 드라이버는 웹 프레임 워크, 데이터베이스, UI, HTTP 클라이언트 등으로 구성된 가장 바깥 쪽 계층입니다.

### Android Clean Architecture 구조 및 특징
> 안드로이드용으로 이해하기 쉽게 만들어진 클린아키텍처 구조는 Entity 레이러를 따로 두지않고 일반적으로 Presentation, Domain, Data 총 3개의 계층으로 크게 나눠지게 됩니다. 그리고 바로 아래 그림을 보면 알 수 있듯이 Presentation -> Domain 방향으로 의존성이 있습니다.<br>

![안드로이드 클린 아키텍처 구조](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FTLaX8%2FbtqVe8vz0KS%2FnKeRIjAm8kcjNkcyXlXCiK%2Fimg.png)

+ Presentation : UI(Activity, Fragment), Presenter 및 ViewModel을 포함합니다. 즉 화면과 입력에 대한 처리 등 UI와 직접적으로 관련된 부분을 담당합니다. 또한 Presentation 레이어는 Domain과 Data 레이어를 포함하고 있다는 특징이 있습니다.
+ Domain : 애플리케이션의 비즈니스 로직을 포함하고 비즈니스 로직에서 필요한 Model 과 UseCase를 포함하고 있습니다. 기존 MVVM을 하고 클린아키텍처를 공부한다면 UseCase를 처음보실텐데 한번만 더 짚고 넘어가자면 각 개별 기능 또는 비즈니스 논리 단위라고 보시면 됩니다. 그래서 UseCase는 보통 한 개의 행동을 담당하고 UseCase의 이름만 보고 이게 무슨 기능을 가졌을지 짐작하고 구분할 수 있어야합니다.  추가로 Domain 레이어는 Presentation, Data 레이어와 어떤 의존성도 맺지 않고 독립적이다는 특징이 있습니다. 
+ Data : Repositoy 구현체, Cache, Room DB, Dao, Model 서버API(Retrofit2) 을 포함하고 있으며 로컬 또는 서버 API와 통신하여 데이터를 CRUD 하는 역할을 합니다. 또한 Mapper 클래스도 포함하고 있는데 DB로 부터 받아온 데이터모델과 UI에 맞는 데이터모델간의 변환을 해주는 역할을 합니다. 추가로 Domain 레이어를 포함하고있다는 특징이 있습니다.
