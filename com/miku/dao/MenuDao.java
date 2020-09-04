package com.miku.dao;

import com.miku.vo.PermissionInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/9 14:02
 * @File MuenDao.py
 */
public class MenuDao extends BaseDao {

    //查询共有方法
    private ArrayList<PermissionInfo> query(String sql) {
        ArrayList<PermissionInfo> list = new ArrayList<>();
        try (
                Connection con = super.openCon();
        ) {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                PermissionInfo mi = new PermissionInfo(rs.getInt("menuid"),rs.getString("menuname"),rs.getInt("pid"),rs.getString("menuurl"),rs.getInt("menutype"),rs.getInt("ordernum"),rs.getString("icon"));
                list.add(mi);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    //pid 是外键 菜单所属是谁
    //roleid要求用户有访问权限
    //根据用户返回其全限
    public ArrayList<PermissionInfo> queryParentMenu(int userId) {
        return this.query("SELECT * from menu where menuid in(select menuid from rolemenu where roleid in(select roleId from user_role where userid ="+userId+"))");
    }

    //返回所有权限
    public ArrayList<PermissionInfo> queryAllMenu(){
        return query("SELECT * from menu where menuid!=0");
    }


}
