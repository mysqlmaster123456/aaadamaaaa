package checkers;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Game implements PropertyChangeListener
{
    private static Game instance = null;

    private final PropertyChangeSupport propertyChangeSupport;
    private BoardBox[][] board;
    private ArrayList<Move> allLegalMoves;
    private ArrayList<Move> legalMoves;
    private boolean gameInProgress = false;
    private BoardPlayer player;

    private Game()
    {
        this.propertyChangeSupport = new PropertyChangeSupport( this );
        this.board = new BoardBox[ 8 ][ 8 ];
    }

    public static Game get()
    {
        if( instance == null )
        {
            instance = new Game();
        }

        return instance;
    }

    public void newGame()
    {
        for( int i = 0; i < 8; i++ )
        {
            for( int j = 0; j < 8; j++ )
            {
                if( i % 2 == j % 2 )
                {
                    if( i < 3 )
                    {
                        this.setPiece( i, j, BoardBox.BLACK );
                    }
                    else if( i > 4 )
                    {
                        this.setPiece( i, j, BoardBox.WHITE );
                    }
                    else
                    {
                        this.setPiece( i, j, BoardBox.EMPTY );
                    }
                }
                else
                {
                    this.setPiece( i, j, BoardBox.EMPTY );
                }
            }
        }

        this.setPlayer( BoardPlayer.WHITE );
        this.gameInProgress = true;
        this.repaint();
    }
    
    public void endGame( BoardPlayer winner )
    {
        EndGameWindow window = new EndGameWindow();
        window.setWinner( winner == BoardPlayer.BLACK ? "Černý" : "Bílý" );
        window.setVisible( true );
        this.gameInProgress = false;
    }

    public boolean isGameInProgress()
    {
        return this.gameInProgress;
    }

    public BoardPlayer getPlayer()
    {
        return this.player;
    }

    private void setPlayer( BoardPlayer player )
    {
        BoardPlayer temp = this.getPlayer();
        this.player = player;
        this.propertyChangeSupport.firePropertyChange( "player", temp, player );
        
        this.setAllLegalMoves( this.getLegalMoves( this.getPlayer() ) );
        this.setLegalMoves( null );
        
        if( this.getAllLegalMoves().isEmpty() )
        {
            this.endGame( temp );
        }
    }

    public ArrayList<Move> getAllLegalMoves()
    {
        return this.allLegalMoves;
    }

    private void setAllLegalMoves( ArrayList<Move> allLegalMoves )
    {
        this.allLegalMoves = allLegalMoves;
    }

    public ArrayList<Move> getLegalMoves()
    {
        return this.legalMoves;
    }

    private void setLegalMoves( ArrayList<Move> legalMoves )
    {
        this.legalMoves = legalMoves;
    }

    public BoardBox getPiece( int x, int y )
    {
        return this.board[ x ][ y ];
    }

    public void setPiece( int x, int y, BoardBox piece )
    {
        this.board[ x ][ y ] = piece;
    }

    private void makeMove( Move move )
    {
        this.setPiece( move.x2, move.y2, this.getPiece( move.x1, move.y1 ) );
        this.setPiece( move.x1, move.y1, BoardBox.EMPTY );
        if( move.isJump() )
        {
            this.setPiece( move.xJump, move.yJump, BoardBox.EMPTY );
        }

        if( move.x2 == 0 && this.getPiece( move.x2, move.y2 ) == BoardBox.WHITE )
        {
            this.setPiece( move.x2, move.y2, BoardBox.WHITE_KING );
        }
        if( move.x2 == 7 && this.getPiece( move.x2, move.y2 ) == BoardBox.BLACK )
        {
            this.setPiece( move.x2, move.y2, BoardBox.BLACK_KING );
        }

        this.setPlayer( this.getPlayer() == BoardPlayer.WHITE ? BoardPlayer.BLACK : BoardPlayer.WHITE );
        this.repaint();
    }

    private ArrayList<Move> getLegalMoves( BoardPlayer player )
    {
        ArrayList<Move> moves = this.getLegalJumps( player );

        if( moves.isEmpty() )
        {
            for( int i = 0; i < 8; i++ )
            {
                for( int j = 0; j < 8; j++ )
                {
                    moves.addAll( this.getLegalMovesFrom( player, i, j ) );
                }
            }
        }

        return moves;
    }

    public ArrayList<Move> getLegalMovesFrom( BoardPlayer player, int x, int y )
    {
        ArrayList<Move> jumps = this.getLegalJumpsFrom( player, x, y );
        if( !jumps.isEmpty() )
        {
            return jumps;
        }

        // The user can move with other pieces, therefore, this one cannot be moved
        ArrayList<Move> allJumps = this.getLegalJumps( player );
        if( !allJumps.isEmpty() )
        {
            return null;
        }

        BoardBox man = BoardBox.BLACK;
        BoardBox king = BoardBox.BLACK_KING;
        if( player == BoardPlayer.WHITE )
        {
            man = BoardBox.WHITE;
            king = BoardBox.WHITE_KING;
        }

        ArrayList<Move> moves = new ArrayList<>();

        BoardBox piece = this.getPiece( x, y );
        int timesCheck = 0;
        if( piece == man )
        {
            timesCheck = 1;
        }
        else if( piece == king )
        {
            timesCheck = 7;
        }
        
        this.rayLegalMoves( moves, timesCheck, player, x, y, 1, 1 );
        this.rayLegalMoves( moves, timesCheck, player, x, y, -1, 1 );
        this.rayLegalMoves( moves, timesCheck, player, x, y, 1, -1 );
        this.rayLegalMoves( moves, timesCheck, player, x, y, -1, -1 );

        return moves;
    }
    
    private void rayLegalMoves( ArrayList<Move> moves, int timesCheck, BoardPlayer player, int x, int y, int xAdd, int yAdd )
    {
        for( int i = 1; i <= timesCheck; i++ )
        {
            int x2 = x + xAdd * i;
            int y2 = y + yAdd * i;
            if( x2 < 0 || x2 >= 8 || y2 < 0 || y2 >= 8 )
            {
                continue;
            }
            
            BoardBox piece = this.getPiece( x2, y2 );
            if( piece != BoardBox.EMPTY )
            {
                return;
            }
            
            if( this.canMove( player, x, y, x2, y2 ) )
            {
                moves.add( new Move( x, y, x2, y2 ) );
            }
        }
    }

    private ArrayList<Move> getLegalJumps( BoardPlayer player )
    {
        ArrayList<Move> jumps = new ArrayList<>();

        for( int i = 0; i < 8; i++ )
        {
            for( int j = 0; j < 8; j++ )
            {
                jumps.addAll( this.getLegalJumpsFrom( player, i, j ) );
            }
        }

        return jumps;
    }

    private ArrayList<Move> getLegalJumpsFrom( BoardPlayer player, int x, int y )
    {
        BoardBox man = BoardBox.BLACK;
        BoardBox king = BoardBox.BLACK_KING;
        if( player == BoardPlayer.WHITE )
        {
            man = BoardBox.WHITE;
            king = BoardBox.WHITE_KING;
        }

        ArrayList<Move> moves = new ArrayList<>();

        BoardBox piece = this.getPiece( x, y );
        int timesCheck = 0;
        if( piece == man )
        {
            timesCheck = 2;
        }
        else if( piece == king )
        {
            timesCheck = 7;
        }
        
        for( int i = 2; i <= timesCheck; i++ )
        {
            if( this.canJump( player, x, y, x + ( i - 1 ), y + ( i - 1 ), x + i, y + i ) )
            {
                moves.add( new Move( x, y, x + i, y + i, x + ( i - 1 ), y + ( i - 1 ) ) );
            }
            if( this.canJump( player, x, y, x + ( i - 1 ), y - ( i - 1 ), x + i, y - i ) )
            {
                moves.add( new Move( x, y, x + i, y - i, x + ( i - 1 ), y - ( i - 1 ) ) );
            }
            if( this.canJump( player, x, y, x - ( i - 1 ), y + ( i - 1 ), x - i, y + i ) )
            {
                moves.add( new Move( x, y, x - i, y + i, x - ( i - 1 ), y + ( i - 1 ) ) );
            }
            if( this.canJump( player, x, y, x - ( i - 1 ), y - ( i - 1 ), x - i, y - i ) )
            {
                moves.add( new Move( x, y, x - i, y - i, x - ( i - 1 ), y - ( i - 1 ) ) );
            }
        }

        return moves;
    }

    private boolean canJump( BoardPlayer player, int x1, int y1, int x2, int y2, int x3, int y3 )
    {
        if( x3 < 0 || x3 >= 8 || y3 < 0 || y3 >= 8 )
        {
            return false;
        }

        BoardBox nextPiece = this.getPiece( x3, y3 );
        if( nextPiece != BoardBox.EMPTY )
        {
            return false;
        }

        BoardBox piece = this.getPiece( x1, y1 );
        BoardBox middlePiece = this.getPiece( x2, y2 );
        if( player == BoardPlayer.WHITE )
        {
            if( piece == BoardBox.WHITE && x3 > x1 )
            {
                return false;
            }
            if( middlePiece != BoardBox.BLACK && middlePiece != BoardBox.BLACK_KING )
            {
                return false;
            }
        }
        if( player == BoardPlayer.BLACK )
        {
            if( piece == BoardBox.BLACK && x3 < x1 )
            {
                return false;
            }
            if( middlePiece != BoardBox.WHITE && middlePiece != BoardBox.WHITE_KING )
            {
                return false;
            }
        }

        return true;
    }

    private boolean canMove( BoardPlayer player, int x1, int y1, int x2, int y2 )
    {
        if( x2 < 0 || x2 >= 8 || y2 < 0 || y2 >= 8 || ( x1 == x2 && y1 == y2 ) )
        {
            return false;
        }

        BoardBox nextPiece = this.getPiece( x2, y2 );
        if( nextPiece != BoardBox.EMPTY )
        {
            return false;
        }

        BoardBox piece = this.getPiece( x1, y1 );
        if( player == BoardPlayer.WHITE && piece == BoardBox.WHITE && x2 > x1 )
        {
            return false;
        }
        if( player == BoardPlayer.BLACK && piece == BoardBox.BLACK && x2 < x1 )
        {
            return false;
        }

        return true;
    }

    public void addEventListener( PropertyChangeListener listener )
    {
        this.propertyChangeSupport.addPropertyChangeListener( listener );
    }

    public void removeEventListener( PropertyChangeListener listener )
    {
        this.propertyChangeSupport.removePropertyChangeListener( listener );
    }

    @Override
    public void propertyChange( PropertyChangeEvent evt )
    {
        if( evt.getPropertyName().equals( "click" ) )
        {
            Point point = (Point) evt.getNewValue();

            boolean actionTaken = false;
            if( this.getLegalMoves() != null && !this.getLegalMoves().isEmpty() )
            {
                for( Move move : this.getLegalMoves() )
                {
                    if( move.x2 == point.x && move.y2 == point.y )
                    {
                        this.makeMove( move );
                        actionTaken = true;
                    }
                }
            }

            if( !actionTaken )
            {
                this.setLegalMoves( this.getLegalMovesFrom( this.getPlayer(), point.x, point.y ) );
                this.repaint();
            }
        }
    }

    public void repaint()
    {
        this.propertyChangeSupport.firePropertyChange( "repaint", false, true );
    }
}
