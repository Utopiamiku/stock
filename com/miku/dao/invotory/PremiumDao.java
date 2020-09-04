package com.miku.dao.invotory;

import com.miku.dao.BaseDao;
import com.miku.utils.SplitePage;
import com.miku.vo.inventory.BreakProInfo;
import com.miku.vo.inventory.BreakageInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/21 19:10
 * @description:
 */
public class PremiumDao extends BaseDao {
    //创建报损单
    public int addSaleStock(BreakageInfo pvo) {
        return alterTable("INSERT byd VALUES('" + pvo.getId() + "','" + new java.sql.Date(pvo.getCreatDate().getTime()) + "'," + pvo.getUserId() + ",'" + pvo.getUserName() + "','" + pvo.getRemark() + "')");
    }

    //删除报损单
    public int deleteSaleStock(String pid) {
        //删除订单商品
        alterTable("delete from bydsp where djid = '" + pid + "'");
        return alterTable("DELETE from byd where djid = '" + pid + "'");
    }

    //添加商品
    public int addSaleStockPro(BreakProInfo cvo) {
        return alterTable("INSERT bydsp VALUES(DEFAULT,'" + cvo.getDjId() + "','" + cvo.getProId() + "','" + cvo.getProName() + "','" + cvo.getUnit() + "','" + cvo.getProModel() + "','" + cvo.getPrice() + "'," + cvo.getProNum() + "," + cvo.getTotalPrice() + ")");
    }

    //根据id销售单
    public BreakageInfo querySaleStockById(String pid) {
        BreakageInfo pvo = new BreakageInfo();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM byd where djid ='" + pid + "'");
                ResultSet rs = pst.executeQuery()
        ) {
            if (rs.next()) {
                pvo = new BreakageInfo(rs.getString("djid"), rs.getDate("riqi"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pvo;
    }

    //查询所有的报损单
    public ArrayList<BreakageInfo> queryAllSaleStock() {
        ArrayList<BreakageInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM byd ");

        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BreakageInfo pvo = new BreakageInfo(rs.getString("djid"), rs.getDate("riqi"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));

                list.add(pvo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询某个日期段的报损单
    public ArrayList<BreakageInfo> queryDatetoDateByPage(Date d1, Date d2, SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        ArrayList<BreakageInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM byd WHERE riqi BETWEEN  ? and ? LIMIT " + start + "," + sp.getPageRows());

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BreakageInfo pvo = new BreakageInfo(rs.getString("djid"), rs.getDate("riqi"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(pvo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //总条数
    public int queryAllRowDatetoDate(Date d1, Date d2) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT count(djid) FROM byd WHERE riqi BETWEEN  ? and ?");

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //返回报损单数量
    public int queryAllRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(djid) from byd");
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

    //返回当前页报损单
    public ArrayList<BreakageInfo> querySaleStockByPage(SplitePage sp) {

        ArrayList<BreakageInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from byd LIMIT " + (sp.getCurrentPage() - 1) * sp.getPageRows() + "," + sp.getPageRows());
                ResultSet rs = pst.executeQuery();
        ) {

            while (rs.next()) {
                BreakageInfo pvo = new BreakageInfo(rs.getString("djid"), rs.getDate("riqi"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(pvo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询所有的报损单编号
    public ArrayList<String> queryAllSaleStockId() {
        ArrayList<String> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM byd ");

        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String djid = rs.getString("djid");
                list.add(djid);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询某个订单的所有商品
    public ArrayList<BreakProInfo> queryProBySaleStockId(String pid) {
        ArrayList<BreakProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from bydsp where djid = ?");

        ) {
            pst.setString(1, pid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BreakProInfo cvo = new BreakProInfo(rs.getInt("id"), rs.getString("djid"), rs.getString("spid"), rs.getString("spname"), rs.getString("spdw"), rs.getString("spxinghao"), rs.getDouble("dj"), rs.getInt("sl"), rs.getDouble("zj"));
                list.add(cvo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}



