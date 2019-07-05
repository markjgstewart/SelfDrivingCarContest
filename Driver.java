
public class Driver {

	public static void move1D(int f, int ff, int fff, float v, TakeAction takeAction) {

	if (v<2){		
	takeAction.a=.1;
	}
	else{
	takeAction.a=-.1;
	}
	if (fff!=0){
	takeAction.a=-2;
	}
}

	
		

}
