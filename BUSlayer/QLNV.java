/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUSlayer;

import DTO.NhanVien;
import Connect.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import DAlayer.QLNVdao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author admin
 */
public class QLNV {
    public Connection conn = Connect.getConnection();
    public String sql = null;
    public PreparedStatement pst = null;
    public Statement st = null;
    public ArrayList<NhanVien> listNV= new ArrayList<>();
    
    public void themNV(NhanVien nv) throws SQLException{
        QLNVdao qldao = new QLNVdao();
        qldao.themNV(nv);
    }
    
    public void themNhieuNV() throws SQLException{
        QLNVdao qldao = new QLNVdao();
        for (int i=0; i<listNV.size(); i++){
            qldao.themNV(listNV.get(i));
        }
    }
    
    public void suaNV(NhanVien nv) throws SQLException{
        QLNVdao qldao = new QLNVdao();
        qldao.suaNV(nv);
    }
    
    public void xoaNV(NhanVien nv) throws SQLException, Exception{
        QLNVdao qldao = new QLNVdao();
        qldao.xoaNV(nv);
    }
    
    public ResultSet rsAllData(String searchdata) throws SQLException{   //load data lên table Nhân Viên
        QLNVdao qldao = new QLNVdao();
        return qldao.rsAllData(searchdata);
    }
    
    public String changeDateFormat(String dateStr) throws ParseException{
        Date day =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateStr);//định dạng cũ và ép kiểu String thành kiểu Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");//định dạng muốn đạt đc
        String date = simpleDateFormat.format(day);//Ép kiểu Date thành kiểu String
        return date;
    }
    
    public Date changeStrToDate(String date) throws ParseException {
        Date day =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(date);
        return day;
    }
    
    public ArrayList<NhanVien> readNVfromExcel (String excelFilePath) throws FileNotFoundException, IOException{    
       // Đọc một file XSL.
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
       // Đối tượng workbook cho file.
        Workbook workbook = getWorkbook(inputStream, excelFilePath);
       // Lấy ra sheet đầu tiên từ workbook
        Sheet firstSheet = workbook.getSheetAt(0);
       
        Iterator <Row> iterator = firstSheet.iterator(); //từng dòng dữ liệu lấy ra từ sheet đầu tiên
        while (iterator.hasNext()){                     //chạy tiếp nếu còn dòng
            Row nextRow = iterator.next();              //Trả về dòng tiếp theo
            Iterator<Cell> cellIterator = nextRow.cellIterator(); //Mỗi dòng sẽ có nhiều cell (nhiều ô)
            NhanVien nv = new NhanVien();               // Mỗi 1 dòng là 1 nhân viên mới
           
            while (cellIterator.hasNext()) {            //Chạy từng ô trong 1 dòng
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex(); //Lấy stt của column để biết nó là của thuộc tính gì trong nv
                
                switch(columnIndex){
                    case 0:
                        nv.setMaNV((String) getCellValue(nextCell));break;
                    case 1:
                        nv.setHoTen((String) getCellValue(nextCell));break;
                    case 2:
                        nv.setGioiTinh((String) getCellValue(nextCell));break;
                    case 3:
                        nv.setSdt((String) getCellValue(nextCell));break;
                    case 4:
                        nv.setNgSinh((String) getCellValue(nextCell));break;
                    case 5:
                        nv.setNgVL((String) getCellValue(nextCell));break;
                    case 6:
                        nv.setChucVu((String) getCellValue(nextCell));break;
                }
            }
            if(!"".equals(nv.getMaNV())) this.listNV.add(nv);
       }
        workbook.close();
        inputStream.close();    
        return listNV;
    }
    
    private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath)
            throws IOException {
        Workbook workbook = null;
     
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
     
        return workbook;
    }
    private Object getCellValue(Cell cell) {
        switch (cell.getCellType())  {
               case BOOLEAN:
                   return(cell.getBooleanCellValue());
               case BLANK:
                   return("");
               case NUMERIC:
                   return(String.valueOf(cell.getNumericCellValue()));  
               case STRING:
                   return cell.getStringCellValue();
               }
     
        return null;
    }
}
