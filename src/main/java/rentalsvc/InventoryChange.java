package rentalsvc;


public class InventoryChange extends AbstractEvent {
    private Long inventoryQty;
    private Long productId;
    
	public Long getInventoryQty() {
		return inventoryQty;
	}
	public void setInventoryQty(Long inventoryQty) {
		this.inventoryQty = inventoryQty;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
