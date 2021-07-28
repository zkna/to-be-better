package com.gyx.datahandler.controller;


import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.gyx.datahandler.service.ITCompanyOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zkna
 * @since 2021-07-14
 */
@RestController
@RequestMapping("/t-company-overview")
public class TCompanyOverviewController {

    @Autowired
    ITCompanyOverviewService itCompanyOverviewService;


}
