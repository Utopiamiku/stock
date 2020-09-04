package com.miku.dao.statistics;

import com.miku.dao.BaseDao;
import com.miku.dao.invotory.PremiumDao;
import com.miku.utils.SplitePage;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.instock.InStockProInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/22 0:05
 * @description:
 */

public class StatisticsSuDao extends BaseDao {
    public ArrayList<InStockInfo> query(String sql) {
        ArrayList<InStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(sql);
        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                InStockInfo isi = new InStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //供应商有关所有订单
    public ArrayList<InStockInfo> queryAllListByAllSupplier() {
        return query("SELECT * from jhd UNION (SELECT * from thd)");
    }

    //查询某个供应商 某段时间的所有订单
    public ArrayList<InStockInfo> queryListBySupplier(int supplierId, Date sDate, Date eDate) {
        return query("SELECT * from jhd where gysid =" + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from thd where gysid = " + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询所有供应商 某段时间的所有订单
    public ArrayList<InStockInfo> queryListByDate(Date sDate, Date eDate) {
        return query("SELECT * from jhd where riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from thd where  riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询所有供应商 某段时间的所有已付未付订单
    public ArrayList<InStockInfo> queryListByDateState(String state, Date sDate, Date eDate) {
        return query("SELECT * from jhd where  jystate = '" + state + "' and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from thd where jystate = '" + state + "'and  riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询某个供应商 某段时间的所有已付未付订单
    public ArrayList<InStockInfo> queryListBySupplierState(String state, int supplierId, Date sDate, Date eDate) {
        return query("SELECT * from jhd where  jystate = '" + state + "' and gysid =" + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from thd where  jystate = '" + state + "' and gysid = " + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询某个订单详细信息
    public ArrayList<InStockProInfo> queryProByPid(String pid) {
        ArrayList<InStockProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(" SELECT * from jhdsp where djid = '" + pid + "' UNION (SELECT * from thdsp where djid = '" + pid + "')");
                ResultSet rs = pst.executeQuery();
        ) {

           while(rs.next()){
               InStockProInfo cvo = new InStockProInfo(rs.getInt("id"), rs.getString("djid"), rs.getString("spid"), rs.getString("spname"), rs.getString("spdw"), rs.getString("spxinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getDouble("dj"), rs.getInt("sl"), rs.getDouble("zj"));
               list.add(cvo);
           }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //更改订单付款状态
    public int alterStateByPid(String payState, String pid) {
        int row = 0;
        row = alterTable("UPDATE jhd set jystate = '" + payState + "'where djid = '" + pid + "'");
        row += alterTable("UPDATE thd set jystate = '" + payState + "'where djid = '" + pid + "'");
        return row;
    }

}
