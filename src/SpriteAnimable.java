import java.awt.Image;


public class SpriteAnimable extends Sprite 
{

	private Animation anim;
	
	public SpriteAnimable(Image img, int l, int h, Animation anim) 
	{
		super(img, l, h);
		this.anim = anim;
	}
	
	public SpriteAnimable(int l, int h, Animation anim) 
	{
		super(l, h);
		this.anim = anim;
	}
	
	public void setAnim(Animation anim)
	{
		this.anim = anim;
	}

}
