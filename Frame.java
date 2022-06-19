import javax.swing.JFrame;

//sets up the frame and adds the necessary panels
public class Frame extends JFrame {

  public Frame(String name){
    this.setTitle(name);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(600, 600);
    this.add(new Panel());
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

}