/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsumoYAntecedentes;

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
  //Analisis de Atrasos
  File file = new File("ConsumoYAntecedentes.xls");
   
  
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
    public void WriteExceConsumoYAntecedentes()
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
            workbook.createSheet( "ConsumoYAntecedentes", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT componente_CA,consumo_uni,desMatePrima,anchoTira,PpapStatus,comentario_CA FROM consumoyantecedentes ";
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next())
              {
                  Label comp  = new Label( 0 , 0, "COMPONENTE" , cf );                  
                  Label conUn= new Label( 1 , 0, "CONSUMO UNITARIO" , cf );                  
                  Label desc= new Label( 2 , 0, "DESCRIPCION DE MATARIA PRIMA", cf );
                  Label ancho= new Label( 3 , 0, "ANCHO DE TIRA" , cf );   
                  Label ppSt= new Label( 4 , 0, "PPAP STATUS" , cf ); 
                  Label com= new Label( 5 , 0, "COMENTARIO", cf ); 
                  
                  Label componente_CA  = new Label( 0 , row, res.getString( "componente_CA" ) , cf );                  
                  Number consumo_uni= new Number( 1 , row, res.getFloat("consumo_uni" ) , cf );                  
                  Label desMatePrima= new Label( 2 , row, res.getString( "desMatePrima" ) , cf );
                  Number anchoTira= new Number( 3 , row, res.getFloat( "anchoTira" ) , cf );   
                  Label PpapStatus= new Label( 4 , row, res.getString( "PpapStatus" ) , cf ); 
                  Label comentario_CA= new Label( 5 , row, res.getString( "comentario_CA" ) , cf ); 
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( comp );
                     excelSheet.addCell( conUn );
                     excelSheet.addCell( desc );
                     excelSheet.addCell( ancho );
                     excelSheet.addCell( ppSt );
                     excelSheet.addCell( com );
                     
                     excelSheet.addCell( componente_CA );
                     excelSheet.addCell( consumo_uni );
                     excelSheet.addCell( desMatePrima );
                     excelSheet.addCell( anchoTira );
                     excelSheet.addCell( PpapStatus );
                     excelSheet.addCell( comentario_CA );
                     
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
