import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;

public class BlinkyGhostObject extends GameObject
{
  BufferedImage blinkyImage = null , powerUpImage = null , powerUpEndingImage = null;
  
  public BlinkyGhostObject(int x , int y , int spawnWait)
  {
    try
    {
      blinkyImage = ImageIO.read(new File("blinkySpriteSheet.png"));
      powerUpImage = ImageIO.read(new File("powerUpSpriteSheet.png"));
      powerUpEndingImage = ImageIO.read(new File("powerUpEndingSpriteSheet.png"));
    }
    catch(IOException e){}
    xPos = x;
    yPos = y;
    newSpawn= true;
    spawnWaitingTime = spawnWait;
    scatterTimer=0;
    frameX=0;
    frameY=84;
    slowDown = 0;
  }
  
  public void hasToMakeDecision(int[]dataBuffInt , int w , int pacmanXPos , int pacmanYPos , Ellipse2D.Double pacmanBounds)
  {
    if(scatterTimer>4000) scatterTimer=1;
    else if(scatterTimer>0) scatterTimer++;
    
    flag = 0;
    for(int x=0 ; x<WIDTH; x++)
    {
      for(int y=0; y<WIDTH ; y++)
      {
        Color cUp = new Color(dataBuffInt[((yPos+x)*w+xPos)+y]);
        if(cUp.getBlue()!=255)
        {
          flag = 1;
        }
      }
    }
    if(flag ==0)
    {
      if(powerUp) powerUpDecisions(dataBuffInt,w , pacmanBounds);
      else if(scatterTimer<500) makeRandomDecision2(dataBuffInt,w,pacmanXPos,pacmanYPos);
      else
      {
        if(pacmanXPos==xPos){
          startVertical = true;
          startHorizontal = false;
        }
        else if(pacmanYPos==yPos) {
          startHorizontal = true;
          startVertical = false;
        }
        else makeRandomDecision2(dataBuffInt,w,pacmanXPos,pacmanYPos);
        if(startVertical)
        {
          if(pacmanYPos<yPos && isAbleToGoUp(dataBuffInt,w)) up();
          else if(pacmanYPos>yPos && isAbleToGoDown(dataBuffInt,w)) down();
          else
          {
            makeRandomDecision(dataBuffInt,w);
          }
        }
        else if(startHorizontal)
        {
          if(pacmanXPos<xPos && isAbleToGoLeft(dataBuffInt,w)) left();
          else if(pacmanXPos>xPos && isAbleToGoRight(dataBuffInt,w)) right();
          else
          {
            makeRandomDecision2(dataBuffInt,w,pacmanXPos,pacmanYPos);
          }
        }
      }
    }
    move();
  }
  
  public void setSpawnSpeeds(int[]dataBuffInt , int w , int pacmanXPos , int pacmanYPos , Ellipse2D.Double pacmanBounds)
  {
    if(spawnWaitingTime!=0) spawnWaitingTime--;
    if(xPos==489 && yPos==240 && newSpawn) 
    {
      left();
      newSpawn = false;
      scatterTimer=1;
    }
    else if(xPos==489 && yPos==303 && spawnWaitingTime==0)
    {
      up();
    }
    hasToMakeDecision(dataBuffInt , w , pacmanXPos , pacmanYPos,pacmanBounds);
  }
  
  public void doMovementProcedure(int[]dataBuffInt , int w , int pacmanXPos , int pacmanYPos , Ellipse2D.Double pacmanBounds)
  {
    if(powerUp)slowDown++;
    if((powerUp && slowDown%2==0) || (!powerUp))
    {
      if(newSpawn) setSpawnSpeeds(dataBuffInt , w , pacmanXPos , pacmanYPos , pacmanBounds);
      else if(!newSpawn) hasToMakeDecision(dataBuffInt , w , pacmanXPos , pacmanYPos , pacmanBounds);
    }
    if(powerUp) doAnimation();
  }
  
  public void makeObject(Graphics g , ImageObserver observer, int powerUpTimer , int ghostTimer , int eatingScore)
  {
    if(ghostTimer==1) g.drawString(""+eatingScore,xPos,yPos+25);
    else if(!powerUp)g.drawImage(blinkyImage,xPos, yPos, xPos+WIDTH, yPos+WIDTH ,frameX,frameY,frameX+WIDTH , frameY+WIDTH,observer);
    else if(powerUp && (powerUpTimer<600 || (powerUpTimer>620 && powerUpTimer<660) || (powerUpTimer>700 && powerUpTimer<740) || (powerUpTimer>780 && powerUpTimer<820)))
      g.drawImage(powerUpImage,xPos, yPos, xPos+WIDTH, yPos+WIDTH ,frameX,frameY,frameX+WIDTH , frameY+WIDTH,observer);
    else g.drawImage(powerUpEndingImage,xPos, yPos, xPos+WIDTH, yPos+WIDTH ,frameX,frameY,frameX+WIDTH , frameY+WIDTH,observer);
  }
  
  public void makeStartUpObject(Graphics g , ImageObserver observer)
  {
     g.drawImage(blinkyImage,xPos, yPos, xPos+WIDTH+20, yPos+WIDTH+20 ,frameX,frameY,frameX+WIDTH , frameY+WIDTH,observer);
  }
}