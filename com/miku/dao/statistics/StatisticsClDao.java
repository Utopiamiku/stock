package com.miku.dao.statistics;

import com.miku.dao.BaseDao;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.instock.InStockProInfo;
import com.miku.vo.salestock.SaleStockInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/22 10:44
 * @description:
 */
public class StatisticsClDao extends BaseDao {

    public ArrayList<SaleStockInfo> query(String sql) {
        ArrayList<SaleStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(sql);
        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SaleStockInfo isi = new SaleStockInfo(rs.getString("djid"), rs.getInt("khid"), rs.getString("khname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"),rs.getDouble("cbje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //供应商有关所有订单
    public ArrayList<SaleStockInfo> queryAllListByAllSupplier() {
        return query("SELECT * from ckd UNION (SELECT * from tkd)");
    }

    //查询某个供应商 某段时间的所有订单
    public ArrayList<SaleStockInfo> queryListBySupplier(int supplierId, Date sDate, Date eDate) {
        return query("SELECT * from ckd where khid =" + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from tkd where khid = " + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询所有供应商 某段时间的所有订单
    public ArrayList<SaleStockInfo> queryListByDate(Date sDate, Date eDate) {
        return query("SELECT * from ckd where riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from tkd where  riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询所有供应商 某段时间的所有已付未付订单
    public ArrayList<SaleStockInfo> queryListByDateState(String state, Date sDate, Date eDate) {
        return query("SELECT * from ckd where  jystate = '" + state + "' and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from tkd where jystate = '" + state + "'and  riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询某个供应商 某段时间的所有已付未付订单
    public ArrayList<SaleStockInfo> queryListBySupplierState(String state, int supplierId, Date sDate, Date eDate) {
        return query("SELECT * from ckd where  jystate = '" + state + "' and khid =" + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'  UNION (SELECT * from tkd where  jystate = '" + state + "' and khid = " + supplierId + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "'  and'" + new java.sql.Date(eDate.getTime()) + "') ");
    }

    //查询某个订单详细信息
    public ArrayList<InStockProInfo> queryProByPid(String pid) {
        ArrayList<InStockProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(" SELECT * from ckdsp where djid = '" + pid + "' UNION (SELECT * from tkdsp where djid = '" + pid + "')");
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
        row = alterTable("UPDATE ckd set jystate = '" + payState + "'where djid = '" + pid + "'");
        row += alterTable("UPDATE tkd set jystate = '" + payState + "'where djid = '" + pid + "'");
        return row;
    }

}
