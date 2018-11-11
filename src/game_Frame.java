import java.awt.*;
import java.awt.event.*;
import java.util.*;
//import java.io.*;
//import javax.sound.*;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
import javax.swing.*;

//������ ������ ���� JFrame, Ű���� �̺�Ʈ ó���� ���� KeyListener, �����带 ������ ���� Runnable
class game_Frame extends JFrame implements KeyListener, Runnable{
	
	//�������� �ʺ�  x ����
	final int f_width = 700;
	final int f_height = 800;
	
	int x,y;
	static int bullet_Count = 2;				//�߰��� ���� �̻����� �ֱ⸦ ���� ����
	
	int[] cs = {0,0,0};			//��� ��ũ�� �ӵ� �����
	int by = 0;					//��ü ��� ��ũ�ѿ�
	
	boolean KeyUp = false;
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;
	boolean KeyZ = false;	//�̻��� �߻�Ű Z
	
	
	static int cnt = 0;								//Ÿ�̹� ������ ���� ���ѷ����� ī������ ����
	
	//speed : �̵��ӵ� ����
	int player_Speed;
	int bullet_Speed;
	int fire_Speed;
	int enemy_Speed;
	int game_Score;
	int player_Hitpoint;
	
	int Boss_HP = 1000;
	
	boolean Boss_Explosion = true;
	
	
	Thread th;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	Image BackGround_img = new ImageIcon("background.png").getImage();
	Image Explo_img;
	Image[] boss_explo;
	
	Image me_img = new ImageIcon("yp.png").getImage();			//ĳ����
	Image bullet_img = new ImageIcon("bullets.png").getImage();	//�̻���
	Image Enemy_img = new ImageIcon("enemy.png").getImage();		//��
	Image bullet2_img = new ImageIcon("bullets_2.png").getImage();
	
	Image Boss_img = new ImageIcon("boss.png").getImage();
	
	ArrayList bullet_List = new ArrayList();		//�ټ��� �̻����� �����ϱ� ���� �迭
	ArrayList Enemy_List = new ArrayList();			//�ټ��� �� ����
	ArrayList Explosion_List = new ArrayList();
	//���� ����Ʈ
	ArrayList Boss_List = new ArrayList();
	
	//���� ���۸�
	Image buffImage;
	Graphics buffg;
	
	bullet bs;									//�̻��� Ŭ���� ����Ű
	bullet bs_1, bs_2;
	
	Enemy en;
	
	Boss boss;
	
	Explosion ex;
	//���� ����Ʈ
	
	game_Frame(){
		init();
		start();
		
		setTitle("WP01");
		setSize(f_width, f_height);
		Dimension screen = tk.getScreenSize();		//���� ������� �ػ� ��
		
		//�������� ������� �߾ӿ� ��ġ��Ű�� ���� ��ǥ�� ���
		int f_xpos = (int)(screen.getWidth()/2 - f_width/2);
		int f_ypos = (int)(screen.getHeight()/2 - f_height/2);
		
		setLocation(f_xpos, f_ypos);
		setResizable(true);
		setVisible(true);
		
		
		
	}
	
	//�����ӿ� �� ������Ʈ ����
	public void init(){
		
		x = (f_width-200)/2-25;		//���� �������� 500 x 800
		y = f_height-50;
		
		
		Explo_img = new ImageIcon("explo_0.png").getImage();
		
		boss_explo = new Image[2];
		boss_explo[0] = new ImageIcon("explo_1.png").getImage();
		boss_explo[1] = new ImageIcon("explo_2.png").getImage();
		
		
		game_Score = 0;
		player_Hitpoint = 8;
		
		player_Speed = 5;
		bullet_Speed = 15;
		fire_Speed = 3;
		enemy_Speed = 10;
		
		//Sound("bg.wav");
	
	}
	
	
	
	//�⺻���� ���� ��� ó�� �κ�
	public void start(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);	//Ű���� �̺�Ʈ ����
		
