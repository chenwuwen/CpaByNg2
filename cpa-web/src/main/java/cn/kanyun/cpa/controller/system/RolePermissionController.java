package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.service.system.RolePermissionService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 *
 * @author Kanyun
 * @date 2017/6/16
 */
@Controller
@RequestMapping("/rolePermission")
public class RolePermissionController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RolePermissionController.class);
    @Resource(name = RolePermissionService.SERVICE_NAME)
    private RolePermissionService rolePermissionService;
}
