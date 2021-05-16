package checkers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JPanel;

public class View extends JPanel implements PropertyChangeListener
{
    private static final int PIECE_SIZE = 40;

    private final PropertyChangeSupport propertyChangeSupport;
    private final Dimension size;

    public View()
    {
        this.propertyChangeSupport = new PropertyChangeSupport( this );
        this.size = new Dimension( PIECE_SIZE * 8, PIECE_SIZE * 8 );

        this.bindMouseEvents();

        this.addEventListener( Game.get() );
        Game.get().addEventListener( this );
    }

    private void bindMouseEvents()
    {
        this.addMouseListener( new MouseAdapter()
        {
            @Override
            public void mousePressed( MouseEvent e )
            {
                super.mousePressed( e );

                if( Game.get().isGameInProgress() )
                {
                    int row = e.getX() / PIECE_SIZE;
                    int col = e.getY() / PIECE_SIZE;
                    propertyChangeSupport.firePropertyChange( "click", null, new Point( col, row ) );
                }
            }
        } );
    }

    @Override
    public Dimension getPreferredSize()
    {
        return this.size;
    }

    @Override
    public Dimension getMinimumSize()
    {
        return this.getPreferredSize();
    }

    @Override
    public void paint( Graphics g )
    {
        super.paint( g );

        int padding = PIECE_SIZE / 10 * 2;
        for( int row = 0; row < 8; row++ )
        {
            for( int col = 0; col < 8; col++ )
            {
                g.setColor( row % 2 == col % 2 ? Color.GRAY : Color.LIGHT_GRAY );
                g.fillRect( col * PIECE_SIZE, row * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE );

                BoardBox piece = Game.get().getPiece( row, col );
                switch( piece )
                {
                    case WHITE:
                        g.setColor( Color.WHITE );
                        g.fillOval( PIECE_SIZE / 10 + col * PIECE_SIZE, 2 + row * PIECE_SIZE, PIECE_SIZE - padding, PIECE_SIZE - padding );
                        break;
                    case BLACK:
                        g.setColor( Color.BLACK );
                        g.fillOval( PIECE_SIZE / 10 + col * PIECE_SIZE, 2 + row * PIECE_SIZE, PIECE_SIZE - padding, PIECE_SIZE - padding );
                        break;
                    case WHITE_KING:
                        g.setColor( Color.WHITE );
                        g.fillOval( PIECE_SIZE / 10 + col * PIECE_SIZE, 2 + row * PIECE_SIZE, PIECE_SIZE - padding, PIECE_SIZE - padding );
                        g.setColor( Color.BLACK );
                        g.drawString( "K", PIECE_SIZE / 2 - PIECE_SIZE / 10 + col * PIECE_SIZE, PIECE_SIZE / 2 + PIECE_SIZE / ( PIECE_SIZE / 2 ) + row * PIECE_SIZE );
                        break;
                    case BLACK_KING:
                        g.setColor( Color.BLACK );
                        g.fillOval( PIECE_SIZE / 10 + col * PIECE_SIZE, 2 + row * PIECE_SIZE, PIECE_SIZE - padding, PIECE_SIZE - padding );
                        g.setColor( Color.WHITE );
                        g.drawString( "K", PIECE_SIZE / 2 - PIECE_SIZE / 10 + col * PIECE_SIZE, PIECE_SIZE / 2 + PIECE_SIZE / ( PIECE_SIZE / 2 ) + row * PIECE_SIZE );
                        break;
                }
            }
        }

        if( Game.get().getLegalMoves() != null )
        {
            for( Move move : Game.get().getLegalMoves() )
            {
                g.setColor( new Color( 0, 177, 0, 16 ) );
                g.fillRect( move.y2 * PIECE_SIZE, move.x2 * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE );
                g.setColor( new Color( 0, 177, 0 ) );
                g.drawRect( move.y2 * PIECE_SIZE, move.x2 * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE );
            }
        }
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
        if( evt.getPropertyName().equals( "repaint" ) )
        {
            this.repaint();
        }
    }
}
