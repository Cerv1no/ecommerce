package com.cerv1no.ecommerce.repositories;

import com.cerv1no.ecommerce.model.Cart;
import com.cerv1no.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

}