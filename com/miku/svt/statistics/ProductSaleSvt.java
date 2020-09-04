package com.miku.svt.statistics;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.ProductTypeDao;
import com.miku.dao.statistics.StatisticsBuyDao;
import com.miku.dao.statistics.StatisticsSaleDao;
import com.miku.utils.JsonResult;
import com.miku.vo.ProductTypeInfo;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.instock.StatisticsProInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/23 16:24
 * @description:
 */
@WebServlet("/ProductSaleSvt")
public class ProductSaleSvt extends HttpServlet {
    private static final long serialVersionUID = 7434927103713473545L;


    StatisticsSaleDao stad = new StatisticsSaleDao();
    private ProductTypeDao ptd = new ProductTypeDao();

    private ArrayList<InStockInfo> list = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        action = action == null ? "start1" : action;
        switch (action) {
            case "start1":
                start1(req, resp);
                break;
            case "start2":
                start2(req, resp);
                break;
            case "queryInStock":
                queryInStock(req, resp);
                break;
        }
    }

    //条件查询进货单
    private void queryInStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String supplierId = req.getParameter("supplierId");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String payState = req.getParameter("payState");

        ArrayList<StatisticsProInfo> inStockInfos = new ArrayList<>();

        boolean flag = !payState.equals("");
        if ( !payState.equals("")) {
            inStockInfos = stad.queryProByIdName(payState);
        } else {
            try {


                Date sDate = sdf.parse(startDate);
                Date eDate = sdf.parse(endDate);
                if (supplierId.equals("all")) {
                    inStockInfos = stad.queryProByDate(sDate, eDate);
                } else {
                    inStockInfos = stad.queryProByDateTy(Integer.valueOf(supplierId), sDate, eDate);
                }

            } catch (ParseException e) {
                e.printStackTrace();
                JsonResult json = new JsonResult();
                json.setStateCode(500);
                json.setMsg("查询失败，请输入正确的日期格式");
                String jsonString = JSONObject.toJSONString(json);
                resp.getWriter().print(jsonString);
                return;
            }
        }

        JsonResult json = new JsonResult();
        json.setStateCode(200);
        json.setObj(inStockInfos);
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter().print(jsonString);
        return;


    }

    //商品类别
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<ProductTypeInfo> supplierInfos = ptd.queryAllType();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //结束时间
        Date end = c.getTime();
        c.add(Calendar.MONTH, -1);
        //开始时间
        Date start = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JsonResult json = new JsonResult();
        json.setObj(supplierInfos);
        json.setMsg(sdf.format(start));
        json.setMsg2(sdf.format(end));

        String stringJson = JSONObject.toJSONString(json);

        resp.getWriter().print(stringJson);


    }

    //全部进货单
    private void start1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonResult json = new JsonResult(stad.queryAllPro());
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);

    }


}



