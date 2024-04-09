package filmInterfaccia;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ModelloFinestra {

	public ModelloFinestra () {
		
	}

	public static void Stile () {
		
        JPanel panel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
				
        Font font = new Font("Trebuchet MS", Font.PLAIN, 12);
        Font fontTitle = new Font("Trebuchet MS", Font.PLAIN, 18);
        UIManager.put("Label.font", fontTitle);
        UIManager.put("TextField.font", font);
        UIManager.put("Button.font", font);
        
        Color backgroundColor = new Color(255, 255, 255);

        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.background", Color.WHITE);
        
        panel.setBackground(backgroundColor);
	}
}