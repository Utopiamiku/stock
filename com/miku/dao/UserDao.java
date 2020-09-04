package com.miku.dao;

import com.miku.utils.TestTool;
import com.miku.vo.UserInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDao extends BaseDao {

    private RoleDao rd = new RoleDao();
    //添加用户
    public int addUser(UserInfo ui){
        return alterTable("INSERT into users VALUES (DEFAULT,'"+ui.getLoginCode()+"','"+ TestTool.INSTALL.getMD5String(ui.getPassWord())+"','"+ui.getUserName()+"',DEFAULT,0,'"+ui.getRemark()+"');");
    }
    //增加用户角色
    public int addUser(int uid,int roleId){
        return alterTable("INSERT into user_role VALUES("+uid+","+roleId+")");
    }


    //删除用户
    public int deleteUser(int uid){
        //删除用户角色
        deleteUserRole(uid);
        //再删除用户账号
        return alterTable("DELETE  from users where userId = "+uid);
    }

    public int deleteUserRole(int uid){
        //删除用户角色
       return alterTable("DELETE  from user_role where userId = "+uid);
    }

    //修改用户
    public int alterUser(UserInfo ui){
        return alterTable("UPDATE  users set password = '"+TestTool.INSTALL.getMD5String(ui.getPassWord())+"',username = '"+ui.getUserName()+"' ,bz='"+ui.getRemark()+"' where logincode  = '"+ui.getLoginCode()+"'");
    }

    //根据账号返回用户
    public UserInfo queryUserByLoginCode(String loginCode) {
        UserInfo ui = null;
        try (
                Connection con = super.openCon();
        ) {
            PreparedStatement pst = con.prepareStatement("SELECT * from users where logincode = ?");
            pst.setString(1, loginCode);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
                ui = new UserInfo(rs.getInt("userid"), rs.getString("logincode"), rs.getString("password"), rs.getString("username"), rs.getInt("roleid"), rs.getInt("state"), rs.getString("bz"));


        } catch (Exception e) {
            e.printStackTrace();
            return ui;
        }
        return ui;
    }

    //返回所有用户
    public ArrayList<UserInfo> queryAllUser() {
        ArrayList<UserInfo> list = new ArrayList<>();
        UserInfo ui = null;
        try (
                Connection con = super.openCon();
        ) {
            PreparedStatement pst = con.prepareStatement("SELECT * from users");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ui = new UserInfo(rs.getInt("userid"), rs.getString("logincode"), rs.getString("password"), rs.getString("username"), rs.getInt("roleid"), rs.getInt("state"), rs.getString("bz"));

                ui.setRoleList( rd.queryAllRoleNameByUserId(ui.getUserId()));
                list.add(ui);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//返回所有用户loginCode  做验证用
    public ArrayList<String> queryAllUserLoginCode() {
        ArrayList<String> list = new ArrayList<>();

        try (
                Connection con = super.openCon();
        ) {
            PreparedStatement pst = con.prepareStatement("SELECT * from users");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String a =rs.getString("logincode");
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }
}
