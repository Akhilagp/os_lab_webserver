
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class webserver_starter
    extends JFrame {
  JPanel jPanel1 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea2 = new JTextArea();
  static Integer listen_port = null;
  public webserver_starter() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    try {
      listen_port = new Integer(args[0]);
    }
    catch (Exception e) {
      listen_port = new Integer(8888);
    }
    webserver_starter webserver = new webserver_starter();
  }
  private void jbInit() throws Exception {
    jTextArea2.setBackground(new Color(0,0,0));
    jTextArea2.setForeground(new Color(255,255,255));
    jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextArea2.setToolTipText("");
    jTextArea2.setEditable(false);
    jTextArea2.setColumns(100);
    jTextArea2.setRows(100);
    this.setTitle("OS HTTP SERVER");
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosed(e);
      }
    });
    jScrollPane1.getViewport().add(jTextArea2);
    jPanel1.add(jScrollPane1);
    this.getContentPane().add(jPanel1, BorderLayout.EAST);
this.setVisible(true);
    this.setSize(1000,1000);
    this.setResizable(true);
    this.validate();
    new server(listen_port.intValue(), this);
  }
void this_windowClosed(WindowEvent e) {
    System.exit(1);
  }
public void send_message_to_window(String s) {
    jTextArea2.append(s);
  }
} 
