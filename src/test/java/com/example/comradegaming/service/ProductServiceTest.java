package com.example.comradegaming.service;


import com.example.comradegaming.entities.Product;
import com.example.comradegaming.enums.Category;
import com.example.comradegaming.jms.Sender;
import com.example.comradegaming.repo.ProductRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jms.core.JmsTemplate;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProductServiceTest {

    private ProductService testService;
    @Autowired
    private ProductRepo testRepository;

    @Mock
    private Sender jms;

    @BeforeEach
    void setup() {
        testService = new ProductService(testRepository, jms);
    }

    @AfterEach
    void tearDown() {
        testRepository.deleteAll();
    }


    @Test
    void find() {

        Product testProduct = new Product("testProduct", 666, "testDescription", "testImage", Category.DigitalGame);

        Product savedTestProduct = testRepository.save(testProduct);
        long returnedId = savedTestProduct.getId();
        Optional<Product> foundTestProductOptional = testService.find(returnedId);
        Product foundTestProduct = foundTestProductOptional.get();

        assertThat(testProduct.getName()).isEqualTo(foundTestProduct.getName());
        assertThat(testProduct.getProductDescription()).isEqualTo(foundTestProduct.getProductDescription());
        assertThat(testProduct.getPrice()).isEqualTo(foundTestProduct.getPrice());
        assertThat(testProduct.getCategory()).isEqualTo(foundTestProduct.getCategory());
    }

    @Test
    void add() {

        //given
        Product testProduct = new Product(
                "testMovieFilm", 1, "testDescription", "testImage", Category.Movie);

        //when
        testService.add(testProduct);
        long testProductID = testProduct.getId();

        //then
        assertThat(testProduct).isEqualTo(testService.find(testProductID).get());
    }

    @Test
    @Disabled
    void delete() {
    }

    @Test
    @Disabled
    void findAll() {
    }
}