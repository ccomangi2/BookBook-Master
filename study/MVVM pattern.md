# MVVM pattern

### MVVM pattern 이란?
> MVVM 패턴은 **프로그램을 설계하기 위한 하나의 방법론**이라고도 생각할 수 있습니다.
나는 패턴이나 아키텍처에 대해 전혀 몰랐을 때 데이터를 가져오고 저장하는 코드, 데이터를 ui에 보여주는 코드, 이벤트에 따른 데이터 변경을 ui 에 갱신해주는 코드를 전부 
Activity class 에 작성했었습니다. 간단한 앱을 만들때는 그런 방식이 그다지 문제가 되지 않았습니다. 하지만, 앱이 점차 커지고 코드의 양이 많아지면서 코드들이 뒤죽박죽 엉키고, 
한가지 기능을 수정하면 거기에 따른 사이드 이펙트를 수정하는데 너무 오래 걸리는 등, 코드를 유지 보수하기가 너무 힘들었습니다.
이러한 문제를 해결하기 위해, Model, View, ViewModel 각각의 기능에 따라 분리하여 코드를 작성하는 것을 MVVM 패턴입니다.

### MVVM 구조
> **관심사 분리**를 해야 합니다.  
![MVVM 구조](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWTrsf%2FbtqFaUOPpEb%2FDba7yoR8oVQVGWW4xlvIVK%2Fimg.png)

### MVVM의 Component
+ Model
  - 앱의 데이터를 처리를 담당
  - View와는 독립적이어야 하며, 생명주기에는 영향을 받지 않도록 한다.
  - 네트워크 연결이 취약하거나 연결이 되어 있지 않아도 앱은 실행되고 돌아가야 한다.
+ View  
  - Activity나 Fragment와 같은 UI 기반의 클래스는 UI 및 운영체제 상호작용을 처리하는 로직만 포함해야 한다.
  - 최대한 가볍게 유지하며 생명주기 관련 문제를 피하도록 한다.
+ ViewModel
  - Model과 View를 연결해 주는 역할
  - View와 관련된 모든 비즈니스 로직은 이 곳에 들어간다.
  - 데이터를 잘 가공해서 View에 뿌리기 쉬운 Model로 바꾸는 역할을 한다.
