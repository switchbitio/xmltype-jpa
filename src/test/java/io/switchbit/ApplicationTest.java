package io.switchbit;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;

import javax.xml.bind.JAXB;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import io.switchbit.domain.Order;
import io.switchbit.persistence.OrderEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
@ActiveProfiles("test")
public class ApplicationTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void persist_order_successfully() throws IOException {
        Order order = JAXB.unmarshal(new ClassPathResource("order.xml").getFile(), Order.class);

        RestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<OrderEntity> response = restTemplate.postForEntity(
                format("http://localhost:%d/orders", port),
                order,
                OrderEntity.class);

        assertThat("Placing order did not return 200 OK", response.getStatusCode(), equalTo(OK));
        assertNotNull("Persisted order Id cannot be null", response.getBody().getId());
    }
}
