package com.miku.svt.data;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.sell.ClientDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.ClientInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/13 16:02
 * @File ClientManageSvt.py
 */
@WebServlet("/ClientManageSvt")
public class ClientManageSvt extends HttpServlet {
    private static final long serialVersionUID = -126852620628949554L;
    private SplitePage sp = new SplitePage();
    private ClientDao cd = new ClientDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        action = action == null ? "null" : action;
        switch (action) {
            case "null":
                String currentPage = req.getParameter("currentPage");
                currentPage = currentPage == null ? "1" : currentPage;
                sp.setCurrentPage(Integer.valueOf(currentPage));
                //数据总条数
                sp.setTotalRows(cd.queryAllClientNum());
                sp.setT(cd.queryClientByPage(sp));
                //返回数据
                JsonResult<SplitePage> json = new JsonResult<>();
                json.setObj(sp);

                String jsonString = JSONObject.toJSONString(json);
                resp.getWriter().print(jsonString);
                break;
            case "addClient":
                addClient(req, resp);
                break;
            case "alterClient":
                alterClient(req,resp);
                break;
            case "deleteClient":
                deleteClient(req,resp);
                break;
        }
    }

    //增加客户
    protected void addClient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientName = req.getParameter("clientName");
        String contact = req.getParameter("contact");
        String contactPhoneNum = req.getParameter("contactPhoneNum");
        String clientAddress = req.getParameter("clientAddress");
        String remark = req.getParameter("remark");
        ClientInfo ci = new ClientInfo();
        ci.setClientName(clientName);
        ci.setContact(contact);
        ci.setContactPhoneNum(contactPhoneNum);
        ci.setClientAdress(clientAddress);
        ci.setRemark(remark);
        int row = cd.addClient(ci);
        if (row > 0) {
            JsonResult<String> json = new JsonResult<>();
            json.setMsg("添加客户成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }

    }
    //修改客户
    protected void alterClient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientId = req.getParameter("clientId");
        String clientName = req.getParameter("clientName");
        String contact = req.getParameter("contact");
        String contactPhoneNum = req.getParameter("contactPhoneNum");
        String clientAddress = req.getParameter("clientAddress");
        String remark = req.getParameter("remark");
        ClientInfo ci = new ClientInfo();
        ci.setClientId(Integer.valueOf(clientId));
        ci.setClientName(clientName);
        ci.setContact(contact);
        ci.setContactPhoneNum(contactPhoneNum);
        ci.setClientAdress(clientAddress);
        ci.setRemark(remark);
        int row = cd.alterClient(ci);
        if (row > 0) {
            JsonResult<String> json = new JsonResult<>();
            json.setMsg("修改客户成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }

    }
//删除用户
    protected void deleteClient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientId = req.getParameter("clientId");
        int row = cd.deleteClient(Integer.valueOf(clientId));
        if (row > 0) {
            JsonResult<String> json = new JsonResult<>();
            json.setMsg("删除客户成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }

    }
}
