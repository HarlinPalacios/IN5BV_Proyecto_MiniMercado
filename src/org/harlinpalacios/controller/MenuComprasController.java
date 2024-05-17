package org.harlinpalacios.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.harlinpalacios.bean.Compras;
import org.harlinpalacios.db.Conexion;
import org.harlinpalacios.system.Principal;

public class MenuComprasController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Compras> listarCompras;

    @FXML private Button btnRegresar;

    // Cuadro de texto 
    @FXML private TextField txtNumeroD;
    @FXML private DatePicker txtFechaD;
    @FXML private TextField txtDescripcionC;
    @FXML private TextField txtTotalD;

    // Columnas 
    @FXML private TableView tblCompras;
    @FXML private TableColumn colNumeroD;
    @FXML private TableColumn colFechaD;
    @FXML private TableColumn colDescripcionC;
    @FXML private TableColumn colTotalD;

    // Botones para el CRUD 
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    // Imagenes
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumeroD.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaD.setCellValueFactory(new PropertyValueFactory<Compras, LocalDate>("fechaDocumento"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalD.setCellValueFactory(new PropertyValueFactory<Compras, Boolean>("totalDocumento"));
    }

    public void seleccionarElemento() {
        txtNumeroD.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtDescripcionC.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtTotalD.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));

    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                java.sql.Date fecha = resultado.getDate("fechaDocumento");
                LocalDate fechaDP = fecha.toLocalDate();

                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        fechaDP,
                        resultado.getString("descripcion"),
                        resultado.getString("totalDocumento")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarCompras = FXCollections.observableArrayList(lista);
    }

// Agregar    
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/harlinpalacios/imagenes/Guardar.png"));
                imgEliminar.setImage(new Image("/org/harlinpalacios/imagenes/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harlinpalacios/imagenes/Agregar Cliente.png"));
                imgEliminar.setImage(new Image("/org/harlinpalacios/imagenes/Eliminar Cliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }

        //Image 
    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroD.getText()));
        LocalDate fechaN = txtFechaD.getValue();
        Date fechaC = Date.valueOf(fechaN);
        registro.setDescripcion(txtDescripcionC.getText());
        registro.setTotalDocumento(txtTotalD.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompra(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setDate(2, fechaC);
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            procedimiento.execute();
            listarCompras.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

// Eliminar    
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/harlinpalacios/imagenes/Agregar Cliente.png"));
                imgEliminar.setImage(new Image("/org/harlinpalacios/imagenes/Eliminar Cliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro",
                            "Eliminar Campra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompra(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listarCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    limpiarControles();
                } else {
                    JOptionPane.showMessageDialog(null, "De ve selccionar un elemento");
                }
        }
    }

// Editar    
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/harlinpalacios/imagenes/Actualizar Clientes.png"));
                    imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Cnacelar.png"));
                    activarControles();
                    txtNumeroD.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/harlinpalacios/imagenes/Editar.png"));
                imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Reportes clientes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompra(?, ?, ?, ?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setNumeroDocumento(Integer.parseInt(txtNumeroD.getText()));
            LocalDate fechaN = txtFechaD.getValue();
            Date fechaC = Date.valueOf(fechaN);
            registro.setDescripcion(txtDescripcionC.getText());
            registro.setTotalDocumento(txtTotalD.getText());
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setDate(2, fechaC);
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Reporte    
    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/og/harlinpalacios/imagenes/Editar.png"));
                imgReporte.setImage(new Image("/org/harlinpalacios/imagenes/Reporte clientes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;

        }
    }

    public void desactivarControles() {
        txtNumeroD.setEditable(false);
        txtFechaD.setEditable(false);
        txtDescripcionC.setEditable(false);
        txtTotalD.setEditable(false);
    }

    public void activarControles() {
        txtNumeroD.setEditable(true);
        txtFechaD.setEditable(true);
        txtDescripcionC.setEditable(true);
        txtTotalD.setEditable(true);
    }

    public void limpiarControles() {
        txtNumeroD.clear();
        txtDescripcionC.clear();
        txtTotalD.clear();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
