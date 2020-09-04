package com.miku.svt.system;

import com.miku.dao.RoleDao;
import com.miku.dao.UserDao;
import com.miku.vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Utopiamiku
 * @date 2020/8/11 17:23
 * @File VerifySvt.py
 */
@WebServlet("/VerifySvt")
public class VerifySvt extends HttpServlet {

    private RoleDao rd = new RoleDao();
    private UserDao ud = new UserDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String action = req.getParameter("action");
        action = action==null?"null":action;

        switch (action){
            case "loginCode":
                String loginCode = req.getParameter("loginCode");
                ArrayList<String> loginCodes = ud.queryAllUserLoginCode();
                System.out.println(loginCode);
                if(loginCodes.contains(loginCode)){
                    out.print("{\"valid\":false}");
                }else{
                    out.print("{\"valid\":true}");
                }
                break;
            case "roleName":
                String roleName = req.getParameter("roleName");
                ArrayList<String> roleNames = rd.queryAllRoleName();
                System.out.println(roleName);
                if(roleNames.contains(roleName)){
                    out.print("{\"valid\":false}");
                }else{
                    out.print("{\"valid\":true}");
                }
                break;
        }

    }
}
