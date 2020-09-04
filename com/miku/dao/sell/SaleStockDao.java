package com.miku.dao.sell;

import com.miku.dao.BaseDao;
import com.miku.utils.SplitePage;
import com.miku.vo.salestock.SaleStockInfo;
import com.miku.vo.salestock.SaleStockProInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/19 11:09
 * @description:
 */
public class SaleStockDao extends BaseDao {
    //创建出库单
    public int addSaleStock(SaleStockInfo ssi) {
        return alterTable("INSERT ckd VALUES('" + ssi.getSaleStockId() + "'," + ssi.getClientId() + ",'" + ssi.getClientName() + "','" + new java.sql.Date(ssi.getCreateDate().getTime()) + "'," + ssi.getOughtBalance() + "," + ssi.getRealBalance() + "," + ssi.getBalance() + "," + ssi.getPayState() + "," + ssi.getUserId() + ",'" + ssi.getUserName() + "','" + ssi.getRemark() + "')");
    }

    //修改订单
    public int alterSalStock(Double cbje,String djid){
        return alterTable("update ckd set cbje = "+cbje+" where djid = '"+djid+"'");
    }

    //删除进货单
    public int deleteSaleStock(String saleStockId) {
        //删除订单商品
        alterTable("delete from jhdsp where djid = '" + saleStockId + "'");
        return alterTable("DELETE from ckd where djid = '" + saleStockId + "'");
    }

    //添加商品
    public int addSaleStockPro(SaleStockProInfo sspi) {
        return alterTable("INSERT ckdsp VALUES(DEFAULT,'" + sspi.getSaleStockId() + "','" + sspi.getProId() + "','" + sspi.getProName() + "','" + sspi.getProUnit() + "','" + sspi.getProModel() + "','" + sspi.getTypeId() + "','" + sspi.getTypeName() + "'," + sspi.getSalePrice() + "," + sspi.getProNum() + "," + sspi.getTotalPrice() + ")");
    }

    //修改商品
    public int alterInStockPro(SaleStockProInfo sspi) {
        return alterTable("UPDATE ckdsp set dj = " + sspi.getSalePrice() + ",sl=" + sspi.getProNum() + " where id =" + sspi.getItemId());
    }

    //删除商品
    public int deleteInStockPro(int itemId) {
        return alterTable("delete from ckdsp where id = " + itemId);
    }

    //根据id销售单
    public SaleStockInfo querySaleStockById(String saleStockId) {
        SaleStockInfo saleStockInfo = new SaleStockInfo();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM ckd where djid ='" + saleStockId + "'");
                ResultSet rs = pst.executeQuery()
        ) {
            if (rs.next()) {
                saleStockInfo = new SaleStockInfo(rs.getString("djid"), rs.getInt("khid"), rs.getString("khname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getDouble("cbje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return saleStockInfo;
    }

    //查询所有的出库单
    public ArrayList<SaleStockInfo> queryAllSaleStock() {
        ArrayList<SaleStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM ckd ");

        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SaleStockInfo ssi = new SaleStockInfo(rs.getString("djid"), rs.getInt("khid"), rs.getString("khname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getDouble("cbje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(ssi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //查询某个日期段的出库单
    public ArrayList<SaleStockInfo> queryDatetoDateByPage(Date d1, Date d2, SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        ArrayList<SaleStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM ckd WHERE riqi BETWEEN  ? and ? LIMIT " + start + "," + sp.getPageRows());

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SaleStockInfo ssi = new SaleStockInfo(rs.getString("djid"), rs.getInt("khid"), rs.getString("khname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getDouble("cbje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(ssi);

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
                PreparedStatement pst = con.prepareStatement("SELECT count(djid) FROM ckd WHERE riqi BETWEEN  ? and ?");

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


    //查询某个日期段的进货单,包括客户id
    public ArrayList<SaleStockInfo> queryDatetoDateByPage(int clientId, Date d1, Date d2, SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        ArrayList<SaleStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM chd WHERE ckid = " + clientId + " and riqi BETWEEN  ? and ?  LIMIT " + start + "," + sp.getPageRows());

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SaleStockInfo ssi = new SaleStockInfo(rs.getString("djid"), rs.getInt("khid"), rs.getString("khname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getDouble("cbje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(ssi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int queryAllRowDatetoDate(int clientId, Date d1, Date d2) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT count(djid) FROM chd WHERE  khid = " + clientId + " and riqi BETWEEN  ? and ?");

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


    //返回出库单数量
    public int queryAllRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(djid) from ckd");
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

    //返回当前页出库单
    public ArrayList<SaleStockInfo> querySaleStockByPage(SplitePage sp) {

        ArrayList<SaleStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from ckd LIMIT " + (sp.getCurrentPage() - 1) * sp.getPageRows() + "," + sp.getPageRows());
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


    //查询所有的出库单编号
    public ArrayList<String> queryAllSaleStockId() {
        ArrayList<String> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM ckd ");

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
    public ArrayList<SaleStockProInfo> queryProBySaleStockId(String SaleStockId) {
        ArrayList<SaleStockProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from ckdsp where djid = ?");

        ) {
            pst.setString(1, SaleStockId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SaleStockProInfo ispi = new SaleStockProInfo(rs.getInt("id"), rs.getString("djid"), rs.getString("spid"), rs.getString("spname"), rs.getString("spdw"), rs.getString("spxinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getDouble("dj"), rs.getInt("sl"), rs.getDouble("zj"));
                list.add(ispi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询某个订单的商品数量
//    public int queryAllRowsBySaleStockId(String inStockId) {
//        int i = 0;
//        try (
//                Connection con = openCon();
//                PreparedStatement pst = con.prepareStatement("SELECT COUNT(djid) from jhd where djid = " + inStockId);
//                ResultSet rs = pst.executeQuery();
//        ) {
//            if (rs.next()) {
//                i = rs.getInt(1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return i;
//
//    }

    //根据进货单编号返回当前页进货单
//    public ArrayList<InStockInfo> queryProPageById(SplitePage sp, String inStockId) {
//
//        ArrayList<InStockInfo> list = new ArrayList<>();
//        try (
//                Connection con = openCon();
//                PreparedStatement pst = con.prepareStatement("SELECT * from jhd where djid = " + inStockId + "  LIMIT " + (sp.getCurrentPage() - 1) * sp.getPageRows() + "," + sp.getPageRows());
//                ResultSet rs = pst.executeQuery();
//        ) {
//
//            while (rs.next()) {
//                InStockInfo isi = new InStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
//                list.add(isi);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

}
