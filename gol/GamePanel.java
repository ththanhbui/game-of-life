package gol;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private World mWorld = null;

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		// Paint the background white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (mWorld == null) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		} else {
			int size = Math.min((this.getHeight() - 52) / mWorld.getHeight(),
					(this.getWidth() - 7) / mWorld.getWidth());
			for (int r = 0; r < mWorld.getHeight(); r++) {
				for (int c = 0; c < mWorld.getWidth(); c++) {
					if (mWorld.getCell(c, r)) {
						g.setColor(Color.BLACK);
						g.fillRect(7 + c * size, 15 + r * size, size, size);
						g.setColor(Color.LIGHT_GRAY);
						g.drawRect(7 + c * size, 15 + r * size, size, size);

					} else {
						g.setColor(Color.LIGHT_GRAY);
						g.drawRect(7 + c * size, 15 + r * size, size, size);
					}
				}
			}

			// Generation Count
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 18));
			g.drawString("Generation: " + mWorld.getGenerationCount(), 20, this.getHeight() - 15);
		}
	}

	public void display(World w) {
		mWorld = w;
		repaint();
	}
}
