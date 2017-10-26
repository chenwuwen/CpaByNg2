package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.service.system.IRolePermissionService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Controller
@RequestMapping("/rolePermission")
public class RolePermissionController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RolePermissionController.class);
    @Resource(name = IRolePermissionService.SERVICE_NAME)
    private IRolePermissionService rolePermissionService;
}
