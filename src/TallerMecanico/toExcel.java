/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TallerMecanico;


import Servicios.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
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
 
  File file = new File("TALLER MECANICO.xls");

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
        int row=4;
        int linea=1;

        //formato fuente para el contenido contenido
        WritableFont wf = new WritableFont( WritableFont.ARIAL, 12, WritableFont.NO_BOLD );
        WritableCellFormat cf = new WritableCellFormat(wf);    
        
        WritableFont wf2 = new WritableFont( WritableFont.ARIAL, 20, WritableFont.NO_BOLD );
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
            workbook.createSheet( "Taller Mecanico", 0 );
            excelSheet = workbook.getSheet(0);
           
        
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }
        String mes=JOptionPane.showInputDialog("INGRESAR FECHA (MM/YYYY): \n 01 ENERO \n 02 FEBRERO \n 03 MARZO \n 04 ABRIL \n 05 MAYO \n 06 JUNIO \n 07 JULIO \n 08 AGOSTO \n 09 SEPTIEMBRE \n 10 OCTUBRE \n 11 NOVIEMBRE \n 12 DICIEMBRE\n\n");
        //Consulta SQL 
        String sql = "SELECT tallermecacicofalla.troquel, tallermecacicofalla.componente, fechaEntrada, descripcion, tallermecanicorep.fechaEnt, tallermecanicorep.reparadaP, tallermecanicorep.turno, tallermecanicorep.solucion, tallermecanicorep.fabricada, tallermecanicorep.reparada, tallermecanicorep.eficaz FROM tallermecacicofalla, tallermecanicorep WHERE tallermecacicofalla.id= tallermecanicorep.id_troquel AND SUBSTRING(fechaEnt, 4, 7) ='"+mes+"' ORDER BY tallermecacicofalla.troquel ASC";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );

              while(res.next())
              {
                  
                  Label nombre= new Label( 2 , 1, "BITACORA DE MANTENIMIENTO DE TROQUELES" , cf2 ); 
                  Label NO= new Label( 0 , 3, "NO." , cf );
                  Label troquel= new Label( 1 , 3, "TROQUEL", cf );   
                  Label COMPONENTE= new Label( 2 , 3, "COMPONENTE" , cf ); 
                  Label fe= new Label( 3 , 3, "FECHA DE ENTRADA", cf ); 
                  Label op= new Label( 4 , 3, "DESCRIPCIÓN DE LA FALLA (DESCRIBIR EN DETALLE EN QUE CONSISTE LA FALLA)" , cf ); 
                  Label noM= new Label( 5 , 3, "FECHA DE ENTREGA" , cf ); 
                  Label act= new Label( 6 , 3, "REPARADA POR" , cf ); 
                  Label opera= new Label( 7 , 3, "TURNO" , cf ); 
                  Label ini= new Label( 8 , 3, "SOLUCIÓN (DESCRIBIR LO QUE SE LE HIZO A LA PIEZA)" , cf ); 
                  Label fi= new Label( 9 , 3, "FAB." , cf ); 
                  Label cantP= new Label( 10 , 3, "REP." , cf ); 
                  Label com= new Label( 11 , 3, "AJUSTE EFICAZ A LA 1a.?" , cf ); 
                  
                  Number li= new Number( 0 , row, linea , cf );   
                  Label troq= new Label( 1 , row, res.getString( "troquel" ), cf );                 
                  Label componente= new Label( 2 , row, res.getString( "componente" ) , cf );
                  Label cr= new Label( 3 , row, res.getString( "fechaEntrada" ) , cf );   
                  Label contS= new Label( 4 , row, res.getString( "descripcion" ) , cf ); 
                  Label fecha= new Label( 5 , row, res.getString( "fechaEnt" ) , cf ); 
                  Label operador= new Label( 6 , row, res.getString( "reparadaP" ) , cf ); 
                  Label noMaquina= new Label( 7 , row, res.getString( "turno" ) , cf ); 
                  Label actividad= new Label( 8 , row, res.getString( "solucion" ) , cf ); 
                  Label operacion= new Label( 9 , row, res.getString( "fabricada" ) , cf ); 
                  Label inicio= new Label( 10 , row, res.getString( "reparada" ) , cf ); 
                  Label fin= new Label( 11 , row, res.getString( "eficaz" ) , cf ); 

                  row ++; 
                  linea ++;
                 try {
                    
                     excelSheet.addCell( nombre );
                     excelSheet.addCell( NO );
                     excelSheet.addCell( troquel );
                     excelSheet.addCell( COMPONENTE );
                     excelSheet.addCell( fe );
                     excelSheet.addCell( op );
                     excelSheet.addCell( noM );
                     excelSheet.addCell( act );
                     excelSheet.addCell( opera );
                     excelSheet.addCell( ini );
                     excelSheet.addCell( fi );
                     excelSheet.addCell( cantP );
                     excelSheet.addCell( com );
                    
                     excelSheet.addCell( li );
                     excelSheet.addCell( troq );                     
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

                 } catch (WriteException ex) {
                     System.err.println(  ex.getMessage() );
                 } 
              }
              
             res.close(); 
             
         }catch( SQLException e ){
            System.err.println( e.getMessage() );
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
