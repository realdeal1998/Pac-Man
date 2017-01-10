import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;

abstract class GameObject
{
  protected int xPos , yPos , xSpeed , ySpeed , flag ,frameX =84 , frameY ,delay, spawnWaitingTime , scatterTimer = 0 , slowDown=0;
  protected Boolean dontAnimate = true,startVertical = false, startHorizontal = true,powerUp = false , newSpawn=true , dead=false;
  protected final int WIDTH = 42;
  
  public void move()
  {
    xPos+=xSpeed;
    yPos+=ySpeed;
    if(xPos<90 && yPos==303) xPos=880;
    else if(xPos>891 && yPos==303) xPos=100;
    doAnimation();
  }
  
  public void doStartUpAnimation()
  {
    left();
    move();
    if(xPos<-650)xPos=1120;
  }
  
  public void up()
  {
    dontAnimate=false;
    ySpeed=-1;
    xSpeed = 0;
    frameY=126;
  }
  
  public void down()
  {
    dontAnimate=false;
    ySpeed=1;
    xSpeed = 0;
    frameY=42;
  }
  
  public void right()
  {
    dontAnimate=false;
    xSpeed=1;
    ySpeed = 0;
    frameY=0;
  }
  
  public void left()
  {
    dontAnimate=false;
    xSpeed=-1;
    ySpeed = 0;
    frameY=84;
  }
  
  public void doAnimation()
  {
    delay++;
    if(frameX>120)
    {
      frameX=0;
    }
    else if(delay%20==0) frameX+=42;
  }
  
  public boolean isAbleToGoUp(int[]dataBuffInt , int w)
  {
    flag = 0;
    for(int y=0 ; y<WIDTH ; y++)
    {
      Color cUp = new Color(dataBuffInt[((yPos-1)*w+xPos)+y]);
      if(cUp.getBlue()>200 && cUp.getRed()>200)
      {
        flag =1;
      }
    }
    if(flag==0)return true;
    else return false;
  }
  
  public boolean isAbleToGoDown(int[]dataBuffInt , int w)
  {
    flag = 0;
    for(int y=0 ; y<WIDTH ; y++)
    {
      Color cDown = new Color(dataBuffInt[((yPos+WIDTH)*w+xPos)+y]);
      if(cDown.getRed()>200 && cDown.getRed()>200)
      {
        flag =1;
      }
    }
    if(flag==0)return true;
    else return false;
  }
  
  public boolean isAbleToGoLeft(int[]dataBuffInt , int w)
  {
    flag = 0;
    for(int y=0 ; y<WIDTH ; y++)
    {
      Color cLeft = new Color(dataBuffInt[((yPos+y)*w+xPos)-1]);
      if(cLeft.getRed()>200 && cLeft.getRed()>200)
      {
        flag =1;
      }
    }
    if(flag==0)return true;
    else return false;
  }
  
  public boolean isAbleToGoRight(int[]dataBuffInt , int w)
  {
    flag = 0;
    for(int y=0 ; y<WIDTH ; y++)
    {
      Color cRight = new Color(dataBuffInt[((yPos+y)*w+xPos)+WIDTH]);
      if(cRight.getBlue()>200 && cRight.getRed()>200)
      {
        flag =1;
      }
    }
    if(flag==0)return true;
    else return false;
  }
  
  public void powerUpDecisions(int[]dataBuffInt , int w ,Ellipse2D.Double pacmanBounds)
  {
    Rectangle2D.Double upperBound = new Rectangle2D.Double(xPos, yPos-83,WIDTH,80);
    Rectangle2D.Double rightBound = new Rectangle2D.Double((xPos+WIDTH)+3, yPos,80,WIDTH);
    Rectangle2D.Double lowerBound = new Rectangle2D.Double(xPos, (yPos+WIDTH)+3,WIDTH,80);
    Rectangle2D.Double leftBound = new Rectangle2D.Double(xPos-83, yPos,80,WIDTH);
    
    if(pacmanBounds.intersects(upperBound) && isAbleToGoDown(dataBuffInt,w))
    {
      down();
    }
    else if(pacmanBounds.intersects(rightBound) && isAbleToGoLeft(dataBuffInt,w))
    {
      left();
    }
    else if(pacmanBounds.intersects(lowerBound) && isAbleToGoUp(dataBuffInt,w))
    {
      up();
    }
    else if(pacmanBounds.intersects(leftBound) && isAbleToGoRight(dataBuffInt,w))
    {
      right();
    }
    else makeRandomDecision(dataBuffInt , w);
  }

  public void makeRandomDecision(int[]dataBuffInt , int w)
  {
    boolean moveMade = false;
    while(!moveMade)
    {
      int randomDirection = (int)(Math.random()*5) +0;
      if(randomDirection ==1 && isAbleToGoUp(dataBuffInt,w))
      {
        moveMade = true;
        up();
      }
      else if(randomDirection ==2 && isAbleToGoRight(dataBuffInt,w))
      {
        moveMade = true;
        right();
      }
      else if(randomDirection ==3 && isAbleToGoDown(dataBuffInt,w))
      {
        moveMade = true;
        down();
      }
      else if(randomDirection >3 && isAbleToGoLeft(dataBuffInt,w))
      {
        moveMade = true;
        left();
      }
    }
  }
  
  public void makeRandomDecision2(int[]dataBuffInt , int w , int pacmanXPos , int pacmanYPos)
  {
    boolean goUp = false , goDown = false , goLeft = false , goRight = false;
    if(pacmanXPos < xPos && isAbleToGoLeft(dataBuffInt,w)) goLeft = true;
    else if (pacmanXPos > xPos && isAbleToGoRight(dataBuffInt,w)) goRight = true;
    if(pacmanYPos < yPos && isAbleToGoUp(dataBuffInt,w)) goUp = true;
    else if(pacmanYPos > yPos && isAbleToGoDown(dataBuffInt,w)) goDown = true;
    if((goUp || goDown) && (goRight || goLeft))
    {
      int randomDirection = (int)(Math.random()*2) +0;
      if(randomDirection == 1)
      {
        if(goUp && isAbleToGoUp(dataBuffInt,w)) up();
        else if(goDown && isAbleToGoDown(dataBuffInt,w)) down();
      }
      else if(randomDirection == 2)
      {
        if(goRight && isAbleToGoRight(dataBuffInt,w)) right();
        else if(goLeft && isAbleToGoLeft(dataBuffInt,w)) left();
      }
    }
    else makeRandomDecision(dataBuffInt,w);
  }
  
  public Rectangle2D.Double getGhostBounds(){return new Rectangle2D.Double(xPos+(WIDTH/2), yPos+(WIDTH/2),2,2);}
  public void enablePowerUp(){powerUp = true;}
  public void disablePowerUp(){powerUp = false;}
  public boolean isAPowerUp(){return powerUp;}
  public int getXPos(){return xPos;}
  public int getYPos(){return yPos;}
  public void isDead()
  {
    dead=true;
    frameX=0;
  }
  public void setFrame(int x){frameX=x;}
  public boolean died() {return dead;}
}