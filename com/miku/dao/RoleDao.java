package com.miku.dao;

import com.miku.utils.SplitePage;
import com.miku.vo.RoleInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/10 22:39
 * @File RoleDao.py
 */
public class RoleDao extends BaseDao {

    //查询所有角色
    public ArrayList<RoleInfo> queryAllRole() {
        ArrayList<RoleInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from role");
        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RoleInfo ri = new RoleInfo(rs.getInt("roleid"), rs.getString("rolename"), rs.getString("bz"));
                list.add(ri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    //根据用户userid返回当前用户角色
    public ArrayList<String> queryAllRoleNameByUserId(int userid) {
        ArrayList<String> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from role where roleid in(select roleId from user_role where userid = ?)");

        ) {
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RoleInfo ri = new RoleInfo(rs.getInt("roleid"), rs.getString("rolename"), rs.getString("bz"));
                list.add(ri.getRoleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    //根据角色id返回该id权限
    public ArrayList<Integer> queryPerssionByroleId(int roleId){
        ArrayList<Integer> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from rolemenu where roleId = "+roleId);
                ){
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("menuid"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询所有角色名称
    public ArrayList<String> queryAllRoleName() {
        ArrayList<String> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from role");
        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RoleInfo ri = new RoleInfo(rs.getInt("roleid"), rs.getString("rolename"), rs.getString("bz"));
                list.add(ri.getRoleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    //查询数据总条数
    public int queryAllRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(roleid) from role ");
                ResultSet rs = pst.executeQuery();
        ) {
            if (rs.next()) {
                i = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //根据当前页数返回角色
    public ArrayList<RoleInfo> queryRoleByPage(SplitePage sp) {
        ArrayList<RoleInfo> list = new ArrayList<>();
        int start = (sp.getCurrentPage() - 1)*sp.getPageRows();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from role LIMIT "+start+","+sp.getPageRows());
        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                RoleInfo ri = new RoleInfo(rs.getInt("roleid"), rs.getString("rolename"), rs.getString("bz"));
                list.add(ri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //添加角色
    public int addRole(RoleInfo ri) {
        return alterTable("INSERT into role VALUES(DEFAULT,'" + ri.getRoleName() + "','" + ri.getRemark() + "')");
    }

    //添加角色权限
    public int addRole(int roleId, int menuId) {
        return alterTable("INSERT INTO rolemenu VALUES(" + roleId + "," + menuId + ")");
    }

    //删除角色权限
    public int deleteRoleMenu(int roleId) {
        return alterTable("DELETE from rolemenu where roleid = " + roleId);
    }

    //修改角色
    public int alterRole(RoleInfo ri) {
        return alterTable("UPDATE role set rolename = '" + ri.getRoleName() + "',bz = '" + ri.getRemark() + "' where roleid = " + ri.getRoleId());
    }

    //删除角色
    public int deleteRole(int roleId) {
        //先删除权限
        deleteRoleMenu(roleId);
//        再删除角色
        return alterTable("DELETE from role where roleid = " + roleId);
    }
}
