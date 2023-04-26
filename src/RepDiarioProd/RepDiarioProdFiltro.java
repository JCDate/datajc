/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RepDiarioProd;

import Modelos.RepDiarioProdM;
import Modelos.TroqueladoresM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.RepDiarioProd_servicio;
import Servicios.Troqueladores_servicio;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author JC
 */
public class RepDiarioProdFiltro extends javax.swing.JFrame {
    Conexion cc = new Conexion();
    Connection cn;
    Usuarios mod;
    TableRowSorter trs;
    private final toExcel excel= new toExcel(); //Generar Excel
    
    private final RepDiarioProd_servicio repDi_servicio = new RepDiarioProd_servicio();
    private List<RepDiarioProdM> repDi;
    
    private final Troqueladores_servicio troq_servicio = new Troqueladores_servicio();
    private List <TroqueladoresM> troq;
    

    public RepDiarioProdFiltro() throws SQLException, ClassNotFoundException {
        this.cn = Conexion.obtener();
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.RepDiarioProdFiltro();
    }
    
    public RepDiarioProdFiltro(Usuarios mod) throws SQLException, ClassNotFoundException{
        this.cn = Conexion.obtener();
        initComponents();
        this.mod = mod;
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
       
        this.RepDiarioProdFiltro();
    }
    
