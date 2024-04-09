package distributore;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class ModelloFinestra {

	public ModelloFinestra () {
		
	}

	public static void Stile () {
		
		
        Font font = new Font("Trebuchet MS", Font.PLAIN, 14);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("Button.font", font);
        
        Color backgroundColor = new Color(255, 255, 255);
        Color textColor = new Color(13, 13, 13);
        Color buttonColor = new Color(224, 235, 235);

        UIManager.put("Label.background", backgroundColor);
        UIManager.put("Label.foreground", textColor);
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", textColor);
        UIManager.put("Button.background", buttonColor);
        UIManager.put("Button.foreground", Color.BLACK);
        
        JPanel panel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(backgroundColor);
	}
}
