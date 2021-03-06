package cn.kanyun.cpa.controller.share;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.model.entity.CpaResult;
import cn.kanyun.cpa.model.entity.user.CpaUser;
import cn.kanyun.cpa.model.entity.user.CpaUserExtend;
import cn.kanyun.cpa.service.file.UploadFileService;
import cn.kanyun.cpa.service.user.CpaUserExtendService;
import cn.kanyun.cpa.service.user.UserService;
import cn.kanyun.cpa.util.QrcodeUtils;
import cn.kanyun.cpa.util.WebPathUtil;
import cn.kanyun.cpa.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;

/**
 * String requestUrl = request.getScheme() //当前链接使用的协议
 * +"://" + request.getServerName()//服务器地址
 * + ":" + request.getServerPort() //端口号
 * +  request.getContextPath() //应用名称，如果应用名称为
 * + request.getServletPath() //请求的相对url
 * + "?" + request.getQueryString(); //请求参数
 * <p>
 * 1.获取域名，如：http://f0rb.iteye.com/
 * StringBuffer url = request.getRequestURL();
 * String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
 * 2.获取带部署环境上下文的域名，如： http://www.iteye.com/admin/
 * StringBuffer url = request.getRequestURL();
 * String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();
 * <p>
 */

/**
 * 1.获取域名，如：http://f0rb.iteye.com/
 * StringBuffer url = request.getRequestURL();
 * String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
 * 2.获取带部署环境上下文的域名，如： http://www.iteye.com/admin/
 * StringBuffer url = request.getRequestURL();
 * String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();
 */

/**
 * 该类为用户分享类,包括生成分享链接,二维码等,如果有用户是从分享链接注册的话,会有相应奖励
 */
@Api(value = "/api/share",tags = "分享模块")
@Controller
@RequestMapping("/api/share")
public class ShareController {

    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Resource(name = CpaUserExtendService.SERVICE_NAME)
    private CpaUserExtendService cpaUserExtendService;
    @Resource(name = UploadFileService.SERVICE_NAME)
    private UploadFileService uploadFileService;
    @Resource(name = UserService.SERVICE_NAME)
    private UserService userService;


    /**
     * @describe:
     * 生成分享二维码, 与链接, 同时生成,(先查看该用户是否有二维码，有则返回,无则生成)
     * @param request
     * @return
     */
    @ApiOperation(value = "/generateChain",notes = "生成分享二维码, 与链接, 同时生成,(先查看该用户是否有二维码，有则返回,无则生成)",httpMethod = "GET",response = CpaResult.class)
    @RequestMapping("/generateChain")
    @ResponseBody
    public CpaResult generateChain(HttpServletRequest request) {
        CpaResult result = new CpaResult();
        try {
            CpaUser user = WebUtil.getSessionUser(request);
            CpaUserExtend userExtend = user.getCpaUserExtend();
            if (null == userExtend.getShareQrUrl()) {
                String shareChain = WebPathUtil.getRequestPath(request, true, false) + "api/user/skipWebPage/" + user.getId();
                String qrPic = ShareController.class.getClassLoader().getResource("").getPath() + "/share/" + System.currentTimeMillis() + ".jpg";
                QrcodeUtils.gen(shareChain, new File(qrPic));
                String pirUrl = uploadFileService.upLoadQRPic(qrPic);
                userExtend.setShareQrUrl(pirUrl);
                userExtend.setCreateDate(LocalDateTime.now());
                userExtend.setShareChain(shareChain);
                cpaUserExtendService.update(userExtend);
            }
            result.setData(userExtend);
            result.setState(CpaConstants.OPERATION_SUCCESS);
        } catch (Exception e) {
            logger.error("ERROR：/api/share/generateChain 生成分享链接出错：", e);
            result.setState(CpaConstants.OPERATION_ERROR);
        }
        return result;
    }
}
