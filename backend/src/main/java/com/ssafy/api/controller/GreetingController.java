package com.ssafy.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.db.entity.Chat;
import com.ssafy.db.entity.Marker;

@RestController
@CrossOrigin("*")
public class GreetingController {
	
	// 이 부분은 서로서로 채팅하기 전에 혼자 들어와서 메시지쳤을 때 잘 전송되는지 확인하기 위함
//	@MessageMapping("/hello")		// 클라이언트에서 /hello쪽으로 메시지를 전달하면 해당 메소드 실행
//	@SendTo("/topic/greetings")		// SendTo 어노테이션에 정의된 족으로 결과 리턴시킴
//	public Greeting greeting(HelloMessage message) throws Exception {
//		Thread.sleep(100); // delay
//		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//	}
	
	@MessageMapping("/chat/{conferenceNo}")		// 클라이언트에서 /chat쪽으로 메시지를 전달하면 해당 메소드 실행 (/app/chat으로 송신)
	@SendTo("/topic/chat/{conferenceNo}")			// SendTo 어노테이션에 정의된 쪽으로 결과 리턴시킴			(/topic/chat으로 수신)
	public Chat chat(Chat chat) throws Exception {
	  return new Chat(chat.getName(), chat.getMessage(), chat.getDate());
	}
	
	@MessageMapping("/marker/{conferenceNo}")
	@SendTo("/topic/marker/{conferenceNo}")
	public Marker marker(Marker marker) throws Exception {
		return new Marker(marker.getLat(), marker.getLng());
	}
	
}
