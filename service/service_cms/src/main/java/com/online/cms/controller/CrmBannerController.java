package com.online.cms.controller;


import com.onlin.common.ResultApi;
import com.online.cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-13
 */
@RestController
@RequestMapping("/cms/banner")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("findBanners")
    @Cacheable(value = "bannerList",key = "'byHot'")
    public ResultApi findBanners(){
        return ResultApi.ok().data("list",crmBannerService.list(null));
    }
}

