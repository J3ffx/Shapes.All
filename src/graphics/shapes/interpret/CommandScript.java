package graphics.shapes.interpret;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.swing.JOptionPane;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.ui.ShapesController;

public class CommandScript extends Command {
	private String filename;
	private String filepath;

	public CommandScript() throws FileNotFoundException {
		super.setName("script");
		this.filename = "MyScript";
		this.filepath = "script/";
	}

	public void execute(Processor p) throws NumberFormatException, IOException {
		ShapesController sc = ((ShapesController) p.getController());
		Boolean stroked = true;
		Boolean filled = true;
		Color filledcolor = Color.RED;
		Color strokedcolor = Color.BLUE;
		Color textcolor = Color.BLACK;
		int size = 12;
		Point point = new Point(5, 5);
		String strcurrentline;
		this.filename = JOptionPane.showInputDialog("Please enter file name lol : ");
		FileReader file = new FileReader(filepath + filename  + ".txt");
		BufferedReader br = new BufferedReader(file);
		while ((strcurrentline = br.readLine()) != null) {
			String[] words = strcurrentline.split(" ");
			for (String i : words) {
				if (i.equals("circle")) {
					{
						int index = strcurrentline.indexOf("circle");
						int cx = Integer.parseInt(words[index + 1]);
						int cy = Integer.parseInt(words[index + 2]);
						int radius = Integer.parseInt(words[index + 3]);
						filled = Boolean.parseBoolean(words[index + 4]);
						stroked = Boolean.parseBoolean(words[index + 5]);
						Field field;
						try {
							field = Class.forName("java.awt.Color").getField(words[index + 6]);
							filledcolor = (Color) field.get(null);
							field = Class.forName("java.awt.Color").getField(words[index + 7]);
							strokedcolor = (Color) field.get(null);
						} catch (NoSuchFieldException | SecurityException | ClassNotFoundException
								| IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
						point.setLocation(cx, cy);
						SCircle c = new SCircle(point, radius);
						c.addAttributes(new ColorAttributes(filled, stroked, filledcolor, strokedcolor));
						c.addAttributes(new SelectionAttributes());
						((SCollection) (sc.getModel())).add(c);
						sc.getView().repaint();

					}
				}
				if (i.equals("rectangle")) {
					int index = strcurrentline.indexOf("rectangle");
					int rx = Integer.parseInt(words[index + 1]);
					int ry = Integer.parseInt(words[index + 2]);
					int width = Integer.parseInt(words[index + 3]);
					int height = Integer.parseInt(words[index + 4]);
					point.setLocation(rx, ry);
					filled = Boolean.parseBoolean(words[index + 5]);
					stroked = Boolean.parseBoolean(words[index + 6]);
					Field field;
					try {
						field = Class.forName("java.awt.Color").getField(words[index + 7]);
						filledcolor = (Color) field.get(null);
						field = Class.forName("java.awt.Color").getField(words[index + 8]);
						strokedcolor = (Color) field.get(null);
					} catch (NoSuchFieldException | SecurityException | ClassNotFoundException
							| IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					SRectangle r = new SRectangle(point, height, width);
					r.addAttributes(new ColorAttributes(filled, stroked, filledcolor, strokedcolor));
					r.addAttributes(new SelectionAttributes());
					((SCollection) (sc.getModel())).add(r);
					sc.getView().repaint();

				}
				if (i.equals("text")) {
					int index = strcurrentline.indexOf("text");
					int tx = Integer.parseInt(words[index + 1]);
					int ty = Integer.parseInt(words[index + 2]);
					String text = words[index + 3];
					point.setLocation(tx, ty);
					filled = Boolean.parseBoolean(words[index + 4]);
					stroked = Boolean.parseBoolean(words[index + 5]);
					Field field;
					try {
						field = Class.forName("java.awt.Color").getField(words[index + 6]);
						filledcolor = (Color) field.get(null);
						field = Class.forName("java.awt.Color").getField(words[index + 7]);
						strokedcolor = (Color) field.get(null);
						field = Class.forName("java.awt.Color").getField(words[index + 8]);
						textcolor = (Color) field.get(null);
					} catch (NoSuchFieldException | SecurityException | ClassNotFoundException
							| IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					size = Integer.parseInt(words[index + 9]);
					SText t = new SText(point, text);
					t.addAttributes(new ColorAttributes(filled, stroked, filledcolor, strokedcolor));
					t.addAttributes(new FontAttributes(size, textcolor));
					t.addAttributes(new SelectionAttributes());
					((SCollection) (sc.getModel())).add(t);
					sc.getView().repaint();

				}
			}
		}
		br.close();
	}
}
