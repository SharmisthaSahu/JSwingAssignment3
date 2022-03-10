package jframe;

public class ProductPoJo {
	static int count = 0;
	int productId;
	String productName;
	double price;

	// Constructor for adding new product by generating ID automatically
	public ProductPoJo( String productName,double price) {
		
		count++;		
		this.productId = count;
		this.productName = productName;
		this.price = price;
		System.out.println(this.productId);
	}

	// Constructor to use while modifying data or deleting
	public ProductPoJo(int productId,  String productName, double price) {
		
		this.productId = productId;
		this.productName = productName;
		this.price = price;
	}

	/**
	 * @return the count
	 */
	public static int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public static void setCount(int count) {
		ProductPoJo.count = count;
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	// Getters and setters
	
	
}
