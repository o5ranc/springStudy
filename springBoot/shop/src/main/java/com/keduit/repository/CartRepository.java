package com.keduit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.keduit.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
