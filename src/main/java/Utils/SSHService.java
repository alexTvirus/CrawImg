/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.util.ArrayList;
import java.util.List;

public class SSHService {

    public static List<SshInfo> getSSHfromTxt(String path) {
        List<SshInfo> listInfo = new ArrayList<>();
        List<String> lists = null;
        try {
            lists = Doc_file_kieu_txt.readFile(path);
            if (lists != null) {
                for (int i = 0; i < lists.size(); i++) {
                    if (!lists.get(i).equals("") && !lists.get(i).equals(" ")) {
                        SshInfo info = new SshInfo();
                        String[] chuoi_tach = lists.get(i).split("\\|");
                        if (chuoi_tach.length >= 3) {
                            info.setUser(chuoi_tach[1]);
                            info.setPass(chuoi_tach[2]);
                            info.setHost(chuoi_tach[0]);
                            listInfo.add(info);
                        }
                    }
                }
            }
            return listInfo;
        } catch (Exception e) {
            System.out.println("loi doc file");
        }
        return null;
    }
}
