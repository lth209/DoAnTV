/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author admin
 */
public class QuyDinh {
    String maqd, tenqd, noidung;

    public QuyDinh(){
        
    }
    public QuyDinh(String maqd, String tenqd, String noidung) {
        this.maqd = maqd;
        this.tenqd = tenqd;
        this.noidung = noidung;
    }
    
    public String getMaqd() {
        return maqd;
    }

    public void setMaqd(String maqd) {
        this.maqd = maqd;
    }

    public String getTenqd() {
        return tenqd;
    }

    public void setTenqd(String tenqd) {
        this.tenqd = tenqd;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
    
}
