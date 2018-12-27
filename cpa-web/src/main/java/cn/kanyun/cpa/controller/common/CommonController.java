package cn.kanyun.cpa.controller.common;

import cn.kanyun.cpa.model.constants.CpaConstants;
import cn.kanyun.cpa.util.ValidateCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
@Api(value = "/api", tags = "公共控制层")
@Controller
@RequestMapping(value = "/api")
public class CommonController {
    /**
     * 生成验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "/validateCode", notes = "请求验证码", httpMethod = "GET")
    @RequestMapping(value = "/validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 4, null);
        request.getSession().setAttribute(CpaConstants.IDENTIFYING_CODE, verifyCode);
        response.setContentType("image/jpeg");
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 34, 3, true, Color.WHITE, Color.BLACK, null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }
}