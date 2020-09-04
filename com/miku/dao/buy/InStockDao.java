package com.miku.dao.buy;

import com.miku.dao.BaseDao;
import com.miku.utils.SplitePage;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.instock.InStockProInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/17 22:22
 * @description:
 */
public class InStockDao extends BaseDao {

    //创建进货单
    public int addInStock(InStockInfo isi) {
        return alterTable("INSERT jhd VALUES('" + isi.getInStockId() + "'," + isi.getSupplierId() + ",'" + isi.getSupplierName() + "','" + new java.sql.Date(isi.getCreateDate().getTime()) + "'," + isi.getOughtBalance() + "," + isi.getRealBalance() + "," + isi.getPayState() + "," + isi.getUserId() + ",'" + isi.getUserName() + "','" + isi.getRemark() + "')");
    }

    //删除进货单
    public int deleteInStock(String inStockId) {
        //删除订单商品
        alterTable("delete from jhdsp where djid = '" + inStockId + "'");
        //删除订单
        return alterTable("DELETE from jhd where djid = '" + inStockId + "'");
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
    public int addInStockPro(InStockProInfo ispi) {
        return alterTable("INSERT jhdsp VALUES(DEFAULT,'" + ispi.getInStockId() + "','" + ispi.getProId() + "','" + ispi.getProName() + "','" + ispi.getProUnit() + "','" + ispi.getProModel() + "','" + ispi.getTypeId() + "','" + ispi.getTypeName() + "'," + ispi.getInPrice() + "," + ispi.getProNum() + "," + ispi.getTotalPrice() + ")");
    }

    //修改商品
    public int alterInStockPro(InStockProInfo ispi) {
        return alterTable("UPDATE jhdsp set dj = " + ispi.getInPrice() + ",sl=" + ispi.getProNum() + " where id =" + ispi.getItemId());
    }

    //删除商品
    public int deleteInStockPro(int itemId) {
        return alterTable("delete from jhdsp where id = " + itemId);
    }

    //根据ID查进货单
    public InStockInfo queryInStockById(String inStockId){
        InStockInfo isi = null;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM jhd WHERE djid = '"+inStockId+"'");
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                isi = new InStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isi;
    }


    //查询某个日期段的进货单
    public ArrayList<InStockInfo> queryDatetoDateByPage(Date d1, Date d2,SplitePage sp) {
        int start = (sp.getCurrentPage()-1)*sp.getPageRows();
        ArrayList<InStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM jhd WHERE riqi BETWEEN  ? and ? LIMIT "+start+","+sp.getPageRows());

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
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
    //总条数
    public int queryAllRowDatetoDate(Date d1, Date d2) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT count(djid) FROM jhd WHERE riqi BETWEEN  ? and ?");

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


    //查询某个日期段的进货单,包括供应商id
    public ArrayList<InStockInfo> queryDatetoDateByPage(int supplierId,Date d1, Date d2,SplitePage sp) {
        int start = (sp.getCurrentPage()-1)*sp.getPageRows();
        ArrayList<InStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM jhd WHERE gysid = "+supplierId+" and riqi BETWEEN  ? and ?  LIMIT "+start+","+sp.getPageRows());

        ) {
            pst.setDate(1, new java.sql.Date(d1.getTime()));
            pst.setDate(2, new java.sql.Date(d2.getTime()));
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
    public int queryAllRowDatetoDate(int supplierId,Date d1, Date d2) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT count(djid) FROM jhd WHERE  gysid = "+supplierId+" and riqi BETWEEN  ? and ?");

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


    //查询所有的进货单
    public ArrayList<InStockInfo> queryAllInStock() {
        ArrayList<InStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM jhd ");

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

    //返回进货单数量
    public int queryAllRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(djid) from jhd");
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

    //返回当前页进货单
    public ArrayList<InStockInfo> queryInStockByPage(SplitePage sp) {

        ArrayList<InStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from jhd LIMIT " + (sp.getCurrentPage() - 1) * sp.getPageRows() + "," + sp.getPageRows());
                ResultSet rs = pst.executeQuery();
        ) {

            while (rs.next()) {
                InStockInfo isi = new InStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询所有的进货单编号
    public ArrayList<String> queryAllInStockId() {
        ArrayList<String> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM jhd ");

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
    public ArrayList<InStockProInfo> queryProByInStockId(String inStockId) {
        ArrayList<InStockProInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from jhdsp where djid = ?");

        ) {
            pst.setString(1, inStockId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                InStockProInfo ispi = new InStockProInfo(rs.getInt("id"), rs.getString("djid"), rs.getString("spid"), rs.getString("spname"), rs.getString("spdw"), rs.getString("spxinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getDouble("dj"), rs.getInt("sl"), rs.getDouble("zj"));
                list.add(ispi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询某个订单的商品数量
    public int queryAllRowsByInStockId(String inStockId) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(djid) from jhd where djid = " + inStockId);
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

    //根据进货单编号返回当前页进货单商品
    public ArrayList<InStockInfo> queryProPageById(SplitePage sp, String inStockId) {

        ArrayList<InStockInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from jhd where djid = " + inStockId + "  LIMIT " + (sp.getCurrentPage() - 1) * sp.getPageRows() + "," + sp.getPageRows());
                ResultSet rs = pst.executeQuery();
        ) {

            while (rs.next()) {
                InStockInfo isi = new InStockInfo(rs.getString("djid"), rs.getInt("gysid"), rs.getString("gysname"), rs.getDate("riqi"), rs.getDouble("yfje"), rs.getDouble("sfje"), rs.getString("jystate"), rs.getInt("userid"), rs.getString("username"), rs.getString("bz"));
                list.add(isi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
