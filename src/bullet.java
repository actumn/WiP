import java.awt.*;

//�̻��� ��ġ �ľ� �� �̵��� ���� Ŭ����
class bullet{
	int x;	//�̻��� ��ǥ
	int y;
	
	int speed;
	
	int who;
	/*	who = 0 �̸� �ڽ��� �߻�
	 * 	who = 1 �̸� ���� �߻�
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