package com.krillinator.demo_5;

import com.krillinator.demo_5.product.Product;
import com.krillinator.demo_5.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void clearDatabase(){
        StepVerifier.create(productRepository.deleteAll())
                .verifyComplete();
    }
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void shouldSaveProductAndRetrieve(){
        Product product = new Product(
                null,
                "Apples",
                "Apples",
                BigDecimal.valueOf(19.9),
                false,
                null);
        //test saving product to database
        StepVerifier.create(
                productRepository.save(product)
        )
                .expectNextMatches(product1 -> product1.name().equals("Apples"))
                .verifyComplete();

        //test if product exists with primary key 1
        StepVerifier.create(
                productRepository.findById(1L)
        )
                .expectNextMatches(product1 -> product1.name().equals("Apples"))
                .verifyComplete();

    }
}
