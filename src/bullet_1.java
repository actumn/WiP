
public class bullet_1 extends bullet {

	int ypx;
	
	bullet_1(int x, int y, int speed, int who, int ypx) {
		super(x, y, speed, who);
		this.ypx = ypx;
		
	}
	
	public void move(){
		y += speed;
		x += (ypx-x)*speed / 800;
	}

}
