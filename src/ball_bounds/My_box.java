package ball_bounds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
class My_box extends Rectangle
{
    //default vars
    /*
     * x,y,width,height
     */
    My_box(int x1,int y,int width,int height)
    {
        super(x1,y,width,height);
    }
 
    public void drawBox(Graphics g)
    {

    		g.setColor(Color.blue);


        g.fillRect(x,y,width,height);
    }
}