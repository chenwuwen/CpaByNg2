package cn.kanyun.cpa.service.user.impl;

import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.CommonServiceImpl;
import cn.kanyun.cpa.service.user.CpaUserExtendService;
import org.springframework.stereotype.Service;

@Service(CpaUserExtendService.SERVICE_NAME)
public class CpaUserExtendServiceImpl extends CommonServiceImpl<Long, CpaUserExtend> implements CpaUserExtendService {
}
