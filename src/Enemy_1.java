
public class Enemy_1 extends Enemy {

	Enemy_1(int x, int y, int speed) {
		super(x, y, speed);
		who = 1;
	}
	
	
	public void move(){
		x -= speed-2;
	}
}
