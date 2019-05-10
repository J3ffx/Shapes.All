package graphics.shapes.interpret;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import graphics.shapes.ui.ShapesController;

public class CNewText extends Command {

	public CNewText() {
		super.setName("text");
	}

	@Override
	public void execute(Processor p) {
		ShapesController c =((ShapesController) p.getController());
		MouseEvent e = new MouseEvent(((Component) c.getView()),MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 100, 100, 1, false);
		String t = JOptionPane.showInputDialog("Please enter text : ");
		c.text(e,t);
	}

}