package com.miku.svt;

import com.miku.dao.MenuDao;
import com.miku.vo.PermissionInfo;
import com.miku.vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/9 14:00
 * @File StartSvt.py
 */
@WebServlet("/start")
public class StartSvt extends HttpServlet {

    private static final long serialVersionUID = 1033003886828214139L;
    private MenuDao md =new MenuDao();
    private PermissionInfo pi = new PermissionInfo();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户
        HttpSession session = req.getSession();
        UserInfo ui = (UserInfo) session.getAttribute("user");

        //查找用户权限
        ArrayList<PermissionInfo> permissionInfos = md.queryParentMenu(ui.getUserId());

        //将用户所拥有的权限插入session
        session.setAttribute("pis",permissionInfos);
        resp.sendRedirect("start.jsp");

    }
}




















