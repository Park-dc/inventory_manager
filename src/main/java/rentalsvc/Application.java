package rentalsvc;
import rentalsvc.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
public class Application {
    protected static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
        
        InventoryRepository inventoryRepository  = applicationContext.getBean(InventoryRepository.class);
        // 랜 상품 셋팅
        String[] products = {"IPAD", "MACBOOK", "EARPHONE"};
        long productId = 1;
        for(String p : products){
            Inventory inventory = new Inventory();

            inventory.setProductName(p);
			inventory.setProductId(productId);
			inventory.setQty(100l);

			productId++;
			inventoryRepository.save(inventory);
        }
        
    }
}
