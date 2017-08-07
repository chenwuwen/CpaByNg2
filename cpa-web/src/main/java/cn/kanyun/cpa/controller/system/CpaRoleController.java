package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.service.system.ICpaRoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/6/16.
 */
@Controller
@RequestMapping("/cpaRole")
public class CpaRoleController {
    private final Logger logger = Logger.getLogger(CpaRoleController.class);
    @Resource
    private ICpaRoleService cpaRoleService;
}
