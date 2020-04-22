package rentalsvc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import rentalsvc.config.kafka.KafkaProcessor;

@Service
public class InventoryService {
    
	@Autowired
    private InventoryRepository inventoryRepository;
    
	/**
     * 재고 변경 
     */
	public void inventoryChange(Long productId, Long qty) {
		// TODO Auto-generated method stub
		Optional<Inventory> inventoryOptional = inventoryRepository.findById(productId);
	    Inventory inventory = inventoryOptional.get();
	    System.out.println("재고 조정 요청 기존 :" + inventory.getQty());
	    System.out.println("재고 조정 요청 변경 :" + qty);
	    
	    inventory.setQty(inventory.getQty() - qty);
	    
	    inventoryRepository.save(inventory);
	}

	/**
     * 재고 확인  
     */
	public Long inventoryCheck(Long productId) {
		// TODO Auto-generated method stub
		Optional<Inventory> inventoryOptional = inventoryRepository.findById(productId);
	    Inventory inventory = inventoryOptional.get();
	    System.out.println("현재 재고 :" + inventory.getQty());
	    
	    return inventory.getQty();
	}
	
	
    /**
     * Rental 요청시 재고 확인
     * 재고 부족시 재고부족 Event 발생 
     * 재고 충분시 재고차감 처감 
     */

    @StreamListener(KafkaProcessor.INPUT)
    public void onRentalRequested(@Payload RentalRequested rentalRequested) {
    	
        try {
            if (rentalRequested.isMe()) {
            	if ((inventoryCheck(rentalRequested.getProductId()) - rentalRequested.getQty()) > 0) {
            		System.out.println("##### listener : " + rentalRequested.toJson());
                    inventoryChange(rentalRequested.getProductId(),rentalRequested.getQty());
                    System.out.println("재고 조정 요청 :" + rentalRequested.getQty().toString());
            	} else {
            		System.out.println("##### 재고 부족 : " + rentalRequested.toJson());
            		InventoryStockShortage inventoryStockShortage = new InventoryStockShortage();
            		inventoryStockShortage.setOrderId(rentalRequested.getOrderId());
            		inventoryStockShortage.setProductId(rentalRequested.getProductId());
            		inventoryStockShortage.publish();
            		System.out.println("##### inventoryStockShortage Event Published ");
            	}
               
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
