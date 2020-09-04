package com.miku.svt.sale;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.ProductDao;
import com.miku.dao.sell.ClientDao;
import com.miku.dao.sell.SaleStockDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.ClientInfo;
import com.miku.vo.ProductInfo;
import com.miku.vo.UserInfo;
import com.miku.vo.salestock.SaleStockInfo;
import com.miku.vo.salestock.SaleStockProInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: Utopiamiku
 * @date: 2020/8/19 8:49
 * @description:
 */
@WebServlet("/SaleOutStockSvt")
public class SaleOutStockSvt extends HttpServlet {
    private static final long serialVersionUID = -5811226945856428568L;

    private ClientDao cd = new ClientDao();
    private ProductDao pd = new ProductDao();
    private SplitePage sp = new SplitePage();
    private SaleStockDao ssd = new SaleStockDao();
    //本次进货商品集合
    private ArrayList<SaleStockProInfo> saleStockPros = new ArrayList<>();
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        action = action == null ? "start1" : action;
        switch (action) {
            case "start1":
                stare1(req, resp);
                break;
            case "start2":
                start2(req, resp);
                break;
            case "start3":
                start3(req, resp);
                break;
            case "addProStock":
                addProStock(req, resp);
                break;
            case "alterInStockPro":
                alterProStock(req, resp);
                break;
            case "deleteInStockPro":
                deleteProStock(req, resp);
                break;
            case "addInStock":
                addInStock(req, resp);
                break;
        }
    }

    //生成订单
    private void addInStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (saleStockPros.isEmpty()) {
            JsonResult json = new JsonResult();
            json.setMsg("请添加出库商品");
            json.setStateCode(500);
            String stringJson = JSONObject.toJSONString(json);
            resp.getWriter().print(stringJson);
            return;
        } else {
            String inStockId = req.getParameter("inStockId");
            String supplier = req.getParameter("supplier");
            String oughtBalance = req.getParameter("oughtBalance");
            String realBalance = req.getParameter("realBalance");
            String nowDate = req.getParameter("nowDate");
            String remark = req.getParameter("remark");
            String payState = req.getParameter("payState");
            SaleStockInfo ssi = new SaleStockInfo();
            try {
                ssi.setSaleStockId(inStockId);
                //客户id
                ssi.setClientId(Integer.valueOf(supplier));
                ssi.setClientName(cd.queryClientById(Integer.valueOf(supplier)).getClientName());

                ssi.setCreateDate(sdf2.parse(nowDate));
                ssi.setOughtBalance(Double.valueOf(oughtBalance));
                ssi.setRealBalance(Double.valueOf(realBalance));
                ssi.setPayState(payState);
                HttpSession session = req.getSession();
                UserInfo ui = (UserInfo) session.getAttribute("user");
                ssi.setUserId(ui.getUserId());
                ssi.setUserName(ui.getUserName());
                ssi.setRemark(remark);
            } catch (NumberFormatException | ParseException e) {
                e.printStackTrace();
                JsonResult json = new JsonResult();
                json.setMsg("应付金额，实付金额，日期不能为空且格式正确");
                json.setStateCode(500);

                String jsonString = JSONObject.toJSONString(json);
                resp.getWriter().print(jsonString);

            }
            //生成进货单
            int row = ssd.addSaleStock(ssi);
            if (row > 0) {
                //添加商品
                int row2 = 0;
                Double cbje = 0d;
                for (SaleStockProInfo sspi : saleStockPros) {
                    //添加进货单商品
                    row2 += ssd.addSaleStockPro(sspi);
                    //更改库存数量
                    ProductInfo pro = pd.queryProByProId(sspi.getProId());
                    //库存数量等于原数量-出库数量
                    pro.setProNum(pro.getProNum() - sspi.getProNum());
                    //更改商品售价
                    pro.setOutPrice(sspi.getSalePrice());
                    //修改状态至2表示该商品已经产生订单
                    pro.setState("2");
                    pd.alterPro(pro);
                    //商品总成本
                    cbje += pro.getInPrice() * sspi.getProNum();
                }
                int row3 = ssd.alterSalStock(cbje, inStockId);

                if (row2 == saleStockPros.size() && row3>0) {

                    saleStockPros.clear();
                    JsonResult json = new JsonResult();
                    json.setMsg("完成");
                    json.setStateCode(200);

                    String jsonString = JSONObject.toJSONString(json);
                    resp.getWriter().print(jsonString);
                    return;
                }
            }
            JsonResult json = new JsonResult();
            json.setMsg("添加失败");
            json.setStateCode(500);

            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
            return;


        }
    }

    //展示进货商品及总金额
    private void start3(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonResult json = new JsonResult();
        json.setStateCode(200);
        json.setObj(saleStockPros);
        if (!saleStockPros.isEmpty()) {
            Double total = 0d;
            Double cbtotal = 0d;
            for (SaleStockProInfo sspi : saleStockPros) {
                total = total + sspi.getTotalPrice();

            }
            json.setMsg(String.valueOf(total));
        }

        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);
    }

    //移除进货商品
    private void deleteProStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proId = req.getParameter("proId");
        for (SaleStockProInfo sspi : saleStockPros) {
            if (sspi.getProId().equals(proId)) {
                saleStockPros.remove(sspi);

                JsonResult json = new JsonResult();
                json.setMsg("移除成功");
                json.setStateCode(200);
//                json.setObj(saleStockPros);
                String stringJson = JSONObject.toJSONString(json);
                resp.getWriter().print(stringJson);
                return;
            }
        }
    }

    //修改本次出库商品
    private void alterProStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proId = req.getParameter("proId");
        for (SaleStockProInfo sspi : saleStockPros) {
            if (sspi.getProId().equals(proId)) {
                String newInPrice = req.getParameter("newInPrice");
                String newProNum = req.getParameter("newProNum");

                Double inPrice = null;
                try {
                    //根据编号获得商品
                    ProductInfo pi = pd.queryProByProId(proId);
                    if (pi.getProNum() < Integer.valueOf(newProNum)) {
                        JsonResult json = new JsonResult();
                        json.setMsg("商品库存不足");
                        json.setStateCode(500);
//                json.setObj(saleStockPros);
                        String stringJson = JSONObject.toJSONString(json);
                        resp.getWriter().print(stringJson);
                        return;
                    }


                    inPrice = Double.valueOf(newInPrice);
                    Integer proNum = Integer.valueOf(newProNum);
                    //本次售价
                    sspi.setSalePrice(inPrice);
                    sspi.setProNum(proNum);

                    JsonResult json = new JsonResult();
                    json.setMsg("修改成功");
                    json.setStateCode(200);
                    json.setObj(saleStockPros);
                    String stringJson = JSONObject.toJSONString(json);
                    resp.getWriter().print(stringJson);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    JsonResult json = new JsonResult();
                    json.setMsg("商品进价或者数量输入格式有误");
                    json.setStateCode(500);

                    String stringJson = JSONObject.toJSONString(json);
                    resp.getWriter().print(stringJson);
                }
                return;

            }
        }
    }

    //添加本次出库商品
    private void addProStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proId = req.getParameter("proId");

        //保证修改商品的唯一性
        if (!saleStockPros.isEmpty()) {
            for (SaleStockProInfo sspi : saleStockPros) {
                if (sspi.getProId().equals(proId)) {
                    JsonResult json = new JsonResult();
                    json.setMsg("已经添加过此商品，如有改变需求可以直接去修改");
                    json.setStateCode(500);
                    String stringJson = JSONObject.toJSONString(json);
                    resp.getWriter().print(stringJson);
                    return;
                }
            }
        }


        //根据编号获得商品
        ProductInfo pi = pd.queryProByProId(proId);

        String inStockId = req.getParameter("inStockNo");
        String newInPrice = req.getParameter("newInPrice");
        String newProNum = req.getParameter("newProNum");


        try {
            if (pi.getProNum() < Integer.valueOf(newProNum)) {
                JsonResult json = new JsonResult();
                json.setMsg("商品库存不足");
                json.setStateCode(500);
//                json.setObj(saleStockPros);
                String stringJson = JSONObject.toJSONString(json);
                resp.getWriter().print(stringJson);
                return;
            }

            Double inPrice = Double.valueOf(newInPrice);
            Integer proNum = Integer.valueOf(newProNum);

            SaleStockProInfo sspi = new SaleStockProInfo();
            //售价
            sspi.setSalePrice(inPrice);
            sspi.setProNum(proNum);
            //出库订单号
            sspi.setSaleStockId(inStockId);
            sspi.setProModel(pi.getProModel());
            sspi.setProName(pi.getProName());
            sspi.setProUnit(pi.getUnit());
            sspi.setTypeId(pi.getTypeId());
            sspi.setTypeName(pi.getTypeName());
            sspi.setProId(proId);
            saleStockPros.add(sspi);


            JsonResult json = new JsonResult();
            json.setMsg("成功");
            json.setStateCode(200);
            json.setObj(saleStockPros);
            String stringJson = JSONObject.toJSONString(json);
            resp.getWriter().print(stringJson);

        } catch (NumberFormatException e) {
            e.printStackTrace();

            JsonResult json = new JsonResult();
            json.setMsg("商品进价或者数量输入格式有误");
            json.setStateCode(500);

            String stringJson = JSONObject.toJSONString(json);
            resp.getWriter().print(stringJson);
        }

    }

    //查询所有客户及生成订单编号
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String saleStockid = "ck0000000000";
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String no = "ck" + sdf.format(d);
        ArrayList<String> inStockIds = ssd.queryAllSaleStockId();
        if (!inStockIds.isEmpty()) {
            for (String id : inStockIds) {
                if (id.contains(no)) {
                    //包前不包后
                    String last = id.substring(10, 12);
                    String newLast = String.valueOf(Integer.valueOf(last) + 1);
                    newLast = newLast.length() == 1 ? "0" + newLast : newLast;
                    saleStockid = no + newLast;
                } else {
                    saleStockid = no + "01";
                }
            }
        } else {
            saleStockid = no + "01";
        }

        //
        ArrayList<ClientInfo> sis = cd.queryAllClient();
        JsonResult json = new JsonResult();
        json.setObj(sis);
        //订单编号
        json.setMsg(saleStockid);
        //当前日期
        json.setMsg2(sdf2.format(d));
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);

    }

    //商品页面相关 显示所有已上架商品
    private void stare1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == "" ? "1" : currentPage;
        sp.setTotalRows(pd.queryAllRowsByState1and2());
        sp.setCurrentPage(Integer.valueOf(currentPage));
        sp.setT(pd.queryProByState1and2(sp));

        JsonResult json = new JsonResult();
        json.setObj(sp);
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);

    }
}


