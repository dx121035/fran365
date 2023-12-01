package com.example.fran365;

import com.example.fran365.resource.Resource;
import com.example.fran365.resource.ResourceRepository;
import com.example.fran365.resource.ResourceService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@DirtiesContext
@Rollback(false)
public class ResourceServiceConcurrencyTest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    @DirtiesContext
    public void testConcurrentUpdate() throws InterruptedException {
        // Given
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // When
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {

                    resourceService.updateProductStock(20, 1);

                    latch.countDown();

            });
        }

        // Then
        latch.await(); // 모든 스레드가 완료될 때까지 기다림
    }
}