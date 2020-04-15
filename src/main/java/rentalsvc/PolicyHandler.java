package rentalsvc;

import rentalsvc.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalRequested_InventoryChange(@Payload RentalRequested rentalRequested){

        if(rentalRequested.isMe()){
            System.out.println("##### listener InventoryChange(rentalRequested) : " + rentalRequested.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCancellationOccured_InventoryChange(@Payload RentalCancellationOccured rentalCancellationOccured){

        if(rentalCancellationOccured.isMe()){
            System.out.println("##### listener InventoryChange(rentalCancellationOccured) : " + rentalCancellationOccured.toJson());
        }
    }

}
