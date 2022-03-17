import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePannel extends JPanel implements ActionListener  {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    Timer timer = new Timer(DELAY,this);
    Random random;
    boolean gameend = false, mainmenu = true, open, running = false;
    JButton Exit = new JButton("Exit");
    JButton Play = new JButton("Play");
    JButton Exit2 = new JButton("Exit");
    JButton Point = new JButton("+1 Point");
    JButton MainmenuButton = new JButton("Main Menu, BROKEN W.I.P");

    GamePannel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.stop();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
         draw(g);
    }
    public void draw(Graphics g){
        if(mainmenu == false) {
            if (running == true) {
                FontMetrics metrics = getFontMetrics(g.getFont());
                if (open == true) {
                    
                }

           /*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }*/

                g.setColor(Color.RED);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.green);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(45, 180, 0));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
                //Draw Points
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
            } else {
                gameOver(g);
            }
        }else{
            g.setFont(new Font("Arial", Font.BOLD, 75));
            g.setColor(Color.GREEN);
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Kitty Snake", (SCREEN_WIDTH - metrics.stringWidth("Kitty Snake")) / 2, g.getFont().getSize());
            Play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mainmenu = false;
                    remove(Exit);
                    remove(Play);
                    timer.start();
                }
            });
            Exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            Exit.setBounds(330, 200, 80, 30);
            Play.setBounds(200, 200, 80, 30);
            remove(Exit2);
            remove(MainmenuButton);
            add(Exit);
            add(Play);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] =y[i-1];
        }

        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
            break;
            case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
            case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
            case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        // Checks if Head collides with Body
        for(int i = bodyParts;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //Checks if head touches left boreder
        if(x[0] < 0){
            running = false;
        }
        //Checks if head touches right boreder
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //Check if head touches top border
        if(y[0] < 0){
            running = false;
        }
        //Check if head touches bottem border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //Game Over Text
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        //Draw Points
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        MainmenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        Exit2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        Exit2.setBounds(200, 200, 80, 30);
        MainmenuButton.setBounds(300, 200, 182, 30);
        add(Exit2);
        add(MainmenuButton);
        gameend = true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{

        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SHIFT:
                    if(gameend == false){
                        if(open == true){
                            open = false;
                            timer.start();
                            System.out.print("Debug Menu Disabled");

                        }
                        else {
                            timer.stop();
                            open = true;
                            System.out.print("Debug Menu Enabled ");
                        }
                    }
                case KeyEvent.VK_K:
                    if(open == true){
                        bodyParts++;
                        applesEaten++;
                    }
                    break;
            }
        }
    }
}
