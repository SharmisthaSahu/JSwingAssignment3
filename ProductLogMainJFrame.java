package jframe;

import java.io.IOException;

import javax.swing.JFrame;


public class ProductLogMainJFrame {

	public static void main(String[] args) throws IOException {
		JFrame frame = new ProductLog();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Prodcut Detail Log");
		frame.setVisible(true);
	}

}
