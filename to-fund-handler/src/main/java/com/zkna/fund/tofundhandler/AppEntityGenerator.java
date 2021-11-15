package com.zkna.fund.tofundhandler;

import cn.org.atool.generator.FileGenerator;
import cn.org.atool.generator.annotation.Table;
import cn.org.atool.generator.annotation.Tables;

public class AppEntityGenerator {
    static final String url = "jdbc:mysql://localhost:3306/fund_eastmoney?useSSL=false&useUnicode=true&characterEncoding=utf-8";
//
//    public static void main(String[] args) {
//        FileGenerator.build(AppEntityGenerator.Abc.class);
//    }

    @Tables(
            /** 数据库连接信息 **/
            url = url, username = "fund", password = "Hundsun!123",
            /** Entity类parent package路径 **/
            basePack = "com.zkna.fund.tofundhandler",
            /** Entity代码源目录 **/
            srcDir = "to-fund-handler/src/main/java",
            /** Dao代码源目录 **/
            daoDir = "to-fund-handler/src/main/java",
            /** 如果表定义记录创建，记录修改，逻辑删除字段 **/
            gmtCreated = "create_date_time", gmtModified = "modify_date_time",
            /** 需要生成文件的表 **/
            tables = @Table(value = {"t_company_list","t_fund_list"})
    )
    static class Abc {
    }



}