
public class Enemy {
	int x;
	int y;
	int speed;
	int who;
	int en_cnt;
	
	Enemy(int x, int y, int speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
		
		who = 0;
		en_cnt = 0;
	}
	
	public void move(){
		y += speed;
	}
	
	public void effect(){
		en_cnt++;
	}
}
