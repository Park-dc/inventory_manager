package rentalsvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 

 @RestController
 public class InventoryController {
	 @Autowired InventoryService inventoryService;

@RequestMapping(value = "/inventoryCheck",
        method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
public Long inventoryCheck(@RequestParam Long productId)
        throws Exception {
		Long currentInventory = 0L;
        currentInventory = inventoryService.inventoryCheck(productId);
        System.out.println("##### /inventory/inventoryCheck  called #####");
		return currentInventory;
        }
 }
