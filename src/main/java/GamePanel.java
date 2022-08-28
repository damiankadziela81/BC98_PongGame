import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddleLeft;
    Paddle paddleRight;
    Ball ball;
    Score score;


    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPaddles(){
        paddleLeft = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddleRight = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g){
        paddleLeft.draw(g);
        paddleRight.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    public void move(){
        paddleLeft.move();
        paddleRight.move();
        ball.move();
    }
    public void checkCollision() {
        paddleCollision();
        ballCollisionWithBorders();
        ballCollisionWithPaddles();
        ballOutOfBounds();
    }
    public void paddleCollision(){
        //stops paddles on window borders
        if (paddleLeft.y <= 0)
            paddleLeft.y = 0;
        if (paddleLeft.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddleLeft.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddleRight.y <= 0)
            paddleRight.y = 0;
        if (paddleRight.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddleRight.y = GAME_HEIGHT - PADDLE_HEIGHT;
    }
    public void ballCollisionWithBorders() {
        //ball collision with window upper and lower borders
        if (ball.y <= 0 || ball.y >= GAME_HEIGHT - BALL_DIAMETER)
            ball.setYDirection(-ball.yVelocity);
    }
    public void ballCollisionWithPaddles(){
        //ball collision with paddles
        if (ball.intersects(paddleLeft)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            paddleLeft.paddleSpeed++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddleRight)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            paddleRight.paddleSpeed++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
    }
    public void ballOutOfBounds(){
        //scoring
        if(ball.x <= 0) {
            score.player2Score++;
            newPaddles();
            newBall();
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER){
            score.player1Score++;
            newPaddles();
            newBall();
        }
    }
    public void run(){
        //game loop
        long lastTime = System.nanoTime();
        double amountOffTicks = 60.0;
        double ns = 1000000000 / amountOffTicks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddleLeft.keyPressed(e);
            paddleRight.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddleLeft.keyReleased(e);
            paddleRight.keyReleased(e);
        }
    }
}
