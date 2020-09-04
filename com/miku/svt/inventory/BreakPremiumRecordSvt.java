package com.miku.svt.inventory;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.invotory.BreakDao;
import com.miku.dao.invotory.PremiumDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.inventory.BreakProInfo;

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
 * @date: 2020/8/21 22:00
 * @description:
 */
@WebServlet("/BreakPremiumRecordSvt")
public class BreakPremiumRecordSvt extends HttpServlet {
    private static final long serialVersionUID = 2418835728119699673L;

    private SplitePage sp1 = new SplitePage();
    private BreakDao breD = new BreakDao();
    private PremiumDao preD = new PremiumDao();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String listType = "bs";

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
            case "deleteStock":
                deleteStock(req, resp);
                break;
            case "queryInStock":
                queryInStock(req, resp);
                break;
        }
    }

    //条件查询单据
    private void queryInStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String form = req.getParameter("form");

        String startDate;
        String endDate;
        listType = req.getParameter("listType")==null?listType: req.getParameter("listType");

        if (form==null) {
            startDate = req.getParameter("startDate");
            endDate = req.getParameter("endDate");
        } else {
            String[] s = form.split("_");

            startDate = s[0];
            endDate = s[1];

        }

        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == null ? "1" : currentPage;
        form = startDate + "_" + endDate;

        try {
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            ArrayList<InStockInfo> inStockInfos;
            sp1.setCurrentPage(Integer.valueOf(currentPage));
            sp1.setPageRows(2);
            switch (listType) {
                case "bs":
                    sp1.setTotalRows(breD.queryAllRowDatetoDate(sDate, eDate));
                    sp1.setT(breD.queryDatetoDateByPage(sDate, eDate, sp1));
                    break;
                case "by":
                    sp1.setTotalRows(preD.queryAllRowDatetoDate(sDate, eDate));
                    sp1.setT(preD.queryDatetoDateByPage(sDate, eDate, sp1));
                    break;
            }


            JsonResult json = new JsonResult(sp1, form, 200);
            resp.getWriter().print(JSONObject.toJSONString(json));
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

    //删除单据
    private void deleteStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int row = 0;
        String inStockId = req.getParameter("inStockId");
        switch (listType) {
            case "bs":
                row = breD.deleteSaleStock(inStockId);
                break;
            case "by":
                row = preD.deleteSaleStock(inStockId);
                break;
        }
        JsonResult json = new JsonResult();
        if (row > 0) {
            json.setMsg("删除成功，该订单产生的商品库存变化已复原");
            json.setStateCode(200);
        } else {
            json.setMsg("发生未知错误，请联系Utopiamiku修复");
            json.setStateCode(500);
        }
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);
    }

    //日期
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        ArrayList<ClientInfo> clientInfos = cd.queryAllClient();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //结束时间
        Date end = c.getTime();
        c.add(Calendar.MONTH, -1);
        //开始时间
        Date start = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JsonResult json = new JsonResult();
//        json.setObj(clientInfos);
        json.setMsg(sdf.format(start));
        json.setMsg2(sdf.format(end));

        resp.getWriter().print(JSONObject.toJSONString(json));


    }

    //查看进货单详情
    private void queryDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inStockId = req.getParameter("inStockId");
        ArrayList<BreakProInfo> list = null;
        switch (listType) {
            case "bs":
                list = breD.queryProBySaleStockId(inStockId);
                break;
            case "by":
                list = preD.queryProBySaleStockId(inStockId);
                break;
        }

        JsonResult json = new JsonResult(list);

        resp.getWriter().print(JSONObject.toJSONString(json));

    }

    //全部报损单
    private void start1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String crrentPage = req.getParameter("currentPage");
        sp1.setPageRows(2);
        sp1.setCurrentPage(Integer.valueOf(crrentPage));

        sp1.setTotalRows(breD.queryAllRows());

        sp1.setT(breD.querySaleStockByPage(sp1));

        JsonResult json = new JsonResult(sp1);
        resp.getWriter().print(JSONObject.toJSONString(json));

    }

}



