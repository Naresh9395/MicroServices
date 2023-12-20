package com.dmantz.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmantz.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
