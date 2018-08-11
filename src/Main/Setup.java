package Main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class Setup {
	Controller controller;
	Controller cont1;
	Controller cont2;
	Controller cont3;
	Controller cont4;
	int contCounter = 1;
	
	final int LEFT_X_AXIS = 0;
	final int LEFT_Y_AXIS = 1;
	final int RIGHT_X_AXIS = 2;
	final int RIGHT_Y_AXIS = 3;
	
	final int SELECT = 0;
	final int LEFT3 = 1;
	final int RIGHT3 = 2;
	final int START = 3;
	final int NORTH = 4;
	final int EAST = 5;
	final int SOUTH = 6;
	final int WEST = 7;
	final int LEFT_TRIGGER = 8;
	final int RIGHT_TRIGGER = 9;
	final int LEFT_BUMPER = 10;
	final int RIGHT_BUMPER = 11;
	final int TRIANGLE = 12;
	final int CIRCLE = 13;
	final int CROSS = 14;
	final int SQUARE = 15;
	final int PS_BUTTON = 16;
	
	public Setup() {
		
	}
	
	public void process() {
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Controllers.poll();
		for(int i = 0;i < Controllers.getControllerCount();i++) {
			controller = Controllers.getController(i);
			System.out.println(controller.getName());
			if(controller.getName().equals("PS3 GamePad")||controller.getName().equals("PLAYSTATION(R)3 Controller")) {
				/*for(int n = 0; n < controller.getButtonCount();n++) {
					System.out.println(n + ": " + controller.getButtonName(n));
				}
				for(int m = 0; m < controller.getAxisCount();m++) {
					System.out.println(m + ": " + controller.getAxisName(m));
				}*/
				for(int l = 0; l < controller.getRumblerCount();l++) {
					System.out.println(l + ": " + controller.getRumblerName(l));
				}
				if(contCounter == 1) {
					cont1 = controller;
					System.out.println("cont 1 configured");
					contCounter = 2;
				}else if(contCounter == 2) {
					cont2 = controller;
					System.out.println("cont 2 configured");
					contCounter = 3;
				}else if(contCounter == 3) {
					cont3 = controller;
					System.out.println("cont 3 configured");
					contCounter = 4;
				}else if(contCounter == 4){
					cont4 = controller;
					System.out.println("cont 4 configured");
					contCounter = 0;
				}
			}
		}
		/*
		double originalTime = System.currentTimeMillis();
		while(System.currentTimeMillis()-originalTime<=10000) {
			cont1.poll();
			System.out.println(cont1.isButtonPressed());
		}*/
	}
	public double getLeftXAxis() {
		cont1.poll();
		return cont1.getAxisValue(this.LEFT_X_AXIS);
	}
	public double getRightXAxis() {
		cont1.poll();
		return cont1.getAxisValue(this.RIGHT_X_AXIS);
	}
	public void setDeadBand() {
		cont1.setXAxisDeadZone((float) 0.1);
		cont1.setYAxisDeadZone((float) 0.1);
		cont1.setZAxisDeadZone((float) 0.1);
		cont1.setRZAxisDeadZone((float) 0.1);
		
	}
	public boolean isSelectPressed(int controller) {
		boolean pressed;
		if(controller == 2) {
			pressed = false;
		}else {
			cont1.poll();
			pressed = cont1.isButtonPressed(SELECT);
		}
		//System.out.println(pressed);
		return pressed;
	}
	public boolean isStartPressed(int controller) {
		boolean pressed;
		if(controller == 2) {
			pressed = false;
		}else {
			cont1.poll();
			pressed = cont1.isButtonPressed(START);
		}
		System.out.println(pressed);
		return pressed;
	}
}
