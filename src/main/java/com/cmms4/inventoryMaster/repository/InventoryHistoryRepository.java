package com.cmms4.inventoryMaster.repository;

import com.cmms4.inventoryMaster.entity.InventoryHistory;
import com.cmms4.inventoryMaster.entity.InventoryHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, InventoryHistoryId> {
    
}
