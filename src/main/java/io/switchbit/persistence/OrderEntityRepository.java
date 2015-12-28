package io.switchbit.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {

}
