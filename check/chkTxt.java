/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package check;

import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class chkTxt {
    public static boolean isEmptyPass(javax.swing.JPasswordField txt){
        String pass = String.valueOf(txt.getPassword());
        if (pass.length()==0) {
            JOptionPane.showMessageDialog(null,"Thông tin bắt buộc chưa điền","Thông báo!",0);
            return true;
        }
        else
            return false;
    }
}
