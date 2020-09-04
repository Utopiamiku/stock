package com.miku.dao.buy;

import com.miku.dao.BaseDao;
import com.miku.utils.SplitePage;
import com.miku.vo.SupplierInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/12 15:22
 * @File SupplierDao.py
 */
public class SupplierDao extends BaseDao {
    //返回所有供应商
    public ArrayList<SupplierInfo> queryAllSupplier() {
        ArrayList<SupplierInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from gys");
                ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                SupplierInfo si = new SupplierInfo(rs.getInt("gysid"), rs.getString("name"), rs.getString("lxren"), rs.getString("lxtel"), rs.getString("address"), rs.getString("bz"));
                list.add(si);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //返回数据总条数
    public int queryAllSupplierRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(gysid) from gys");
                ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //返回当前页供应商
    public ArrayList<SupplierInfo> queryAllSupplierByPage(SplitePage sp) {
        ArrayList<SupplierInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from gys LIMIT ?,?");

        ) {
            pst.setInt(1, (sp.getCurrentPage() - 1) * sp.getPageRows());
            pst.setInt(2, sp.getPageRows());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SupplierInfo si = new SupplierInfo(rs.getInt("gysid"), rs.getString("name"), rs.getString("lxren"), rs.getString("lxtel"), rs.getString("address"), rs.getString("bz"));
                list.add(si);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据供应商id返回供应商
    public SupplierInfo querySupplierById(int supplierId) {
        SupplierInfo si =null;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from gys where gysid = " + supplierId);
                ResultSet rs = pst.executeQuery();
        ) {
            if (rs.next()){
                si = new SupplierInfo(rs.getInt("gysid"), rs.getString("name"), rs.getString("lxren"), rs.getString("lxtel"), rs.getString("address"), rs.getString("bz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return si;
    }

    //添加供应商  供应商名称不能为空
    public int addSupplier(SupplierInfo si) {
        return alterTable("INSERT into gys VALUES(DEFAULT,'" + si.getSupplierName() + "','" + si.getContact() + "','" + si.getContactPhoneNum() + "','" + si.getSupplierAddress() + "','" + si.getRemark() + "')");
    }

    //修改供应商 供应商名称不能为空
    public int alterSupplier(SupplierInfo si) {
        return alterTable("UPDATE gys set name='" + si.getSupplierName() + "',lxren='" + si.getContact() + "',lxtel='" + si.getContactPhoneNum() + "',address='" + si.getSupplierAddress() + "',bz='" + si.getRemark() + "' where gysid = " + si.getSupplierId());
    }

    //删除供应商
    public int deleteSupplier(int supplierId) {
        return alterTable("DELETE  from gys where gysid = " + supplierId);
    }


}
