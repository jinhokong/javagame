package ball_bounds;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class base_frame extends JFrame implements ActionListener
{
	
    canvas draw_panel;									///�����̴� Ʋ �ȿ� ���� �۵� 
    JButton Game_Start,Game_End;
    JLabel Game_Score;
    JLabel Game_Score_Value;
    JLabel Timer;
    JLabel Timer_Value;
    JLabel RandomTime;
    JLabel RandomTime_Value;
    JLabel Position_X;
    JLabel Position_X_Value;
    JLabel Position_Y;
    JLabel Position_Y_Value;
    JPanel ui_panel;									///��ü���� ūƲ
    static int counter=1;
    javax.swing.Timer timer = new javax.swing.Timer(1000,new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Timer_Value.setText(String.valueOf(counter));
			counter++;
		}
	});
    base_frame()
    {
    	
        super("Ball Bounds");
 
        //our UI panel which contains gui comps
        ui_panel=new JPanel();
        
        
        // ���� �κ� �� ����
        Game_Score=new JLabel("Game Score:");
        Game_Score_Value = new JLabel(" 0");
        Timer=new JLabel(" 타이머 :");
        Timer_Value=new JLabel("0");
        Position_X = new JLabel(" X 좌표 :");
        Position_X_Value = new JLabel(" 0");
        Position_Y = new JLabel(" Y 좌표 :");
        Position_Y_Value = new JLabel(" 0");
        RandomTime=new JLabel("랜덤시간: ");
        RandomTime_Value=new JLabel("0");
        
        
        
        // ��ư�κ�
        Game_Start=new JButton("START");
        Game_End=new JButton("GAME END");
        
 
        //add listeners
        
        Game_Start.addActionListener(this);
        Game_End.addActionListener(this);
        Game_Start.setFocusable(false);
        Game_End.setFocusable(false);
        setLayout(new FlowLayout());
        draw_panel=new canvas(this);
 
        //adding to UI panel
        ui_panel.add(Game_Score);
        ui_panel.add(Game_Score_Value);
        ui_panel.add(Timer);
        ui_panel.add(Timer_Value);
        ui_panel.add(Position_X);
        ui_panel.add(Position_X_Value);
        ui_panel.add(Position_Y);
        ui_panel.add(Position_Y_Value);
        ui_panel.add(RandomTime);
        ui_panel.add(RandomTime_Value);
        ui_panel.add(Game_Start);
        ui_panel.add(Game_End);
 
        //adding to our JFrame
        
        
        add(draw_panel);
        add(ui_panel);
 
        setSize(1500,1000);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JLabel GameScoreValue() {
    	return Game_Score_Value;
    }    
    public JLabel PositionXValue(){
    	return Position_X_Value;
    }
    public JLabel PositionYValue(){
    	return Position_Y_Value;
    }
    public JLabel RandomValue(){
    	return RandomTime_Value;
    }
    public static void main(String[] args)
    {
        new base_frame();
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==Game_Start)
        {
            draw_panel.addBall(20);
            System.out.println("START GAME"); 
            timer.start();        	
        }
        else
        if(ae.getSource()==Game_End)
        {		
                draw_panel.removeBall();
                System.out.println("Removed a ball");
                timer.stop();
                
                counter=1;
                Game_Score_Value.setText(" 0");
                Timer_Value.setText("0");
        }
    }
}