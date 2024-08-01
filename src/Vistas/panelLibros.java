
package Vistas;

/**
 * Panel de Libros
 *
 * @author Steven Paul Valdiviezo Suquilanda
 * @date 22/07/2024
 * @version 1.0
 */

import Modelos.*;
import Controladores.*;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class panelLibros extends javax.swing.JPanel {
    //Importar DefaultTable

    DefaultTableModel Modelo;
    //datos globales
    public cLibros listLibros = new cLibros();
    String clave = ""; //guarda el campo clave para editar
    //*******************METODOS PERSONALIZADOS *******************

    /**
     * Leer datos del formulario y guardar en un objeto
     *
     * @return Objeto Libros
     */

    public Libros leer() {
        Libros ob = null;
        if (form_validado()) {
            ob = new Libros();
            ob.Codigo = txtCodigo.getText();
            ob.Nombre = txtNombre.getText();
            ob.Descripcion = txtDescripcion.getText();
            ob.Autor = txtAutor.getText();
            ob.Categoria = cmbCategoria.getSelectedItem().toString();
            ob.Precio = Double.valueOf(txtPrecio.getText());
            ob.Stock = Integer.valueOf(txtStock.getText());
            System.out.print(ob.Ver());
        }
        return ob;
    }

    /*
     * Validar formulario
     */
    public boolean form_validado() {
        boolean ok = true;
        String men = "Campos con errores";
        //validar requerido
        if (!Validaciones.esRequerido(txtCodigo)) {
            ok = false;
            men += ", Codigo";
        }

        if (!Validaciones.esRequerido(txtNombre)) {
            ok = false;
            men += ", Nombre";
        }

        if (!Validaciones.esRequerido(txtDescripcion)) {
            ok = false;
            men += ", Descripción";
        }
        
        if (!Validaciones.esLetras(txtAutor)) {
            ok = false;
            men += ", Autor";
        }

        if (!Validaciones.esCombobox(cmbCategoria)) {
            ok = false;
            men += ", Categoria";
        }
        
        if (!Validaciones.esFlotante(txtPrecio)) {
            ok = false;
            men += ", Precio";
        }
        
        if (!Validaciones.esNumero(txtStock)) {
            ok = false;
            men += ", Stock";
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
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtAutor.setText("");
        cmbCategoria.setSelectedIndex(0);
        txtPrecio.setText("");
        txtStock.setText("");
        txtCodigo.requestFocus();  //envia curso o enfoque a la caja de texto cedula
        frmPrincipal.lbMensaje.setText("");
    }

    /*
     * quitar validaciones
     */
    public void quitar_validaciones() {
        Validaciones.pinta_text(txtCodigo);
        Validaciones.pinta_text(txtNombre);
        Validaciones.pinta_text(txtDescripcion);
        Validaciones.pinta_text(txtAutor);
        Validaciones.pinta_text(cmbCategoria);
        Validaciones.pinta_text(txtPrecio);
        Validaciones.pinta_text(txtStock);
        frmPrincipal.lbMensaje.setText("");
    }
    
     public final void ver_registro(int pos) {
        if (pos >= 0 && pos < listLibros.Count()) {
            Libros ob = listLibros.getLibros(pos);
            txtCodigo.setText(ob.Codigo);
            txtNombre.setText(ob.Nombre);
            txtDescripcion.setText(ob.Descripcion);
            txtAutor.setText(ob.Autor);
            cmbCategoria.setSelectedItem(ob.Categoria);
            txtPrecio.setText(ob.Precio+"");
            txtStock.setText(ob.Stock+"");
        }
    }
     public void verTabla_Formulario(){
         try{
         int fila = TablaLibros.getSelectedRow();
         String codigo = TablaLibros.getValueAt(fila, 0).toString();
         int l = listLibros.localizar(codigo);
         ver_registro(l);
         } catch (Exception ex) {
            
        }
     }
      /*
     * Buscar datos segun cedula
     */
    public void buscar() {
        try {
            int pos = listLibros.localizar(txtCodigo.getText());
            if (pos > -1) {
                ver_registro(pos);
            } else {
                frmPrincipal.lbMensaje.setText("Registro no encontrado");
            }
        } catch (Exception ex) {
            frmPrincipal.lbMensaje.setText(ex.getMessage());
        }
    }

    /*
     * Guardar estudiante, cuando es nuevo o se modifica un existente
     */
    public void guardar() {
        Libros ob = leer();
        try {
            if (ob != null) {
                if (clave.equals("")) {//guardar un nuevo estudiante
                    listLibros.nuevo(ob);
                } else {//guardar datos de estudiante editado
                    listLibros.modificar(ob, clave);
                }
                frmPrincipal.lbMensaje.setText("Registro guardado exitosamente");
                listLibros.guardar(); //guarda en el archivo csv
                TablaLibros.setModel(listLibros.getTabla());
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

    /*
     * Buscar datos por criterios cedula y apellido
     */
    public void buscar_varios() {
        try {
            cLibros p = listLibros.buscar_Codigo(txtCri.getText()); //busca por cedula
            if (p.Count() == 0) {
                p = listLibros.buscar_Nombre(txtCri.getText()); //buscar por Nombre
            }
            TablaLibros.setModel(p.getTabla());
        } catch (IOException ex) {
            frmPrincipal.lbMensaje.setText(ex.getMessage());
        }
    }

    /*
     * Eliminar datos de un estudiante
     */
    public void eliminar() {
        try {
            listLibros.eliminar(txtCodigo.getText());
            listLibros.guardar();
            TablaLibros.setModel(listLibros.getTabla());
            frmPrincipal.lbMensaje.setText("Registro eliminado");
        } catch (IOException ex) {
            frmPrincipal.lbMensaje.setText(ex.getMessage());
        }
    }


    /*
     * Habilitar o desabilitar textos
     */
    public final void habilitar_textos(Boolean ok) {
        txtCodigo.setEditable(ok);
        txtNombre.setEditable(ok);
        txtDescripcion.setEditable(ok);
        txtAutor.setEditable(ok);
        cmbCategoria.setEditable(ok);
        txtPrecio.setEditable(ok);
        txtStock.setEditable(ok);
    }

    /*
     * Habilitar o desabilitar botones
     */
    public final void habilitar_botones(Boolean ok) {
        btnAgregar.setEnabled(ok);
        btnModificar.setEnabled(ok);
        //btBuscar.setEnabled(ok);
        btnEliminar.setEnabled(ok);

        //hacen lo contrario de los otros botones
        btnGuardar.setEnabled(!ok);
        btnDeshacer.setEnabled(!ok);
    }
    /**
     * Creates new form panelLibros
     */
    public panelLibros() {
        initComponents();
        try {
            //Carga los datos del archivo a memoria utilizando ArrayList
            listLibros.leer();
            //Muestra los datos en la tabla
            TablaLibros.setModel(listLibros.getTabla());
            TablaLibros.getColumnModel().getColumn(0).setPreferredWidth(10); //Cambia el tamaño de la columna codigo
            frmPrincipal.lbMensaje.setText("Carga exitosa de todos los Libros");
            //Muestra el primer registro u objeto del arreglo en el formulario
            if(listLibros.Count()>0)
                    ver_registro(0);
        } catch (IOException ex) {
            
        }
             //Importar DefaultTable
      Modelo = new DefaultTableModel();
      Modelo.addColumn("Codigo");
      Modelo.addColumn("Libro");
      Modelo.addColumn("Descripción");
      Modelo.addColumn("Autor");
      Modelo.addColumn("Categoria");
      Modelo.addColumn("Precio");
      Modelo.addColumn("Stock");
      TablaLibros.setModel(Modelo);
      
      
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
        jLabel8 = new javax.swing.JLabel();
        panelDatos = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnDeshacer = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtAutor = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        cmbCategoria = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCri = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaLibros = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAgre = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(135, 68, 33));
        panelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        panelTitulo.setPreferredSize(new java.awt.Dimension(779, 60));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("GESTIÓN DE LIBROS");
        jLabel8.setPreferredSize(new java.awt.Dimension(240, 50));
        panelTitulo.add(jLabel8);

        add(panelTitulo, java.awt.BorderLayout.PAGE_START);

        panelDatos.setBackground(new java.awt.Color(227, 215, 204));

        jToolBar1.setBackground(new java.awt.Color(227, 215, 204));
        jToolBar1.setRollover(true);

        btnAgregar.setBackground(new java.awt.Color(227, 215, 204));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Add Libro.png"))); // NOI18N
        btnAgregar.setBorder(null);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setFocusable(false);
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Add Libro_claro.png"))); // NOI18N
        btnAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAgregar);

        btnModificar.setBackground(new java.awt.Color(227, 215, 204));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Modificar libro.png"))); // NOI18N
        btnModificar.setBorder(null);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setFocusable(false);
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Modificar libro_claro.png"))); // NOI18N
        btnModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnModificar);

        btnDeshacer.setBackground(new java.awt.Color(227, 215, 204));
        btnDeshacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Deshacer.png"))); // NOI18N
        btnDeshacer.setBorder(null);
        btnDeshacer.setContentAreaFilled(false);
        btnDeshacer.setFocusable(false);
        btnDeshacer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeshacer.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Deshacer_claro.png"))); // NOI18N
        btnDeshacer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeshacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshacerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDeshacer);

        btnGuardar.setBackground(new java.awt.Color(227, 215, 204));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Guardar.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Guardar_claro.png"))); // NOI18N
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGuardar);

        btnEliminar.setBackground(new java.awt.Color(227, 215, 204));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Eliminar libro.png"))); // NOI18N
        btnEliminar.setBorder(null);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Eliminar libro_claro.png"))); // NOI18N
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEliminar);

        btnListar.setBackground(new java.awt.Color(227, 215, 204));
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Listar-ordenar.png"))); // NOI18N
        btnListar.setBorder(null);
        btnListar.setContentAreaFilled(false);
        btnListar.setFocusable(false);
        btnListar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnListar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Listar-ordenar_claro.png"))); // NOI18N
        btnListar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnListar);

        jPanel1.setBackground(new java.awt.Color(227, 215, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos del Libro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(102, 51, 0))); // NOI18N
        jPanel1.setName(""); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Codigo:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Descripción:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Categoria:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Autor:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Precio:");

        txtCodigo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtNombre.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtDescripcion.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtAutor.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtPrecio.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Novela", "Ciencia ficción", "Fantasía", "Misterio / Suspense", "Romance", "Literatura clásica", "Biografías", "Historia", "Ciencia y tecnología", "Política", "Sociedad", "Negocios", "Economía", "Infantil", "Juvenil", "Biología", "Arte", "Fotografía", "Religión", "Espiritualidad", "Salud", "Fitness", "Psicología ", "Nutrición", "Dietética", "Guía", "Aventura", "Astronomía", "Ecología", "Medio ambiente", "Comedia", "Académico", "Manual Técnico" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Stock:");

        txtStock.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAutor, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(71, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Libro a Buscar:");

        txtCri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCriKeyReleased(evt);
            }
        });

        TablaLibros.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        TablaLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Libro", "Descripción", "Autor", "Categoria", "Precio", "Stock"
            }
        ));
        TablaLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaLibrosMouseClicked(evt);
            }
        });
        TablaLibros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaLibrosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TablaLibros);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/buscar libro.png"))); // NOI18N

        btnAgre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Nueva factura.png"))); // NOI18N
        btnAgre.setBorder(null);
        btnAgre.setContentAreaFilled(false);
        btnAgre.setFocusable(false);
        btnAgre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgre.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Nueva factura_claro.png"))); // NOI18N
        btnAgre.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelDatosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCri, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 105, 105)
                                .addComponent(btnAgre))))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAgre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        add(panelDatos, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
        TablaLibros.setModel(listLibros.getTabla());
        TablaLibros.getColumnModel().getColumn(0).setPreferredWidth(10);
        //Mensaje para el usuario
        frmPrincipal.lbMensaje.setText("Carga exitosa de todos los Libros");
    }//GEN-LAST:event_btnListarActionPerformed

    private void TablaLibrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaLibrosKeyReleased
        verTabla_Formulario();
    }//GEN-LAST:event_TablaLibrosKeyReleased

    private void TablaLibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaLibrosMouseClicked
        verTabla_Formulario();
    }//GEN-LAST:event_TablaLibrosMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        quitar_validaciones();
        limpiar_textos();
        clave = ""; //nuevo
        //habilitar textos
        habilitar_textos(true);
        //deshabilitar botones
        habilitar_botones(false);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (!txtCodigo.getText().trim().equals("")) {
            clave = txtCodigo.getText().trim(); //captura la cédula antes de modificar
            txtCodigo.requestFocus();  //envia curso o enfoque a la caja de texto cedula
            frmPrincipal.lbMensaje.setText("");
            //habilitar textos
            habilitar_textos(true);
            //deshabilitar botones
            habilitar_botones(false);
            quitar_validaciones();
        } else
            frmPrincipal.lbMensaje.setText("Seleccione o busque un registro a editar");
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnDeshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshacerActionPerformed
        //quitar validaciones
        quitar_validaciones();
        //desabilitar textos
        habilitar_textos(false);
        //habilitar botones
        habilitar_botones(true);
        //cargar registro anterior a la modificación
        int pos = listLibros.localizar(clave);
        if (pos >= 0)
            ver_registro(pos);
        else
            limpiar_textos();
    }//GEN-LAST:event_btnDeshacerActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (!txtCodigo.getText().trim().equals("")) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminiar este registro?", "Sistema Académico", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                eliminar();
            }
        } else
            frmPrincipal.lbMensaje.setText("Seleccione el registro a eliminar");
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriKeyReleased
        buscar_varios();
    }//GEN-LAST:event_txtCriKeyReleased

    private void btnAgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgreActionPerformed
     //Pasar datos de la tabla 
        for (int i = 0; i < TablaLibros.getRowCount(); i++) {
            String Datos []= new String[5];
            Datos[0]=TablaLibros.getValueAt(i, 0).toString();
            Datos[1]=TablaLibros.getValueAt(i, 1).toString();      
            Datos[2]=TablaLibros.getValueAt(i, 5).toString();
            panelFacturas.modelo2.addRow(Datos);
        }

    }//GEN-LAST:event_btnAgreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaLibros;
    private javax.swing.JButton btnAgre;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnDeshacer;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCri;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
