package com.miku.svt.instock;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.ProductDao;
import com.miku.dao.buy.InStockDao;
import com.miku.dao.buy.SupplierDao;
import com.miku.utils.JsonResult;
import com.miku.utils.SplitePage;
import com.miku.vo.ProductInfo;
import com.miku.vo.SupplierInfo;
import com.miku.vo.UserInfo;
import com.miku.vo.instock.InStockInfo;
import com.miku.vo.instock.InStockProInfo;

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
 * @date: 2020/8/17 19:36
 * @description:
 */
@WebServlet("/InStockSvt")
public class InStockSvt extends HttpServlet {
    private static final long serialVersionUID = -9162306580063221066L;

    private SupplierDao sd = new SupplierDao();
    private ProductDao pd = new ProductDao();
    private SplitePage sp = new SplitePage();
    private InStockDao isd = new InStockDao();
    //本次进货商品集合
    private ArrayList<InStockProInfo> inStockPros = new ArrayList<>();
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
        if (inStockPros.isEmpty()) {
            JsonResult json = new JsonResult();
            json.setMsg("请添加进货商品");
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
            InStockInfo isi = new InStockInfo();
            try {
                isi.setInStockId(inStockId);
                isi.setSupplierId(Integer.valueOf(supplier));
                isi.setSupplierName(sd.querySupplierById(Integer.valueOf(supplier)).getSupplierName());

                isi.setCreateDate(sdf2.parse(nowDate));
                isi.setOughtBalance(Double.valueOf(oughtBalance));
                isi.setRealBalance(Double.valueOf(realBalance));
                isi.setPayState(payState);
                HttpSession session = req.getSession();
                UserInfo ui = (UserInfo) session.getAttribute("user");
                isi.setUserId(ui.getUserId());
                isi.setUserName(ui.getUserName());
                isi.setRemark(remark);
            } catch (NumberFormatException | ParseException e) {
                e.printStackTrace();
                JsonResult json = new JsonResult();
                json.setMsg("应付金额，实付金额，日期不能为空且格式正确");
                json.setStateCode(500);

                String jsonString = JSONObject.toJSONString(json);
                resp.getWriter().print(jsonString);

            }
            //生成进货单
            int row = isd.addInStock(isi);
            if (row > 0) {
                //添加商品
                int row2 = 0;
                for (InStockProInfo ispi : inStockPros) {
                    //添加进货单商品
                    row2 += isd.addInStockPro(ispi);
                    //更改库存数量
                    ProductInfo pro = pd.queryProByProId(ispi.getProId());
                    //库存数量等于原数量加进货数量
                    pro.setProNum(pro.getProNum() + ispi.getProNum());
                    //更改商品进价
                    pro.setInPrice(ispi.getInPrice());

                    //修改状态至2表示该商品已经产生订单
                    pro.setState("2");
                    pd.alterPro(pro);

                }

                if (row2 == inStockPros.size()) {

                    inStockPros.clear();
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
        json.setObj(inStockPros);
        if (!inStockPros.isEmpty()) {
            Double total = 0d;
            for (InStockProInfo ipi : inStockPros) {
                total = total + ipi.getTotalPrice();
            }
            json.setMsg(String.valueOf(total));
        }
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);
    }

    //移除进货商品
    private void deleteProStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proId = req.getParameter("proId");
        for (InStockProInfo ipi : inStockPros) {
            if (ipi.getProId().equals(proId)) {
                inStockPros.remove(ipi);

                JsonResult json = new JsonResult();
                json.setMsg("移除成功");
                json.setStateCode(200);
                json.setObj(inStockPros);
                String stringJson = JSONObject.toJSONString(json);
                resp.getWriter().print(stringJson);
                return;
            }
        }
    }

    //修改本次进货商品
    private void alterProStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proId = req.getParameter("proId");
        for (InStockProInfo ipi : inStockPros) {
            if (ipi.getProId().equals(proId)) {
                String newInPrice = req.getParameter("newInPrice");
                String newProNum = req.getParameter("newProNum");

                Double inPrice = null;
                try {
                    inPrice = Double.valueOf(newInPrice);
                    Integer proNum = Integer.valueOf(newProNum);

                    ipi.setInPrice(inPrice);
                    ipi.setProNum(proNum);

                    JsonResult json = new JsonResult();
                    json.setMsg("修改成功");
                    json.setStateCode(200);
                    json.setObj(inStockPros);
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

    //添加本次进货商品
    private void addProStock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String proId = req.getParameter("proId");

        //保证修改商品的唯一性
        if (!inStockPros.isEmpty()) {
            for (InStockProInfo ipi : inStockPros) {
                if (ipi.getProId().equals(proId)) {
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
            Double inPrice = Double.valueOf(newInPrice);
            Integer proNum = Integer.valueOf(newProNum);

            InStockProInfo ipi = new InStockProInfo();
            ipi.setInPrice(inPrice);
            ipi.setProNum(proNum);
            ipi.setInStockId(inStockId);
            ipi.setProModel(pi.getProModel());
            ipi.setProName(pi.getProName());
            ipi.setProUnit(pi.getUnit());
            ipi.setTypeId(pi.getTypeId());
            ipi.setTypeName(pi.getTypeName());
            ipi.setProId(proId);
            inStockPros.add(ipi);

            JsonResult json = new JsonResult();
            json.setMsg("成功");
            json.setStateCode(200);
            json.setObj(inStockPros);
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

    //查询所有供应商及生成订单编号
    private void start2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inStockid = "jh0000000000";
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String no = "jh" + sdf.format(d);
        ArrayList<String> inStockIds = isd.queryAllInStockId();
        if (!inStockIds.isEmpty()) {
            for (String id : inStockIds) {
                if (id.contains(no)) {
                    //包前不包后
                    String last = id.substring(10, 12);
                    String newLast = String.valueOf(Integer.valueOf(last) + 1);
                    newLast = newLast.length() == 1 ? "0" + newLast : newLast;
                    inStockid = no + newLast;
                } else {
                    inStockid = no + "01";
                }
            }
        } else {
            inStockid = no + "01";
        }

        //
        ArrayList<SupplierInfo> sis = sd.queryAllSupplier();
        JsonResult json = new JsonResult();
        json.setObj(sis);
        //订单编号
        json.setMsg(inStockid);
        //当前日期
        json.setMsg2(sdf2.format(d));
        String stringJson = JSONObject.toJSONString(json);
        resp.getWriter().print(stringJson);

    }

    //商品页面相关  显示所有已上架商品
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
