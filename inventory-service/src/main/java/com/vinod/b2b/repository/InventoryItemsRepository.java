package com.vinod.b2b.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinod.b2b.entity.InventoryItem;

public interface InventoryItemsRepository extends JpaRepository<InventoryItem, UUID>{

}
