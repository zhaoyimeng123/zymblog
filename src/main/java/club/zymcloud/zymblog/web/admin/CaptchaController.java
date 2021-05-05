package club.zymcloud.zymblog.web.admin;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wf.captcha.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * 验证码
 */
@Controller
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping("/getVerifyCode")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 可计算的验证码
     * @param response
     * @throws Exception
     */
    @GetMapping("/getCode")
    public void getCode(HttpServletResponse response) throws Exception {
        ServletOutputStream outputStream = response.getOutputStream();

        //算术验证码 数字加减乘除. 建议2位运算就行:captcha.setLen(2);
        //ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40);

        // 中文验证码
        //ChineseCaptcha captcha = new ChineseCaptcha(120, 40);

        // 英文与数字验证码
        SpecCaptcha captcha = new SpecCaptcha(120, 40);

        //英文与数字动态验证码
        //GifCaptcha captcha = new GifCaptcha(120, 40);

        // 中文动态验证码
        //ChineseGifCaptcha captcha = new ChineseGifCaptcha(120, 40);
        // 几位数运算，默认是两位
        captcha.setLen(4);
        // 获取运算的结果
        String result = captcha.text();
        System.out.println(result);
        captcha.out(outputStream);
    }

}
