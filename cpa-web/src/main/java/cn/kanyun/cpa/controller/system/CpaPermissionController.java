package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.controller.itempool.CpaRepertoryController;
import cn.kanyun.cpa.service.system.ICpaPermissionService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Controller
@RequestMapping("/cpaPermission")
public class CpaPermissionController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CpaPermissionController.class);
    @Resource
    private ICpaPermissionService cpaPermissionService;
}
