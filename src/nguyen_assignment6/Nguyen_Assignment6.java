
package nguyen_assignment6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Nguyen_Assignment6 extends JFrame implements MouseListener {

	private static JFrame frame;
	private int setting = 0;
	private static final int SPACE = 35;
	private static final float RADPERSECOND = (float) (Math.PI / 30.0);
	private static final float RADPERNUM = (float) (Math.PI / -6);
	private int size;
	private int centerX;
	private int centerY;
	private static final JLabel settingLabel = new JLabel("Click to Change Clock Face!");

	private final JPanel digitalPanel = new JPanel(new GridLayout(3, 1));

	SimpleDateFormat dateFormat;

	Calendar calendar;
	int hour;
	int minute;
	int second;
	Color colorSecond, colorMHour, colorNumber;

	Timer timer;
	TimeZone timeZone;

	DigitalClock dateLable = new DigitalClock("date");
	DigitalClock timeLable = new DigitalClock("time");
	DigitalClock dayLable = new DigitalClock("day");

	public static void main(String args[]) {
		frame = new Nguyen_Assignment6();
		settingLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
		settingLabel.setForeground(Color.WHITE);
		frame.add(settingLabel, BorderLayout.SOUTH);
		frame.setTitle("Nguyen_ClockGUI");
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public Nguyen_Assignment6() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(700, 100, 400, 450);
		getContentPane().setBackground(new Color(0x800080));

		addMouseListener(this);

		timer = new Timer();
		timeZone = TimeZone.getDefault();
		timer.schedule(new TickTimerTask(), 0, 1000);
	}

	class TickTimerTask extends TimerTask {

		@Override
		public void run() {

			calendar = (Calendar) Calendar.getInstance(timeZone);
			repaint();
		}

	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		// Analog Face Setting
		if (setting == 0) {
			g.setColor(new Color(0x800080));
			g.fillOval(25, SPACE, 350, 350);
			g.setColor(Color.BLACK);
			g.fillOval(35, SPACE + 10, 330, 330);

			size = 400 - SPACE;
			centerX = 400 / 2;
			centerY = 400 / 2 + 10;

			drawClockFace(g);

			drawNumberClock(g);

			// system time
			calendar = Calendar.getInstance();
			hour = calendar.get(Calendar.HOUR);
			minute = calendar.get(Calendar.MINUTE);
			second = calendar.get(Calendar.SECOND);

			drawHands(g, hour, minute, second, colorSecond.RED, colorMHour.WHITE);

			// point clock
			g.setColor(Color.WHITE);
			g.fillOval(centerX - 5, centerY - 5, 10, 10);
			g.setColor(Color.RED);
			g.fillOval(centerX - 3, centerY - 3, 6, 6);

		}

		// Digital Face Setting
		if (setting == 1) {

			digitalPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
			digitalPanel.setBackground(Color.BLACK);
			digitalPanel.add(dateLable);
			digitalPanel.add(timeLable);
			digitalPanel.add(dayLable);

		}

	}

	private void drawClockFace(Graphics g) {

		for (int sec = 0; sec < 60; sec++) {
			int ticStart;
			if (sec % 5 == 0) {
				ticStart = size / 2 - 10;
			} else {
				ticStart = size / 2 - 5;
			}

			drawRadius(g, centerX, centerY, RADPERSECOND * sec, ticStart - 20, size / 2 - 20, colorNumber.WHITE);

		}
	}

	private void drawRadius(Graphics g, int x, int y, double angle, int minRadius, int maxRadius, Color colorNumber) {
		float sine = (float) Math.sin(angle);
		float cosine = (float) Math.cos(angle);
		int dxmin = (int) (minRadius * sine);
		int dymin = (int) (minRadius * cosine);
		int dxmax = (int) (maxRadius * sine);
		int dymax = (int) (maxRadius * cosine);
		g.setColor(colorNumber);
		g.drawLine(x + dxmin, y + dymin, x + dxmax, y + dymax);
	}

	private void drawNumberClock(Graphics g) {

		g.setFont(new Font("Monospaced", Font.BOLD, 16));
		for (int num = 12; num > 0; num--) {
			drawnum(g, RADPERNUM * num, num);
		}

	}

	private void drawnum(Graphics g, float angle, int n) {

		float sine = (float) Math.sin(angle);
		float cosine = (float) Math.cos(angle);
		int dx = (int) ((size / 2 - 20 - 25) * -sine);
		int dy = (int) ((size / 2 - 20 - 25) * -cosine);

		g.drawString("" + n, dx + centerX - 5, dy + centerY + 5);
	}

	private void drawHands(Graphics g, double hour, double minute, double second, Color colorSecond, Color colorMHour) {

		double rsecond = (second * 6) * (Math.PI) / 180;
		double rminute = ((minute + (second / 60)) * 6) * (Math.PI) / 180;
		double rhours = ((hour + (minute / 60)) * 30) * (Math.PI) / 180;

		g.setColor(colorSecond);
		g.drawLine(centerX, centerY, centerX + (int) (150 * Math.cos(rsecond - (Math.PI / 2))),
				centerY + (int) (150 * Math.sin(rsecond - (Math.PI / 2))));
		g.setColor(colorMHour);
		g.drawLine(centerX, centerY, centerX + (int) (120 * Math.cos(rminute - (Math.PI / 2))),
				centerY + (int) (120 * Math.sin(rminute - (Math.PI / 2))));
		g.drawLine(centerX, centerY, centerX + (int) (90 * Math.cos(rhours - (Math.PI / 2))),
				centerY + (int) (90 * Math.sin(rhours - (Math.PI / 2))));

		try {

			Image image = ImageIO.read(new URL("https://cdn2.iconfinder.com/data/icons/spring-30/30/Chicken-32.png"));
			g.drawImage(image, centerX + (int) (150 * Math.cos(rsecond - (Math.PI / 2))) - 15,
					centerY + (int) (150 * Math.sin(rsecond - (Math.PI / 2))) - 15, this);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	// event change from Analog Face to Digital Face by cliking mouse
	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (setting == 0) {

			setting = 1;

			digitalPanel.setVisible(true);
			frame.add(digitalPanel);

		}

		else {

			setting = 0;
			digitalPanel.setVisible(false);
		}

		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
