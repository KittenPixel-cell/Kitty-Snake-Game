import javax.swing.*;

public class GameFrame  extends JFrame {

    GameFrame(){
        this.add(new GamePannel());
        this.setTitle("Kitty Snake V1.0");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
