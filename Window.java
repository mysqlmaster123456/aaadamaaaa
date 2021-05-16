package checkers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Window extends javax.swing.JFrame implements PropertyChangeListener
{
    public Window()
    {
        this.initComponents();
        
        Game.get().addEventListener( this );
        Game.get().newGame();
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        view1 = new checkers.View();
        jLabel1 = new javax.swing.JLabel();
        jLabelPlayer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout view1Layout = new javax.swing.GroupLayout(view1);
        view1.setLayout(view1Layout);
        view1Layout.setHorizontalGroup(
            view1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        view1Layout.setVerticalGroup(
            view1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        jLabel1.setText("Na tahu:");

        jLabelPlayer.setText("Černý");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPlayer))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelPlayer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(view1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void propertyChange( PropertyChangeEvent evt )
    {
        if( evt.getPropertyName().equals( "player" ) )
        {
            BoardPlayer player = (BoardPlayer) evt.getNewValue();
            this.jLabelPlayer.setText( player == BoardPlayer.BLACK ? "Černý" : "Bílý" );
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelPlayer;
    private checkers.View view1;
    // End of variables declaration//GEN-END:variables
}
