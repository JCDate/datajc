/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class DropXlsx implements DropTargetListener {
    
    private JTable jtable;
    private DefaultTableModel tableModel;
    protected DropTarget dropTarget;
    
   
    public DropXlsx() {
    }

    public void setJtable(JTable jtable) throws SQLException, ClassNotFoundException {
        this.jtable = jtable;
        dropTarget = new DropTarget(jtable, this);    
        tableModel = new DefaultTableModel();
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {/*...*/
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {/*...*/
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {/*...*/
    }

    @Override
    public void dragExit(DropTargetEvent dte) {/*...*/
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            /* proporciona datos para operaciones de transferencia en swing */
            Transferable tr = dtde.getTransferable();
            /* Devuelve una array de objetos DataFlavor */
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            if (flavors.length > 0) {
                /* Si existe una lista de objetos de archivo */
                if (flavors[0].isFlavorJavaFileListType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    /* obtiene un List con los archivos arrastrados al componente */
                    java.util.List list = (java.util.List) tr.getTransferData(flavors[0]);
                    if (!list.isEmpty()) {
                        /* abre el primer archivo */
                        File file = new File(list.get(0).toString());
                        if (file.exists()) {
                            /* Si el archivo corresponde a un archivo excel *.xlsx */
                            if (file.getName().endsWith("xlsx")) {
                                readXLSX(file);
                            } else {
                                JOptionPane.showMessageDialog(null, "No es un archivo *.xlsx valido", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            System.err.println("error archivo no existe ");
                        }
                    }
                    dtde.dropComplete(true);
                    return;
                }
            }
            dtde.rejectDrop();
        } catch (UnsupportedFlavorException | IOException | HeadlessException ex) {
            System.err.println(ex.getMessage());
            dtde.rejectDrop();
        }
    }

    /**
     * Lee un archivo excel (Primera hoja)
     *
     * @param file Archivo excel
     */
    private void readXLSX(File file) {        
        tableModel = new DefaultTableModel();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = wb.getSheetAt(0);//primeta hoja            
            Row row = null;
            Cell cell = null;

            //obtiene cantidad total de columnas con contenido
            int maxCol = 0;
            for (int a = 0; a <= sheet.getLastRowNum(); a++) {
                if(sheet.getRow(a)!=null){
                    if (sheet.getRow(a).getLastCellNum() > maxCol) {
                        maxCol = sheet.getRow(a).getLastCellNum();
                    }    
                }                
            }
            if (maxCol > 0) {
                //Añade encabezado a la tabla
                    tableModel.addColumn("Documento nro." );
                    tableModel.addColumn("Fecha desde");
                    tableModel.addColumn("Fecha hasta");
                    tableModel.addColumn("Contacto");
                    tableModel.addColumn("Unidad de Cliente");
                    tableModel.addColumn("Consignatario");
                    tableModel.addColumn("Puerta");
                    tableModel.addColumn("Item de cliente");
                    tableModel.addColumn("Nro. de revisión de ilustración.");
                    tableModel.addColumn("Item de asociado");
                    tableModel.addColumn("Descripción de cliente");
                    tableModel.addColumn("Estado");
                    tableModel.addColumn("Orden");
                    tableModel.addColumn("Línea de Orden");
                    tableModel.addColumn("Modo de abastecimiento");
                    tableModel.addColumn("Fecha de Despacho");
                    tableModel.addColumn("Fecha de arribo");
                    tableModel.addColumn("Cantidad requerida");
                    tableModel.addColumn("Cantidad neta");
                    tableModel.addColumn("Cantidad despachada");
                    tableModel.addColumn("Unidad");
                    tableModel.addColumn("Precio");
                    tableModel.addColumn("Moneda");
                    tableModel.addColumn("Estado de ítem");
                    tableModel.addColumn("Fecha de estado de ítem");
              
                //recorre fila por fila
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
             
                    int index = 0;
                    row = rowIterator.next();

                    Object[] obj = new Object[row.getLastCellNum()];
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                           cell = cellIterator.next();

                        //contenido para celdas vacias
                        while (index < cell.getColumnIndex()) {
                            obj[index] = "";
                            index += 1;
                        }
                        //extrae contenido de archivo excel
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                obj[index] = cell.getBooleanCellValue();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell))
                                    {
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        obj[index] = sdf.format(cell.getDateCellValue());
                                    }else{
                                        obj[index] = cell.getNumericCellValue(); 
                                    }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                obj[index] = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                obj[index] = " ";
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                obj[index] = cell.getCellFormula();
                                break; 
                           
                            default:
                                obj[index] = "";
                                break;
                        } 
                        
                        index += 1;
                    }
                    tableModel.addRow(obj);
                }
                jtable.setModel(tableModel);
                //jtable.setAutoResizeMode(jtable.AUTO_RESIZE_OFF);
                jtable.doLayout();
                
                borrarFilasCon("Orden"); 
                
                //Eliminar columnas
                jtable.removeColumn(jtable.getColumnModel().getColumn(0));
                jtable.removeColumn(jtable.getColumnModel().getColumn(1));
                jtable.removeColumn(jtable.getColumnModel().getColumn(1));
                jtable.removeColumn(jtable.getColumnModel().getColumn(1));
                jtable.removeColumn(jtable.getColumnModel().getColumn(2));
                jtable.removeColumn(jtable.getColumnModel().getColumn(3));
                jtable.removeColumn(jtable.getColumnModel().getColumn(3));
                jtable.removeColumn(jtable.getColumnModel().getColumn(3));
                jtable.removeColumn(jtable.getColumnModel().getColumn(3));
                jtable.removeColumn(jtable.getColumnModel().getColumn(4));
                jtable.removeColumn(jtable.getColumnModel().getColumn(4));
                jtable.removeColumn(jtable.getColumnModel().getColumn(4));
                jtable.removeColumn(jtable.getColumnModel().getColumn(5));
                jtable.removeColumn(jtable.getColumnModel().getColumn(6));
                jtable.removeColumn(jtable.getColumnModel().getColumn(6));
                jtable.removeColumn(jtable.getColumnModel().getColumn(6));
                jtable.removeColumn(jtable.getColumnModel().getColumn(6));
                jtable.removeColumn(jtable.getColumnModel().getColumn(6));
                jtable.removeColumn(jtable.getColumnModel().getColumn(6));
               

            }else{
                JOptionPane.showMessageDialog(null, "Nada que importar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            System.err.println("" + ex.getMessage());
        }
    }
    
     public void borrarFilasCon(String nombre)
     {
        for (int f = 0; f < tableModel.getRowCount(); f++)
        {
          for(int c = 0; c < tableModel.getColumnCount()-1; c++)
          {
            if(f!=c){
               if (tableModel.getValueAt(f, c).equals(nombre)) 
                {
                    
                  tableModel.removeRow(f);

                } 
            }           
          }
        }   
        
     }


}
