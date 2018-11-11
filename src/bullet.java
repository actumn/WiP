import java.awt.*;

//미사일 위치 파악 및 이동을 위한 클래스
class bullet{
	int x;	//미사일 좌표
	int y;
	
	int speed;
	
	int who;
	/*	who = 0 이면 자신이 발사
	 * 	who = 1 이면 적이 발사
	 */
	
	bullet(int x, int y, int speed, int who){
		this.x = x+25;
		this.y = y;
		this.speed = speed;
		this.who = who;
	}
	
	public void move(){
		y -= speed;
	}
}