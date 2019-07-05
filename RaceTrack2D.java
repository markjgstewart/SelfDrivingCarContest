/*
Short program written for a high school / middle school engineering contest. Contestants program the function inside Driver A to try to complete the course. See README for rules.
*/

import java.io.IOException;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.io.*;


public class RaceTrack2D {
	static int L;				//length of track
	static int TIME_MAX=200; 		//Race aborted after this time
	static double t=0, dt = 0.002; 	
	static boolean finished;
	public static RaceFrame gui;
	static int obstacle0=0,obstacle1=0;	//horiztonal location of obstacles
	static int[][] track;
	final static int WALL=2, FREE=0, OTHER_CAR=1;
	static int MAX_ACCEL = 2; 		//m per s^2

	public static void makeTrack() {
		L = (int)(40*Math.random())+40;
		track = new int[4][L]; //track has two lanes
		for (int i=0;i<L;i++) {
			track[0][i]=WALL; track[1][i]=FREE; track[2][i]=FREE;track[3][i]=WALL;
		}
		//place random obstacles
		while (Math.abs(obstacle1-obstacle0)<4) {
			obstacle0 = (int)(20*Math.random())+20;
			obstacle1 = (int)(20*Math.random())+20;
		}
		track[1][obstacle0]=WALL; track[2][obstacle1]=WALL;
	}

	public static int[][] getView(int[][] track, Car car, Car otherCar) {
		int[][] view = new int[3][10];
		for (int i=0;i<10;i++) {
			if ((car.x+i)>(L-1)) {view[0][i]=WALL;view[1][i]=WALL;view[2][i]=WALL;} //if at end of track, see a wall
			else {
				view[0][i]=track[car.lane-1][(int)car.x+i];
				view[1][i]=track[car.lane][(int)car.x+i];
				view[2][i]=track[car.lane+1][(int)car.x+i];
			}
		}
		int diff = (int)otherCar.x-(int)car.x;
		if (diff >= 0 && diff < 10 ) {
			if (otherCar.lane == car.lane) view[1][diff]=OTHER_CAR;
			else if (otherCar.lane ==1 && car.lane==2) view[0][diff]=OTHER_CAR;
			else if (otherCar.lane ==2 && car.lane==1) view[2][diff]=OTHER_CAR;
		}
		return view;
	}

	public static void update(Car carMe, Car carYou, TakeAction action) {
		if (action.a>MAX_ACCEL) action.a=MAX_ACCEL; if (action.a<-1*MAX_ACCEL) action.a=-1*MAX_ACCEL; //max acceleration of 2
		carMe.v += action.a*dt; carMe.x += carMe.v*dt; 

		if ((int)carMe.x==(int)carYou.x && carMe.lane==carYou.lane) {finished=true; carMe.crashedCar=1;}
		else if (carMe.x > L && carMe.v>.1) {finished=true; carMe.crashedEnd=1;}
		else if (track[carMe.lane][(int)carMe.x]!=FREE) {finished=true; carMe.crashedWall=1;}
		else if (carMe.x > L-2 && carMe.v<.1) {finished=true;}	//should update how race finishes
		carMe.lane = action.lane;
	}

	public static void score(Car carA, Car carB, char symbol) {
		float score=100*carA.x/L+5; 			//start with a max score of 100 to reach end plus 5 for compiling.
		if (t>=TIME_MAX) score*=.75;			//25% deduction for running out of time
		if (carA.crashedWall==1)score*=0.20;		//80% deduction for crashing into a wall
		else if (carA.crashedEnd==1) score*=0.8;	//20% deduction for not stopping at the end
		else if (carA.crashedCar==1) score*=.9;		//10% deduction for crashing into another car
		else if (carA.x > L-1) score+=L/t;		//if finish the track, earn bonus for a faster average speed
		System.out.println("Score for car "+symbol+" is "+score);

		if (carA.crashedWall==1) System.out.println("Car "+symbol+" crashed into a wall!");
		else if (carA.crashedCar==1) System.out.println("Car  "+symbol+" crashed into the other car!");
		else if (carA.crashedEnd==1) System.out.println("Car "+symbol+" crashed into the end. You need to slow down first!");
		else System.out.println("FINISHED! Great job!");
	}

	public static void main(String[] args) {
		makeTrack();
		Car[] car = new Car[2]; car[0] = new Car(1,0,0); car[1]=new Car(2,0,0);
		TakeAction action=new TakeAction();
		finished = false;	
		gui = new RaceFrame(L,RaceFrame.TWO_LANES);
		gui.setObstacles(obstacle0,obstacle1);
		
		while (!finished && t<TIME_MAX) {
			action.set(0,car[0].lane); //defaults to zero acceleration and current lane
			DriverA.getMove(getView(track,car[0],car[1]), car[0].v, car[0].lane, action);
			update(car[0],car[1], action);

			action.set(0,car[1].lane); //defaults to zero acceleration and current lane
			DriverB.getMove(getView(track,car[1],car[0]), car[1].v, car[1].lane, action);
			update(car[1],car[0],action);

			gui.updateCars((int)car[0].x, car[0].lane, (int)car[1].x, car[1].lane, t); //sends the time as the only message
			t+=dt; //should not update until both cars have updated
		}
		score(car[0],car[1],'A');
		score(car[1],car[0],'B');
	} 
}
