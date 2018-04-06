import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import javax.swing.JToggleButton;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollBar;
import java.awt.Color;

public class GUI{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 737, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("ChronoTimer");

		JButton btnPower = new JButton("Power");
		btnPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnPower.setForeground(Color.BLACK);
		btnPower.setBounds(12, 25, 94, 25);
		frame.getContentPane().add(btnPower);

		JRadioButton ch_1 = new JRadioButton("ch1");
		ch_1.setBounds(160, 0, 64, 25);
		frame.getContentPane().add(ch_1);

		JRadioButton ch_2 = new JRadioButton("ch2");
		ch_2.setBounds(160, 60, 64, 25);
		frame.getContentPane().add(ch_2);

		JButton btnFunction = new JButton("Function");
		btnFunction.setBounds(12, 236, 94, 25);
		frame.getContentPane().add(btnFunction);

		JButton btnSwap = new JButton("Swap");
		btnSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		btnSwap.setBounds(12, 280, 94, 25);
		frame.getContentPane().add(btnSwap);

		JRadioButton ch_3 = new JRadioButton("ch3");
		ch_3.setBounds(222, 0, 64, 25);
		frame.getContentPane().add(ch_3);

		JRadioButton ch_4 = new JRadioButton("ch4");
		ch_4.setBounds(225, 60, 61, 25);
		frame.getContentPane().add(ch_4);

		JRadioButton ch_5 = new JRadioButton("ch5");
		ch_5.setBounds(284, 0, 64, 25);
		frame.getContentPane().add(ch_5);

		JRadioButton ch_6 = new JRadioButton("ch6");
		ch_6.setBounds(284, 60, 64, 25);
		frame.getContentPane().add(ch_6);

		JRadioButton ch_7 = new JRadioButton("ch7");
		ch_7.setBounds(346, 0, 64, 25);
		frame.getContentPane().add(ch_7);

		JRadioButton ch_8 = new JRadioButton("ch8");
		ch_8.setBounds(347, 60, 63, 25);
		frame.getContentPane().add(ch_8);

		JTextArea queueScreen = new JTextArea();
		queueScreen.setBounds(144, 118, 278, 274);
		frame.getContentPane().add(queueScreen);

		JButton btnPrinterPower = new JButton("Printer Power");
		btnPrinterPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPrinterPower.setBounds(482, 41, 157, 25);
		frame.getContentPane().add(btnPrinterPower);

		JTextArea printScreen = new JTextArea();
		printScreen.setBounds(470, 79, 184, 69);
		frame.getContentPane().add(printScreen);

		JPanel panel = new JPanel();
		panel.setBounds(485, 224, 143, 136);
		frame.getContentPane().add(panel);
		panel.setLayout(new MigLayout("", "[][][]", "[][][][]"));

		JButton button = new JButton("1");
		panel.add(button, "cell 0 0");

		JButton button_1 = new JButton("2");
		panel.add(button_1, "cell 1 0,growx");

		JButton button_2 = new JButton("3");
		panel.add(button_2, "cell 2 0,growx");

		JButton button_3 = new JButton("4");
		panel.add(button_3, "cell 0 1");

		JButton button_4 = new JButton("5");
		panel.add(button_4, "cell 1 1");

		JButton button_5 = new JButton("6");
		panel.add(button_5, "cell 2 1,growx");

		JButton button_6 = new JButton("7");
		panel.add(button_6, "cell 0 2");

		JButton button_7 = new JButton("8");
		panel.add(button_7, "cell 1 2");

		JButton button_8 = new JButton("9");
		panel.add(button_8, "cell 2 2,growx");

		JButton button_9 = new JButton("*");
		panel.add(button_9, "cell 0 3");

		JButton button_10 = new JButton("0");
		panel.add(button_10, "cell 1 3");

		JButton button_11 = new JButton("#");
		panel.add(button_11, "cell 2 3");
	}
}
