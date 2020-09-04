package com.miku.svt.instock;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.ProductDao;
import com.miku.dao.ProductTypeDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.ProductTypeInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author: Utopiamiku
 * @date: 2020/8/18 23:00
 * @description:
 */
@WebServlet("/QuantityStockStock")
public class QuantityStockStock extends HttpServlet {
    private static final long serialVersionUID = 6783537127826339993L;

    private SplitePage sp =new SplitePage();
    private SplitePage sp2 =new SplitePage();
    private ProductDao pd = new ProductDao();
    private ProductTypeDao ptd = new ProductTypeDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        action = action==null?"start1":action;
        switch (action){
            case "start1":
                start1(req,resp);
                break;
            case "start2":
                start2(req,resp);
                break;
            case  "queryByType":
                queryByType(req,resp);
                break;
        }
    }
//返回所有商品类别
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<ProductTypeInfo> list = ptd.queryAllType();

        JsonResult json = new JsonResult();
        json.setStateCode(200);
        json.setObj(list);
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);
    }


    private void queryByType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String typeId = req.getParameter("typeId");

        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == null ? "1" : currentPage;
        sp2.setCurrentPage(Integer.valueOf(currentPage));
        //数据总条数
        sp2.setTotalRows(pd.queryAllProRowsByTypeId(Integer.valueOf(typeId)));
        //当前页数据
        sp2.setT(pd.queryProdPageByTypeId(Integer.valueOf(typeId),sp2));
        //返回数据
        JsonResult<SplitePage> json = new JsonResult<>();
        json.setObj(sp2);
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);
    }
//初始页面返回所有商品
    private void start1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == null ? "1" : currentPage;
        sp.setCurrentPage(Integer.valueOf(currentPage));
        //数据总条数
        sp.setTotalRows(pd.queryAllProRows());
        //当前页数据
        sp.setT(pd.queryProductByPage(sp));
        //返回数据
        JsonResult<SplitePage> json = new JsonResult<>();
        json.setObj(sp);
        json.setStateCode(200);
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);
    }
}
