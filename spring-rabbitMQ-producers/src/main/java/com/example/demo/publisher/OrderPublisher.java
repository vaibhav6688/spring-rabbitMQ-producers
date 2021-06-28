package com.example.demo.publisher;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.MessagingConfig;
import com.example.demo.dto.Order;
import com.example.demo.dto.OrderStatus;

@RestController
@RequestMapping("/order")
public class OrderPublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@PostMapping("/{restaurentName}")
	public String bookOrder(@RequestBody Order order, @PathVariable String restaurentName) {
		
		order.setOrderId(UUID.randomUUID().toString());
		//restaurent-service
		//payment-service
		
		OrderStatus orderStatus = new OrderStatus(order, "Progress", "Order placed successfully in "+ restaurentName);
		rabbitTemplate.convertAndSend(MessagingConfig.EXCHNAGE, MessagingConfig.ROUTING_KEY, orderStatus);
		
		return "Success !!";
	}
	
}
