package com.miku.svt.statistics;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.ProductTypeDao;
import com.miku.dao.statistics.StatisticsSaleDao;
import com.miku.utils.JsonResult;
import com.miku.vo.ProductTypeInfo;
import com.miku.vo.SaleInfo;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.salestock.SaleStockInfo;

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
 * @date: 2020/8/23 21:09
 * @description:
 */
@WebServlet("/SaleMonthSvt")
public class SaleMonthSvt extends HttpServlet {
    private static final long serialVersionUID = -8795251348944207699L;
    StatisticsSaleDao stad = new StatisticsSaleDao();
    private ProductTypeDao ptd = new ProductTypeDao();

    private ArrayList<InStockInfo> list = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    Calendar c = Calendar.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        action = action == null ? "start1" : action;
        switch (action) {
            case "start1":
                try {
                    start1(req, resp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

        String startDate = req.getParameter("startDate");
        String endDates = req.getParameter("endDate");

        ArrayList<SaleInfo> sis = new ArrayList<>();


        try {
            long sDate = sdf.parse(startDate).getTime();
            long eDate = sdf.parse(endDates).getTime();

            while (sDate <= eDate) {

                c.setTime(new Date(sDate));
                c.add(Calendar.MONTH, +1);
                Date endDate = c.getTime();


                //查询一个月总订单
                Date d = new Date(sDate);
                String s1 = sdf.format(d);
                Date newd = sdf.parse(s1);
                ArrayList<SaleStockInfo> saleStockInfos = stad.querySaleStock(newd, endDate);
                Double salePrice = 0d;
                Double costPrice = 0d;

                if (saleStockInfos != null) {
                    for (SaleStockInfo s : saleStockInfos) {
                        //一个订单销售额
                        salePrice += s.getRealBalance();
                        //一个订单成本
                        costPrice += s.getBalance();
                    }
                }


                SaleInfo si = new SaleInfo(newd, salePrice, costPrice, salePrice - costPrice);
                sis.add(si);

                sDate = c.getTime().getTime();
            }

            //总利润
            Double totalProfit = 0d;
            for (SaleInfo saleInfo : sis) {
                totalProfit += saleInfo.getProfit();
            }


            JsonResult json = new JsonResult(sis, String.valueOf(totalProfit), 200);
            String stringJson = JSONObject.toJSONString(json);
            resp.getWriter().print(stringJson);
            return;

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

    //商品类别
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<ProductTypeInfo> supplierInfos = ptd.queryAllType();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //结束时间
        Date end = c.getTime();
        c.add(Calendar.MONTH, -6);
        //开始时间
        Date start = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        JsonResult json = new JsonResult();
        json.setObj(supplierInfos);
        json.setMsg(sdf.format(start));
        json.setMsg2(sdf.format(end));

        String stringJson = JSONObject.toJSONString(json);

        resp.getWriter().print(stringJson);


    }

    //全部进货单
    private void start1(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
        ArrayList<SaleInfo> sis = new ArrayList<>();

        c.setTime(new Date());
        //结束时间
        Date end = c.getTime();
        String format = sdf.format(end);
        end = sdf.parse(format);
        c.setTime(end);
        long eDate = end.getTime();

        c.add(Calendar.MONTH, -6);
        //开始时间
        Date start = c.getTime();
        long sDate = start.getTime();
        while (sDate <= eDate) {

            c.setTime(new Date(sDate));
            c.add(Calendar.MONTH, +1);
            Date endDate = c.getTime();


            //查询一个月总订单
            Date d = new Date(sDate);
            String s1 = sdf.format(d);
            Date newd = sdf.parse(s1);
            ArrayList<SaleStockInfo> saleStockInfos = stad.querySaleStock(newd, endDate);
            Double salePrice = 0d;
            Double costPrice = 0d;

            if (saleStockInfos != null) {
                for (SaleStockInfo s : saleStockInfos) {
                    //一个订单销售额
                    salePrice += s.getRealBalance();
                    //一个订单成本
                    costPrice += s.getBalance();
                }
            }


            SaleInfo si = new SaleInfo(newd, salePrice, costPrice, salePrice - costPrice);
            sis.add(si);

            sDate = c.getTime().getTime();
        }

        //总利润
        Double totalProfit = 0d;
        for (SaleInfo saleInfo : sis) {
            totalProfit += saleInfo.getProfit();
        }


        JsonResult json = new JsonResult(sis, String.valueOf(totalProfit), 200);
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);

    }


}



