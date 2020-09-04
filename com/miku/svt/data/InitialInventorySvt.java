package com.miku.svt.data;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.ProductDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.ProductInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Utopiamiku
 * @date: 2020/8/16 23:11
 * @description:
 */
@WebServlet("/InitialInventorySvt")
public class InitialInventorySvt extends HttpServlet {

    private static final long serialVersionUID = 1544721974179437766L;
    private ProductDao pd = new ProductDao();
    private SplitePage sp1 = new SplitePage();
    private SplitePage sp2 = new SplitePage();


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
            case "addProToStock":
                addProToStock(req, resp);
                break;
            case "deleteProToStock":
                deleteProToStock(req, resp);
                break;
        }
    }

    //将商品下架
    private void deleteProToStock(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String proId = req.getParameter("proId");
        ProductInfo pi = pd.queryProByProId(proId);
        if(!pi.getState().equals("2")){

            pi.setProNum(0);
            pi.setInPrice(0);
            pi.setState("0");
            int row = pd.alterPro(pi);
            if (row > 0) {
                JsonResult json = new JsonResult();
                json.setStateCode(200);
                json.setMsg("成功");
                String jsonString = JSONObject.toJSONString(json);
                resp.getWriter().print(jsonString);
            }
        }else{
            JsonResult json = new JsonResult();
            json.setStateCode(500);
            json.setMsg("该商品已产生单据  不能删除");
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }
    }
    //将商品添加到仓库
    private void addProToStock(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String proId = req.getParameter("proId");
        ProductInfo pi = pd.queryProByProId(proId);
        String proNum = req.getParameter("proNum");
        String inPrice = req.getParameter("alterPrice");
        inPrice = inPrice == "" ? "0" : inPrice;
        proNum = proNum == "" ? "0" : proNum;
        try {
            pi.setProNum(Integer.valueOf(proNum));
            pi.setInPrice(Double.valueOf(inPrice));
            pi.setState("1");

        } catch (Exception e) {
            JsonResult json = new JsonResult();
            e.printStackTrace();
            json.setStateCode(500);
            json.setMsg("商品数量或成本价输入格式不对");
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }
        int row = pd.alterPro(pi);
        if (row > 0) {
            JsonResult json = new JsonResult();
            json.setStateCode(200);
            json.setMsg("成功");
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }
    }

    //商品信息已入库
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == "" ? "1" : currentPage;
        sp2.setPageRows(5);
        sp2.setCurrentPage(Integer.valueOf(currentPage));
        sp2.setTotalRows(pd.queryAllRowsByState1and2());

        sp2.setT(pd.queryProByState1and2(sp2));
        JsonResult json = new JsonResult();
        json.setObj(sp2);
        json.setStateCode(200);
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);
    }

    //商品信息  未入库
    private void start1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == "" ? "1" : currentPage;
        sp1.setCurrentPage(Integer.valueOf(currentPage));
        sp1.setPageRows(4);
        sp1.setTotalRows(pd.queryAllRowByState("0"));
        sp1.setT(pd.queryProByState("0", sp1));
        JsonResult json = new JsonResult();
        json.setObj(sp1);
        json.setStateCode(200);
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);
    }


}
