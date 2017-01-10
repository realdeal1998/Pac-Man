import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class pacman extends JFrame implements Runnable, KeyListener
{
  BufferedImage background=null , path = null ,pointsLevelOne = null , pacmanLivesImage = null,winningBackground = null,startUpImage =null;
  final int UP_DIRECTION = 12 , DOWN_DIRECTION = 6, LEFT_DIRECTION = 9, RIGHT_DIRECTION = 3 , NO_DIRECTION = 0 , CLASSIC_MODE = 448 , EXTREME_MODE = 488;
  int level =1 , w , h,posToStart ,powerUpTimer=0 ,up=0, down=0,left=0,right=0 , spawnTimer =1
    , scatterTimer=0, pause = -1 , deathTimer =0 , lives =3 ,eatingScore = 0 , killedTimer =0 , ghostKilled=0,score=0 ,
    direction=0,winningTimer=-150,newGameTimer=0,startUp=0 , startUpTimer=0 , yRect = CLASSIC_MODE;
  int [] dataBuffInt;
  int []pointsData;
  MyDrawPanel drawPanel = new MyDrawPanel();
  PacmanObject pacman, startUpPacman = new PacmanObject(1348,320);
  InkyGhostObject inky , startUpInky = new InkyGhostObject(1224,333,0);
  PinkyGhostObject pinky , startUpPinky = new PinkyGhostObject(1162,333,0);
  BlinkyGhostObject blinky, startUpBlinky = new BlinkyGhostObject(1100,333,0);
  ClydeGhostObject clyde, startUpClyde = new ClydeGhostObject(1286,333,0);
  boolean  won=false ,powerUp=false , died = false ,lost=false , newGame = true , extremeMode;
  PointsObject [] points = new PointsObject[244];//244
  Font font = new Font("Bauhaus 93" , Font.PLAIN , 27);
  Font gameOverFont = new Font("Bauhaus 93" , Font.PLAIN , 32);
  Font startFont = new Font("Arial" , Font.PLAIN , 22);
  Font scoreFont = new Font("Bauhaus 93" , Font.PLAIN , 37), pausedFont = new Font("Bauhaus 93" , Font.PLAIN , 112);
  public static void main(String[] args)
  {
    new pacman();
  }

  public pacman()
  {
    addKeyListener(this);
    try{
      background = ImageIO.read(new File("pacmanLevelOne.png"));
      winningBackground = ImageIO.read(new File("pacmanLevelOneWon.png"));
      startUpImage = ImageIO.read(new File("pacmanStartupScreen.png"));
      path = ImageIO.read(new File("pacmanLevelOnePath.png"));
      pointsLevelOne = ImageIO.read(new File("pacmanLevelOnePoints.png"));
      pacmanLivesImage = ImageIO.read(new File("pacmanSpriteSheetbeta.png"));
      w = background.getWidth();
      h = background.getHeight();
      dataBuffInt = new int[w*h];
      pointsData = new int[w*h];
      dataBuffInt = path.getRGB(0, 0, w, h, null, 0, w);
      pointsData = pointsLevelOne.getRGB(0, 0, w, h, null, 0, w);
    }
    catch(IOException e){}
    posToStart = 0;
    Thread th = new Thread (this);
    th.start ();
    this.setLayout(null);
    this.add(drawPanel);
    drawPanel.setBackground(Color.BLACK);
    drawPanel.setBounds(-90,0,1200,725);
    this.setTitle("Pacman");
    this.setSize(1010, 725);
    this.setVisible(true);
  }

  public void keyReleased (KeyEvent e)
  {
    int release = e.getKeyCode();
    if(release == KeyEvent.VK_UP && !won && down==1)
    {
      direction = UP_DIRECTION;
      up=0; down=1;
    }
    else if(release == KeyEvent.VK_UP && !won){up=0;}
    if(release == KeyEvent.VK_DOWN && !won && up==1)
    {
      direction = UP_DIRECTION;
      up=1; down=0;
    }
    else if(release == KeyEvent.VK_DOWN && !won){down=0;}
    if(release == KeyEvent.VK_LEFT && !won && right==1)
    {
      direction = RIGHT_DIRECTION;
      left=0; right=1;
    }
    else if(release == KeyEvent.VK_LEFT && !won){left=0;}
    if(release == KeyEvent.VK_RIGHT && !won && left==1)
    {
      direction = LEFT_DIRECTION;
      left=1; right=0;
    }
    else if(release == KeyEvent.VK_RIGHT && !won){right=0;}
  }
  public void keyTyped(KeyEvent e){}

  public void keyPressed (KeyEvent e)
  {
    int code =e.getKeyCode();
    if (code == KeyEvent.VK_UP)
    {
      if(startUp==1)
      {
        direction = UP_DIRECTION;
        up=1;
      }
      else
      {
        yRect = CLASSIC_MODE;
      }
    }
    else if (code == KeyEvent.VK_DOWN)
    {
      if(startUp==1)
      {
        direction = DOWN_DIRECTION;
        down=1;
      }
      else
      {
        yRect = EXTREME_MODE;
      }
    }
    else if (code == KeyEvent.VK_LEFT)
    {
      direction = LEFT_DIRECTION;
      left=1;
    }
    else if (code == KeyEvent.VK_RIGHT)
    {
      direction = RIGHT_DIRECTION;
      right=1;
    }
    else if(code==32)
    {
      if(!pacman.died() && !won && !lost && startUp==1)pause*=-1;
    }
    else if(code==10 && startUp==0)
    {
      if(yRect == CLASSIC_MODE)
      {
        extremeMode = false;
        lives=3;
      }
      else
      {
        extremeMode=true;
        lives=1;
      }
      won = lost = powerUp = died = false;
      newGame = true;
      posToStart = powerUpTimer= up=down=left=right=scatterTimer=deathTimer=eatingScore=ghostKilled=score=direction=killedTimer=startUpTimer=newGameTimer=0;
      spawnTimer =1;
      winningTimer=-150;
      pacman = new PacmanObject(489,494);
      inky = new InkyGhostObject(447,303,1200);
      pinky = new PinkyGhostObject(489,303,600);
      blinky = new BlinkyGhostObject(489,240,0);
      clyde = new ClydeGhostObject(531,303,1800);
      spawnPoints();
      startUp=1;
    }
  }

  public void run ()
  {
    while(true)
    {
      repaint();
      try
      {
        Thread.sleep (7);
      }
      catch (InterruptedException ex){}
      if(startUp==0)
      {
        startUpTimer++;
        if(startUpTimer>100) startUpTimer=0;
        startUpPacman.doStartUpAnimation();
        startUpClyde.doStartUpAnimation();
        startUpInky.doStartUpAnimation();
        startUpPinky.doStartUpAnimation();
        startUpBlinky.doStartUpAnimation();
      }
      else if(newGame && pause!=1)
      {
        newGameTimer++;
      }
      else if(!won && !pacman.dead && pause!=1 && ghostKilled==0)
      {
        int flag=0;
        for(int x=0;x<points.length;x++)
        {
          if(!points[x].isItRedeemed()) {
            flag = 1;
            break;
          }
        }
        if(flag==0)
        {
          won=true;
          repaint();
        }

        if(direction==UP_DIRECTION && pacman.isAbleToGoUp(dataBuffInt , w))
        {
          pacman.up();
          direction=0;
        }
        else if(direction==DOWN_DIRECTION && pacman.isAbleToGoDown(dataBuffInt , w))
        {
          pacman.down();
          direction=0;
        }
        else if(direction==LEFT_DIRECTION && pacman.isAbleToGoLeft(dataBuffInt , w))
        {
          pacman.left();
          direction=0;
        }
        else if(direction==RIGHT_DIRECTION && pacman.isAbleToGoRight(dataBuffInt , w))
        {
          pacman.right();
          direction=0;
        }
        pacman.checkCollisions(dataBuffInt , w);
        if(pacman.getBounds().intersects(blinky.getGhostBounds()) && blinky.isAPowerUp())
        {
          ghostKilled =1;
          if(eatingScore==0) eatingScore=200;
          else eatingScore*=2;
          score+=eatingScore;
        }
        else if(pacman.getBounds().intersects(pinky.getGhostBounds())&& pinky.isAPowerUp()){
          ghostKilled = 3;
          if(eatingScore==0) eatingScore=200;
          else eatingScore*=2;
          score+=eatingScore;
        }
        else if(pacman.getBounds().intersects(inky.getGhostBounds())&& inky.isAPowerUp()){
          ghostKilled =2;
          if(eatingScore==0) eatingScore=200;
          else eatingScore*=2;
          score+=eatingScore;
        }
        else if(pacman.getBounds().intersects(clyde.getGhostBounds())&& clyde.isAPowerUp())
        {
          ghostKilled =4;
          if(eatingScore==0) eatingScore=200;
          else eatingScore*=2;
          score+=eatingScore;
        }
        else if((pacman.getBounds().intersects(blinky.getGhostBounds()) || pacman.getBounds().intersects(inky.getGhostBounds()) ||
                 pacman.getBounds().intersects(pinky.getGhostBounds()) || pacman.getBounds().intersects(clyde.getGhostBounds())))
        {
          lives--;
          pacman.isDead();
          direction = NO_DIRECTION;
          repaint();
          continue;
        }

        clyde.doMovementProcedure(dataBuffInt , w , pacman.getXPos() , pacman.getYPos(), pacman.getBounds());
        inky.doMovementProcedure(dataBuffInt , w , pacman.getXPos() , pacman.getYPos(), pacman.getBounds());
        pinky.doMovementProcedure(dataBuffInt , w , pacman.getXPos() , pacman.getYPos(), pacman.getBounds());
        blinky.doMovementProcedure(dataBuffInt , w , pacman.getXPos() , pacman.getYPos() , pacman.getBounds());

        for(int x=0;x<points.length;x++)
        {
          if(points[x].isAPowerUp())points[x].doAnimation();
          if(pacman.getBounds().intersects(points[x].getBounds()) && points[x].isAPowerUp() && !points[x].isItRedeemed())
          {
            powerUp = true;
            powerUpTimer = 0;
            blinky.enablePowerUp();
            inky.enablePowerUp();
            pinky.enablePowerUp();
            clyde.enablePowerUp();
            points[x].redeemIt();
            score+=50;
          }
          else if(pacman.getBounds().intersects(points[x].getBounds()) && !points[x].isItRedeemed())
          {
            points[x].redeemIt();
            score+=10;
          }
        }

        if(powerUp && powerUpTimer > 860)
        {
          powerUp = false;
          blinky.disablePowerUp();
          inky.disablePowerUp();
          pinky.disablePowerUp();
          clyde.disablePowerUp();
          eatingScore=0;
        }
        else powerUpTimer++;

      }

      else if(pacman.died())
      {
        deathTimer++;
        if(deathTimer<220)
        {
          blinky.doAnimation();
          inky.doAnimation();
          pinky.doAnimation();
          clyde.doAnimation();
        }
        else if(deathTimer<520) pacman.doDyingAnimation();
        else if(lives!=0)
        {
          pacman = new PacmanObject(489,494);
          inky = new InkyGhostObject(447,303,1200);
          pinky = new PinkyGhostObject(489,303,600);
          blinky = new BlinkyGhostObject(489,240,0);
          clyde = new ClydeGhostObject(531,303,1800);
          direction = NO_DIRECTION;
          deathTimer = scatterTimer = newGameTimer=0;
          spawnTimer =1;
          newGame = true;
        }
        else if(deathTimer<820)
        {
          lost = true;
        }
        else
        {
          startUpPacman = new PacmanObject(1348,320);
          startUpInky = new InkyGhostObject(1224,333,0);
          startUpPinky = new PinkyGhostObject(1162,333,0);
          startUpBlinky = new BlinkyGhostObject(1100,333,0);
          startUpClyde = new ClydeGhostObject(1286,333,0);
          startUp=0;
        }
      }

      else if(ghostKilled>0)
      {
        killedTimer++;
        if(ghostKilled==1 && killedTimer>200)
        {
          blinky = new BlinkyGhostObject(489,303,400);
          ghostKilled =0;
          killedTimer=0;
        }
        else if(ghostKilled==2 && killedTimer>200)
        {
          inky = new InkyGhostObject(447,303,400);
          ghostKilled =0;
          killedTimer=0;
        }
        else if(ghostKilled==3 && killedTimer>200)
        {
          pinky = new PinkyGhostObject(489,303,400);
          ghostKilled =0;
          killedTimer=0;
        }
        else if(ghostKilled==4 && killedTimer>200)
        {
          clyde = new ClydeGhostObject(531,303,400);
          ghostKilled =0;
          killedTimer=0;
        }
      }

      else if(won)
      {
        winningTimer++;
        pacman.setFrame(84);
        repaint();
        if(winningTimer>710)
        {
          startUpPacman = new PacmanObject(1348,320);
          startUpInky = new InkyGhostObject(1224,333,0);
          startUpPinky = new PinkyGhostObject(1162,333,0);
          startUpBlinky = new BlinkyGhostObject(1100,333,0);
          startUpClyde = new ClydeGhostObject(1286,333,0);
          startUp=0;
        }
      }

    }
  }

  public void spawnPoints()
  {
    for(int x=0; x<points.length; x++)
    {
      points[x] = new PointsObject();
      for(int r=posToStart;r<dataBuffInt.length;r++)
      {
        Color currentCol = new Color(pointsData[r]);
        if(currentCol.getGreen()==255 && currentCol.getBlue()==0)
        {
          points[x].setPosition((r%w),(int)(r/w));
          posToStart = r+1;
          break;
        }
        else if(currentCol.getRed()==255 && currentCol.getGreen()==0)
        {
          if(!extremeMode)points[x].makeItAPowerUp();
          points[x].setPosition((r%w),(int)(r/w));
          posToStart = r+1;
          break;
        }
      }
    }
  }

  class MyDrawPanel extends JPanel
  {
    public void paintComponent (Graphics g)
    {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.setFont(font);

      if(startUp==1)g2.drawImage(background, 0, 0, this);
      else g2.drawImage(startUpImage, 0, 0, this);

      if(startUp==1)
      {
        if(newGame)
        {
          if(newGameTimer>300 && newGameTimer<600)
          {
            pacman.makeObject(g2,this,ghostKilled);
            inky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
            pinky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
            blinky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
            clyde.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
          }
          if(newGameTimer<600)
          {
            g2.setColor(Color.ORANGE);
            g2.setFont(gameOverFont);
            g2.drawString("      READY!",416,400);
            for(int x=0;x<points.length;x++)
            {
              if(!points[x].isItRedeemed())points[x].makeObject(g2 , null ,powerUpTimer);
            }
          }
          else newGame=false;
        }

        else if(lost)
        {
          g2.setColor(Color.RED);
          g2.setFont(gameOverFont);
          g2.drawString("GAME    OVER",416,400);
        }

        else if(won)
        {
          if(winningTimer<410)
          {
            if((winningTimer>50 && winningTimer<100) || (winningTimer>150 && winningTimer<200) || (winningTimer>250 && winningTimer<300) || (winningTimer>350 && winningTimer<400))
            {
              g2.drawImage(winningBackground, 0, 0, this);
            }
          }
          else if(winningTimer<710)
          {
            g2.setColor(Color.RED);
            g2.setFont(gameOverFont);
            g2.drawString(" YOU   WIN!",416,400);
          }
          pacman.makeObject(g2,this,ghostKilled);
        }

        else if(pacman.dead)
        {
          pacman.makeObject(g2,this,ghostKilled);
          if(deathTimer<220)
          {
            inky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
            pinky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
            blinky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
            clyde.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
          }
        }

        else
        {
          for(int x=0;x<points.length;x++)
          {
            if(!points[x].isItRedeemed())points[x].makeObject(g2 , null ,powerUpTimer);
          }
          g2.setColor(new Color(51,255,255));
          pacman.makeObject(g2,this,ghostKilled);
          inky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
          pinky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
          blinky.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
          clyde.makeObject(g2,this,powerUpTimer,ghostKilled,eatingScore);
        }
        g2.setColor(Color.BLACK);
        g2.fillRect(38,303,90,42);
        g2.fillRect(892,303,90,42);
        for(int x=0,r=920;x<lives && r<1200;x++,r+=45)
        {
          g2.drawImage(pacmanLivesImage,r,77,r+42,119,42,84,84,126,this);
        }
        g2.setColor(Color.WHITE);
        g2.setFont(scoreFont);
        g2.drawString(""+score,945,170);
        if(pause==1)
        {
          g2.setColor(Color.WHITE);
          g2.setFont(pausedFont);
          g2.drawString("PAUSED",326,360);
          g2.setFont(gameOverFont);
          g2.drawString("PRESS SPACE TO CONTINUE",326,393);
        }
      }
      else
      {
        g2.setFont(gameOverFont);
        if(startUpTimer>0 && startUpTimer<50) g2.setColor(Color.BLUE);
        else g2.setColor(Color.WHITE);
        g2.drawString("PRESS ENTER TO INSERT COIN",386,573);
        g2.fillRect(456,yRect,20,10);
        g2.setColor(Color.WHITE);
        g2.drawString("CLASSIC MODE",486,463);
        g2.drawString("EXTREME MODE",486,503);
        if(yRect==EXTREME_MODE)
        {
          g2.setFont(startFont);
          g2.drawString("1 LIFE,NO POWERUPS!",746,463);
          g2.drawString("BEAT IT IF YOU CAN!",746,503);
        }
        startUpPacman.makeStartUpObject(g2,this);
        startUpClyde.makeStartUpObject(g2,this);
        startUpInky.makeStartUpObject(g2,this);
        startUpPinky.makeStartUpObject(g2,this);
        startUpBlinky.makeStartUpObject(g2,this);
      }
    }
  }
}

