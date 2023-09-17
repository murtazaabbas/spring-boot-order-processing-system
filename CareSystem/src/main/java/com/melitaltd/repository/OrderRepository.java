package com.melitaltd.repository;

import com.melitaltd.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<List<OrderEntity>> findByApprove(int approve);
    Optional<OrderEntity> findById(int Id);

}
