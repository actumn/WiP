
public class Boss {
	
	int x;
	int y;
	int boss_cnt;
	
	Boss( int x,int y ){
		this.x = x;
		this.y = y;
		
		boss_cnt = 0;
	}
	
	public void move(){
		if(boss_cnt < 55){
			y += 5;
		}
		else if (130 < boss_cnt && boss_cnt < 150){
			x -= 12;
			y -= 3;
		}
		else if (240 < boss_cnt && boss_cnt < 270){
			x += 16;
		}
		
		else if (360 < boss_cnt && boss_cnt < 380){
			x -= 12;
			y += 3;
		}
	}
	
	public void boss_cntCount(){
		boss_cnt ++;
	}
	
}
