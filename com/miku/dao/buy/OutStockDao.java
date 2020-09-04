package com.miku.dao.buy;

import com.miku.dao.BaseDao;
import com.miku.utils.SplitePage;
import com.miku.vo.instock.OutStockInfo;
import com.miku.vo.instock.OutStockProInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/21 11:24
 * @description:
 */
public class OutStockDao extends BaseDao {
    //创建退货单
    public int addOutStock(OutStockInfo isi) {
        return alterTable("INSERT thd VALUES('" + isi.getOutStockId() + "'," + isi.getSupplierId() + ",'" + isi.getSupplierName() + "','" + new java.sql.Date(isi.getCreateDate().getTime()) + "'," + isi.getOughtBalance() + "," + isi.getRealBalance() + "," + isi.getPayState() + "," + isi.getUserId() + ",'" + isi.getUserName() + "','" + isi.getRemark() + "')");
    }

    //删除退货单
    public int deleteOutStock(String inStockId) {
        //删除订单商品
        alterTable("delete from thdsp where djid = '" + inStockId + "'");
        //删除订单
        return alterTable("DELETE from thd where djid = '" + inStockId + "'");
    }

    //查询进货单里边有没有此商品id
//    public int queryRowByInStockId(String spId) {
//        int i = 0;
//        try (
//                Connection con = openCon();
//                PreparedStatement pst = con.prepareStatement("SELECT COUNT(spid) from jhdsp where spid = '" + spId + "'");
//                ResultSet rs = pst.executeQuery();
//        ) {
//            if(rs.next()){
//                i = rs.getInt(1);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return i;
//    }

    //添加商品
    public int addInStockPro(OutStockProInfo ispi) {
        return alterTable("INSERT thdsp VALUES(DEFAULT,'" + ispi.getInStockId() + "','" + ispi.getProId() + "','" + ispi.getProName() + "','" + ispi.getProUnit() + "','" + ispi.getProModel() + "','" + ispi.getTypeId() + "','" + ispi.getTypeName() + "'," + ispi.getInPrice() + "," + ispi.getProNum() + "," + ispi.getTotalPrice() + ")");
    }

    //修改商品
    public int alterInStockPro(OutStockProInfo ispi) {
        return alterTable("UPDATE thdsp set dj = " + ispi.getInPrice() + ",sl=" + ispi.getProNum() + " where id =" + ispi.getItemId());
    }

    //删除商品
    public int deleteInStockPro(int itemId) {
        return alterTable("delete from thdsp where id = " + itemId);
    }

    //根据ID查进货单
    public OutStockInfo queryOutStockById(String inStockId){
        OutStockInfo isi = null;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM thd WHERE djid = '"+inStockId+"'");
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                isi = new OutStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isi;
    }


    //查询某个日期段的退货单
    public ArrayList<OutStockInfo> queryDatetoDateByPage(Date d1, Date d2, SplitePage sp) {
        int start = (sp.getCurrentPage()-1)*sp.getPageRows();
        ArrayList<OutStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM thd WHERE riqi BETWEEN  ? and ? LIMIT "+start+","+sp.getPageRows());

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OutStockInfo isi = new OutStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);

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
                PreparedStatement pst = con.prepareStatement("SELECT count(djid) FROM thd WHERE riqi BETWEEN  ? and ?");

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


    //查询某个日期段的退货单,包括供应商id
    public ArrayList<OutStockInfo> queryDatetoDateByPage(int supplierId,Date d1, Date d2,SplitePage sp) {
        int start = (sp.getCurrentPage()-1)*sp.getPageRows();
        ArrayList<OutStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM thd WHERE gysid = "+supplierId+" and riqi BETWEEN  ? and ?  LIMIT "+start+","+sp.getPageRows());

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OutStockInfo isi = new OutStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int queryAllRowDatetoDate(int supplierId,Date d1, Date d2) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT count(djid) FROM thd WHERE  gysid = "+supplierId+" and riqi BETWEEN  ? and ?");

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


    //查询所有的退货单
    public ArrayList<OutStockInfo> queryAllInStock() {
        ArrayList<OutStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM thd ");

        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OutStockInfo isi = new OutStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //返回退货单数量
    public int queryAllRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(djid) from thd");
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

    //返回当前页退货单
    public ArrayList<OutStockInfo> queryInStockByPage(SplitePage sp) {

        ArrayList<OutStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from thd LIMIT " + (sp.getCurrentPage() - 1) * sp.getPageRows() + "," + sp.getPageRows());
                ResultSet rs = pst.executeQuery();
        ) {

            while (rs.next()) {
                OutStockInfo isi = new OutStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询所有的退货单编号
    public ArrayList<String> queryAllInStockId() {
        ArrayList<String> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM thd ");

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
    public ArrayList<OutStockProInfo> queryProByInStockId(String inStockId) {
        ArrayList<OutStockProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from thdsp where djid = ?");

        ) {
            pst.setString(1, inStockId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OutStockProInfo ispi = new OutStockProInfo(rs.getInt("id"), rs.getString("djid"), rs.getString("spid"), rs.getString("spname"), rs.getString("spdw"), rs.getString("spxinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getDouble("dj"), rs.getInt("sl"), rs.getDouble("zj"));
                list.add(ispi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    //查询某个订单的商品数量
//    public int queryAllRowsByInStockId(String inStockId) {
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
//
//    //根据进货单编号返回当前页进货单商品
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


