/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUSlayer;

import DAlayer.QLNSdao;
import DTO.CTHD;
import DTO.HoaDon;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class QLNS {
    private QLNSdao dao = new QLNSdao();
    
    public void themHD(HoaDon hd) throws SQLException{
        dao.themHD(hd);
    }
    
    public void themCTHD(CTHD ct) throws SQLException{
        dao.themCTHD(ct);
    }
    
    public ResultSet getDataFromCTHD(String mahd) throws SQLException{
        return dao.getDataFromCTHD(mahd);
    }
}
