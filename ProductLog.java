package jframe;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.border.*;

public class ProductLog extends JFrame {
//Initialize static values for frame size and Result area size
	private static final int AREA_ROWS = 25;
	private static final int AREA_COLUMNS = 50;
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 300;

//Create Action Listener
	private ActionListener listener;
//Create an object of ProductLogActions class
	ProductLogActions actions;

//Create Panels
	private JPanel addProductPanel;
	private JPanel modifyProductPanel;
	private JPanel deleteProductPanel;
	private JPanel resultPanel;
	private JTextArea resultArea;
	private JPanel centerPanel;
	private JPanel northPanel;

//Create Labels 
	private JLabel deletepIdLabel;

	private JLabel addpNameLabel;
	private JLabel addpriceLabel;

	private JLabel modifypNameLabel;
	private JLabel modifypriceLabel;
	private JLabel modifypIdLabel;

//Create textFields	
	private JTextField deletepIdTextField;
	private JTextField addpNameTextField;
	private JTextField addpriceTextField;

	private JTextField mpIdTextField;
	private JTextField mpNameTextField;
	private JTextField mpriceTextField;

//Create all buttons
	private JButton addDataButton;
	private JButton addshowAllDataButton;
	private JButton modifyDataButton;
	private JButton deleteDataButton;
	private JButton modifyshowAllDataButton;
	private JButton deleteshowAllDataButton;

	/* constructor */

