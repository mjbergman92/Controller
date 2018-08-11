package Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class JoystickGraph {

	static double prevTime;
	static double originalTime = System.currentTimeMillis();
	static boolean run = true;
	static boolean disabled = false;
	
	public static void main(String args[]) {
		
		Setup setup = new Setup();
		
		setup.process();
		setup.setDeadBand();
		//create and configure the window
		JFrame window = new JFrame();
		window.setTitle("Joystick Input GUI");
		window.setSize(1200,1000);
		window.isResizable();
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JButton button = new JButton("Load");
		window.add(button,BorderLayout.NORTH);
		
		//create xy line graph
		XYSeries leftSeries = new XYSeries("Left Stick Readings");
		XYSeries rightSeries = new XYSeries("Right Stick Readings");
		XYSeriesCollection dataset = new XYSeriesCollection(leftSeries);
		dataset.addSeries(rightSeries);
		JFreeChart chart = ChartFactory.createXYLineChart("Stick Readings", "Time (seconds)", "Percent Input", dataset);
		window.add(new ChartPanel(chart),BorderLayout.CENTER);
		
		
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(button.getText().equals("Load")) {
					
					button.setText("End");
					run = true;
					disabled = false;
					Thread thread = new Thread() {
						@Override public void run() {
							while(run) {
								try {
									leftSeries.add((System.currentTimeMillis()-originalTime)/1000, setup.getLeftXAxis());
									rightSeries.add((System.currentTimeMillis()-originalTime)/1000, setup.getRightXAxis());
									if(setup.isSelectPressed(1)) {
										button.doClick();
									}
								}catch(Exception e) {}
							}
						}
					};
				thread.start();
				}else if(button.getText().equals("End")){
					Thread starterThread = new Thread() {
						@Override public void run() {
							button.setText("Load");
							run = false;
							disabled = true;
							while(disabled) {
								if(setup.isStartPressed(1)) {
									button.doClick();
								}
							}
							
						}
					};
					starterThread.start();
				}
			}
			
		});
		
		
		
		
		
		
		//show the window
		window.setVisible(true);
		button.doClick();
		
	}
}
