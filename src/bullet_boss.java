
public class bullet_boss extends bullet{

	int angle;
	
	bullet_boss(int x, int y, int angle, int speed, int who) {
		super(x, y, speed, who);
		this.angle = angle;
	}
	
	public void move(){
		x += Math.cos(Math.toRadians(angle))*speed;
		y += Math.sin(Math.toRadians(angle))*speed;
	}

}
