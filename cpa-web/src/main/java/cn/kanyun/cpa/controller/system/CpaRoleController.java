package cn.kanyun.cpa.controller.system;

import cn.kanyun.cpa.service.system.CpaRoleService;
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
@RequestMapping("/api/cpaRole")
public class CpaRoleController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CpaRoleController.class);
    @Resource(name = CpaRoleService.SERVICE_NAME)
    private CpaRoleService cpaRoleService;
}
