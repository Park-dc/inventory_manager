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
     * Rental 요청시 재고 변경 
     */

    @StreamListener(KafkaProcessor.INPUT)
    public void onProductChanged(@Payload RentalRequested rentalRequested) {
    
        try {
            if (rentalRequested.isMe()) {
                System.out.println("##### listener : " + rentalRequested.toJson());
                inventoryChange(rentalRequested.getProductId(),rentalRequested.getQty());
                System.out.println("재고 조정 요청 :" + rentalRequested.getQty().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
