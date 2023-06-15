package com.matkap.webshopbasics;

import com.matkap.webshopbasics.entity.*;
import com.matkap.webshopbasics.repository.CustomerRepository;
import com.matkap.webshopbasics.repository.OrderItemRepository;
import com.matkap.webshopbasics.repository.OrderRepository;
import com.matkap.webshopbasics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class WebshopBasicsApplication implements CommandLineRunner {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(WebshopBasicsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Customer[] customers = new Customer[]{
				new Customer("Matija","Kapitan", "matija.kapitan1@gmail.com"),
				new Customer("Petar", "Petrovic", "petar.petrovic@gmail.com"),
				new Customer("Ivan", "Ivic", "ivica23@gmail.com"),
				new Customer("Harry", "Potter", "herry.potter@gmail.com")
		};

		for (Customer x:customers) {
			customerRepository.save(x);
		}

		Product[] products = new Product[]{
				new Product("Apple", BigDecimal.valueOf(1.23),"Red apple",true),
				new Product("Jabuka", BigDecimal.valueOf(12.23),"crvena jabuka",true),
				new Product("Jabuka", BigDecimal.valueOf(12.23),"crvena jabuka",true),
				new Product("Tea", BigDecimal.valueOf(2.47),"Green tea",false)
		};

		for (Product x: products) {
			productRepository.save(x);
		}

		Order[] orders = new Order[]{
				new Order(customers[0], StatusEnum.DRAFT),
				new Order(customers[1], StatusEnum.DRAFT)
		};

		for (Order x: orders) {
			orderRepository.save(x);
		}

		OrderItem[] orderItems = new OrderItem[]{
			new OrderItem(orders[0],products[0],12)
		};

		for (OrderItem x: orderItems) {
			orderItemRepository.save(x);
		}

	}

}
