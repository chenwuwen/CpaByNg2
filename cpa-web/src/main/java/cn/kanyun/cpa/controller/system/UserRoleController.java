package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.service.system.IUserRoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController {
    private final Logger logger = Logger.getLogger(UserRoleController.class);
    @Resource
    private IUserRoleService userRoleService;
}
