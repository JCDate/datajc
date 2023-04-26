/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MateriaPrima;

import Servicios.Conexion;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
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
  
  File file = new File("MateriaPrima.xls");
  
  
  
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
    public void WriteExcelMateriaPrima()
    {
        int row=1;
        //formato fuente para el contenido contenido
        WritableFont wf = new WritableFont( WritableFont.ARIAL, 12, WritableFont.NO_BOLD );
        WritableCellFormat cf = new WritableCellFormat(wf);    

        //Interfaz para una hoja de cálculo
        WritableSheet excelSheet = null;
        WritableWorkbook workbook = null;

        //Establece la configuración regional para generar la hoja de cálculo
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        try {
            workbook = Workbook.createWorkbook( file, wbSettings );
            //hoja con nombre de la tabla
            workbook.createSheet( "MateriaPrima", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT calibre,consumoR,inventarioF,inventarioA,solicitudTransito,kgSolicitar,aprobacion FROM materia_prima ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label cal  = new Label( 0 , 0, "CALIBRE" , cf );                  
                  Label conR= new Label( 1 , 0, "CONSUMO BY RELEASES (KG)" , cf );                  
                  Label inF= new Label( 2 , 0, "INVENTARIO FISICO" , cf );
                  Label inA= new Label( 3 , 0, "INVENTARIO ACTUAL" , cf ); 
                  Label sT= new Label( 4 , 0, "SOLICITUD EN TRANSITO" , cf ); 
                  Label kgS= new Label( 5 , 0, "KG A SOLICITAR" , cf );
                  Label aprob= new Label( 6 , 0, "APROBACION" , cf ); 
                                    
                  Label calibre = new Label( 0 , row, res.getString( "calibre" ) , cf );                  
                  Label consumoR= new Label( 1 , row, res.getString( "consumoR" ) , cf ); 
                  Label inventarioF= new Label( 2 , row, res.getString( "inventarioF" ) , cf );
                  Label inventarioA= new Label( 3 , row, res.getString( "inventarioA" ) , cf );
                  Label solucitudT= new Label( 4 , row, res.getString( "solicitudTransito" ) , cf );
                  Label kgsSolicitar= new Label( 5 , row, res.getString( "kgSolicitar" ) , cf );
                  Label apronacion= new Label( 6 , row, res.getString( "aprobacion" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( cal );
                     excelSheet.addCell( conR );
                     excelSheet.addCell( inF);
                     excelSheet.addCell( inA );
                     excelSheet.addCell( sT );
                     excelSheet.addCell( kgS );
                     excelSheet.addCell( aprob );                     

                     excelSheet.addCell( calibre );
                     excelSheet.addCell( consumoR );
                     excelSheet.addCell( inventarioF );
                     excelSheet.addCell( inventarioA );
                     excelSheet.addCell( solucitudT );
                     excelSheet.addCell( kgsSolicitar );
                     excelSheet.addCell( apronacion );

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
