package com.miku.svt.sale;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.sell.ClientDao;
import com.miku.dao.sell.SaleStockDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.ClientInfo;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.salestock.SaleStockInfo;
import com.miku.vo.salestock.SaleStockProInfo;

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
 * @date: 2020/8/20 16:38
 * @description:
 */
@WebServlet("/SaleOutStockRecordSvt")
public class SaleOutStockRecordSvt extends HttpServlet {
    private static final long serialVersionUID = -7067528422993103161L;
    private SplitePage sp1 = new SplitePage();

    private SaleStockDao ssd = new SaleStockDao();
    private ClientDao cd = new ClientDao();

    private ArrayList<SaleStockInfo> list = new ArrayList<>();
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
            case "deleteStock":
                deleteStock(req, resp);
                break;
            case "queryInStock":
                queryInStock(req, resp);
                break;
        }
    }

    //条件查询进货单
    private void queryInStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String form = req.getParameter("form");
        String supplierId;
        String startDate;
        String endDate;
        String inStockId;

        if("null".equals(form)){
            supplierId = req.getParameter("supplierId");
            startDate = req.getParameter("startDate");
            endDate = req.getParameter("endDate");
            inStockId = req.getParameter("inStockId");

        }else{
            String[] s = form.split("_");
            supplierId = s[0];
            startDate = s[1];
            endDate = s[2];
            inStockId = s[3];
        }
        inStockId = inStockId==""?"abc":inStockId;
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage==null?"1":currentPage;
        form = supplierId+"_"+startDate+"_"+endDate+"_"+inStockId;

        //如果有填写单号
        if (!inStockId.equals("abc")) {
            SaleStockInfo ssi = ssd.querySaleStockById(inStockId);
            ArrayList<SaleStockInfo> list1 = new ArrayList<>();
            list1.add(ssi);

            sp1.setPageRows(1);
            sp1.setCurrentPage(1);
            sp1.setTotalRows(1);
            sp1.setT(list1);

            JsonResult json = new JsonResult(sp1,form,200);
            resp.getWriter().print(JSONObject.toJSONString(json));
            return;

        }
        //选择所有客户
        try {
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            ArrayList<InStockInfo> inStockInfos;
            if (supplierId.equals("all")) {

                sp1.setTotalRows(ssd.queryAllRowDatetoDate(sDate, eDate));
                sp1.setCurrentPage(Integer.valueOf(currentPage));
                sp1.setPageRows(2);
                sp1.setT(ssd.queryDatetoDateByPage(sDate, eDate, sp1));

            } else {
                sp1.setTotalRows(ssd.queryAllRowDatetoDate(Integer.valueOf(supplierId), sDate, eDate));
                sp1.setCurrentPage(Integer.valueOf(currentPage));
                sp1.setPageRows(2);
                sp1.setT(ssd.queryDatetoDateByPage(Integer.valueOf(supplierId), sDate, eDate, sp1));
            }
            JsonResult json = new JsonResult(sp1,form,200);

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

    //删除进货单
    private void deleteStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inStockId = req.getParameter("inStockId");
        ArrayList<SaleStockProInfo> ispi = ssd.queryProBySaleStockId(inStockId);

        int row = ssd.deleteSaleStock(inStockId);
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

    //全部客户
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<ClientInfo> clientInfos = cd.queryAllClient();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //结束时间
        Date end = c.getTime();
        c.add(Calendar.MONTH, -1);
        //开始时间
        Date start = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JsonResult json = new JsonResult();
        json.setObj(clientInfos);
        json.setMsg(sdf.format(start));
        json.setMsg2(sdf.format(end));

        resp.getWriter().print(JSONObject.toJSONString(json));


    }

    //查看进货单详情
    private void queryDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inStockId = req.getParameter("inStockId");
        ArrayList<SaleStockProInfo> list = ssd.queryProBySaleStockId(inStockId);

        JsonResult json = new JsonResult(list);
        resp.getWriter().print(JSONObject.toJSONString(json));

    }

    //全部销售单
    private void start1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String crrentPage = req.getParameter("currentPage");
        sp1.setPageRows(2);
        sp1.setCurrentPage(Integer.valueOf(crrentPage));
        sp1.setTotalRows(ssd.queryAllRows());
        list = ssd.querySaleStockByPage(sp1);
        sp1.setT(list);

        JsonResult json = new JsonResult(sp1);
        resp.getWriter().print(JSONObject.toJSONString(json));

    }

}
