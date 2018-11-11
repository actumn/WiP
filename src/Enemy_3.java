
public class Enemy_3 extends Enemy {

	
	Enemy_3(int x, int y, int speed) {
		super(x, y, speed);
		who = 3;
	}
	
	public void move(){
		y += speed;
		
		if ( en_cnt < 20 )
			x += speed;
		else
			x -= speed;
		
	}
	
}
