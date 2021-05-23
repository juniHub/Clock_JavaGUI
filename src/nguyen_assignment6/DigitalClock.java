
package nguyen_assignment6;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import java.util.*;
import java.text.*;

public class DigitalClock extends JLabel implements ActionListener {
 
  String type;
  SimpleDateFormat dateFormat;
 
  public DigitalClock(String type) {
    this.type = type;
    setForeground(Color.WHITE);
 
    switch (type) {
      case "date" : dateFormat = new SimpleDateFormat("MMMM dd yyyy");
                    setFont(new Font("Monospaced", Font.PLAIN, 16));
                    setHorizontalAlignment(SwingConstants.LEFT);
                    break;
      case "time" : dateFormat = new SimpleDateFormat("hh:mm:ss a");
                    setFont(new Font("Monospaced", Font.BOLD, 40));
                    setHorizontalAlignment(SwingConstants.CENTER);
                    break;
      case "day"  : dateFormat = new SimpleDateFormat("EEEE");
                    setFont(new Font("Monospaced", Font.PLAIN, 20));
                    setHorizontalAlignment(SwingConstants.RIGHT);
                    break;
      default     : dateFormat = new SimpleDateFormat();
                    break;
    }
 
    Timer time = new Timer(1000, this);
    time.start();
  }
 
  @Override
  public void actionPerformed(ActionEvent ae) {
    Date date = new Date();
    setText(dateFormat.format(date));
  }
}