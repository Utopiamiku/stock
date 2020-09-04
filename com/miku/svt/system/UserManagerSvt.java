package com.miku.svt.system;

import com.miku.dao.RoleDao;
import com.miku.dao.UserDao;
import com.miku.vo.RoleInfo;
import com.miku.vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/10 20:44
 * @File UserManagerSvt.py
 */
@WebServlet("/userManager")
public class UserManagerSvt extends HttpServlet {

    private static final long serialVersionUID = -6586912277936776633L;
    private UserDao ud = new UserDao();
    private RoleDao rd = new RoleDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<UserInfo> users = ud.queryAllUser();
        ArrayList<RoleInfo> roles = rd.queryAllRole();
        String action = req.getParameter("action");

        action = action == null ? "null" : action;
        switch (action) {
            case "addUser":
                //添加用户
                UserInfo ui = new UserInfo();
                String loginCode = req.getParameter("loginCode");
                ui.setLoginCode(loginCode);
                ui.setPassWord(req.getParameter("userPwd"));
                ui.setUserName(req.getParameter("userName"));
                String remark = req.getParameter("remark");
                remark = remark == null ? "null" : remark;
                ui.setRemark(remark);
                //添加用户
                ud.addUser(ui);
                //接受用户所选择的角色
                String[] selectRole = req.getParameterValues("allRole[]");
                //获取刚才添加的用户
                UserInfo newUser = ud.queryUserByLoginCode(loginCode);
                //增加用户角色
                for (String r : selectRole) {
                    ud.addUser(newUser.getUserId(), Integer.valueOf(r));
                }
                resp.sendRedirect("start.jsp?nextUrl=userManager");
                break;
//修改用户
            case "alterUser":
                    alterUser(req,resp);
                break;
            case "deleteUser":
                String userId = req.getParameter("userId");
                ud.deleteUser(Integer.valueOf(userId));
                resp.sendRedirect("start.jsp?nextUrl=userManager");
                break;
        }
        if (action.equals("null")) {
            req.setAttribute("roles", roles);
            req.setAttribute("users", users);
            req.getRequestDispatcher("jsp/menuSystem/userManage.jsp").forward(req, resp);
        }

    }

    //修改用户
    protected void alterUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserInfo ui = new UserInfo();
        String userId = req.getParameter("userId");
        ui.setUserId(Integer.valueOf(userId));
        String loginCode = req.getParameter("loginCode");
        ui.setLoginCode(loginCode);
        ui.setPassWord(req.getParameter("userPwd"));
        ui.setUserName(req.getParameter("userName"));
        String remark = req.getParameter("remark");
        remark = remark == null ? "null" : remark;
        ui.setRemark(remark);
        //修改用户
        ud.alterUser(ui);
        //接收用户所选择的角色

        String[] selectRole = req.getParameterValues("allRole[]");
        //获取刚才添加的用户
        UserInfo newUser = ud.queryUserByLoginCode(loginCode);
        //删除用户之前的所有角色
        ud.deleteUserRole(Integer.valueOf(userId));
        //增加新的用户角色
        for (String r : selectRole) {
            ud.addUser(newUser.getUserId(), Integer.valueOf(r));
        }
        resp.sendRedirect("start.jsp?nextUrl=userManager");

    }
}
