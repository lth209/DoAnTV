/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUSlayer;

import DAlayer.QLDGdao;
import DTO.DocGia;
import DTO.NhanVien;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
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
public class QLDG {
    private ArrayList<DocGia> ds = new ArrayList();
    private DocGia info = new DocGia();
    private QLDGdao data = new QLDGdao();
    public QLDG(){};
    public QLDG(DocGia dg)
    {
        info = dg;
    }
    public ResultSet BangThongTinDocGiaViPham() throws SQLException
    {
        return data.BangThongTinDocGiaViPham();
    }
    public ResultSet XemThongTinDocGiaViPham(DocGia dg) throws SQLException
    {
        return data.XemThongTinDocGiaViPham(dg.getMaDG());
    }
    public ResultSet XemCuonSachDocGiaMuon(DocGia dg) throws SQLException
    {
        return data.XemCuonSachDocGiaMuon(dg.getMaDG());
    }

    public ArrayList<DocGia> getDs() {
        return ds;
    }

    public void setDsDG(ArrayList<DocGia> dsDG) {
        this.ds = dsDG;
    }
    
    public void themDG(DocGia dg) throws SQLException{
        QLDGdao da = new QLDGdao();
        da.themDG(dg);
    }
    
    public void suaDG(DocGia dg) throws SQLException{
        QLDGdao da = new QLDGdao();
        da.suaDG(dg);
    }
    
    public ResultSet rsAllData() throws SQLException{   //load data lên table Nhân Viên
        QLDGdao da = new QLDGdao();
        return da.rsAllData();
    }
    
    public ResultSet rsAll4Property() throws SQLException{   //load data lên table Nhân Viên
        QLDGdao da = new QLDGdao();
        return da.rsAll4Property();
    }
    
    public ResultSet rsAllDataOfDocGia(DocGia dg) throws SQLException{  
        QLDGdao da = new QLDGdao();
        return da.rsAllDataOfDocGia(dg.getMaDG());
    }
    public void addDGtolist(DocGia dg){
        this.ds.add(dg);
    }
    
    public void xoaDG() throws Exception{
        QLDGdao da = new QLDGdao();
        if (ds.size()<1) throw new Exception("Chọn ít nhất 1 dòng!");
        for (int i=0; i<ds.size(); i++){
            da.xoaDG(ds.get(i).getMaDG());
        }
    }
    
    public void xoaDG(DocGia dg) throws SQLException{
        QLDGdao da = new QLDGdao();
        da.xoaDG(dg.getMaDG());
    }
    
    public ResultSet DGmuon(DocGia dg) throws SQLException{   
        QLDGdao da = new QLDGdao();
        return da.DGmuon(dg.getMaDG());
    }
    
    public ResultSet tracuuDG(DocGia dg) throws SQLException{   
        QLDGdao da = new QLDGdao();
        return da.tracuuDG(dg);
    }
    
    
    public Date stringToDate(String date) throws ParseException{
        Date day =new SimpleDateFormat("dd-MMM-yyyy").parse(date);
        return day;
    }
    
    public String DateToString(Date date) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");//định dạng muốn đạt đc
        String day = simpleDateFormat.format(date);
        return day;
    }
    
     public ArrayList<DocGia> readNVfromExcel (String excelFilePath) throws FileNotFoundException, IOException, SQLException{
         QLDGdao da = new QLDGdao();
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
            DocGia dg = new DocGia();               // Mỗi 1 dòng là 1 nhân viên mới
           
            while (cellIterator.hasNext()) {            //Chạy từng ô trong 1 dòng
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex(); //Lấy stt của column để biết nó là của thuộc tính gì trong nv
                
                switch(columnIndex){
                    case 0:
                        dg.setMaDG((String) getCellValue(nextCell));break;
                    case 1:
                        dg.setHoTen((String) getCellValue(nextCell));break;
                    case 2:
                        dg.setGioiTinh((String) getCellValue(nextCell));break;
                    case 3:
                        dg.setCmnd((String) getCellValue(nextCell));break;
                    case 4:
                        dg.setNgSinh((String) getCellValue(nextCell));break;
                    case 5:
                        dg.setDiaChi((String) getCellValue(nextCell));break;
                    case 6:
                        dg.setSdt((String) getCellValue(nextCell));break;
                    case 7:
                        dg.setLoaidg((String) getCellValue(nextCell));break;
                    case 8:
                        dg.setGhichu((String) getCellValue(nextCell));break;
                       
                }
            }
            if(!"".equals(dg.getMaDG())) this.ds.add(dg);
            da.themDG(dg);
       }
        workbook.close();
        inputStream.close();    
        return ds;
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
                   return(cell.getStringCellValue());  
               case STRING:
                   return cell.getStringCellValue();
               }
     
        return null;
    }
}
