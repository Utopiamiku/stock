package com.miku.dao;

import com.miku.utils.SplitePage;
import com.miku.vo.ProductInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/13 9:49
 * @File ProductDao.py
 */
public class ProductDao extends BaseDao {
    //查询公用方法返回集合
    private ArrayList<ProductInfo> queryPros(String sql) {
        ArrayList<ProductInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(sql);
        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ProductInfo pi = new ProductInfo(rs.getString("spid"), rs.getString("spname"), rs.getString("xinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getString("dw"), rs.getDouble("jhprice"), rs.getDouble("chprice"), rs.getDouble("scjj"), rs.getInt("kcsl"), rs.getDouble("kczj"), rs.getInt("minnum"), rs.getString("csname"), rs.getString("state"), rs.getString("bz"));
                list.add(pi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询公用方法返回商品
    private ProductInfo queryPro(String sql) {
        ProductInfo pi = new ProductInfo();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement(sql);
        ) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pi = new ProductInfo(rs.getString("spid"), rs.getString("spname"), rs.getString("xinghao"), rs.getInt("lbid"), rs.getString("lbname"), rs.getString("dw"), rs.getDouble("jhprice"), rs.getDouble("chprice"), rs.getDouble("scjj"), rs.getInt("kcsl"), rs.getDouble("kczj"), rs.getInt("minnum"), rs.getString("csname"), rs.getString("state"), rs.getString("bz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    //查询最后添加的一件商品编号
    public String queryLastProId() {
        String i = "00001";
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("select spid from spxx order by spid DESC limit 1");
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //查询商品总条数
    public int queryAllProRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(spid) from spxx");
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //查询当前页面商品
    public ArrayList<ProductInfo> queryProductByPage(SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        return queryPros("SELECT * from spxx LIMIT " + start + "," + sp.getPageRows());
    }

//报警商品总数量
    public int queryAllAlarmProRows() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(spid) from spxx where kcsl<minnum");
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    //所有报警商品
    public ArrayList<ProductInfo> queryAlarmProByPage(SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        return queryPros("SELECT * from spxx where kcsl < minnum LIMIT " + start + "," + sp.getPageRows());
    }

    //查询所有商品
    public ArrayList<ProductInfo> queryAllPro() {
        return queryPros("SELECT * from spxx");
    }

    //根据类别查询商品总条数
    public int queryAllProRowsByTypeId(int typeId) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(spid) from spxx where lbid = " + typeId);
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //根据类别查询当前页商品
    public ArrayList<ProductInfo> queryProdPageByTypeId(int typeId, SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        return queryPros("SELECT * from spxx where lbid = " + typeId + " LIMIT " + start + "," + sp.getPageRows());
    }

    //根据类别查询商品
    public ArrayList<ProductInfo> queryProByTypeId(int typeId) {
        return queryPros("SELECT * from spxx where lbid = " + typeId);
    }

    //根据商品id查商品
    public ProductInfo queryProByProId(String proId) {
        return queryPro("SELECT * from spxx where spid = " + proId);
    }

    //根据商品名称查商品
    public ProductInfo queryProByProName(String proName) {
        return queryPro("SELECT * from spxx where spname = " + proName);
    }

    //根据商品状态当前页的商品
    public ArrayList<ProductInfo> queryProByState(String state, SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        return queryPros("SELECT * from spxx where state = " + state + " LIMIT " + start + "," + sp.getPageRows());
    }

    //所有上架商品总条数
    public int queryAllRowsByState1and2() {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(spid) from spxx where state != '0'");
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //返回所有上架的商品
    public ArrayList<ProductInfo> queryProByState1and2(SplitePage sp) {
        int start = (sp.getCurrentPage() - 1) * sp.getPageRows();
        return queryPros("SELECT * from spxx where state != '0' LIMIT " + start + "," + sp.getPageRows());
    }

    //根据商品状态查商品总条数
    public int queryAllRowByState(String state) {
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(spid) from spxx where state =" + state);
        ) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //增加商品
    public int addPro(ProductInfo pi) {
        return alterTable("INSERT into spxx VALUES('" + pi.getProNo() + "','" + pi.getProName() + "','" + pi.getProModel() + "'," + pi.getTypeId() + ",'" + pi.getTypeName() + "','" + pi.getUnit() + "'," + pi.getInPrice() + "," + pi.getOutPrice() + "," + pi.getCostPrice() + "," + pi.getProNum() + "," + pi.getProTotalPrice() + "," + pi.getMinProNum() + ",'" + pi.getFactory() + "','" + pi.getState() + "','" + pi.getRemark() + "')");
    }

    //修改商品
    public int alterPro(ProductInfo pi) {
        return alterTable("UPDATE spxx set spname='" + pi.getProName() + "',xinghao='" + pi.getProModel() + "',lbid=" + pi.getTypeId() + ",lbname='" + pi.getTypeName() + "',dw='" + pi.getUnit() + "',jhprice=" + pi.getInPrice() + ",chprice=" + pi.getOutPrice() + ",scjj=" + pi.getCostPrice() + ",kcsl=" + pi.getProNum() + ",kczj=" + pi.getProTotalPrice() + ",minnum=" + pi.getMinProNum() + ",csname='" + pi.getFactory() + "',state='" + pi.getState() + "',bz='" + pi.getRemark() + "' where spid='" + pi.getProNo() + "'");
    }

    //删除商品
    public int deletePro(String proId) {
        return alterTable("DELETE from spxx where spid = " + proId);
    }
}
