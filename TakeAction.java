public class TakeAction {
	public double a;
	public int lane;
	
	public TakeAction() {a=0; lane=1;}
	public TakeAction(double a0, int lane0) {a=a0;lane=lane0;}

	public void set(double a0, int lane0) {a=a0;lane=lane0;}	

	public void switchLeft() { //actual lanes are 1 and 2, so car will still crash if in left lane and switching left
		if (lane>0) lane--;
	}
	public void switchRight() {
		if (lane<3) lane++;
	}
	
}
