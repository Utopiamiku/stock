package com.miku.dao.statistics;

import com.miku.dao.BaseDao;
import com.miku.vo.instock.InStockProInfo;
import com.miku.vo.instock.StatisticsProInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/23 14:56
 * @description:
 */
public class StatisticsBuyDao extends BaseDao {

    private ArrayList<StatisticsProInfo> query(String sql) {
        ArrayList<StatisticsProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                StatisticsProInfo ispi = new StatisticsProInfo(rs.getInt("id"), rs.getString("djid"), rs.getString("spid"), rs.getString("spname"), rs.getString("spdw"), rs.getString("spxinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getDouble("dj"), rs.getInt("sl"), rs.getDouble("zj"),rs.getString("gysname"),rs.getDate("riqi"));
                list.add(ispi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//查询所有商品
    public ArrayList<StatisticsProInfo> queryAllPro(){
        return query("SELECT jhdsp.*,jhd.gysname,jhd.riqi from jhdsp RIGHT JOIN jhd on jhdsp.djid = jhd.djid \n" +
                "UNION (SELECT thdsp.*,thd.gysname,thd.riqi from thdsp RIGHT JOIN thd on thdsp.djid = thd.djid )\n");
    }

    //根据日期
    public ArrayList<StatisticsProInfo> queryProByDate(Date sDate,Date eDate){
        return query("SELECT jhdsp.*,jhd.gysname,jhd.riqi from jhdsp RIGHT JOIN jhd on jhdsp.djid = jhd.djid where riqi BETWEEN '"+new java.sql.Date(sDate.getTime()) +"' and '"+new java.sql.Date(eDate.getTime()) +"'\n" +
                "UNION (SELECT thdsp.*,thd.gysname,thd.riqi from thdsp RIGHT JOIN thd on thdsp.djid = thd.djid where riqi BETWEEN '"+new java.sql.Date(sDate.getTime()) +"' and '"+new java.sql.Date(eDate.getTime()) +"')\n");
    }
    //类别
    public ArrayList<StatisticsProInfo> queryProByDateTy(int lbid,Date sDate,Date eDate){
        return query("SELECT jhdsp.*,jhd.gysname,jhd.riqi from jhdsp RIGHT JOIN jhd on jhdsp.djid = jhd.djid where lbid = "+lbid+" and riqi BETWEEN '"+new java.sql.Date(sDate.getTime()) +"' and '"+new java.sql.Date(eDate.getTime()) +"'\n" +
                "UNION (SELECT thdsp.*,thd.gysname,thd.riqi from thdsp RIGHT JOIN thd on thdsp.djid = thd.djid where lbid = "+lbid+" and riqi BETWEEN '"+new java.sql.Date(sDate.getTime()) +"' and '"+new java.sql.Date(eDate.getTime()) +"')\n");
    }

    //根据编号或名称
    public ArrayList<StatisticsProInfo> queryProByIdName(String idName){
        return query("SELECT jhdsp.*,jhd.gysname,jhd.riqi from jhdsp RIGHT JOIN jhd on jhdsp.djid = jhd.djid where spid = '"+idName+"' or spname = '"+idName+"'\n" +
                "UNION (SELECT thdsp.*,thd.gysname,thd.riqi from thdsp RIGHT JOIN thd on thdsp.djid = thd.djid where spid = '"+idName+"' or spname = '"+idName+"')");
    }
}
