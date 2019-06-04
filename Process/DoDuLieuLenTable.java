package Process;

import java.sql.*;
import javax.swing.*;
import Connect.*;
import net.proteanit.sql.*;

public class DoDuLieuLenTable {
    public static PreparedStatement pst = null; //biến thực thi sql
    public static ResultSet rs = null; //kết quả trả về là 1 bảng hoặc 1 dòng dữ liệu
    public static Connection conn = Connect.getConnection();
    
    //Nạp dữ liệu cho 1 bảng
    public static void LoadDataIntoTable(String sql, JTable tb)
    {
        try
        {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tb.setModel(DbUtils.resultSetToTableModel(rs)); //nạp dữ liệu lên cái bảng mà mình truyền vào
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Thông báo lỗi", 1);
        }
    }
    //Hàm cho dữ liệu từ 1 JTable lên Textfield
    public static ResultSet ShowTextField(String sql)
    {
        try
        {
            pst = conn.prepareStatement(sql);
            return pst.executeQuery();
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
