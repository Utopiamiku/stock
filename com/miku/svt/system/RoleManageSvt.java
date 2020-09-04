package com.miku.svt.system;

import com.alibaba.fastjson.JSONObject;
import com.miku.dao.MenuDao;
import com.miku.dao.RoleDao;
import com.miku.utils.JsonResult;
import com.miku.utils.PermissionSet;
import com.miku.utils.SplitePage;
import com.miku.vo.PermissionInfo;
import com.miku.vo.RoleInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Utopiamiku
 * @date 2020/8/11 19:51
 * @File RoleManageSvt.py
 */
@WebServlet("/roleManageSvt")
public class RoleManageSvt extends HttpServlet {

    private static final long serialVersionUID = 8173716327156708814L;
    private RoleDao rd = new RoleDao();
    private SplitePage sp = new SplitePage();
    private MenuDao md = new MenuDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        action = action == null ? "null" : action;
        switch (action) {
            case "null":
                start(req, resp);
                break;
            case "addRole":
                addRole(req, resp);
                break;
            case "alterRole":
                alterRole(req,resp);
                break;
            case "deleteRole":
                deleteRole(req,resp);
                break;
        }
    }

    //修改角色及权限
    private void alterRole(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        String roleId = req.getParameter("roleId");
        String rolemuen = req.getParameter("rolemuen");
        String roleName = req.getParameter("roleName");
        String remark = req.getParameter("remark");
        String[] muens = rolemuen.split(",");
        if(roleId.equals("1")){
            JsonResult json = new JsonResult();
            json.setStateCode(500);
            json.setMsg("抱歉  你的权限不够");
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
            return;
        }

        //删除角色原本权限
        int row = rd.deleteRoleMenu(Integer.valueOf(roleId));
        //添加新的权限
        int row1 = 0;
        for(String m:muens){
            int mId = Integer.valueOf(m);
            row1 += rd.addRole(Integer.valueOf(roleId),mId);
        }
        RoleInfo ri = new RoleInfo();
        ri.setRemark(remark);
        ri.setRoleName(roleName);
        ri.setRoleId(Integer.valueOf(roleId));
        //修改角色
        int row2 = rd.alterRole(ri);
        if(row1 == muens.length  && row2>0){
            JsonResult json = new JsonResult();
            json.setStateCode(200);
            json.setMsg("角色修改成功");
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }


    }

    //删除角色
    private void deleteRole(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        String roleId = req.getParameter("roleId");

        if(roleId.equals("1")){
            JsonResult json = new JsonResult();
            json.setStateCode(500);
            json.setMsg("抱歉  你的权限不够");
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
            return;
        }

        //删除角色权限
        int row = rd.deleteRoleMenu(Integer.valueOf(roleId));
        //删除角色
        int row2 = rd.deleteRole(Integer.valueOf(roleId));
        if(row >= 0 && row2>0){
            JsonResult json = new JsonResult();
            json.setStateCode(200);
            json.setMsg("角色删除成功");
            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }

    }

    //初始化页面
    private void start(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //分页相关
        String currentPage = req.getParameter("currentPage");
        currentPage = currentPage == null ? "1" : currentPage;
        sp.setCurrentPage(Integer.valueOf(currentPage));
        sp.setTotalRows(rd.queryAllRows());
        sp.setT(rd.queryRoleByPage(sp));

        //权限相关
        String roleId = req.getParameter("roleId");
        ArrayList<Integer> permissionInfos1 = new ArrayList<>();
        if (!"0".equals(roleId)) {
            //需要修改的角色原先拥有de权限
            permissionInfos1 = rd.queryPerssionByroleId(Integer.valueOf(roleId));
        }
        //所有权限
        ArrayList<PermissionInfo> permissionInfos = md.queryAllMenu();
        ArrayList<PermissionSet> listPs = new ArrayList<>();
        for ( PermissionInfo pi : permissionInfos) {
            PermissionSet ps = new PermissionSet();
            ps.setId(pi.getMenuId());
            ps.setpId(pi.getParentId());
            ps.setName(pi.getMenuName());
            listPs.add(ps);
            for (int permissionId : permissionInfos1) {
                //如果角色有此权限，则默认勾选
                if (pi.getMenuId() == permissionId) {
                    ps.setChecked(true);
                }
            }
        }
        JsonResult json = new JsonResult();
        json.setObj2(listPs);
//        String jsonStringTree = JSONObject.toJSONString(json);
        json.setObj(sp);
        String jsonString = JSONObject.toJSONString(json);
        resp.getWriter(). print(jsonString);
    }

    //添加角色
    private void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String roleName = req.getParameter("roleName");
        String remark = req.getParameter("remark");
        RoleInfo ri = new RoleInfo();
        ri.setRoleName(roleName);
        ri.setRemark(remark);
        int row = rd.addRole(ri);
        if (row > 0) {
            JsonResult json = new JsonResult();
            json.setMsg("添加角色成功");
            json.setStateCode(200);

            String jsonString = JSONObject.toJSONString(json);
            resp.getWriter().print(jsonString);
        }

    }
}
