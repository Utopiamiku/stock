package com.miku.svt.data;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.buy.SupplierDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.SupplierInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/12 16:37
 * @File SupplierManage.py
 */
@WebServlet("/SupplierManageSvt")
public class SupplierManageSvt extends HttpServlet {
    private static final long serialVersionUID = -4952545472352478727L;
    private SupplierDao sd = new SupplierDao();
    private SplitePage sp = new SplitePage();

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
                sp.setTotalRows(sd.queryAllSupplierRows());
                //当前页数据
                sp.setT(sd.queryAllSupplierByPage(sp));
                //返回数据
                JsonResult<SplitePage> json = new JsonResult<>();
                json.setObj(sp);

                String jsonString = JSONObject.toJSONString(json);
                resp.getWriter().print(jsonString);
                break;
            case "addSupplier":
                addSupplier(req, resp);
                break;
            case "alterSupplier":
                alterSupplier(req, resp);
                break;
            case "deleteSupplier":
                deleteSupplier(req,resp);
                break;

        }

    }

    //增加供应商
    protected void addSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String supplierName = req.getParameter("supplierName");
        String contact = req.getParameter("contact");
        String contactPhoneNum = req.getParameter("contactPhoneNum");
        String supplierAddress = req.getParameter("supplierAddress");
        String remark = req.getParameter("remark");

        SupplierInfo si = new SupplierInfo();
        si.setSupplierName(supplierName);
        si.setContact(contact);
        si.setContactPhoneNum(contactPhoneNum);
        si.setSupplierAddress(supplierAddress);
        si.setRemark(remark);
        //增加供应商
        int row = 0;
        if (supplierName != "") {
            row = sd.addSupplier(si);
        }
        JsonResult<String> json = new JsonResult<>();
        if (row > 0) {
            json.setStateCode(200);
            json.setMsg("添加成功");
        } else {
            json.setStateCode(500);
            json.setMsg("供应商名称不能为空");
        }
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);

    }

    //修改供应商
    protected void alterSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String supplierId = req.getParameter("supplierId");
        String supplierName = req.getParameter("supplierName");
        String contact = req.getParameter("contact");
        String contactPhoneNum = req.getParameter("contactPhoneNum");
        String supplierAddress = req.getParameter("supplierAddress");
        String remark = req.getParameter("remark");

        SupplierInfo si = new SupplierInfo();
        si.setSupplierId(Integer.valueOf(supplierId));
        si.setSupplierName(supplierName);
        si.setContact(contact);
        si.setContactPhoneNum(contactPhoneNum);
        si.setSupplierAddress(supplierAddress);
        si.setRemark(remark);
        //修改供应商
        int row = 0;
        if (supplierName != "") {
            row = sd.alterSupplier(si);
        }
        JsonResult<String> json = new JsonResult<>();
        if (row > 0) {
            json.setStateCode(200);
            json.setMsg("修改成功");
        } else {
            json.setStateCode(500);
            json.setMsg("供应商名称不能为空");
        }
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);

    }

    //删除供应商
    protected void deleteSupplier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String supplierId = req.getParameter("supplierId");
        int row = sd.deleteSupplier(Integer.valueOf(supplierId));

        JsonResult<String> json = new JsonResult<>();
        if (row > 0) {
            json.setStateCode(200);
            json.setMsg("删除成功");
        } else {
            json.setStateCode(500);
            json.setMsg("发生未知错误，等待Utopiamiku解决");
        }
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);

    }

}
