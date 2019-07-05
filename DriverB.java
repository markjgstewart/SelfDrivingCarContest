public class DriverB{
	public static void getMove(int[][] view, float velocity, int lane, TakeAction takeAction) {
		takeAction.a=1;
		if (view[1][2]!=RaceTrack2D.FREE) {
			if (view[2][0]==RaceTrack2D.WALL) takeAction.switchLeft();
			else takeAction.switchRight();
		}
	}
}
