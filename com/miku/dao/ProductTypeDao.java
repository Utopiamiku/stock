package com.miku.dao;

import com.miku.utils.SplitePage;
import com.miku.vo.ProductTypeInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/13 10:57
 * @File ProductTypeDao.py
 */
public class ProductTypeDao extends BaseDao {
    //返回所有商品类别
    public ArrayList<ProductTypeInfo> queryAllType(){
        ArrayList<ProductTypeInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from splb");
                ResultSet rs = pst.executeQuery();
                ){
            while (rs.next()){
                ProductTypeInfo pti = new ProductTypeInfo(rs.getInt("lbid"),rs.getString("lbname"),rs.getInt("pid"));
                list.add(pti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据类别id返回名称
    public ProductTypeInfo queryTypeById(int typeId){
        ProductTypeInfo pti = new ProductTypeInfo();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from splb where lbid = "+typeId);
                ResultSet rs = pst.executeQuery();
        ){
            if (rs.next()){
                pti = new ProductTypeInfo(rs.getInt("lbid"),rs.getString("lbname"),rs.getInt("pid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pti;
    }

    //查询商品类别总条数
    public int queryAllTypeRows(){
        int i = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT count(lbid) from splb");
                ResultSet rs = pst.executeQuery();
        ){
            if (rs.next()){
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    //返回当前页类别
    public ArrayList<ProductTypeInfo> queryTypeByPage(SplitePage sp){
        ArrayList<ProductTypeInfo> list = new ArrayList<>();
        int start = (sp.getCurrentPage()-1)*sp.getPageRows();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("SELECT * from splb limit ?,?");

        ){
            pst.setInt(1,start);
            pst.setInt(2,sp.getPageRows());
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                ProductTypeInfo pti = new ProductTypeInfo(rs.getInt("lbid"),rs.getString("lbname"),rs.getInt("pid"));
                list.add(pti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //增加商品类别
    public int addType(ProductTypeInfo pti){
        return alterTable("INSERT splb VALUES(DEFAULT,'"+pti.getTypeName()+"',"+pti.getPid()+")");
    }
    //删除商品类别
    public int deleteType(int typeId){
        return alterTable("DELETE from splb where lbid = "+typeId);
    }
}
