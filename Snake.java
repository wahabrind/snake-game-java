package snake;

import java.awt.*;

import javax.swing.JPanel;


import java.awt.event.*;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener
{
        public int allLocations[][];
        
    
	public static Snake snake;

	public JFrame jframe;

	public RenderPanel renderPanel= new RenderPanel();

	public Timer timer = new Timer(50, this);

	public ArrayList<Point> snakeParts = new ArrayList<Point>();

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE =10;

	public int ticks = 0, direction = DOWN, score,tailLength = 2, time;
        public int scoreend[]=new int[1000];
	public Point head, cherry;

	

	public boolean over = false, paused;

        public int gola=0;
	public Snake()
	{
                
		jframe = new JFrame("Snake Game");
		jframe.setVisible(true);
		jframe.setSize(805, 700);
		jframe.setResizable(false);
		jframe.setLocation(200,20);
		jframe.add(renderPanel);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
        }

	public void startGame()
	{     
		over = false;
		paused = false;
		time = 0;
		score =0;
		tailLength = 1;
		ticks = 0;
		direction = DOWN;
		head = new Point(10,0);
		cherry = new Point((int) (Math.random() * 79), (int) (Math.random() * 66));
		timer.start();
	}

	@Override
public void actionPerformed(ActionEvent args)
	{
            
                renderPanel.repaint();
		ticks++;
		if (head != null && !over && !paused)
		{
                  
            		
                    time++;
                     if(score==100)
                    {
                    timer.setDelay(45);
                    }

                     else if(score==200)
                    {
                    timer.setDelay(35);
                    }
                     else if(score==300)
                    {
                    timer.setDelay(25);
                    }

                     else if(score==400)
                    {
                    timer.setDelay(15);
                    }
                     else if(score==500)
                    {
                    timer.setDelay(5);
                    }

			snakeParts.add(new Point(head.x, head.y));
                       System.out.println(snakeParts);
			if (direction == UP)
			{
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1) )
				{
					head = new Point(head.x, head.y-1);
				}
				else
				{
					over=true;
                                        //startGame();
				}
			}

			if (direction == DOWN)
			{
				if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1))
				{
					head = new Point(head.x, head.y + 1);
				}
				else
				{
					over=true;
                                        //startGame();
				}
			}

			if (direction == LEFT)
			{
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
				{
					head = new Point(head.x - 1, head.y);
				}
				else
				{
					over=true;
                                        //startGame();
				}
			}

			if (direction == RIGHT)
			{
				if (head.x + 1 < 80 && noTailAt(head.x + 1, head.y))
				{
					head = new Point(head.x + 1, head.y);
				}
                                else
                                {
                                        over=true;
                                        //startGame();
                                }
			}

			if (snakeParts.size() > tailLength)
			{
                            snakeParts.remove(0);
                            
			}

			if (cherry != null)
			{
				if (head.equals(cherry))
				{   
                                    if(gola%5==0&&gola!=0)
                                    {
					score += 50;
					tailLength+=10;
					cherry.setLocation((int) (Math.random() * 79), (int) (Math.random() * 66));
                                        gola=0;
                                    }
                                    else
                                    {
                                        score += 10;
					tailLength++;
					cherry.setLocation((int) (Math.random() * 79), (int) (Math.random() * 66));
                                        gola++;
                                    
                                    }
                                    }
			}
		}
	}

	public boolean noTailAt(int x, int y)
	{
		for (int i=0;i<snakeParts.size();i++)
		{
			if (snakeParts.get(i).equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
        }
public static void main(String args[])	
   	{
		snake = new Snake();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)
		{
			direction = LEFT;
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT)
		{
			direction = RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN)
		{
			direction = UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)
		{
			direction = DOWN;
		}

		if (i == KeyEvent.VK_SPACE)
		{
			if (over)
			{
				startGame();
			}
			else
			{
				paused = !paused;
			}
		}
                
                		if (i == KeyEvent.VK_N)
                                {
                                startGame();
                                snakeParts.clear();
                                }
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

}
class RenderPanel extends JPanel
{
        
	

	@Override
	protected void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		
		Snake snake = Snake.snake;

		g.setColor(Color.black);
		
		g.fillRect(0, 0, 800, 700);
                 //body colour
		g.setColor(Color.blue);

		for (int i=0;i<snake.snakeParts.size();i++)
		{
			  
			g.fillOval(snake.snakeParts.get(i).x* Snake.SCALE , snake.snakeParts.get(i).y* Snake.SCALE , Snake.SCALE, Snake.SCALE);
		}
                //head colour
		g.setColor(Color.yellow);
		g.fillOval(snake.head.x * Snake.SCALE-1, snake.head.y* Snake.SCALE-1 , Snake.SCALE+2, Snake.SCALE+2);
		
		g.setColor(Color.WHITE);
                if(snake.gola%5==0&&snake.gola!=0)
                {
                g.fillOval(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, Snake.SCALE+3, Snake.SCALE+3);
                }
		else
                {
		g.fillOval(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
                }
		String string = "Score: " + snake.score + ", Length: " + snake.tailLength ;
		
		g.setColor(Color.white);
		
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);

		string = "Game Over!";

		if (snake.over)
		{
			g.drawString(string, 350,300);
                        for(int i=0;i<=snake.score;i++)
                        {
                            string="score:"+i;
                           
                            g.setColor(Color.DARK_GRAY);
                            g.fillRect(250,350, 250,120);
                           g.setColor(Color.white);
                           g.drawString(string, 350,400);
                            
                        }
                        string="Press 'n' To Play Again";
                           g.drawString(string, 320,450);
                        
                        
		}

		string = "Paused!";

		if (snake.paused && !snake.over)
		{
			g.drawString(string,300,200);
		}
                g.drawString("made by abdul wahab and team",300,600);
	}
}
