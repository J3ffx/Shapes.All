package graphics.shapes.ui;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.swing.JOptionPane;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;

public class toHTML {

	private String filename;
	private PrintWriter o;
	private String filepath;

	public toHTML() {
		this.filename = "MyHtmlSave";
		this.filepath = "html/";
	}

	public void convert(SCollection model) {
		try {
			this.filename = JOptionPane.showInputDialog("Please enter file name : ");
			this.o = new PrintWriter(new BufferedOutputStream(new FileOutputStream(filepath + filename + ".html")),
					true);
			o.println("  <!DOCTYPE html>\r\n" + " <html>\r\n" + " <head>\r\n" + " <title>404's Editor</title>\r\n"
					+ " </head>\r\n" + " <body>\r\n" + " <div>\r\n" + " <svg viewBox=\"0 0 1200 400\">");
			for (Iterator<Shape> i = model.iterator(); i.hasNext();) {
				Shape shape = (Shape) i.next();
				echo(shape);

			}
			o.println(" </svg>\r\n" + "  </div>\r\n" + " </body>\r\n" + " </html>");
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void echo(Shape shape) {
		if (shape instanceof SRectangle) {
			SRectangle rec = (SRectangle) shape;
			ColorAttributes colorAtt = (ColorAttributes) rec.getAttributes(ColorAttributes.id);
			String f = "none";
			String s = "none";
			if (colorAtt.isFilled()) {
				f = toHexString(colorAtt.getFilledColor());
			}
			if (colorAtt.isStroked()) {
				s = toHexString(colorAtt.getStrokedColor());
			}
			o.println("	<rect x=\"" + rec.getLoc().x + "\" y=\"" + rec.getLoc().y + "\"" + " height=\""
					+ rec.getRect().height + "\"" + " width=\"" + rec.getRect().width + "\"" + " fill=\"" + f + "\""
					+ " stroke=\"" + s + "\">");
			o.println("</rect>");
		}
		if (shape instanceof SCircle) {
			SCircle cir = (SCircle) shape;
			ColorAttributes colorAtt = (ColorAttributes) cir.getAttributes(ColorAttributes.id);
			String f = "none";
			String s = "none";
			if (colorAtt.isFilled())
				f = toHexString(colorAtt.getFilledColor());
			if (colorAtt.isStroked())
				s = toHexString(colorAtt.getStrokedColor());
			int x = cir.getLoc().x + cir.getRadius();
			int y = cir.getLoc().y + cir.getRadius();
			o.println("	<circle cx=\"" + x + "\" cy=\"" + y + "\"" + " r=\"" + cir.getRadius() + "\"" + " fill=\"" + f
					+ "\"" + " stroke=\"" + s + "\"" + "\">");
			o.println("</circle>");
		}
		if (shape instanceof SText) {
			SText tex = (SText) shape;
			ColorAttributes colorAtt = (ColorAttributes) tex.getAttributes(ColorAttributes.id);
			FontAttributes fontAtt = (FontAttributes) tex.getAttributes(FontAttributes.id);
			String f = "none";
			String s = "none";
			if (colorAtt.isFilled())
				f = toHexString(colorAtt.getFilledColor());
			if (colorAtt.isStroked())
				s = toHexString(colorAtt.getStrokedColor());
			int w = tex.getBounds().width+10;
			o.println("	<rect x=\"" + tex.getLoc().x + "\" y=\"" + tex.getLoc().y + "\"" + " height=\""
					+ tex.getBounds().height + "\"" + " width=\"" + w + "\"" + " fill=\"" + f + "\""
					+ " stroke=\"" + s + "\">");
			o.println("</rect>");
			int t = fontAtt.getFontColor().getRGB();
			int x = tex.getLoc().x+ 2;
			int y = tex.getLoc().y + tex.getHeight() - 2;
			int size = fontAtt.getFontSize();
			o.println("<text x=\"" + x + "\" y=\"" + y + "\" font-family=\"" + fontAtt.getFontType() + "\" font-size=\""
					+ size + "\"" + " fill=\"" + t + "\"" + "\">" + tex.getText() + "</text>");
			o.println("</text>");
		}
		if (shape instanceof SCollection) {
			SCollection col = (SCollection) shape;

			for (Iterator<Shape> it = col.iterator(); it.hasNext();) {
				Shape realShape = it.next();
				echo(realShape);
			}

		}
	}

	public final static String toHexString(Color color) throws NullPointerException {
		String hexColor = "#" + Integer.toHexString(color.getRGB()).substring(2);
		return hexColor;
	}

}
