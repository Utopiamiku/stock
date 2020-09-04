package com.miku;

import com.miku.dao.BaseDao;

/**
 * @author: Utopiamiku
 * @date: 2020/8/25 11:22
 * @description:
 */
public class ResetDao extends BaseDao {


    public void deleteApartData() {
        alterTable(" DELETE from bsdsp");
        alterTable(" DELETE from bsd");
        alterTable("DELETE from bydsp");
        alterTable("DELETE from byd");
        alterTable("DELETE from ckdsp");
        alterTable("DELETE from ckd");
        alterTable("DELETE from jhdsp");
        alterTable("DELETE from jhd");
        alterTable("DELETE from thdsp");
        alterTable("DELETE from thd");
        alterTable("DELETE from tkdsp");
        alterTable("DELETE from tkd");
        alterTable("DELETE from gys");
        alterTable("DELETE from kh");
        alterTable("DELETE from spxx");
        alterTable("DELETE from splb");

    }


    public void deleteAllData() {
        alterTable(" DELETE from bsdsp");
        alterTable(" DELETE from bsd");
        alterTable("DELETE from bydsp");
        alterTable("DELETE from byd");
        alterTable("DELETE from ckdsp");
        alterTable("DELETE from ckd");
        alterTable("DELETE from jhdsp");
        alterTable("DELETE from jhd");
        alterTable("DELETE from thdsp");
        alterTable("DELETE from thd");
        alterTable("DELETE from tkdsp");
        alterTable("DELETE from tkd");
        alterTable("DELETE from gys");
        alterTable("DELETE from kh");
        alterTable("DELETE from spxx");
        alterTable("DELETE from splb");
        alterTable(" DELETE from rolemenu where roleid !=1");
        alterTable("DELETE from role where roleid !=1");
        alterTable("DELETE from user_role where userid !=1");
        alterTable("DELETE from users where userid !=1");

    }

}
