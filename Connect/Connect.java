package Connect;

import java.sql.*;
import javax.swing.*;

public class Connect {
    public static Connection conn;
   
    public static Connection getConnection()
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "qltv2", "123");
            return conn;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Kết nối cơ sở dữ liệu thất bại", "Thông báo", 1);
            return null;
        }
    }
}
