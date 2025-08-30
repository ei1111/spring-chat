## 스프링 채팅 프로그램

### stomp을 이용해서 채팅 프로그램 구현
기능: spring secutiry를 통해 로그인한 사용자는 채팅을 할 수 있다. 
사용자 기능 구별: 아이디가 consultant로 만들어진 사용자는 모든 채팅방을 볼수 있지만, 일반 유저는 자신의 채팅 방만
확인이 가능하다.

기능 요약
사용자 -> 로그인시 -> 화면에 채팅방 html 랜더링
-> 채팅방 개설 -> 채팅시 stomp를 통해 실시간으로 채팅 기능, 실시간으로 채팅 내용 StompChatController에 전달,
StompChatController에서 디비 저장 후 반환 채팅 내용 html으로 화면 렌더링
-> 로그아웃 되어도 채팅 내용 볼수 있는데 StompChatController에서 저장된 채팅 내용 디비에서 조회 후
화면 랜더링