		th = new Thread(this);	//������ ����
		th.start();				//������ ����
		
	}
	
	public void run(){			//�����尡 ���� ������ �κ�
		try{					//���ܿɼ� �������� ��������
				while(true && player_Hitpoint > 0){		//whlie ������ ���� ����
				KeyProcess();	//Ű���� �Է� ó���� �Ͽ� x,y����
				EnemyProcess();
				bulletProcess();
				ExplosionProcess();
				
				
				
				repaint();		//���ŵ� x,y�� �̹��� ���� �׸���
				
				Thread.sleep(30);	//20 �и��ʷ� ������ ���� ����
				cnt++;
				en_cntProcess();
				
				BossProcess();
				boss_cntProcess();
				}
		} catch(Exception e){

			e.printStackTrace();
		}
			
	}
	
	//���� �޼ҵ�
	public void bulletProcess(){
		if(KeyZ == true){
			
			if(( cnt % fire_Speed ) == 0){

				bs = new bullet(x-2, y, bullet_Speed, 0);
				bullet_List.add(bs);
				bs = new bullet(x+2, y, bullet_Speed, 0);
				bullet_List.add(bs);
				
				
				if(bullet_Count == 0){
					
					bs_1 = new bullet(x-60, y, bullet_Speed, 0);
					bs_2 = new bullet(x+60, y, bullet_Speed, 0);
					
					bullet_List.add(bs_1);
					bullet_List.add(bs_2);
					
					bullet_Count +=10;
					
				}
			
				bullet_Count -=1;
				
			}
		}	

		for (int i = 0; i < bullet_List.size(); i++){
			
			bs = (bullet)bullet_List.get(i);
			bs.move();
			
			if( bs.y < 40 || bs.x < 0 || bs.x > f_width || bs.y > f_height + 20 ){	//ȭ�� ������ ������ �����
			
				bullet_List.remove(i);
			}
			
			//ĳ���Ϳ� �� �̻��� �浹
			if(Crash(x, y, bs.x, bs.y, me_img, Enemy_img) == true && bs.who == 1){
				player_Hitpoint --;
				
				ex = new Explosion(x, y, 1);
				
				Explosion_List.add(ex);
				
				bullet_List.remove(i);
			}
				
			if(cnt > 850){
				if(Crash(bs.x, bs.y, boss.x, boss.y, bullet_img, Boss_img) == true && boss.boss_cnt > 80 && bs.who == 0){
					Boss_HP -= 10;
					game_Score += 10;
				
					bullet_List.remove(i);
				}
			}
				
			for(int j = 0; j < Enemy_List.size(); j++){
				en = (Enemy) Enemy_List.get(j);
					
				//crash�� �ļ�, �浹����
				if(Crash(bs.x, bs.y, en.x, en.y, bullet_img, Enemy_img) == true && bs.who == 0){
					bullet_List.remove(i);
					Enemy_List.remove(j);
					
					game_Score += 10;
					
					ex = new Explosion(en.x + Enemy_img.getWidth(null) / 2, en.y + Enemy_img.getHeight(null) / 2, 0);
					
					Explosion_List.add(ex);
				}
				
				
			}
					
		}
			
	}
	
	public void ExplosionProcess(){
		//���� ����Ʈ ó��
		
		for ( int i = 0; i < Explosion_List.size(); i++){
			ex = (Explosion)Explosion_List.get(i);
			ex.effect();
		
		}
		
	}
		
	
	//�浹 ����
	public boolean Crash(int x1, int y1, int x2, int y2, Image img1, Image img2){
		/*	x1 = bs.x		y1 = bs.y
		 * 	x2 = en.x		y2 = en.y
		 * 	img1 = bullet	img2 = enemy
		 *	img1 = Player	img2 = enemy
		 */
		boolean check = false;
		
		if( Math.abs(( x1 + img1.getWidth(null) / 2) - ( x2 + img2.getWidth(null) / 2 )) < ( img2.getWidth(null) / 2 + img1.getWidth(null) / 2 ) && 
				Math.abs(( y1 + img1.getHeight(null) / 2 ) - ( y2 + img2.getHeight(null) / 2 )) < (img2.getHeight(null) / 2 + img1.getHeight(null) / 2 )){
			check = true;
		}else check = false;
		
		return check;
	}
	
	
	public void EnemyProcess(){
		
		for (int i = 0; i < Enemy_List.size(); i++){
			en = (Enemy)(Enemy_List.get(i));
			//�迭�� ���� �����Ǿ������� �ش�Ǵ� ���� �Ǻ�
			en.move();
			
			if( en.y > f_height || en.x > f_width + 500 || en.x < - 500 ){
				Enemy_List.remove(i);
			}
			
			if( Crash(x, y, en.x, en.y, me_img, Enemy_img) == true){
				player_Hitpoint --;
				Enemy_List.remove(i);
				
				game_Score += 10;
				
				ex = new Explosion(en.x + Enemy_img.getWidth(null) / 2, en.y + Enemy_img.getHeight(null) / 2, 0);
				
				Explosion_List.add(ex);
				
				ex = new Explosion(x, y, 1);
				
				Explosion_List.add(ex);
			}
			
			if ( cnt % 60 == 0 && cnt != 0){
				bs = new bullet_1(en.x - 30, en.y + 25, bullet_Speed - 5 , 1, x);
				
				bullet_List.add(bs);
			}
		}
		
		
		
		if ( cnt == 0){
			en = new Enemy((f_width-200)/2+175, -100, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2+150, -200, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2+175, -300, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2+150, -400, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2+175, -500, enemy_Speed);
			Enemy_List.add(en);
		}
		
		if ( cnt == 100){
			en = new Enemy((f_width-200)/2-225, -100, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2-200, -200, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2-225, -300, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2-200, -400, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2-225, -500, enemy_Speed);
			Enemy_List.add(en);
		}
		
		if ( cnt == 200 || cnt == 350 || cnt == 500 || cnt == 770 ){
		
			en = new Enemy((f_width-200)/2+175, -100, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2+75, -125, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2-25, -100, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2-125, -125, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy((f_width-200)/2-225, -100, enemy_Speed);
			Enemy_List.add(en);
		}
		
		if ( cnt == 275 ){
			en = new Enemy_1((f_width-200)+100, 350, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy_1((f_width-200)+200, 350, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy_1((f_width-200)+300, 350, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy_1((f_width-200)+400, 350, enemy_Speed);
			Enemy_List.add(en);
		}
		
		if ( cnt == 425){
			en = new Enemy_2(-100, 250, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy_2(-200, 250, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy_2(-300, 250, enemy_Speed);
			Enemy_List.add(en);
			en = new Enemy_2(-400, 250, enemy_Speed);
			Enemy_List.add(en);
		}
		if ( cnt == 575 || cnt == 580 || cnt == 585 || cnt == 590 || cnt == 595 || cnt == 600){
			en = new Enemy_3((f_width - 200)/2, 0, enemy_Speed-2 );
			Enemy_List.add(en);
			en = new Enemy_4((f_width - 200)/2, 0, enemy_Speed-2 );
			Enemy_List.add(en);
		}
		
		if ( cnt == 670 || cnt == 675 || cnt == 680 || cnt == 685 || cnt == 690){
			en = new Enemy_3( 100, 0, enemy_Speed-2 );
			Enemy_List.add(en);
		}
		
		if ( cnt == 700 || cnt == 705 || cnt == 710 || cnt == 715 || cnt == 720){
			en = new Enemy_4((f_width - 300), 0, enemy_Speed-2);
			Enemy_List.add(en);
		}
		
	}
	
	public void en_cntProcess(){
		for ( int i = 0; i < Enemy_List.size(); i++){
			en = (Enemy)Enemy_List.get(i);
			if(en.who == 3 || en.who == 4){
				en.effect();
			}
		}
	}
	
	public void BossProcess(){
		if( cnt == 850 ){
			Boss_HP = 1000;
			boss = new Boss((f_width - 200)/2 - 90, -200);
			Boss_List.add(boss);
		}
		if( cnt > 850 && Boss_HP > 0 ){
			boss.move();
			
			if( Crash(x, y, boss.x, boss.y, me_img, Boss_img) == true){
				player_Hitpoint --;
			}
		}
		
		if( Boss_HP == 0 && Boss_Explosion ){

			ex = new Explosion(boss.x + Boss_img.getWidth(null) / 2, boss.y + Boss_img.getHeight(null) / 2, 2);
			Explosion_List.add(ex);
			
			Boss_Explosion = false;
		}
		
		if ( Boss_HP < 0){
			Boss_HP = 0;
		}
	
		if ( cnt > 850 && Boss_HP > 0 ){
			if (boss.boss_cnt % 5 == 0 && ((boss.boss_cnt > 80 && boss.boss_cnt < 120) || (boss.boss_cnt > 380 && boss.boss_cnt < 410))){
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 0 + boss.boss_cnt - 80, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 30 + boss.boss_cnt - 80, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 60 + boss.boss_cnt - 80, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 90 + boss.boss_cnt - 80, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 120 + boss.boss_cnt - 80, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 150 + boss.boss_cnt - 80, bullet_Speed - 4, 1);
				bullet_List.add(bs);
			}
			if (boss.boss_cnt % 14 == 0 && boss.boss_cnt > 160 && boss.boss_cnt < 240){
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 18, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 36, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 54, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 72, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 90, bullet_Speed - 4, 1);
				bullet_List.add(bs);
			}
			if (boss.boss_cnt % 14 == 7 && boss.boss_cnt > 160 && boss.boss_cnt < 240){
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 9, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 27, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 45, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 63, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 81, bullet_Speed - 4, 1);
				bullet_List.add(bs);
			}
			if ((boss.boss_cnt % 16 == 0 || boss.boss_cnt % 16 == 3 || boss.boss_cnt % 16 == 6 || boss.boss_cnt % 16 == 9) && boss.boss_cnt > 280 && boss.boss_cnt < 360){
				bs = new bullet_boss( 15, boss.y + 10, 90, bullet_Speed - 3, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( 65, boss.y + 10, 90, bullet_Speed - 3, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( 165, boss.y + 10, 90, bullet_Speed - 3, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( 225, boss.y + 10, 90, bullet_Speed - 3, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( 265, boss.y + 10, 90, bullet_Speed - 3, 1);
				bullet_List.add(bs);
			}
			if (boss.boss_cnt % 16 == 0 && boss.boss_cnt > 280 && boss.boss_cnt < 360){
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 99, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 102, bullet_Speed - 4, 1);
				bullet_List.add(bs);
			}
			if (boss.boss_cnt % 16 == 8 && boss.boss_cnt > 280 && boss.boss_cnt < 360){
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 97, bullet_Speed - 4, 1);
				bullet_List.add(bs);
				bs = new bullet_boss( boss.x + 60, boss.y + 90, 100, bullet_Speed - 4, 1);
				bullet_List.add(bs);
			}
		}
	}
	
	public void boss_cntProcess(){
		if ( cnt > 850) {
			boss.boss_cntCount();
			
			if ( boss.boss_cnt > 420){
				boss.boss_cnt = 80;
			}
		}
		
	}
	
	public void paint(Graphics g){
		
		buffImage = createImage(f_width, f_height);//������۸� ����ũ�� = ȭ�� ũ��
		buffg = buffImage.getGraphics();
		
		
		update(g);
		
	}
	
	public void update(Graphics g){
		Draw_Background();
		Draw_Char();								//������ �׷��� �׸��� ������	
		Draw_Enemy();
		Draw_bullet();
		Draw_Boss();
		
		Draw_Explosion();
		Draw_StatusText();
		
		g.drawImage(buffImage, 0, 0, this);			//ȭ�鿡 ���ۿ� �׸� �׸��� ������ �׸���
	}
	
	public void Draw_Background(){
		
		
		if ( by < 0){
			buffg.drawImage(BackGround_img, 0, by, this);	
			by += 3;
			
		}else {by = -1700;}
	}
	
	public void Draw_Char(){
	
		//0,0���� �ػ� ũ�⸸ŭ ȭ���� ����
		buffg.drawImage(me_img, x, y, this);
		//yp�� �Ʒ� �߾ӿ�
	}
	
	//�̻����� �׸��� �޼ҵ�
	public void Draw_bullet(){
		for (int i = 0; i < bullet_List.size(); i++){
			
			bs = (bullet)(bullet_List.get(i));	//�̻��� ��ġ�� Ȯ��
	
			if( bs.who == 0 )
				buffg.drawImage(bullet_img, bs.x, bs.y, this);	//�̻��� �׸���
			if( bs.who == 1 )
				buffg.drawImage(bullet2_img, bs.x, bs.y, this);
			
		}
	}
	
	public void Draw_Enemy(){
		for(int i = 0; i < Enemy_List.size(); i++){
			en = (Enemy)(Enemy_List.get(i));
			buffg.drawImage(Enemy_img, en.x, en.y, this);
			
		}
	}
	
	public void Draw_Boss(){
		if(cnt > 850 && Boss_HP > 0){
			for(int i = 0; i < Boss_List.size(); i++){
				boss = (Boss)Boss_List.get(0);
				buffg.drawImage(Boss_img, boss.x, boss.y, this);
			}
			
		}
	}
	
	public void Draw_Explosion(){
		
		for( int i = 0; i < Explosion_List.size(); i++){
			ex = (Explosion) Explosion_List.get(i);
			
			if ( ex.damage == 0 ){
				if ( ex.ex_cnt < 7){
					buffg.drawImage(Explo_img, ex.x - Explo_img.getWidth(null) / 2, ex.y - Explo_img.getHeight(null), this);
				}
				else if ( ex.ex_cnt > 7 ){
					Explosion_List.remove(i);
					ex.ex_cnt = 0;
				}
			}
			else if ( ex.damage == 1 ){
				if ( ex.ex_cnt < 7){
					buffg.drawImage(Explo_img, ex.x + 15, ex.y - 10, this);
				}
				else if ( ex.ex_cnt > 7){
					Explosion_List.remove(i);
					ex.ex_cnt = 0;
				}
			}
			else if ( ex.damage == 2 ) {
				if ( ex.ex_cnt < 7){
					buffg.drawImage(boss_explo[0], ex.x - 20, ex.y - 20, this);
					buffg.drawImage(boss_explo[0], ex.x - 10, ex.y + 50, this);
					buffg.drawImage(boss_explo[1], ex.x + 30, ex.y + 40, this);
				}
				else if ( ex.ex_cnt < 14){
					buffg.drawImage(boss_explo[0], ex.x + 30, ex.y - 40, this);
					buffg.drawImage(boss_explo[1], ex.x - 20, ex.y + 20, this);
					buffg.drawImage(boss_explo[1], ex.x + 10, ex.y - 50, this);
				}
				else if ( ex.ex_cnt < 21){
					buffg.drawImage(boss_explo[1], ex.x + 40, ex.y, this);
					buffg.drawImage(boss_explo[1], ex.x - 25, ex.y + 25, this);
				}
				else if ( ex.ex_cnt > 21 ){
					Explosion_List.remove(i);
					ex.ex_cnt = 0;
				}
			}
		}
		
	}
	
	
	public void Draw_StatusText(){
		buffg.clearRect(500, 0, 200, f_height);
		
		buffg.setFont(new Font("Default", Font.BOLD, 20));
		
		buffg.drawString("SCORE : " + game_Score, 510, 50);
		buffg.drawString("HitPoint : " + player_Hitpoint, 510, 100);
		buffg.drawString("bullet Count : " + bullet_List.size(), 510, 150);
		buffg.drawString("Enemy Count : " + Enemy_List.size(), 510, 200);
	
		if ( cnt > 850 )
			buffg.drawString("Boss HP : "+ Boss_HP, 510, 250);
	}
	
	
	//Ű���� �Է� �̺�Ʈ ó��
	
	public void keyPressed(KeyEvent e){
		
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
			KeyUp = true;
			break;
		case KeyEvent.VK_DOWN :
			KeyDown = true;
			break;
		case KeyEvent.VK_LEFT :
			KeyLeft = true;
			break;
		case KeyEvent.VK_RIGHT :
			KeyRight = true;
			break;
			
		case KeyEvent.VK_Z :
			KeyZ = true;
			break;
		}
	}
	
	//Ű���带 ������ ��������
	
	public void keyReleased(KeyEvent e){
		
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
			KeyUp = false;
			break;
		case KeyEvent.VK_DOWN :
			KeyDown = false;
			break;
		case KeyEvent.VK_LEFT :
			KeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT :
			KeyRight = false;
			break;
			
		case KeyEvent.VK_Z :
			KeyZ = false;
			break;
		}
	}
	
	//Ű���尡 Ÿ���� �ɶ�
	
	public void keyTyped(KeyEvent e){
		
	}
	
	//���� ĳ���� ������ ����
	
	public void KeyProcess(){
		
		if(KeyUp == true && y > 25) y-=6;
		if(KeyDown == true && y + me_img.getHeight(null) < f_height)	y+=6;
		if(KeyLeft == true && x > 0) x-=6;
		if(KeyRight == true && x + me_img.getWidth(null) < f_width-200) x+=6;
		
	}

	/*public void Sound(String fileName){
		try{
			FileInputStream stream = new FileInputStream(fileName);
			sun.audio.AudioStream audio = new sun.audio.AudioStream(stream);
			AudioPlayer.player.start(audio);
			//AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			
			//Clip clip = AudioSystem.getClip();
			//clip.open(ais);
			//clip.start();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}*/
}