package llamadasalsistema;
import java.awt.*; 
import java.awt.print.*; 
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.util.StringTokenizer;

public class Dis extends javax.swing.JFrame {
    public Dis() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JImprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JtxtInformacion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JImprimir.setText("Imprimir");
        JImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JImprimirActionPerformed(evt);
            }
        });

        JtxtInformacion.setColumns(20);
        JtxtInformacion.setRows(5);
        jScrollPane1.setViewportView(JtxtInformacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JImprimir)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JImprimir))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JImprimirActionPerformed
        PaginationExample pagination = new PaginationExample(); 
        pagination.imprimirnomina();
        
    }//GEN-LAST:event_JImprimirActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dis().setVisible(true);
            }
        });
    }
    public class PaginationExample implements Printable{
       //Se obtienen las lineas de texto del JTextArea, la linea de texto finaliza cuando se encuentra el caracter de nueva linea \n
       StringTokenizer lineasdetexto = new StringTokenizer(JtxtInformacion.getText(), "\n", true);
       //Se obtiene el total de lineas de texto
       int totallineas = lineasdetexto.countTokens();

    int[] paginas;  // Arreglo de número de paginas que se necesitaran para imprimir todo el texto 

    String[] textoLineas; //Lineas de texto que se imprimiran en cada hoja

    //Metodo que se crea por default cuando una clase implementa a Printable
    public int print(Graphics g, PageFormat pf, int pageIndex)
    throws PrinterException {
        //Se establece la fuente, el tipo, el tamaño, la metrica según la fuente asignada, 
        //obtiene la altura de cada linea de texto para que todas queden iguales
        Font font = new Font("Serif", Font.PLAIN, 12);
        FontMetrics metrics = g.getFontMetrics(font);
        int altodelinea = metrics.getHeight();
        //Calcula el número de lineas por pagina y el total de paginas
        if (paginas == null) {
            initTextoLineas();
            //Calcula las lineas que le caben a cada página dividiendo la altura imprimible entre la altura de la linea de texto
            int lineasPorPagina = (int)(pf.getImageableHeight()/altodelinea);
            //Calcula el numero de páginas dividiendo el total de lineas entre el numero de lineas por página
            int numeroPaginas = (textoLineas.length-1)/lineasPorPagina;
            paginas = new int[numeroPaginas];
            for (int b=0; b<numeroPaginas; b++) {
                paginas[b] = (b+1)*lineasPorPagina; 
            }
        }
        //Si se recibe un indice de página mayor que el total de páginas calculadas entonces 
        //retorna NO_SUCH_PAGE para indicar que tal pagina no existe 
        if (pageIndex > paginas.length) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        /*Dibujamos cada línea de texto en cada página,
         * se aumenta a la posición 'y' la altura de la línea a cada línea de texto para evitar la saturación de texto 
         */

        int y = 0; 
        int start = (pageIndex == 0) ? 0 : paginas[pageIndex-1];
        int end   = (pageIndex == paginas.length) ? textoLineas.length : paginas[pageIndex];
        for (int line=start; line<end; line++) {
            y += altodelinea;
            g.drawString(textoLineas[line], 0, y);
        }
        /* Retorna PAGE_EXISTS para indicar al invocador que esta página es parte del documento impreso
         */
        return PAGE_EXISTS;
    }
    
     /* Agrega las lineas de texto al arreglo */
    public void initTextoLineas() {
        if (textoLineas == null) {
            int numLineas=totallineas;
            textoLineas = new String[numLineas];
            //Se llena el arreglo que contiene todas las lineas de texto
            while(lineasdetexto.hasMoreTokens()){
            for (int i=0;i<numLineas;i++) {
                textoLineas[i] = lineasdetexto.nextToken();
            }
            }
        }
    }
    
    //Este metodo crea un objeto Printerjob el cual es inicializado y asociado con la impresora por default
    public void imprimirnomina() {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         //Si el usuario presiona imprimir en el dialogo de impresión, 
         //entonces intenta imprimir todas las lineas de texto
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JImprimir;
    private javax.swing.JTextArea JtxtInformacion;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
