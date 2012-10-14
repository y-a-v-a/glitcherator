package glitcherator;

import javax.swing.*;

public class App {

	private String imagename;

	public static void main(String[] args) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Glitcherator");
		
		App app = new App();
		JFrame frame = new JFrame("Tutorials");
		
		app.imagename = args[0];
		
		Glitcherator g = new Glitcherator(app.imagename);
		g.build().show(frame).export("");
	}
}
