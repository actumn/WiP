
public class Enemy_2 extends Enemy {

	Enemy_2(int x, int y, int speed) {
		super(x, y, speed);
		who = 2;
	}
	
	public void move(){
		x += speed-2;
	}
}
