package io.switchbit;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.StringWriter;

import javax.xml.bind.JAXB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.switchbit.domain.Order;
import io.switchbit.persistence.OrderEntity;
import io.switchbit.persistence.OrderEntityRepository;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @RequestMapping(value = "/orders", method = POST, consumes = APPLICATION_XML_VALUE)
    public OrderEntity placeOrder(@RequestBody final Order order) {
        StringWriter orderXml = new StringWriter();
        JAXB.marshal(order, orderXml);

        return orderEntityRepository.save(new OrderEntity(order));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
