import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;

public class PacmanObject extends GameObject
{
  BufferedImage pacmanImage = null , pacmanDyingImage = null;
  
  public PacmanObject(int x,int y)
  {
    try
    {
      pacmanImage = ImageIO.read(new File("pacmanSpriteSheetbeta.png"));
      pacmanDyingImage = ImageIO.read(new File("pacmanDyingSpriteSheet.png"));
    }
    catch(IOException e){}
    xPos = x;
    yPos = y;
    xSpeed = 0;
    ySpeed = 0;
    dead = false;
  }
  
  @Override
  public void doAnimation()
  {
    delay++;
    if(frameX>120)
    {
      frameX=0;
    }
    else if(delay%8==0 && !dontAnimate) frameX+=42;
  }
  
  
  
  public void checkCollisions(int[]dataBuffInt , int w)
  {
    for(int y=0 ; y<WIDTH ; y++)
    {
      Color cUp = new Color(dataBuffInt[((yPos-1)*w+xPos)+y]);
      Color cDown = new Color(dataBuffInt[((yPos+WIDTH)*w+xPos)+y]);
      Color cLeft = new Color(dataBuffInt[((yPos+y)*w+xPos-1)]);
      Color cRight = new Color(dataBuffInt[((yPos+y)*w+xPos)+WIDTH]);
      if(ySpeed >0 && cDown.getRed()>240)
      {
        ySpeed=0;
        dontAnimate = true;
      }
      else if(ySpeed <0 && cUp.getRed()>240)
      {
        ySpeed=0;
        dontAnimate = true;
      }
      if(xSpeed >0 && cRight.getRed()>240)
      {
        xSpeed=0;
        dontAnimate = true;
      }
      else if(xSpeed <0 && cLeft.getRed()>240)
      {
        xSpeed=0;
        dontAnimate = true;
      }
    }
    move();
  } 
  
  public void doDyingAnimation()
  {
    delay++;
    if(delay%16==0 && frameX!=506) frameX+=42;
  }
  
  public Ellipse2D.Double getBounds()
  {
    return new Ellipse2D.Double(xPos+(WIDTH/2) , yPos +(WIDTH/2) , (WIDTH/6) , (WIDTH/6));
  }
  
  public void makeObject(Graphics g , ImageObserver observer , int ghostKilled)
  {
    if(ghostKilled>0); 
    else if(dead) g.drawImage(pacmanDyingImage,xPos, yPos, xPos+WIDTH, yPos+WIDTH ,frameX,0,frameX+WIDTH , 0+WIDTH,observer);
    else g.drawImage(pacmanImage,xPos, yPos, xPos+WIDTH, yPos+WIDTH ,frameX,frameY,frameX+WIDTH , frameY+WIDTH,observer); 
      //g.fillRect(xPos,yPos,42,42);
  }
  public void makeStartUpObject(Graphics g , ImageObserver observer)
  {
      g.drawImage(pacmanImage,xPos, yPos, xPos+WIDTH+50, yPos+WIDTH+50 ,frameX,frameY,frameX+WIDTH , frameY+WIDTH,observer); 
  }
}