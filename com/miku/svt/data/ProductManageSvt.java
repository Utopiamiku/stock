package com.miku.svt.data;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.ProductDao;
import com.miku.dao.ProductTypeDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.ProductInfo;
import com.miku.vo.ProductTypeInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/14 10:25
 * @File ProductManageSvt.py
 */
@WebServlet("/ProductManageSvt")
public class ProductManageSvt extends HttpServlet {

    private static final long serialVersionUID = 1598516352024194535L;
    private SplitePage sp = new SplitePage();
    private SplitePage sp2 = new SplitePage();
    private ProductDao pd = new ProductDao();
    private ProductTypeDao ptd = new ProductTypeDao();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        action = action == null ? "null" : action;
        switch (action) {
            case "null":
                start1(req, resp);
                break;
            case "start2":
                start2(req, resp);
                break;
            case "getAllType":
                getAllType(req, resp);
                break;
            case "addType":
                addType(req, resp);
                break;
            case "deleteType":
                deleteType(req, resp);
                break;
            case "addPro":
                addPro(req, resp);
                break;
            case "alterPro":
                alterePro(req, resp);
                break;
            case "deletePro":
                deletePro(req, resp);
                break;
        }
    }

    //start1
    //所有商品
    protected void start1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == null ? "1" : currentPage;
        sp.setPageRows(5);
        sp.setCurrentPage(Integer.valueOf(currentPage));
        //数据总条数
        sp.setTotalRows(pd.queryAllProRows());
        //当前页数据
        sp.setT(pd.queryProductByPage(sp));
        //返回数据
        JsonResult<SplitePage> json = new JsonResult<>();
        json.setObj(sp);
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);
    }

    //start2
    protected void start2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == null ? "1" : currentPage;
        sp2.setCurrentPage(Integer.valueOf(currentPage));
        //数据总条数
        sp2.setTotalRows(ptd.queryAllTypeRows());
        //当前页数据
        sp2.setT(ptd.queryTypeByPage(sp2));
        //返回数据
        JsonResult<SplitePage> json2 = new JsonResult<>();
        json2.setObj(sp2);
        String jsonString2 = JSONObject.toJSONString(json2);
        resp.getWriter().print(jsonString2);
    }

    //获取所有类别
    protected void getAllType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //查询最后一件商品编号
        String proId = pd.queryLastProId();
        String newProId = String.valueOf(Integer.valueOf(proId) + 1);


        ArrayList<ProductTypeInfo> ptis = new ArrayList<>();
        ptis = ptd.queryAllType();
        JsonResult<ArrayList<ProductTypeInfo>> json = new JsonResult();
        json.setObj(ptis);
        json.setStateCode(200);
        json.setMsg(newProId);
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);
    }

    //添加商品类别
    protected void addType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeName = req.getParameter("typeName");
        ProductTypeInfo pti = new ProductTypeInfo();
        pti.setTypeName(typeName);
        int row = ptd.addType(pti);
        if (row > 0) {
            JsonResult<ArrayList<ProductTypeInfo>> json = new JsonResult();
            json.setMsg("商品类别添加成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }


    }

    //删除商品类别
    protected void deleteType(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeId = req.getParameter("typeId");

        int row = ptd.deleteType(Integer.valueOf(typeId));
        if (row > 0) {
            JsonResult<ArrayList<ProductTypeInfo>> json = new JsonResult();
            json.setMsg("商品类别删除成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }
    }

    //添加商品
    protected void addPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String proTypeId = req.getParameter("typeId");
        String proId = req.getParameter("proId");
        String proName = req.getParameter("proName");
        String unit = req.getParameter("unit");
        String proModel = req.getParameter("proModel");
        String minProNum = req.getParameter("minProNum");
        String inPrice = req.getParameter("inPrice");
        String outPrice = req.getParameter("outPrice");
        String factoy = req.getParameter("factoy");
        String remark = req.getParameter("remark");
        ProductInfo pi = new ProductInfo();
        JsonResult<ArrayList<ProductTypeInfo>> json = new JsonResult();
        try {
            pi.setTypeId(Integer.valueOf(proTypeId));
            //类别名称
            pi.setTypeName(ptd.queryTypeById(Integer.valueOf(proTypeId)).getTypeName());
            pi.setProNo(proId);
            pi.setProName(proName);
            pi.setUnit(unit);
            pi.setProModel(proModel);
            minProNum = minProNum == "" ? "0" : minProNum;
            pi.setMinProNum(Integer.valueOf(minProNum));
            inPrice = inPrice == "" ? "0" : inPrice;
            pi.setInPrice(Double.valueOf(inPrice));
            outPrice = outPrice == "" ? "0" : outPrice;
            pi.setOutPrice(Double.valueOf(outPrice));
            pi.setFactory(factoy);
            pi.setRemark(remark);
            pi.setState("0");
        } catch (NumberFormatException e) {

            json.setMsg("商品添加失败，商品进价或商品售价或最低库存填入格式不符合要求");
            json.setStateCode(500);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
            e.printStackTrace();
        }
        int row = pd.addPro(pi);
        if (row > 0) {
            json.setMsg("商品添加成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }

    }

    //修改商品
    protected void alterePro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String proTypeId = req.getParameter("typeId");
        String proId = req.getParameter("proId");
        String proName = req.getParameter("proName");
        String unit = req.getParameter("unit");
        String proModel = req.getParameter("proModel");
        String minProNum = req.getParameter("minProNum");
        String inPrice = req.getParameter("inPrice");
        String outPrice = req.getParameter("outPrice");
        String factoy = req.getParameter("factoy");
        String remark = req.getParameter("remark");
        JsonResult<ArrayList<ProductTypeInfo>> json = new JsonResult();
        ProductInfo pi = pd.queryProByProId(proId);
        try {


            pi.setTypeId(Integer.valueOf(proTypeId));
            //类别名称
            pi.setTypeName(ptd.queryTypeById(Integer.valueOf(proTypeId)).getTypeName());
            pi.setProNo(proId);
            pi.setProName(proName);
            pi.setUnit(unit);
            pi.setProModel(proModel);
            minProNum = minProNum == "" ? "0" : minProNum;
            pi.setMinProNum(Integer.valueOf(minProNum));
            inPrice = inPrice == "" ? "0" : inPrice;
            pi.setInPrice(Double.valueOf(inPrice));
            outPrice = outPrice == "" ? "0" : outPrice;
            pi.setOutPrice(Double.valueOf(outPrice));
            pi.setFactory(factoy);
            pi.setRemark(remark);
            pi.setState("0");
        } catch (NumberFormatException e) {

            json.setMsg("商品修改失败，商品进价或商品售价或最低库存填入格式不符合要求");
            json.setStateCode(500);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
            e.printStackTrace();
        }
        int row = pd.alterPro(pi);
        if (row > 0) {
            json.setMsg("商品修改成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }

    }

    //删除商品
    protected void deletePro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String proId = req.getParameter("proId");
        ProductInfo pro = pd.queryProByProId(proId);
        if(pro.getState().equals("2") || pro.getState().equals("1")){
            JsonResult<ArrayList<ProductTypeInfo>> json = new JsonResult();
            json.setMsg("该商品已经产生订单 或者已经上架 不能删除");
            json.setStateCode(500);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
            return;
        }

        int row = pd.deletePro(proId);
        if (row > 0) {
            JsonResult<ArrayList<ProductTypeInfo>> json = new JsonResult();
            json.setMsg("商品删除成功");
            json.setStateCode(200);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }
    }


}
