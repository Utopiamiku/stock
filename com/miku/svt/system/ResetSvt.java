package com.miku.svt.system;

import com.alibaba.fastjson.JSONObject;
import com.miku.ResetDao;
import com.miku.utils.JsonResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Utopiamiku
 * @date: 2020/8/24 10:39
 * @description:
 */
@WebServlet("/ResetSvt")
public class ResetSvt extends HttpServlet {
    private static final long serialVersionUID = 8827418405392364271L;

    private ResetDao rd = new ResetDao();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action){
            case "delete":
                delete(req,resp);
                break;
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String delete = req.getParameter("delete");
        if(delete.equals("apart")){
            rd.deleteAllData();
            JsonResult json = new JsonResult("商品及订单信息清除成功",200);
            resp.getWriter().print(JSONObject.toJSON(json));

        }
        if (delete.equals("all")){
            rd.deleteAllData();
            JsonResult json = new JsonResult("所有数据已重置",200);
            resp.getWriter().print(JSONObject.toJSON(json));
        }


    }
}