	public ProductLog() throws IOException // design the interface
	{
		// set size of the productlog frame
		setSize(FRAME_WIDTH, FRAME_HEIGHT);

		actions = new ProductLogActions();
		// Inner class for implementation of action listener
		class ProductSelectionListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				String filepath = "product.txt";
				try {
					// open the pointer to file
					actions.open(filepath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Add Product Action
				if (ae.getSource() == addDataButton) {
					try {
						// get size of record to set it to count in pojo class
						ProductPoJo.setCount(actions.size());
						// get the user passed values to variables
						double price = Double.parseDouble(addpriceTextField.getText());
						String name = addpNameTextField.getText();

						// hold the values in temp product using the ProductPojo class constructor
						ProductPoJo tempProduct = new ProductPoJo(name, price);

						// then add the data
						actions.addNewProduct(tempProduct, actions.size());
						// Show a pop up once the record is added
						JOptionPane.showMessageDialog(null, "Product added Successfully ");

						// reset the text field to blank once the insert operation is doe
						addpriceTextField.setText("");
						addpNameTextField.setText("");
						// finally close the file
						actions.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				if (ae.getSource() == modifyDataButton) {
					try {
						// fetch the user given Product id to a variable
						int searchId = Integer.parseInt(mpIdTextField.getText());
						// search the id which need to be modified
						int modifyRecord = actions.modifyProduct(searchId);
						if (modifyRecord >= 0) {
							// If the id found then hold the latest values in a temp product using
							// constructor
							ProductPoJo tempProduct = new ProductPoJo(searchId, mpNameTextField.getText(),
									Double.parseDouble(mpriceTextField.getText()));
							// call action method to write the latest data
							actions.addNewProduct(tempProduct, modifyRecord);

							// show a final pop up to user on action
							JOptionPane.showMessageDialog(null,
									"Modification Successfull Of ID ::" + mpIdTextField.getText());
						} else {
							// show a message if product not found
							JOptionPane.showMessageDialog(null, "Product not found");
						}
						// reset the fields to blank
						mpIdTextField.setText("");
						mpNameTextField.setText("");
						mpriceTextField.setText("");
						// close the file
						actions.close();
					} catch (Exception e2) {

					}
				}

				if (ae.getSource() == deleteDataButton) {
					try {
						// fetch the user given Product id to a variable
						int deletePId = Integer.parseInt(deletepIdTextField.getText());
						// search for the user passed productid
						int deleteRecord = actions.modifyProduct(deletePId);
						// if product id is found
						if (deleteRecord >= 0) {
							// call delete record action
							actions.deleteProduct(deleteRecord);
							// show message once the record is deleted
							JOptionPane.showMessageDialog(null,
									"Deleted the Product for ID:: " + deletepIdTextField.getText());
						} else
							// show message if product id not found
							JOptionPane.showMessageDialog(null, "Product Not Found");
						// close the file
						actions.close();
						// reset the id field to blank
						deletepIdTextField.setText("");

					} catch (Exception e2) {

					}

				}
				if (ae.getSource() == addshowAllDataButton || ae.getSource() == deleteshowAllDataButton
						|| ae.getSource() == modifyshowAllDataButton) {
					try {
						// open pointers
						actions.open("product.txt");
						// Fetch all products
						String readAllProducts = actions.readAllProducts();
						// show the result in result area
						resultArea.setText(readAllProducts);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		listener = new ProductSelectionListener();

		// methods to create panel and layout
		CreatePanelToAdd();
		CreatePanelToModify();
		CreatePanelToDelete();
		CreateResultArea();
		CreateLayout();

	}

	private void CreatePanelToDelete() {
		deleteProductPanel = new JPanel();
		deleteProductPanel.setLayout(new GridLayout(2, 2));
		deleteProductPanel.setBorder(new TitledBorder(new EtchedBorder(), "Delete Product From File"));

		deletepIdLabel = new JLabel("Product Id");
		deletepIdTextField = new JTextField(10);
		deleteProductPanel.add(deletepIdLabel);
		deleteProductPanel.add(deletepIdTextField);

		deleteDataButton = new JButton("DELETE PRODUCT");
		deleteDataButton.addActionListener(listener);
		deleteProductPanel.add(deleteDataButton);

		deleteshowAllDataButton = new JButton("Show Data");
		deleteshowAllDataButton.addActionListener(listener);
		deleteProductPanel.add(deleteshowAllDataButton);
	}

	public void CreatePanelToAdd() {
		addProductPanel = new JPanel();
		addProductPanel.setLayout(new GridLayout(3, 2));
		addProductPanel.setBorder(new TitledBorder(new EtchedBorder(), "Add Product To File"));

		addpNameLabel = new JLabel("Product Name");
		addpNameTextField = new JTextField(20);
		addProductPanel.add(addpNameLabel);
		addProductPanel.add(addpNameTextField);

		addpriceLabel = new JLabel("Price");
		addpriceTextField = new JTextField(30);
		addProductPanel.add(addpriceLabel);
		addProductPanel.add(addpriceTextField);

		addDataButton = new JButton("ADD PRODUCT");
		addDataButton.addActionListener(listener);
		addProductPanel.add(addDataButton);

		addshowAllDataButton = new JButton("Show Data");
		addshowAllDataButton.addActionListener(listener);

		addProductPanel.add(addshowAllDataButton);

	}

	public void CreatePanelToModify() {
		modifyProductPanel = new JPanel();
		modifyProductPanel.setLayout(new GridLayout(4, 2));
		modifyProductPanel.setBorder(new TitledBorder(new EtchedBorder(), "Modify Product on File"));

		modifypIdLabel = new JLabel("Product Id");
		mpIdTextField = new JTextField(10);
		modifyProductPanel.add(modifypIdLabel);
		modifyProductPanel.add(mpIdTextField);

		modifypNameLabel = new JLabel("Product Name");
		mpNameTextField = new JTextField(20);
		modifyProductPanel.add(modifypNameLabel);
		modifyProductPanel.add(mpNameTextField);

		modifypriceLabel = new JLabel("Price");
		mpriceTextField = new JTextField(10);
		modifyProductPanel.add(modifypriceLabel);
		modifyProductPanel.add(mpriceTextField);

		modifyDataButton = new JButton("MODIFY PRODUCT");
		modifyDataButton.addActionListener(listener);
		modifyProductPanel.add(modifyDataButton);

		modifyshowAllDataButton = new JButton("Show Data");
		modifyshowAllDataButton.addActionListener(listener);

		modifyProductPanel.add(modifyshowAllDataButton);
	}

	private void CreateResultArea() {
		resultArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		resultArea.setEditable(false);
		resultArea.setLineWrap(true);
	}

	public void CreateLayout() {
		centerPanel = new JPanel();
		northPanel = new JPanel();
		resultPanel = new JPanel();
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Product Log"));
		resultPanel.add(resultArea);

		northPanel.add(addProductPanel);
		northPanel.add(modifyProductPanel);
		centerPanel.add(deleteProductPanel);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(resultPanel, BorderLayout.SOUTH);

	}

}
