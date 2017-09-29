package ball_bounds;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class canvas extends JComponent implements KeyListener,MouseMotionListener
{
	public int gamescore=0;	
    private final int width=1500;
    private final int height=800;
    int color=255,incr=1;
    boolean ishit=false;
    boolean ballcolorchange = true;
    boolean shotshotdori= false;
	JLabel Game_Score_Value;
	JLabel Position_X_Value;
	JLabel Position_Y_Value;
	JLabel RandomTime_Value;
	ArrayList<ball> ballarray;
    ArrayList<shotdori> shotdoriarray;
	int mouse_X=0;
	hama h1;
	shotdori s1;
    My_box b1;
    MouseEvent me;
    nano n1;
    Font font;
    Image textimg = Toolkit.getDefaultToolkit().getImage("text.png");
    boolean shotable =true;
    int time;
    
    javax.swing.Timer timer = new javax.swing.Timer(1,new ActionListener() {
    	   @Override
    	   public void actionPerformed(ActionEvent e) {
    	    time++;
    	    if(time%200==0){
    	           shotable=true;
    	          }
    	   } 		
 	});
    canvas(base_frame parent)
    {
    	timer.start(); 
        ballarray=new ArrayList<ball>(1);
        shotdoriarray=new ArrayList<shotdori>(20);
        b1=new My_box(0,0,200,20);
        h1=new hama(50);
        n1=new nano();
        font=new Font("font",font.BOLD,50);
        addKeyListener(this);
        addMouseMotionListener(this);
        Game_Score_Value = parent.GameScoreValue();
        Position_X_Value = parent.PositionXValue();
        Position_Y_Value = parent.PositionYValue();
        RandomTime_Value = parent.RandomValue();
        setPreferredSize(new Dimension(width,height));
        setFocusable(true);
    }

    public void addBall(int size)
    {
        if(ballarray.isEmpty())
        {
        	ballarray.add(new ball(size));
        	gamescore=0;
        	Game_Score_Value.setText(String.valueOf( gamescore));
        }
    	System.out.println("START GAME");
    }
    public void removeBall()
    {
        if(!ballarray.isEmpty())
        {
            ballarray.clear();
            
        }
        if(!shotdoriarray.isEmpty())
        {
        	shotdoriarray.clear();
            
        }
        JOptionPane.showMessageDialog(null, "점수:"+gamescore+"   시간:"+base_frame.counter);
       
            System.out.println("GAME END");
    }
    public int getBallCount()
    {
            return ballarray.size();
    }
    
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Image bgimg = Toolkit.getDefaultToolkit().getImage("bg.png");
        g.drawImage(bgimg, 0,0, null);
        //g.fillRect(0,0,getWidth(),getHeight());
        h1.drawhama(g, false);
        g.setColor(Color.BLACK);
        b1.drawBox(g);
        n1.drawnano(g);
        if(ishit==true)
        {
        	String score=String.valueOf(gamescore);
        	g.setFont(font);
        	g.drawImage(textimg, 110,370, null);
        	g.drawString(score, 190, 450);
        	//add(Game_Score_Value);
        }
        try{
            Thread.sleep(5);
            for(ball temp_ball:ballarray)
            {
                temp_ball.drawBall(g,false);
                temp_ball.collision(b1);
                 if(temp_ball.y_pos>780)
                {
                	removeBall();
                	break;
                	//gameover!
                }
            }
             for(shotdori temp_shotdori:shotdoriarray)
          	{
        	   		temp_shotdori.drawshotdori(g, false);
        	   		h1.collision(temp_shotdori);
          	}
                
            
            repaint();//calls the paint method
 
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
 
    //keyboard call-back methods
    public void keyPressed(KeyEvent ke)
    {
    	
    	 if(shotable){
    	        if(ke.getKeyCode()==KeyEvent.VK_A)//increase x
    	        {
    	        shotdoriarray.add(new shotdori(30,mouse_X));
    	        System.out.println("SHOT Shotdori");
    	        shotable=false;
       // shotshotdori=true;
    	        }
        }
    }
    public void keyReleased(KeyEvent ke)
    {
    	/* if(ke.getKeyCode()==KeyEvent.VK_A)//increase x
         {
             shotshotdori=false;
         }*/
    }
    public void keyTyped(KeyEvent ke)
    {
    }
 
    //mouse call-back methods
 
    public void mouseMoved(MouseEvent me)
    {
        //move the rectangle box too
       // b1.setLocation(me.getX(),me.getY());
    	 b1.setLocation(me.getX()-100,600);
    	 mouse_X=me.getX();
    	 Position_X_Value.setText(String.valueOf(mouse_X));
    	 
    	 //mouse event!
    }
    public void mouseDragged(MouseEvent me)
    {
 
    }
 
    //Ball inner-class
    class ball
    {
        private int x_pos=0;
        private int y_pos=0;
        private int dir_x=1;
        private int dir_y=1;
        private int size=0;
 
        ball(int size)
        {
            this.size=size;
        }
 
        private void calculate_direction()
        {
            //move the ball
            x_pos=x_pos-dir_x;
            y_pos=y_pos-dir_y;
 
            //for X-direction
            if(x_pos<0)
                        {              
                            x_pos=0;    
                    dir_x=-1;//incr   
                        }
                        else  
                       if(x_pos+size>getWidth())
            {
                 dir_x=1;//decr
            }
 
            //for Y-direction
            if(y_pos<0)  
                        {  
                                 y_pos=0;     
                             dir_y=-1;//incr                         
 
                        }                         
                         else                                           
                         if(y_pos+size>getHeight())
            {
                dir_y=1;//decr
            }
        }
 
        public void changeDirection_Y()
        {
                    if(dir_y==-1)
            {
                dir_y=1;
            }
            else
            {
                dir_y=-1;
            }
        }
 
        public void changeDirection_X()
        {
            if(dir_x==-1)
            {
                dir_x=1;
            }
            else
            {
                dir_x=-1;
            }
        }
 
        public void drawBall(Graphics g,boolean bound)
        {
            calculate_direction();
            if(ballcolorchange)
            	g.setColor(Color.YELLOW);
            else
            	g.setColor(Color.RED);
            g.fillOval(x_pos,y_pos,size,size);
 
            if(bound)
            {
            	
                g.drawRect(x_pos,y_pos,size,size);
            }
        }
 
        public Rectangle getRectBounds()
        {
        	
            return new Rectangle(x_pos,y_pos,size,size);
        }
 
                //this method is called for all balls and checked whether it touches the rect.if so,the ball's direction gets changed accordingly.
        public void collision(My_box rect)
        {
                           //if ball collides with top/bottom part of the rect
            if(this.getRectBounds().intersectsLine(rect.x,rect.y,rect.x+rect.width,rect.y) ||
                           this.getRectBounds().intersectsLine(rect.x,rect.y+rect.height, rect.x+rect.width,rect.y+rect.height))
            {
            	
            	if(ballcolorchange)
            		ballcolorchange=false;
            	else
            		ballcolorchange=true;
            	System.out.println("hit");
                changeDirection_Y();//reverses the direction along Y
 
            }
 
              //if ball collides with left/right part of the rect
            if(this.getRectBounds().intersectsLine(rect.x,rect.y    ,    rect.x,rect.y+rect.height) ||
                           this.getRectBounds().intersectsLine(rect.x+rect.width,rect.y  ,   rect.x+rect.width,rect.y+rect.height))
            {
                changeDirection_X();//reverses the direction along X
 
            }
 
        }
    }
    class hama
    {
        private int x_pos=0;
        private int y_pos=0;
        private int dir_x=1;
        private int size=0;
        private int t=0;
        int randomtime=0;
        int limit=1;
        boolean israndom=false;
        Random random=new Random();
        Image hamaimg =Toolkit.getDefaultToolkit().getImage("hama.png");
 
        hama(int size)
        {
            this.size=size;
        }
 
        private void calculate_direction()
        {
            //move the ball
            x_pos=x_pos+dir_x;
            /*
            if(x_pos<0)
            {     
            	dir_x=0;
            	israndom=random_move();
            		if(israndom=true)
            		{
            			dir_x=-1;
            		}
            }
            else if(x_pos+size>getWidth())
            {
                dir_x=0;
                israndom=random_move();
           		if(israndom=true)
           		{
           			dir_x=1;
           		}
            }*/
            if(x_pos<0)
            { 
             if(dir_x==-1){
             randomtime=random.nextInt(4)+1;
             RandomTime_Value.setText(String.valueOf( randomtime));}
             System.out.println(randomtime);
             dir_x=0;
             if(time%1000==0&&random_move())
             {dir_x=1;}
            }
            else if (x_pos+size>getWidth())
            {
             if(dir_x==1){
            	 randomtime=random.nextInt(4)+1;
             System.out.println(randomtime);
             RandomTime_Value.setText(String.valueOf( randomtime));}
             dir_x=0;
             if(time%1000==0&&random_move())
             {dir_x=-1;}
            }
 
        }
        private boolean random_move()
        {
        	
        	
        	
        	if(time>randomtime)
        	{
        		return true;
        	}
        	return false;
        	
        	/*
        	Random random=new Random();
        	return true;*/
        }
        public void drawhama(Graphics g,boolean bound)
        {
            calculate_direction();
            
            Image hamaimg = Toolkit.getDefaultToolkit().getImage("hama.png");
            g.drawImage(hamaimg, x_pos,y_pos, null);
            
 
            if(bound)
            {
            	
                g.drawRect(x_pos,y_pos,size,size);
            }
        }
 
        public Rectangle getRectBounds()
        {
        	
            return new Rectangle(x_pos,y_pos,size,size);
        }
 
                //this method is called for all balls and checked whether it touches the rect.if so,the ball's direction gets changed accordingly.
        public void collision(shotdori s1)
        {
                           //if ball collides with top/bottom part of the rect
            if(this.getRectBounds().intersectsLine(s1.x_pos,s1.y_pos,s1.x_pos+s1.size,s1.y_pos) ||
                           this.getRectBounds().intersectsLine(s1.x_pos,s1.y_pos+s1.size,s1.x_pos+s1.size,s1.y_pos+s1.size)||
                           this.getRectBounds().intersectsLine(s1.x_pos,s1.y_pos,s1.x_pos,s1.y_pos+s1.size)||
                           this.getRectBounds().intersectsLine(s1.x_pos+s1.size,s1.y_pos,s1.x_pos+s1.size,s1.y_pos+s1.size)
                           )
            {
            	hit();
                s1.y_pos=-200;
                s1.dir_y=0;
            	System.out.println("Get Scoore!");
            	gamescore=gamescore+1;
            	Game_Score_Value.setText(String.valueOf(gamescore));
               
            }
            
        }
        public void hit()
        {
        	ishit=true;
        }
    }
        class shotdori
        {
            private int x_pos;
            private int y_pos=600;
            private int dir_x=1;
            private int dir_y=1;
            private int size=0;
     
            shotdori(int size,int mouse_X)
            {
                this.size=size;
                x_pos=mouse_X;
            }
     
            private void calculate_direction()
            {

            	y_pos=y_pos-dir_y;
                if(y_pos<-500)
                {
                 dir_y=0;
                }
            }
     
         
     
          
            public void drawshotdori(Graphics g,boolean bound)
            {
                calculate_direction();
                
                Image shotdoriimg = Toolkit.getDefaultToolkit().getImage("shot.png");
                g.drawImage(shotdoriimg, x_pos,y_pos, null);
     
                if(bound)
                {
                    g.drawRect(x_pos,y_pos,size,size);
                }
            }
     

                    //this method is called for all balls and checked whether it touches the rect.if so,the ball's direction gets changed accordingly.
        }
    }
class nano
{
	private int x_pos=10;
    private int y_pos=500;
    private int size=1;
    
    public void drawnano(Graphics g)
    {
    	Image nanoimg = Toolkit.getDefaultToolkit().getImage("nano.png");
        g.drawImage(nanoimg, x_pos,y_pos, null);
    }
}



    
