package com.miku.dao.statistics;

import com.miku.dao.BaseDao;
import com.miku.vo.instock.StatisticsProInfo;
import com.miku.vo.salestock.SaleStockInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/23 16:15
 * @description:
 */
public class StatisticsSaleDao extends BaseDao {

    private ArrayList<StatisticsProInfo> query(String sql) {
        ArrayList<StatisticsProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                StatisticsProInfo ispi = new StatisticsProInfo(rs.getInt("id"), rs.getString("djid"), rs.getString("spid"), rs.getString("spname"), rs.getString("spdw"), rs.getString("spxinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getDouble("dj"), rs.getInt("sl"), rs.getDouble("zj"), rs.getString("khname"), rs.getDate("riqi"));
                list.add(ispi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询某天的出库单
    public ArrayList<SaleStockInfo> querySaleStock(Date d) {
        ArrayList<SaleStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from ckd where riqi = '" + new java.sql.Date(d.getTime()) + "'");
                ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                SaleStockInfo ssi = new SaleStockInfo(rs.getString("djid"), rs.getInt("khid"), rs.getString("khname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getDouble("cbje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(ssi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询某段时间的出库单
    public ArrayList<SaleStockInfo> querySaleStock(Date sDate, Date eDate) {
        ArrayList<SaleStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from ckd where riqi between  '" + new java.sql.Date(sDate.getTime()) + "'and '" + new java.sql.Date(eDate.getTime()) + "'");
                ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                SaleStockInfo ssi = new SaleStockInfo(rs.getString("djid"), rs.getInt("khid"), rs.getString("khname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getDouble("cbje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(ssi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //查询所有商品
    public ArrayList<StatisticsProInfo> queryAllPro() {
        return query("SELECT ckdsp.*,ckd.khname,ckd.riqi from ckdsp RIGHT JOIN ckd on ckdsp.djid = ckd.djid \n" +
                "UNION (SELECT tkdsp.*,tkd.khname,tkd.riqi from tkdsp RIGHT JOIN tkd on tkdsp.djid = tkd.djid )\n");
    }

    //根据日期
    public ArrayList<StatisticsProInfo> queryProByDate(Date sDate, Date eDate) {
        return query("SELECT ckdsp.*,ckd.khname,ckd.riqi from ckdsp RIGHT JOIN ckd on ckdsp.djid = ckd.djid where riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'\n" +
                "UNION (SELECT tkdsp.*,tkd.khname,tkd.riqi from tkdsp RIGHT JOIN tkd on tkdsp.djid = tkd.djid where riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "')\n");
    }

    //类别
    public ArrayList<StatisticsProInfo> queryProByDateTy(int lbid, Date sDate, Date eDate) {
        return query("SELECT ckdsp.*,ckd.khname,ckd.riqi from ckdsp RIGHT JOIN ckd on ckdsp.djid = ckd.djid where lbid = " + lbid + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "'\n" +
                "UNION (SELECT tkdsp.*,tkd.khname,tkd.riqi from tkdsp RIGHT JOIN tkd on tkdsp.djid = tkd.djid where lbid = " + lbid + " and riqi BETWEEN '" + new java.sql.Date(sDate.getTime()) + "' and '" + new java.sql.Date(eDate.getTime()) + "')\n");
    }

    //根据编号或名称
    public ArrayList<StatisticsProInfo> queryProByIdName(String idName) {
        return query("SELECT ckdsp.*,ckd.khname,ckd.riqi from ckdsp RIGHT JOIN ckd on ckdsp.djid = ckd.djid where spid = '" + idName + "' or spname = '" + idName + "'\n" +
                "UNION (SELECT tkdsp.*,tkd.khname,tkd.riqi from tkdsp RIGHT JOIN tkd on tkdsp.djid = tkd.djid where spid = '" + idName + "' or spname = '" + idName + "')");
    }
}


