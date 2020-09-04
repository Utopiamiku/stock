package com.miku.svt;

import com.miku.dao.UserDao;
import com.miku.utils.TestTool;
import com.miku.vo.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author Utopiamiku
 * @date 2020/8/9 10:54
 * @File LoginSvt.py
 */
@WebServlet("/login")
public class LoginSvt extends HttpServlet {

    private static final long serialVersionUID = -233440597343864207L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String loginCode = req.getParameter("loginCode");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        UserDao ud = new UserDao();

        if (loginCode != "" && password != "") {
            UserInfo ui = ud.queryUserByLoginCode(loginCode);
            //如果账号密码与数据库匹配
            String a = TestTool.INSTALL.getMD5String(password);
            System.out.println(a);
            if (loginCode.equals(ui.getLoginCode()) && a.equals(ui.getPassWord() ) ) {

                //不论有没有记住密码，把账号都存入cookie
                Cookie rc = new Cookie("rememberCode", ui.getLoginCode());
                rc.setMaxAge(365 * 24 * 60 * 60);
                resp.addCookie(rc);

                //如果勾选了记住密码,创建存放密码的cookie
                if (remember != null) {
                    Cookie cookie = new Cookie("rememberPwd", null);
                    cookie.setMaxAge(0);//设置为dao0 立即删除
                    resp.addCookie(cookie);

                    Cookie rp = new Cookie("rememberPwd",password);
                    rp.setMaxAge(24 * 60 * 60);
                    resp.addCookie(rp);

                }

                //如果没有勾选并且以前保存过密码，则删除此密码cookie
                if (remember == null) {
                    Cookie cookie = new Cookie("rememberPwd", null);
                    cookie.setMaxAge(0);//设置为dao0 立即删除

                    resp.addCookie(cookie);
                }

                //将用户存入session
                session.setAttribute("user", ui);
                resp.sendRedirect("start");

            } else {

                req.setAttribute("errorMsg", "账号密码不匹配");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } else {

            req.setAttribute("errorMsg", "请输入账号和密码");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
}
