package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.service.system.UserRoleService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserRoleController.class);
    @Resource(name = UserRoleService.SERVICE_NAME)
    private UserRoleService userRoleService;
}
