/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAlayer;

import Connect.Connect;
import DTO.DocGia;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class QLDGdao {
    private static Connection conn = Connect.getConnection();
    private static String sql = null;
    private static PreparedStatement pst = null;
    private static Statement st = null;
    private static ResultSet rs= null;
    private static CallableStatement cs = null;
    
    public ResultSet BangThongTinDocGiaViPham() throws SQLException
    {
        sql = "SELECT DOCGIA.MADG  \"MÃ ĐỘC GIẢ\", THEDOCGIA.MATHE  \"MÃ THẺ\", HOTEN \"HỌ TÊN\", SODT \"SDT\", SUM(PHIEUPHAT.TIENPHAT) \"TIỀN PHẠT\" FROM DOCGIA, THEDOCGIA, PHIEUPHAT WHERE DOCGIA.MADG=PHIEUPHAT.MADG AND DOCGIA.MADG=THEDOCGIA.MADG GROUP BY DOCGIA.MADG, THEDOCGIA.MATHE, HOTEN, SODT, DIACHI, NGSINH";
        st = conn.createStatement();
        return st.executeQuery(sql);
    }
    public ResultSet XemThongTinDocGiaViPham(String MaDG) throws SQLException
    {
        sql = "SELECT DOCGIA.MADG \"MÃ ĐỘC GIẢ\", THEDOCGIA.MATHE\"MÃ THẺ\", HOTEN\"HỌ TÊN\", DIACHI\"ĐỊA CHỈ\", NGSINH \"NGÀY SINH\", SODT \"SỐ ĐIỆN THOẠI\", SUM(PHIEUPHAT.TIENPHAT) \"TIENPHAT\" FROM DOCGIA, THEDOCGIA, PHIEUPHAT WHERE DOCGIA.MADG=PHIEUPHAT.MADG AND DOCGIA.MADG=THEDOCGIA.MADG AND DOCGIA.MADG = '"+MaDG+"' GROUP BY DOCGIA.MADG, THEDOCGIA.MATHE, HOTEN, SODT, DIACHI, NGSINH";
        st = conn.createStatement();
        return st.executeQuery(sql);
    }
    public ResultSet XemCuonSachDocGiaMuon(String MaDG) throws SQLException
    {
        sql = "SELECT CUONSACH.MACUONSACH \"MÃ CUỐN SÁCH\", TUASACH.TENSACH \"TÊN CUỐN SÁCH\", PHIEUMUON.NGLAP \"NGÀY MƯỢN\" FROM PHIEUMUON, CTPHIEUMUON, TUASACH, CUONSACH WHERE PHIEUMUON.MAPHIEUMUON=CTPHIEUMUON.MAPHIEUMUON AND CTPHIEUMUON.MACUONSACH=CUONSACH.MACUONSACH AND TUASACH.MATUASACH=CUONSACH.MATUASACH AND PHIEUMUON.MADG = '"+MaDG+"'";
        st = conn.createStatement();
        return st.executeQuery(sql);
    }
    public void themDG(DocGia dg) throws SQLException{
        sql = "INSERT INTO DOCGIA "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        pst = conn.prepareStatement(sql);
        pst.setString(1, dg.getMaDG());
        pst.setString(2, dg.getHoTen());
        pst.setString(3, dg.getGioiTinh());
        pst.setString(4, dg.getCmnd());
        pst.setString(5, dg.getNgSinh());
        pst.setString(6, dg.getDiaChi());
        pst.setString(7, dg.getSdt());
        pst.setString(8, dg.getLoaidg());
        pst.setString(9, dg.getGhichu());
        pst.execute();
        
    }
    
    public void suaDG(DocGia dg) throws SQLException{
        sql = "UPDATE DOCGIA "
                + "SET HoTen=?, GioiTinh=?,NgSinh=?, CMND=?, DiaChi=?, SoDT=?, LoaiDG=?, GhiChu=? WHERE MaDG=? ";
        pst = conn.prepareStatement(sql);
        pst.setString(1, dg.getHoTen());
        pst.setString(2, dg.getGioiTinh());
        pst.setString(3, dg.getNgSinh());
        pst.setString(4, dg.getCmnd());
        pst.setString(5, dg.getDiaChi());
        pst.setString(6, dg.getSdt());
        pst.setString(7, dg.getLoaidg());
        pst.setString(8, dg.getGhichu());
        pst.setString(9, dg.getMaDG());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Đã sửa Độc giả thành công", "Thông báo", 1);
    }
    
    public ResultSet rsAllData() throws SQLException{   //load data lên table Nhân Viên
        sql = "SELECT * FROM DOCGIA";//Nhập in hoa hay thường lộn xộn vẫn search đc
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            //tblNV.setModel(DbUtils.resultSetToTableModel(rs));
            return rs;
    }
    
    public ResultSet rsAll4Property() throws SQLException{   //load data lên table Nhân Viên
        sql = "SELECT MADG \"MÃ ĐỘC GIẢ\", HOTEN\"HỌ TÊN\", CMND, SODT\"SỐ ĐIỆN THOẠI\" FROM DOCGIA";//Nhập in hoa hay thường lộn xộn vẫn search đc
     
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        
            //tblNV.setModel(DbUtils.resultSetToTableModel(rs));
        return rs;
    }
    
    public ResultSet rsAllDataOfDocGia(String madg) throws SQLException{   //load data lên table Nhân Viên
        sql = "SELECT * FROM DOCGIA WHERE MADG=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, madg);
            rs = pst.executeQuery();
            
            //tblNV.setModel(DbUtils.resultSetToTableModel(rs));
            return rs;
    }
    
    public void xoaDG(String madg) throws SQLException{
            sql = "{call sp_XOADOCGIA(?)}";
            cs = conn.prepareCall(sql);
            cs.setString(1,madg);
            cs.execute();
    }
    
    public ResultSet DGmuon(String madg) throws SQLException{
        sql = "SELECT PM.MAPHIEUMUON \"MÃ PHIẾU MƯỢN\", CS.MACUONSACH \"MÃ CUỐN SÁCH\", TS.TENSACH \"TÊN SÁCH\", PM.NGLAP \"NGÀY LẬP\", PM.NGHETHAN \"NGÀY HẾT HẠN\"" +
"    FROM PHIEUMUON PM, CTPHIEUMUON CT, CUONSACH CS, TUASACH TS" +
"    WHERE PM.MAPHIEUMUON=CT.MAPHIEUMUON AND CT.MACUONSACH=CS.MACUONSACH AND TS.MATUASACH=CS.MATUASACH AND UPPER(PM.MADG) = UPPER(?)";
        pst = conn.prepareStatement(sql);
        pst.setString(1, madg);
        rs = pst.executeQuery();
        //tblNV.setModel(DbUtils.resultSetToTableModel(rs));
        return rs;
    }
    
    public ResultSet tracuuDG(DocGia dg) throws SQLException{
        int []arr = new int[8];
        int i=0;
        sql = "SELECT MADG \"MÃ ĐỘC GIẢ\", HOTEN \"HỌ TÊN\", CMND, SODT \"SỐ ĐIỆN THOẠI\" FROM DOCGIA WHERE ";
        if (dg.getMaDG().length()!=0) {sql+="UPPER(MADG)=UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        if (dg.getHoTen().length()!=0) {sql+="UPPER(HOTEN) like UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        if (dg.getGioiTinh().length()!=0) {sql+="UPPER(GIOITINH)=UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        if (dg.getNgSinh().length()!=0) {sql+="UPPER(NGSINH)=UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        if (dg.getCmnd().length()!=0) {sql+="UPPER(CMND) like UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        if (dg.getDiaChi().length()!=0) {sql+="UPPER(DIACHI) like UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        if (dg.getSdt().length()!=0) {sql+="UPPER(SODT) like UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        if (dg.getLoaidg().length()!=0) {sql+="UPPER(LOAIDG)=UPPER(?) AND ";arr[i]=1;}
        else arr[i]=0;i++;
        int size=sql.length();
        sql=sql.substring(0, size-4);
        pst = conn.prepareStatement(sql);
        i=1;
        for (int j=0; j<8; j++){
            if (arr[j]==1&&j==0) {pst.setString(i, dg.getMaDG());i++;}
            if (arr[j]==1&&j==1) {pst.setString(i, "%"+dg.getHoTen()+"%");i++;}
            if (arr[j]==1&&j==2) {pst.setString(i, dg.getGioiTinh());i++;}
            if (arr[j]==1&&j==3) {pst.setString(i, dg.getNgSinh());i++;}
            if (arr[j]==1&&j==4) {pst.setString(i, "%"+dg.getCmnd()+"%");i++;}
            if (arr[j]==1&&j==5) {pst.setString(i, "%"+dg.getDiaChi()+"%");i++;}
            if (arr[j]==1&&j==6) {pst.setString(i, "%"+dg.getSdt()+"%");i++;}
            if (arr[j]==1&&j==7) {pst.setString(i, dg.getLoaidg());i++;}
        }
        rs = pst.executeQuery();
        return rs;
    }
}
