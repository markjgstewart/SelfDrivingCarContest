/* 
Mark Stewart. Part of Engineering Contest.
GUI to display cars on track.
*/


import javax.swing.*;
import java.awt.*;

class RaceFrame extends JFrame {
	public final static int ONE_LANE=1;
	public final static int TWO_LANES=2;
	JPanel[][] panel;
	int L,H; //length of track in panels, one panel is one meter. H is number of panels tall
	int carX=0;		//needs to track previous car position
	int carX0=0,carX1=0; 	//for 2 car version
	int lane0=1,lane1=2; 	//for 2 car version
	int[] y;
	
	public RaceFrame(int length,int numberOfLanes) {
		L = length; H=11;
		y = new int[4]; y[0]=1; y[1]=3; y[2]=7; y[3]=9; //determines relative vertical sizing
		panel = new JPanel[H][L];
		for (int r=0;r<H;r++) {
			for (int c=0;c<L;c++) {
				panel[r][c]=new JPanel();
				if ((r==y[0]) || (r==y[3])) panel[r][c].setBackground(new Color(100,100,100));	//color the two side walls
				if ( (numberOfLanes==TWO_LANES) && (r==(y[1]+y[2])/2) ) panel[r][c].setBackground(new Color(200,200,200)); //sets middle strip
			}
		}
		
		if (numberOfLanes==ONE_LANE) setTitle("One lane. GO GO GO GO");
		if (numberOfLanes==TWO_LANES) setTitle("Two lane track with obstacles. Go for it!");
		setSize(600,200);		//should be proportional to screen size
		setLocation(100,100);		//ditto
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(H,L));
		
		for (int r=0;r<H;r++) {
			for (int c=0;c<L;c++) {
				contentPane.add(panel[r][c]);
			}
		}
		setVisible(false);
		setVisible(true);	//seems to trick it into updating. The must be a better way.
	}
	public void setObstacles(int obstacle0, int obstacle1) {
		try {Thread.sleep(1000);}	//dramatic pause
		catch(Exception e) {System.out.println("Trouble sleeping...");};
	
		panel[y[1]][obstacle0].setBackground(new Color(100,100,100));
		panel[y[2]][obstacle1].setBackground(new Color(100,100,100));
		panel[y[1]+1][obstacle0].setBackground(new Color(100,100,100));
		panel[y[2]+1][obstacle1].setBackground(new Color(100,100,100));
		panel[y[1]-1][obstacle0].setBackground(new Color(100,100,100));
		panel[y[2]-1][obstacle1].setBackground(new Color(100,100,100));
		
		setVisible(false);
		setVisible(true);
		
		try {Thread.sleep(1000);}	//another dramatic pause
		catch(Exception e) {System.out.println("Trouble sleeping...");};
	}
	
	public void updateCars(int x0new, int lane0new, int x1new, int lane1new, double t) {
		if (carX0<L) panel[y[lane0]][carX0].setBackground(Color.WHITE);
		carX0=x0new; lane0=lane0new;
		if (carX0<L) panel[y[lane0new]][carX0].setBackground(Color.RED);
		
		if (carX1<L) panel[y[lane1]][carX1].setBackground(Color.WHITE);
		carX1=x1new; lane1=lane1new;
		if (carX1<L) panel[y[lane1new]][carX1].setBackground(Color.GREEN);
		//if (x+1<L) panel[H/2][x+1].setBackground(Color.YELLOW);
		//if (x+2<L) panel[H/2][x+2].setBackground(Color.YELLOW);
		//if (x+3<L) panel[H/2][x+3].setBackground(Color.YELLOW); //headlights - should correlate to the 3x10 view
		setTitle("GO GO GO GO: t="+String.format("%.2f",t));
		try {Thread.sleep(2);}
		catch(Exception e) {System.out.println("Trouble sleeping...");};
	}
	
	public void updateCar(int xnew, String message) {
		if (carX<L) panel[H/2][carX].setBackground(Color.WHITE);	//erase previous car position
		carX=xnew;
		if (carX<L) panel[H/2][carX].setBackground(Color.RED);	//car is red
		if (carX+1<L) panel[H/2][carX+1].setBackground(Color.YELLOW);
		if (carX+2<L) panel[H/2][carX+2].setBackground(Color.YELLOW);
		if (carX+3<L) panel[H/2][carX+3].setBackground(Color.YELLOW); //headlights
		
		setTitle("GO GO GO GO:"+message);			//message contains information about velocity and acceleration
		try {Thread.sleep(2);}
		catch(Exception e) {System.out.println("Trouble sleeping...");};
		//setVisible(false);
		//setVisible(true);
	}
}
