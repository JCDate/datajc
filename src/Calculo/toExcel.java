/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculo;

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
  
  File file = new File("Calculos.xls");
  

 
  
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
    public void WriteExcelCalculo()
    {
        int row=2;
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
            workbook.createSheet( "Calculos", 0 );
            excelSheet = workbook.getSheet(0);
            System.out.println(  "creando hoja excel.....Listo"  );            
        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        }

        //Consulta SQL 
        String sql = "SELECT componente, cr, calibre,blank_kg FROM datoslistaprecios ORDER BY componente ASC";
        String sql2 = "SELECT componente, dimHojaW, dimHojaL,pesoH_kg,dimRollo_p,pesoRollo_kgs,anchoTira_p FROM datostecnicosmp ORDER BY componente ASC";
        String sql3 = "SELECT componente, pzas_tira, pzas_tiraEntero,tiras_hojas,tiras_hojasEntero,pzas_hojasEntero,peso_pzasCal,peso_pzasLP FROM calculosteoricos ORDER BY componente ASC";
         
         try{
             PreparedStatement pstm = cn.prepareStatement( sql );
             ResultSet res = pstm.executeQuery();
             
             PreparedStatement pstm2 = cn.prepareStatement( sql2 );
             ResultSet res2 = pstm2.executeQuery();
             
             PreparedStatement pstm3 = cn.prepareStatement( sql3 );
             ResultSet res3 = pstm3.executeQuery();
             
             System.out.println(  "obteniendo registros.....Listo"  );
             
                  
              while(res.next() && res2.next() && res3.next())
              {
                  Label lista  = new Label( 1 , 0, "DATOS LISTA DE PRECIOS" , cf ); 
                  Label comp  = new Label( 0 , 1, "COMPONENTE" , cf );                  
                  Label cr= new Label( 1 , 1, "CR" , cf );                  
                  Label cal= new Label( 2 , 1, "CALIBRE" , cf );
                  Label blank= new Label( 3 , 1, "BLANK KG" , cf ); 
                  
                  Label tecnicos  = new Label( 7 , 0, "DATOS TECNICOS DE M. P." , cf );                  
                  Label dimW= new Label( 6 , 1, "DIM. HOJA W" , cf );                  
                  Label dimL= new Label( 7 , 1, "DIM. HOJA L" , cf );
                  Label pesoHk= new Label( 8 , 1, "PESO H/KG" , cf ); 
                  Label dimRP= new Label( 9 , 1, "DIM. ROLLO P." , cf ); 
                  Label pesoRK= new Label( 10 , 1, "PESO  ROLLO KGS" , cf ); 
                  Label anchoT= new Label( 11 , 1, "ANCHO TIRA P." , cf ); 
                  
                  Label calculo  = new Label( 16 , 0, "CALCULOS TEORICOS" , cf ); 
                  Label pzaT= new Label( 14 , 1, "PZAS TIRA" , cf );                  
                  Label pzasTE= new Label( 15 , 1, "PZAS TIRA ENTERO" , cf );
                  Label tiraH= new Label( 16 , 1, "TIRAS HOJAS" , cf ); 
                  Label tirasHE= new Label( 17 , 1, "TIRAS HOJAS ENTERO" , cf ); 
                  Label pzasHE= new Label( 18 , 1, "PZAS HOJAS ENTERO" , cf ); 
                  Label pesopzasC= new Label( 19 , 1, "PESO PZAS (CAL)" , cf ); 
                  Label pesopzasLP= new Label( 20 , 1, "PESO PZAS (L.P.)" , cf );
                                    
                  Label componente  = new Label( 0 , row, res.getString( "componente" ) , cf );                  
                  Label CR= new Label( 1 , row, res.getString( "cr" ) , cf ); 
                  Label calibre= new Label( 2 , row, res.getString( "calibre" ) , cf );
                  Label blankKg= new Label( 3 , row, res.getString( "blank_kg" ) , cf );
                  
                  Label dimHojaW= new Label( 6 , row, res2.getString( "dimHojaW" ) , cf ); 
                  Label dimHojaL= new Label( 7 , row, res2.getString( "dimHojaL" ) , cf );
                  Label pesoH_kg= new Label( 8 , row, res2.getString( "pesoH_kg" ) , cf );
                  Label dimRollo_p= new Label( 9 , row, res2.getString( "dimRollo_p" ) , cf );
                  Label pesoRollo_kgs= new Label( 10 , row, res2.getString( "pesoRollo_kgs" ) , cf );
                  Label anchoTira_p= new Label( 11 , row, res2.getString( "anchoTira_p" ) , cf );
                                  
                  Label pzas_tira= new Label( 14 , row, res3.getString( "pzas_tira" ) , cf ); 
                  Label pzas_tiraEntero= new Label( 15 , row, res3.getString( "pzas_tiraEntero" ) , cf );
                  Label tiras_hojas= new Label( 16 , row, res3.getString( "tiras_hojas" ) , cf );
                  Label tiras_hojasEntero= new Label( 17 , row, res3.getString( "tiras_hojasEntero" ) , cf );
                  Label pzas_hojasEntero= new Label( 18 , row, res3.getString( "pzas_hojasEntero" ) , cf );
                  Label peso_pzasCal= new Label( 19 , row, res3.getString( "peso_pzasCal" ) , cf );
                  Label peso_pzasLP= new Label( 20 , row, res3.getString( "peso_pzasLP" ) , cf );
                  
                  row ++;                  
                 try {
                     excelSheet.addCell( lista );
                     excelSheet.addCell( comp );
                     excelSheet.addCell( cr );
                     excelSheet.addCell( cal);
                     excelSheet.addCell( blank );
                     
                     excelSheet.addCell( tecnicos );
                     excelSheet.addCell( dimW );
                     excelSheet.addCell( dimL);
                     excelSheet.addCell( pesoHk );
                     excelSheet.addCell( dimRP );
                     excelSheet.addCell( pesoRK );
                     excelSheet.addCell( anchoT );
                     
                     excelSheet.addCell( calculo );
                     excelSheet.addCell( pzaT );
                     excelSheet.addCell( pzasTE);
                     excelSheet.addCell( tiraH );
                     excelSheet.addCell( tirasHE );
                     excelSheet.addCell( pzasHE );
                     excelSheet.addCell( pesopzasC );
                     excelSheet.addCell( pesopzasLP );
                     
                     excelSheet.addCell( componente );
                     excelSheet.addCell( CR );
                     excelSheet.addCell( calibre );
                     excelSheet.addCell( blankKg );
                     
                     excelSheet.addCell( dimHojaW );
                     excelSheet.addCell( dimHojaL );
                     excelSheet.addCell( pesoH_kg );
                     excelSheet.addCell( dimRollo_p );
                     excelSheet.addCell( pesoRollo_kgs );
                     excelSheet.addCell( anchoTira_p );
                     
                     excelSheet.addCell( pzas_tira );
                     excelSheet.addCell( pzas_tiraEntero );
                     excelSheet.addCell( tiras_hojas );
                     excelSheet.addCell( tiras_hojasEntero );
                     excelSheet.addCell( pzas_hojasEntero );
                     excelSheet.addCell( peso_pzasCal );
                     excelSheet.addCell( peso_pzasLP );

                 } catch (WriteException ex) {
                     System.err.println(  ex.getMessage() );
                 } 
              }
             res.close();  
             res2.close(); 
             res3.close();
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
