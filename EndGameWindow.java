package checkers;

public class EndGameWindow extends javax.swing.JFrame
{
    public EndGameWindow()
    {
        this.initComponents();
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jLabelWinner = new javax.swing.JLabel();
        jButtonNewGame = new javax.swing.JButton();
        jButtonEndGame = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        jLabel1.setText("Vyhrál:");

        jLabelWinner.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelWinner.setText("Černý");

        jButtonNewGame.setText("Restartovat hru");
        jButtonNewGame.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonNewGameActionPerformed(evt);
            }
        });

        jButtonEndGame.setText("Ukončit hru");
        jButtonEndGame.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonEndGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNewGame, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelWinner)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButtonEndGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelWinner))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonNewGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEndGame)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewGameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonNewGameActionPerformed
    {//GEN-HEADEREND:event_jButtonNewGameActionPerformed
        Game.get().newGame();
        this.dispose();
    }//GEN-LAST:event_jButtonNewGameActionPerformed

    private void jButtonEndGameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonEndGameActionPerformed
    {//GEN-HEADEREND:event_jButtonEndGameActionPerformed
        System.exit( 0 );
    }//GEN-LAST:event_jButtonEndGameActionPerformed

    public void setWinner( String text )
    {
        this.jLabelWinner.setText( text );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEndGame;
    private javax.swing.JButton jButtonNewGame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelWinner;
    // End of variables declaration//GEN-END:variables
}
