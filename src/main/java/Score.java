import java.awt.*;

public class Score extends Rectangle{

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1Score;
    int player2Score;

    Score(int GAME_WIDTH, int GAME_HEIGHT){
        //because these are static we use class name instead of the instance (this)
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font(null,Font.PLAIN,60));
        g.drawString(String.valueOf(player1Score /10)+String.valueOf(player1Score %10),(GAME_WIDTH/2)-85,50);
        g.drawString(String.valueOf(player2Score /10)+String.valueOf(player2Score %10),(GAME_WIDTH/2)+20,50);

        g.drawLine(GAME_WIDTH/2,0,GAME_WIDTH/2, GAME_HEIGHT);
    }
}
