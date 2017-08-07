package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.service.system.IRolePermissionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Controller
@RequestMapping("/rolePermission")
public class RolePermissionController {
    private final Logger logger = Logger.getLogger(RolePermissionController.class);
    @Resource
    private IRolePermissionService rolePermissionService;
}
