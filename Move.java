package checkers;

public class Move
{
    public int x1, y1;
    public int x2, y2;
    public int xJump, yJump;

    public Move( int x1, int y1, int x2, int y2 )
    {
        this( x1, y1, x2, y2, -1, -1 );
    }

    public Move( int x1, int y1, int x2, int y2, int xJump, int yJump )
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.xJump = xJump;
        this.yJump = yJump;
    }
    
    public boolean isJump()
    {
        return xJump != -1 && yJump != -1;
    }
}
