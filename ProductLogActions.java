package jframe;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ProductLogActions {
	// create randomfileaccess object
	private RandomAccessFile file = null;

	// initialize int data size
	public static final int INT_SIZE = 4;

	// initialize double size data
	public static final int DOUBLE_SIZE = 8;

	// initialize double size data
	public static final int CHAR_SIZE = 50;

	// create final memory size of a record
	public static final int PRODUCT_RECORD_SIZE = INT_SIZE + DOUBLE_SIZE + CHAR_SIZE;

	// create default constructor
	public ProductLogActions() {
		file = null;
	}

	// open pointer to file
	public void open(String filename) throws IOException {
		if (file != null)
			file.close();
		file = new RandomAccessFile(filename, "rw");

	}

	// close the file by assigning null
	public void close() throws IOException {
		if (file != null)
			file.close();
		file = null;
	}

	// return length of a record
	public int size() throws IOException {
		return (int) (file.length() / PRODUCT_RECORD_SIZE);
	}

	// adding product details to file
	public void addNewProduct(ProductPoJo productPoJo, int numberOfProducts) throws IOException {
		// search the index in the file
		file.seek(numberOfProducts * PRODUCT_RECORD_SIZE);

		// write the product id , price  the file
		file.writeInt(productPoJo.getProductId());
		file.writeDouble(productPoJo.getPrice());
		
		//hold the product name in a variable
		String newProductName = productPoJo.getProductName();
		//get the length of the product name
		int lengthOfModiefiedProductName = newProductName.length();
		//deduct the length of the product from 25
		//running the loop to go through 25 char data(50 bytes) for the product name to add white spaces in the extra blocks
		for (int i = 1; i <= (25 - lengthOfModiefiedProductName); i++) {
			newProductName += " ";
		}
		//write the product name
		file.writeChars(newProductName);
	}

	public int modifyProduct(int searchId) throws IOException {
		//running a loop through entire records to match with id
		for (int i = 0; i < this.size(); i++) {
			file.seek(i * PRODUCT_RECORD_SIZE);
			//if the product id found , then return the position
			if (file.readInt() == searchId)
				return i;
		}
		// if the product id not found return -1
		return -1;
	}

	public void deleteProduct(int searchId) throws IOException {
		//find the position of the user passed product id
		file.seek(searchId * PRODUCT_RECORD_SIZE);
		file.writeInt(-1);
	}

	public String readAllProducts() throws IOException { // TODO Auto-generated

		// create result header
		String tableData = "|   Product Id           |           Product Name                    |  Price\t |\n";
		String separator = "-----------------------------------------------------------------------------------------------------------\n";
		//run loop to find all the product
		for (int i = 0; i < this.size(); i++) {
			file.seek(i * PRODUCT_RECORD_SIZE);

			String productName = "";
			// read product id, product price and product name
			int prodId = file.readInt();
			double prodPrice = file.readDouble();
			for (int j = 0; j < 25; j++) {
				productName += file.readChar();
			}
			// add the record 
			if (prodId != -1)
				tableData = tableData + separator + "|\t" + prodId + "|\t" + productName + "|\t" + prodPrice + "  |\n";
		}

		// return string for output
		return tableData;
	}

}
