package com.miku.dao.sell;

import com.miku.dao.BaseDao;
import com.miku.utils.SplitePage;
import com.miku.vo.ClientInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/11 9:43
 * @File ClientDao.py
 */
public class ClientDao extends BaseDao {
//返回所有客户
    public ArrayList<ClientInfo> queryAllClient(){
        ArrayList<ClientInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("select * from kh");
                ){
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                ClientInfo ci = new ClientInfo(rs.getInt("khid"),rs.getString("khname"),rs.getString("lxren"),rs.getString("lxtel"),rs.getString("address"),rs.getString("bz"));
                list.add(ci);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //数据总条数
    public int queryAllClientNum(){
        int row = 0;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("select count(khid) from kh");
        ){
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                row = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    //根据页码返回客户
    public ArrayList<ClientInfo> queryClientByPage(SplitePage sp){
        ArrayList<ClientInfo> list = new ArrayList<>();
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("select * from kh LIMIT "+(sp.getCurrentPage()-1)*sp.getPageRows()+","+sp.getPageRows());
                ResultSet rs = pst.executeQuery();
        ){

            while (rs.next()){
                ClientInfo ci = new ClientInfo(rs.getInt("khid"),rs.getString("khname"),rs.getString("lxren"),rs.getString("lxtel"),rs.getString("address"),rs.getString("bz"));
                list.add(ci);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据客户id返回客户
    public ClientInfo queryClientById(int clientId){
       ClientInfo ci = null;
        try (
                Connection con = openCon();
                PreparedStatement pst = con.prepareStatement("select * from kh where khid = "+clientId);
                ResultSet rs = pst.executeQuery();
        ){

            if (rs.next()){
               ci = new ClientInfo(rs.getInt("khid"),rs.getString("khname"),rs.getString("lxren"),rs.getString("lxtel"),rs.getString("address"),rs.getString("bz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ci;
    }

    //添加客户
    public int addClient(ClientInfo ci){
        return alterTable("INSERT kh VALUES(DEFAULT,'"+ci.getClientName()+"','"+ci.getContact()+"','"+ci.getContactPhoneNum()+"','"+ci.getClientAdress()+"','"+ci.getRemark()+"');");
    }

    //修改客户
    public int alterClient(ClientInfo ci){
        return alterTable("UPDATE kh set khname='"+ci.getClientName()+"',lxren='"+ci.getContact()+"',lxtel='"+ci.getContactPhoneNum()+"',address='"+ci.getClientAdress()+"',bz='"+ci.getRemark()+"' where khid = "+ci.getClientId());
    }

    //删除客户
    public int deleteClient(int clientId){
        return alterTable("DELETE  from  kh where khid = "+clientId);
    }
}
