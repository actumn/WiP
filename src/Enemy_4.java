
public class Enemy_4 extends Enemy {

	
	Enemy_4(int x, int y, int speed) {
		super(x, y, speed);
		who = 4;
	}
	
	public void move(){
		y += speed;
		
		if ( en_cnt < 20 )
			x -= speed;
		else
			x += speed;
	}
	
}
