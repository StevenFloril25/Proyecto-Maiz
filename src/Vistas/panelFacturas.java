package Vistas;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import Modelos.*;
import Controladores.*;
import java.io.IOException;
import javax.swing.table.TableModel;

/**
 * Panel de Facturas
 *
 * @author Ashley Malavé y William Miranda
 * @date 21/07/2024
 * @version 1.0
 */
public class panelFacturas extends javax.swing.JPanel {

    cFactura ListF = new cFactura();
    cCliente ListC = new cCliente();
    cDetalle_Factura ListFD = new cDetalle_Factura();
    cLibros ListL = new cLibros();

    String clave = "";

    public static DefaultTableModel modelo2;

    public panelFacturas() {
        initComponents();
         modelo2 = new DefaultTableModel();
        modelo2.addColumn("Código");
        modelo2.addColumn("Libro");
        modelo2.addColumn("Precio");

        Tabla_Lis.setModel(modelo2);
        //cargar datos de los archivos
        try {
            ListF.leer();
            ListC.leer();
            ListFD.leer();
            ListL.leer();
            if (ListF.Count() > 0) {
                ver_registro(0);
            }
        } catch (Exception ex) {
        }
    }

    public void guardar() {
        Factura ob = leer();
        try {
            if (ob != null) {
                if (clave.equals("")) {//guardar un nuevo estudiante
                    ListF.nuevo(ob);
                } else {//guardar datos de estudiante editado
                    ListF.modificar(ob, clave);
                }
                frmPrincipal.lbMensaje.setText("Registro guardado exitosamente");
                ListF.guardar(); //guarda en el archivo csv

                //deshabilitar textos
                habilitar_textos(false);
                //habilitar botones
                habilitar_botones(true);
            }
        } catch (IOException ex) {
            frmPrincipal.lbMensaje.setText(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public final void ver_registro(int pos) {
        if (pos >= 0 && pos < ListF.Count()) {
            Factura ob = ListF.getFactura(pos);
            //N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
            txtn_fac.setText(ob.N_fac);
            txtfecha.setText(ob.Fecha);
            txtCedula.setText(ob.Cd_cliente);

            txtsubt.setText(ob.Subtotal + "");
            txtdesc.setText(ob.Descuento + "");
            txtiva.setText(ob.Iva + "");
            txttotal.setText(ob.Total + "");

        }
    }

    public Factura leer() {
        Factura ob = null;
        if (form_validado()) {
            ob = new Factura();
            //Nac,Fecha,_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
            ob.N_fac = txtn_fac.getText();
            ob.Fecha = txtfecha.getText();
            ob.Cd_cliente = txtCedula.getText();
            ob.Subtotal = Double.valueOf(txtsubt.getText());
            ob.Descuento = Double.valueOf(txtdesc.getText());
            ob.Iva = Double.valueOf(txtiva.getText());
            ob.Total = Double.valueOf(txttotal.getText());
            System.out.print(ob.Ver());
        }
        return ob;
    }
    
    /*public Detalle_Factura leerDF() {
        Detalle_Factura ob = null;
        if (form_validado()) {
            ob = new Detalle_Factura();
             //id_detalle N_fac Codigo Cantidad Precio Importe
          for (int fila = 0; fila < Tabla_DF.getRowCount(); fila++) {
            ob.N_fac = txtfecha.getText();
            ob.Codigo = txtCedula.getText();
            ob.Cantidad = Double.valueOf(txtsubt.getText());
            ob.Precio = Double.valueOf(txtdesc.getText());
            ob.Importe = Double.valueOf(Tabla_DF.getValueAt(fila, 3);
            System.out.print(ob.Ver());
        }
        }
        return ob;
    }/*

    /*
     * Validar formulario
     */
    public boolean form_validado() {
        boolean ok = true;
        String men = "Campos con errores";
        //validar requerido
        //N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
        if (!Validaciones.esRequerido(txtn_fac)) {
            ok = false;
            men += ", No. Fac";
        }

        if (!Validaciones.esRequerido(txtfecha)) {
            ok = false;
            men += ", Fecha";
        }

        if (!Validaciones.esCedula(txtCedula)) {
            ok = false;
            men += ", Cedula";
        }

        if (!ok) {
            frmPrincipal.lbMensaje.setText(men);
        } else {
            frmPrincipal.lbMensaje.setText("");
        }
        //validar más controles
        return ok;
    }

    /*
     * Metodo para limpiar cajas de texto
     */
    public void limpiar_textos() {
        //Nac,Fecha,_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total

        txtn_fac.setText("");
        txtfecha.setText("");
        txtCedula.setText("");
        txtsubt.setText("");
        txtdesc.setText("");
        txtiva.setText("");
        txttotal.setText("");
        txtcliente.setText("");
        txtdirecc.setText("");
        DefaultTableModel modelo = (DefaultTableModel) Tabla_DF.getModel();
        modelo.setRowCount(0);
        txttel.setText("");
        txtn_fac.requestFocus();  //envia enfoque a la caja de texto numero de factura
        frmPrincipal.lbMensaje.setText("");
    }

    /*
     * quitar validaciones
     */
    public void quitar_validaciones() {
        Validaciones.pinta_text(txtn_fac);
        Validaciones.pinta_text(txtfecha);
        Validaciones.pinta_text(txtCedula);
        Validaciones.pinta_text(txtsubt);
        Validaciones.pinta_text(txtdesc);
        Validaciones.pinta_text(txtiva);
        Validaciones.pinta_text(txttotal);
        frmPrincipal.lbMensaje.setText("");
    }

    /*
     * Buscar datos segun cedula
     */
    public void buscar() {
        try {
            int pos = ListF.localizar(txtCedula.getText());
            if (pos > -1) {
                ver_registro(pos);
            } else {
                frmPrincipal.lbMensaje.setText("Registro no encontrado");
            }
        } catch (Exception ex) {
            frmPrincipal.lbMensaje.setText(ex.getMessage());
        }
    }

    public void anular() {
        try {
            ListF.anular(txtn_fac.getText());
            ListF.guardar();
            frmPrincipal.lbMensaje.setText("Registro eliminado");
        } catch (IOException ex) {
            frmPrincipal.lbMensaje.setText(ex.getMessage());
        }
    }

    public void calcular() {
        try {

            for (int fila = 0; fila < Tabla_DF.getRowCount(); fila++) {
                double can = Double.valueOf(Tabla_DF.getValueAt(fila, 2).toString());
                double pre = Double.valueOf(Tabla_DF.getValueAt(fila, 3).toString());

                double imp = can * pre;
                Tabla_DF.setValueAt(imp, fila, 4);
            }
        } catch (Exception ex) {
        }
    }

    public void calcular2() {
        try {
            double subt = 0;
            double p = Double.valueOf(txtporc.getText());
            for (int fila = 0; fila < Tabla_DF.getRowCount(); fila++) {
                subt += Double.valueOf(Tabla_DF.getValueAt(fila, 4).toString());

            }
            double iva = subt * 0.15;
            double desc = (subt + iva) * (p / 100);
            double total = subt + iva - desc;
            txtsubt.setText(subt + "");
            txtiva.setText(iva + "");
            txtdesc.setText(desc + "");
            txttotal.setText(total + "");
            leer();
        } catch (Exception ex) {
        }

    }

    public void eliminarFilaSeleccionada() {

        int filaSeleccionada = Tabla_DF.getSelectedRow();

        if (filaSeleccionada != -1) {

            DefaultTableModel model = (DefaultTableModel) Tabla_DF.getModel();

            model.removeRow(filaSeleccionada);
        } else {
            System.out.println("¡Error!, Selecionar fila a elimanar");
        }
    }

    /*
     * Habilitar o desabilitar textos
     */
    public final void habilitar_textos(Boolean ok) {

        txtCedula.setEditable(ok);
        txtn_fac.setEditable(ok);
        txtfecha.setEditable(ok);
        txtsubt.setEditable(ok);
        txtdesc.setEditable(ok);
        txtiva.setEditable(ok);
        txttotal.setEditable(ok);
    }

    /*
     * Habilitar o desabilitar botones
     */
    public final void habilitar_botones(Boolean ok) {
        btnnuevo.setEnabled(ok);

        btnbuscar.setEnabled(ok);
        btnanular.setEnabled(!ok);

        //hacen lo contrario de los otros botones
        btnguardar.setEnabled(!ok);

    }

    public void buscarCliente() {

        try {
            ListC.leer();
            String ced = txtCedula.getText();
            int pos = ListC.localizar(ced);
            Cliente c = ListC.getCliente(pos);

            txtcliente.setText(c.Nombre + c.Apellido);
            txtdirecc.setText(c.Direccion);
            txttel.setText(c.Telefono);

        } catch (Exception ex) {

        }
    }

    public void buscarFactura() {
        try {
            ListF.leer();
            String n_fac = txtn_fac.getText();
            int pos = ListF.localizar(n_fac);
            Factura f = ListF.getFactura(pos);
//N_fac,Fecha,Cd_cliente,Subtotal,Descuento,Iva,Total
            txtfecha.setText(f.Fecha);
            txtCedula.setText(f.Cd_cliente);
            txtsubt.setText(f.Subtotal + "");
            txtdesc.setText(f.Descuento + "");
            txtiva.setText(f.Iva + "");
            txttotal.setText(f.Total + "");

        } catch (Exception ex) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnnuevo = new javax.swing.JButton();
        btnbuscar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtn_fac = new javax.swing.JTextField();
        txtfecha = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtcliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtdirecc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_DF = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtsubt = new javax.swing.JTextField();
        txtdesc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtiva = new javax.swing.JTextField();
        txttotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtporc = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_Lis = new javax.swing.JTable();
        btncalcular = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(135, 68, 33));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gestión de Facturas");
        jLabel1.setPreferredSize(new java.awt.Dimension(190, 50));
        panelTitulo.add(jLabel1);

        add(panelTitulo, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(227, 215, 204));

        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Nueva factura.png"))); // NOI18N
        btnnuevo.setBorder(null);
        btnnuevo.setContentAreaFilled(false);
        btnnuevo.setFocusable(false);
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Nueva factura_claro.png"))); // NOI18N
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnnuevo);

        btnbuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/factura_buscar.png"))); // NOI18N
        btnbuscar.setBorder(null);
        btnbuscar.setContentAreaFilled(false);
        btnbuscar.setFocusable(false);
        btnbuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnbuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/factura_buscar_brillo.png"))); // NOI18N
        btnbuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnbuscar);

        btnanular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/anular_factura.png"))); // NOI18N
        btnanular.setBorder(null);
        btnanular.setContentAreaFilled(false);
        btnanular.setFocusable(false);
        btnanular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanular.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/anular_factura_claro.png"))); // NOI18N
        btnanular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });
        jToolBar1.add(btnanular);

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Guardar.png"))); // NOI18N
        btnguardar.setBorder(null);
        btnguardar.setContentAreaFilled(false);
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Guardar_claro.png"))); // NOI18N
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnguardar);

        jPanel1.setBackground(new java.awt.Color(227, 215, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2), "Datos de la Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel4.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nº Factura:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Fecha de emisión:");

        txtn_fac.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtn_fac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtn_facKeyReleased(evt);
            }
        });

        txtfecha.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtfecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtn_fac, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(239, 239, 239))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtn_fac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2), "Datos del Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel3.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Cédula:");

        txtCedula.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCedulaKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Cliente:");

        txtcliente.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Dirección:");

        txtdirecc.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Teléfono:");

        txttel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(37, 37, 37)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtdirecc, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtdirecc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2), "Registro de compras", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel5.setOpaque(false);

        Tabla_DF.setBackground(new java.awt.Color(241, 236, 231));
        Tabla_DF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Libro", "Cantidad", "Precio", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla_DF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_DFMouseClicked(evt);
            }
        });
        Tabla_DF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_DFKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_DF);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Subtotal:");

        txtsubt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtdesc.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Descuento:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Iva:");

        txtiva.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txttotal.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Total:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("%");

        txtporc.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtporc.setText("10");
        txtporc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtporcActionPerformed(evt);
            }
        });

        Tabla_Lis.setBackground(new java.awt.Color(241, 236, 231));
        Tabla_Lis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Libro", "Precio"
            }
        ));
        jScrollPane2.setViewportView(Tabla_Lis);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txtporc, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(48, 48, 48)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsubt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(105, 105, 105)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(36, 36, 36)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(48, 48, 48)
                        .addComponent(txtiva, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtsubt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtiva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtporc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btncalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Contabilidad.png"))); // NOI18N
        btncalcular.setBorder(null);
        btncalcular.setContentAreaFilled(false);
        btncalcular.setFocusable(false);
        btncalcular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncalcular.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Contabilidad_claro.png"))); // NOI18N
        btncalcular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Eliminar libro.png"))); // NOI18N
        btneliminar.setBorder(null);
        btneliminar.setContentAreaFilled(false);
        btneliminar.setFocusable(false);
        btneliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Eliminar libro_claro.png"))); // NOI18N
        btneliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnListar.setBackground(new java.awt.Color(227, 215, 204));
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/negocio_oscuro.png"))); // NOI18N
        btnListar.setBorder(null);
        btnListar.setContentAreaFilled(false);
        btnListar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnListar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/negocio.png"))); // NOI18N
        btnListar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnListar)
                        .addGap(194, 194, 194)
                        .addComponent(btneliminar)
                        .addGap(41, 41, 41)
                        .addComponent(btncalcular)
                        .addGap(96, 96, 96)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btncalcular)
                            .addComponent(btneliminar)
                            .addComponent(btnListar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        panelBuscar_F pr = new panelBuscar_F();
        pr.setVisible(true);
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        eliminarFilaSeleccionada();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void Tabla_DFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_DFKeyReleased
        calcular();
        calcular2();
    }//GEN-LAST:event_Tabla_DFKeyReleased

    private void txtCedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyReleased
        buscarCliente();
    }//GEN-LAST:event_txtCedulaKeyReleased

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        quitar_validaciones();
        limpiar_textos();
        clave = ""; //nuevo
        //habilitar textos
        habilitar_textos(true);
        //deshabilitar botones
        habilitar_botones(false);
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        if (!txtn_fac.getText().trim().equals("")) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminiar este registro?", "Sistema de Ventas de Libros", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                anular();
                limpiar_textos();
            }
        } else
            frmPrincipal.lbMensaje.setText("Seleccione el registro a eliminar");
    }//GEN-LAST:event_btnanularActionPerformed

    private void Tabla_DFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_DFMouseClicked
        calcular();
        calcular2();
    }//GEN-LAST:event_Tabla_DFMouseClicked

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        calcular();
        calcular2();
    }//GEN-LAST:event_btncalcularActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        guardar();
        habilitar_botones(true);
    }//GEN-LAST:event_btnguardarActionPerformed

    private void txtporcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtporcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtporcActionPerformed

    private void txtn_facKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtn_facKeyReleased
        buscarFactura();
        if (txtn_fac.getText().isEmpty()) {
            limpiar_textos();
        }
    }//GEN-LAST:event_txtn_facKeyReleased

    private void txtfechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfechaActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        TableModel modelo = Tabla_Lis.getModel();
        int fila [] =Tabla_Lis.getSelectedRows();
        Object [] row = new Object[5];

        DefaultTableModel Colocar =(DefaultTableModel)Tabla_DF.getModel();
        for (int i = 0; i < fila.length; i++) {
            row[0]=modelo.getValueAt(fila[i], 0);
            row[1]=modelo.getValueAt(fila[i], 1);
            row[3]=modelo.getValueAt(fila[i], 2);

            Colocar.addRow(row);
        }

    }//GEN-LAST:event_btnListarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_DF;
    private javax.swing.JTable Tabla_Lis;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtcliente;
    private javax.swing.JTextField txtdesc;
    private javax.swing.JTextField txtdirecc;
    private javax.swing.JTextField txtfecha;
    private javax.swing.JTextField txtiva;
    private javax.swing.JTextField txtn_fac;
    private javax.swing.JTextField txtporc;
    private javax.swing.JTextField txtsubt;
    private javax.swing.JTextField txttel;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
