package com.miku.svt.statistics;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.buy.SupplierDao;
import com.miku.dao.statistics.StatisticsSuDao;
import com.miku.utils.JsonResult;
import com.miku.vo.SupplierInfo;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.instock.InStockProInfo;

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
 * @date: 2020/8/22 0:09
 * @description:
 */
@WebServlet("/SupplierSvt")
public class SupplierSvt extends HttpServlet {
    private static final long serialVersionUID = 2775291350838642046L;

    private StatisticsSuDao stad = new StatisticsSuDao();
    private SupplierDao sd = new SupplierDao();

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
            case "queryDetail":
                queryDetail(req, resp);
                break;
            case "settle":
                deleteStock(req, resp);
                break;
            case "queryInStock":
                queryInStock(req, resp);
                break;
        }
    }

    //条件查询进货单
    private void queryInStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String supplierId=  req.getParameter("supplierId");
        String startDate = req.getParameter("startDate");
        String endDate  = req.getParameter("endDate");
        String payState = req.getParameter("payState");

        ArrayList<InStockInfo> inStockInfos = new ArrayList<>();

        try {
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);

            if(supplierId.equals("all") && payState.equals("all")){
                inStockInfos = stad.queryListByDate(sDate,eDate);
            }
            if(supplierId.equals("all") && !payState.equals("all")){
                inStockInfos = stad.queryListByDateState(payState,sDate,eDate);
            }
            if(!supplierId.equals("all") && payState.equals("all")){
                inStockInfos = stad.queryListBySupplier(Integer.valueOf(supplierId),sDate,eDate);
            }if(!supplierId.equals("all") && !payState.equals("all")){
                inStockInfos = stad.queryListBySupplierState(payState,Integer.valueOf(supplierId),sDate,eDate);
            }
            JsonResult json = new JsonResult();
            json.setStateCode(200);
            json.setObj(inStockInfos);
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
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

    //删除进货单
    private void deleteStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inStockId = req.getParameter("inStockId");
        String payState = req.getParameter("payState");
        int row = stad.alterStateByPid(payState, inStockId);
        JsonResult json = new JsonResult();
        if (row > 0) {
            json.setMsg("成功");
            json.setStateCode(200);
        } else {
            json.setMsg("发生未知错误，请联系Utopiamiku修复");
            json.setStateCode(500);
        }
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);
    }

    //全部供应商
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<SupplierInfo> supplierInfos = sd.queryAllSupplier();
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

    //查看进货单详情
    private void queryDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inStockId = req.getParameter("inStockId");
        ArrayList<InStockProInfo> list = stad.queryProByPid(inStockId);
        JsonResult json = new JsonResult();
        json.setObj(list);
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);
    }

    //全部进货单
    private void start1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonResult json = new JsonResult(stad.queryAllListByAllSupplier());
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);

    }


}

