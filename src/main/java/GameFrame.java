import javax.swing.*;

public class GameFrame  extends JFrame {

    public GamePannel game;

    public static GameFrame getInstance;

    GameFrame(){

        game = new GamePannel();
        this.add(game);
        this.setTitle("Kitty Snake V1.0");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void reset()
    {
        this.remove(game);
        this.add(game);
    }


}
