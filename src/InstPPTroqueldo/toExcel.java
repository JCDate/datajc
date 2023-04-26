/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InstPPTroqueldo;

import Servicios.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author JC
 */
public class toExcel {
     /* DATOS PARA LA CONEXION */
  
  File file = new File("Inst. y puesta a punto de Troquelado.xls");
  
  
  Conexion cc = new Conexion();
  Connection cn;
  

    /**
 * Constructor de clase
 */
    public toExcel()
    {
     
       try{
         
        this.cn = Conexion.obtener();
         //obtenemos la conexión
        
      }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
    }

    /**
 * Metodo para obtener los registros de la base de datos y crear el archivo excel
 */
    public void WriteExcel()
    {
        int row=3;
        int linea=1;
        
        //formato fuente para el contenido contenido
        WritableFont wf = new WritableFont( WritableFont.ARIAL, 12, WritableFont.NO_BOLD );
        WritableCellFormat cf = new WritableCellFormat(wf); 
        
        WritableFont wf2 = new WritableFont( WritableFont.ARIAL, 22, WritableFont.NO_BOLD );
        WritableCellFormat cf2 = new WritableCellFormat(wf2);

        //Interfaz para una hoja de cálculo
        WritableSheet excelSheet = null;
        WritableWorkbook workbook = null;

        //Establece la configuración regional para generar la hoja de cálculo
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        try {
            workbook = Workbook.createWorkbook( file, wbSettings );
            //hoja con nombre de la tabla
            workbook.createSheet( "INSTPPT", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }
        String mes=JOptionPane.showInputDialog("INGRESAR FECHA (MM/YYYY): \n 01 ENERO \n 02 FEBRERO \n 03 MARZO \n 04 ABRIL \n 05 MAYO \n 06 JUNIO \n 07 JULIO \n 08 AGOSTO \n 09 SEPTIEMBRE \n 10 OCTUBRE \n 11 NOVIEMBRE \n 12 DICIEMBRE\n\n");
        //Consulta SQL 
        String sql = "SELECT instpptroquelado.componente,instpptroquelado.cr,instpptroquelado.calibre,fecha,operacion,troquel,maquina,operador,libs,causaR,estructura.OP FROM instpptroquelado, estructura WHERE instpptroquelado.componente=estructura.componente AND SUBSTRING(fecha, 4, 7) ='"+mes+"'";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                   Label letrero= new Label( 1 , 0, "INSTALACION Y PUESTA A PUNTO DE TROQUELADOS" , cf2 );
                  

                  Label ID= new Label( 0 , 2, "ID" , cf );                  
                  Label comp= new Label( 1 , 2, "COMPONENTE", cf );
                  Label c= new Label( 2 , 2, "C/R" , cf );   
                  Label cantS= new Label( 3 , 2, "CALIBRE" , cf ); 
                  Label fe= new Label( 4 , 2, "FECHA", cf ); 
                  Label op= new Label( 5 , 2, "OPERACION" , cf ); 
                  Label noM= new Label( 6 , 2, "TROQUEL" , cf ); 
                  Label act= new Label( 7 , 2, "MAQUINA" , cf ); 
                  Label opera= new Label( 8 , 2, "OPERADOR" , cf ); 
                  Label ini= new Label( 9 , 2, "LBS" , cf ); 
                  Label fi= new Label( 10 , 2, "CAUSA REP." , cf ); 
                  Label OP= new Label( 11 , 2, "TOTAL OP." , cf ); 
                     
                  Number li= new Number( 0 , row, linea , cf );                 
                  Label componente= new Label( 1 , row, res.getString( "componente" ) , cf );
                  Label cr= new Label( 2 , row, res.getString( "cr" ) , cf );   
                  Number contS= new Number( 3 , row, res.getLong( "calibre" ) , cf ); 
                  Label fecha= new Label( 4 , row, res.getString( "fecha" ) , cf ); 
                  Label operador= new Label( 5 , row, res.getString( "operacion" ) , cf ); 
                  Label noMaquina= new Label( 6 , row, res.getString( "troquel" ) , cf ); 
                  Label actividad= new Label( 7 , row, res.getString( "maquina" ) , cf ); 
                  Label operacion= new Label( 8 , row, res.getString( "operador" ) , cf ); 
                  Label inicio= new Label( 9 , row, res.getString( "libs" ) , cf ); 
                  Label fin= new Label( 10 , row, res.getString( "causaR" ) , cf ); 
                  Label oper= new Label( 11 , row, res.getString( "OP" ) , cf );
                   
                  linea ++;
                  row ++;                  
                 try {
                    
                     excelSheet.addCell( letrero );
                     
                     excelSheet.addCell( ID );
                     excelSheet.addCell( comp );
                     excelSheet.addCell( c );
                     excelSheet.addCell( cantS );
                     excelSheet.addCell( fe );
                     excelSheet.addCell( op );
                     excelSheet.addCell( noM );
                     excelSheet.addCell( act );
                     excelSheet.addCell( opera );
                     excelSheet.addCell( ini );
                     excelSheet.addCell( fi );
                     excelSheet.addCell( OP );
           
                     excelSheet.addCell( li );
                     excelSheet.addCell( componente );
                     excelSheet.addCell( cr );
                     excelSheet.addCell( contS );
                     excelSheet.addCell( fecha );
                     excelSheet.addCell( operador );
                     excelSheet.addCell( noMaquina );
                     excelSheet.addCell( actividad );
                     excelSheet.addCell( operacion );
                     excelSheet.addCell( inicio );
                     excelSheet.addCell( fin );
                     excelSheet.addCell( oper );
                     
                     
                 } catch (WriteException ex) {
                     System.err.println(  ex.getMessage() );
                 } 
              }
              int cod = 3+linea;
              Label codigo= new Label( 11 , cod, "F - IP 01 REV 01" , cf );
              excelSheet.addCell( codigo );
             res.close();         
         }catch( SQLException e ){
            System.err.println( e.getMessage() );
        } catch (WriteException ex) {
          Logger.getLogger(toExcel.class.getName()).log(Level.SEVERE, null, ex);
      }

        //Escribe el archivo excel en disco
        try {
            workbook.write();
            workbook.close();
            System.out.println(  "Escribiendo en disco....Listo"  );         
        } catch (IOException ex) {
            System.err.println(  ex.getMessage() );
        }
        catch (WriteException ex) {
           System.err.println(  ex.getMessage() );
        }

        System.out.println(  "Proceso completado...."  );
    }
 
}
