package BUSlayer;

import java.sql.*;
import javax.swing.*;
import Connect.*;
import DAlayer.Userdao;
import DTO.NhanVien;
import DTO.User;

public class Login {
    public static Connection conn = null;
    public static User user = new User();
    private Userdao ql = new Userdao();
    private NhanVien nv = new NhanVien();
    
    public Login() {
    }

    public NhanVien getNv() {
        return nv;
    }
    
    
    public boolean Login(User us) throws SQLException
    {
        
        //User user = new User();
        user = ql.Login(us);
        nv = ql.userInfo();
        if(user!=null){
            setUser(user);
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public void setUser(User us){
        user = us;
    }
    
}
