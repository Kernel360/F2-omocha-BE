package org.auction.client.demo.interfaces;

import java.time.LocalDateTime;

import org.auction.client.demo.interfaces.response.DemoResponse;
import org.auction.domain.demo.infrastructure.DemoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

	private final DemoRepository demoRepository;

	@GetMapping("")
	public DemoResponse save() {

		return DemoResponse.builder()
			.email("demo@gmail.com")
			.name("홍길동")
			.registeredAt(LocalDateTime.now())
			.build();
	}
}
