import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle{

    int whichPaddle;
    int yVelocity;
    int paddleSpeed = 10;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int whichPaddle){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.whichPaddle = whichPaddle;
    }
    public void keyPressed(KeyEvent e){
        switch(whichPaddle){
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W){
                    setYDirection(-paddleSpeed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_S){
                    setYDirection(paddleSpeed);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    setYDirection(-paddleSpeed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN){
                    setYDirection(paddleSpeed);
                    move();
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e){
        switch(whichPaddle){
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W){
                    setYDirection(0);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_S){
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    setYDirection(0);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN){
                    setYDirection(0);
                    move();
                }
                break;
        }
    }
    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }
    public void move(){
        y = y + yVelocity;
    }
    public void draw(Graphics g){
        if(whichPaddle ==1)
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.RED);
        g.fillRect(x,y,width,height);
    }
}
