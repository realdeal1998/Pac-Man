import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

class PointsObject extends GameObject
{
  protected boolean redeemed = false;
  protected int width = 5;
  
  public void setPosition(int x , int y)
  {
    xPos = x;
    yPos = y;
  }
  
  public void doAnimation()
  {
    if(delay>40)delay=0;
    if(powerUp)delay++;
  }

  public void makeObject(Graphics g , ImageObserver observer , int powerUpTimer)
  {
    g.setColor(Color.WHITE);
    if(!powerUp)g.fillRect(xPos-(width/2), yPos-(width/2), width, width);
    else if(delay<=20 && powerUp)g.fillOval(xPos-(width/2), yPos-(width/2), width, width);
  }
  
  public void makeItAPowerUp()
  {
    width = 20;
    powerUp = true;
  }
  
  public void redeemIt(){redeemed = true;}
  public boolean isItRedeemed(){return redeemed;}
  public Rectangle2D.Double getBounds(){return new Rectangle2D.Double(xPos, yPos,width,width);}
}