    private void RepDiarioProdFiltro() throws SQLException, ClassNotFoundException {
        String fecha=JOptionPane.showInputDialog("INGRESAR FECHA");
         
        this.troq = this.troq_servicio.recuperarTodas(Conexion.obtener());
        int Size = this.troq.size();
        
        String options[] =new String[Size];
        for(int i = 0; i < this.troq.size(); i++){
        options[i]  =   
                this.troq.get(i).getNombre();   
        }
        Object opera = null;
        if(Size==1){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0]},"Seleccione"
            );
        }
        if(Size==2){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1]},"Seleccione"
            );
        }
        if(Size==3){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2]},"Seleccione"
            );
        }
        if(Size==4){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3]},"Seleccione"
            );
        }
        if(Size==5){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4]},"Seleccione"
            );
        }
        if(Size==6){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5]},"Seleccione"
            );
        }
        if(Size==7){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6]},"Seleccione"
            );
        }
        if(Size==8){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7]},"Seleccione"
            );
        }
        if(Size==9){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8]},"Seleccione"
            );
        }
        if(Size==10){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9]},"Seleccione"
            );
        }
        if(Size==11){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10]},"Seleccione"
            );
        }
        if(Size==12){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11]},"Seleccione"
            );
        }
        if(Size==13){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12]},"Seleccione"
            );
        }
        if(Size==14){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13]},"Seleccione"
            );
        }
        if(Size==15){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14]},"Seleccione"
            );
        }
        if(Size==16){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15]},"Seleccione"
            );
        }
        if(Size==17){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16]},"Seleccione"
            );
        }
        if(Size==18){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17]},"Seleccione"
            );
        }
        if(Size==19){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18]},"Seleccione"
            );
        }
        if(Size==20){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19]},"Seleccione"
            );
        }
        if(Size==21){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20]},"Seleccione"
            );
        }
        if(Size==22){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21]},"Seleccione"
            );
        }
        if(Size==23){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22]},"Seleccione"
            );
        }
        if(Size==24){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23]},"Seleccione"
            );
        }
        if(Size==25){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24]},"Seleccione"
            );
        }
        if(Size==26){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25]},"Seleccione"
            );
        }
        if(Size==27){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26]},"Seleccione"
            );
        }
        if(Size==28){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27]},"Seleccione"
            );
        }
        if(Size==29){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28]},"Seleccione"
            );
        }
        if(Size==30){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29]},"Seleccione"
            );
        }
        if(Size==31){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30]},"Seleccione"
            );
        }
         if(Size==32){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31]},"Seleccione"
            );
        }
         if(Size==33){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32]},"Seleccione"
            );
         }
         if(Size==34){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33]},"Seleccione"
            );
         }
         if(Size==35){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34]},"Seleccione"
            );
         }
         if(Size==36){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35]},"Seleccione"
            );
         }
         if(Size==37){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36]},"Seleccione"
            );
         }
         if(Size==38){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37]},"Seleccione"
            );
         }
         if(Size==39){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38]},"Seleccione"
            );
         }
         if(Size==40){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38], options[39]},"Seleccione"
            );
         }
          if(Size==41){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38], options[39], options[40]},"Seleccione"
            );
         }
          if(Size==42){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38], options[39], options[40], options[41]},"Seleccione"
            );
         }
           if(Size==43){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38], options[39], options[40], options[41], options[42]},"Seleccione"
            );
         }
           if(Size==44){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38], options[39], options[40], options[41], options[42], options[43]},"Seleccione"
            );
         }
           if(Size==45){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38], options[39], options[40], options[41], options[42], options[43], options[44]},"Seleccione"
            );
         }
           if(Size==46){
            opera = JOptionPane.showInputDialog(null,"Seleccionar operador", "OPERADOR", JOptionPane.QUESTION_MESSAGE, null,
                new Object[] { "Seleccione", options[0], options[1], options[2], options[3], options[4], options[5], options[6], options[7], options[8], options[9], options[10], options[11], options[12], options[13], options[14], options[15], options[16], options[17], options[18], options[19], options[20], options[21], options[22], options[23], options[24], options[25], options[26], options[27], options[28], options[29], options[30], options[31], options[32], options[33], options[34], options[35], options[36], options[37], options[38], options[39], options[40], options[41], options[42], options[43], options[44], options[45]},"Seleccione"
            );
         }
       try{
            PreparedStatement consulta;
            consulta = cn.prepareStatement("SELECT * FROM repdiprod WHERE operador='"+opera+"' AND fecha='"+fecha+"'");
            ResultSet resultado = consulta.executeQuery();
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            while(resultado.next()){
               dtm.addRow(new Object[]{
                   resultado.getString("fecha"),
                   resultado.getString("operador"), 
                   resultado.getString("orden"), 
                   resultado.getString("componente"), 
                   resultado.getString("cr"), 
                   resultado.getInt("contS"), 
                   resultado.getString("noMaquina"), 
                   resultado.getString("actividad"), 
                   resultado.getString("operacion"),
                   resultado.getInt("tOp"), 
                   resultado.getString("inicio"),
                   resultado.getString("fin"),
                   resultado.getInt("cantProd"),
                   resultado.getInt("cantParcial"),
                   resultado.getString("comentario")
               });                                                                                                                                                                                                                                                                                            
            }
           
            Statement comando=cn.createStatement();
            
            //TOTAL DE estampas terminadas
                    ResultSet registro = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura WHERE repdiprod.fecha='"+fecha+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND actividad LIKE '%PROCESO%' AND repdiprod.operador ='"+opera+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND actividad LIKE '%PROCESO%'");
                    if(registro.next()==true) {
                        jLabel6.setText(registro.getString("suma"));
                    }
                    
                    //TOTAL DE estampas terminadas BAJO VOLUMEN
                    ResultSet registroB = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura, consumoyantecedentes WHERE repdiprod.fecha='"+fecha+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='BAJO VOLUMEN' AND repdiprod.operador ='"+opera+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='BAJO VOLUMEN'");
                    if(registroB.next()==true) {
                        jLabel14.setText(registroB.getString("suma"));
                    }
 
                    //TOTAL DE estampas terminadas ALTO VOLUMEN
                    ResultSet registroA = comando.executeQuery("SELECT SUM(cantProd) as suma FROM repdiprod, estructura, consumoyantecedentes WHERE repdiprod.fecha='"+fecha+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='ALTO VOLUMEN' AND repdiprod.operador ='"+opera+"' AND estructura.OP=repdiprod.operacion AND estructura.componente=repdiprod.componente AND consumoyantecedentes.componente_CA=repdiprod.componente AND actividad LIKE '%PROCESO%' AND consumoyantecedentes.tipo='ALTO VOLUMEN'");
                    if(registroA.next()==true) {
                        jLabel12.setText(registroA.getString("suma"));
                    }

                    //TOTAL DE Golpes
                    ResultSet registroG = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod WHERE fecha='"+fecha+"' AND operador='"+opera+"'");
                    if(registroG.next()==true) {
                        jLabel8.setText(registroG.getString("total"));
                    }
                    
                    //TOTAL DE Golpes BAJO VOLUMAN
                    ResultSet registroGB = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod,consumoyantecedentes WHERE fecha='"+fecha+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='BAJO VOLUMEN' AND operador='"+opera+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='BAJO VOLUMEN'");
                    if(registroGB.next()==true) {
                        jLabel18.setText(registroGB.getString("total"));
                    }
                    
                    //TOTAL DE Golpes ALTO VOLUMAN
                    ResultSet registroGA = comando.executeQuery("SELECT SUM(cantProd) AS total FROM repdiprod,consumoyantecedentes WHERE fecha='"+fecha+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='ALTO VOLUMEN' AND operador='"+opera+"' AND consumoyantecedentes.componente_CA=repdiprod.componente AND consumoyantecedentes.tipo='ALTO VOLUMEN'");
                    if(registroGA.next()==true) {
                        jLabel16.setText(registroGA.getString("total"));
                    }
                    
                    //TOTAL DELIENAS
                    ResultSet registroS = comando.executeQuery("SELECT COUNT( DISTINCT repdiarot.orden, repdiarot.componente, repdiarot.operacion, repdiarot.cantProd) AS total FROM repdiarot, estructura,repdiprod WHERE repdiarot.fecha='"+fecha+"' AND repdiarot.operador='"+opera+"' AND repdiarot.operacion=estructura.OP AND repdiprod.cantParcial >= repdiprod.contS AND repdiprod.id=repdiarot.id AND repdiarot.componente= estructura.componente");    
                    if(registroS.next()==true) {
                        jLabel10.setText(registroS.getString("total"));
                    }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }  
    }
      @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
              getImage(ClassLoader.getSystemResource("jc/img/jc.png"));

        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("FILTRO POR FECHA Y OPERADOR");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "FECHA", "OPERADOR", "ORDEN", "COMPONENTE", "C/R", "<HTML><CENTER>CANT. <P>SOLICITADA</P></CENTER></HTML>", "NO. MAQUINA", "ACTIVIDAD", "OPERACION", "TOTAL OP", "INICIO", "FIN", "<HTML><CENTER>CANT.<P> PRODUCIDA</P></CENTER><>/HTML", "<HTML><CENTER>CANT. <P>PARCIAL</P></CENTER></HTML>", "COMENTARIO"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 1260, 390));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        jButton2.setText("CERRAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 120, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("EST. TERMINADA:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 570, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("GOLPES:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 570, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("jLabel8");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 570, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("TOTAL DE LINEAS TERMINADAS:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("EST. TERMINADA B.V:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 600, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("jLabel14");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 600, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("EST. TERMINADA A.V:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 630, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("jLabel12");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 630, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("GOLPES B.V:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 600, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("jLabel18");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 600, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("GOLPES A.V:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 630, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("jLabel16");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 630, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(135, 206, 235));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        RepDiarioProdFiltro.this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(RepDiarioProdFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RepDiarioProdFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RepDiarioProdFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RepDiarioProdFiltro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RepDiarioProdFiltro().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RepDiarioProdFiltro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RepDiarioProdFiltro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
