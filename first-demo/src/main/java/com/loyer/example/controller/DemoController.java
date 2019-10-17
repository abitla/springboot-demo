package com.loyer.example.controller;

import com.alibaba.fastjson.JSON;
import com.loyer.example.entity.Result;
import com.loyer.example.enums.HintEnum;
import com.loyer.example.service.DemoService;
import com.loyer.example.utils.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kuangq
 * @projectName example
 * @title DemoController
 * @description 测试Controller
 * @date 2019-07-16 15:26
 */
@RestController
@RequestMapping("demo")
@Api(tags = "测试接口Api")
public class DemoController {

    public static String version = "1.0";

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    DemoService demoService;

    @GetMapping("showVersion")
    public Result showVersion() {
        return Result.hintEnum(HintEnum.HINT_1000, version);
    }

    @GetMapping("getPort/{uuid}")
    public Result getPort(@PathVariable("uuid") String uuid) throws Exception {
        redisUtil.setValue(uuid, uuid, 600L);
        return Result.success(uuid);
    }

    @PostMapping("postPort")
    public Result postPort(@RequestBody Map params) throws Exception {
        redisUtil.setValue(GeneralUtil.getUUID(), JSON.toJSONString(params), 600L);
        return Result.hintEnum(HintEnum.HINT_1000, params);
    }

    @GetMapping("queryDataBase")
    public Result queryDataBase() throws Exception {
        return demoService.queryDataBase();
    }

    @PostMapping("upload")
    public Result upload(HttpServletRequest httpServletRequest) {
        return demoService.upload(httpServletRequest);
    }

    @PostMapping("saveFile")
    public Result saveFile(@RequestBody List<Map> params) throws Exception {
        return demoService.saveFile(params);
    }

    @PostMapping("deleteFile")
    public Result deleteFile(@RequestBody Map params) throws Exception {
        return demoService.deleteFile(params);
    }

    @PostMapping("queryFileInfo")
    public Result queryFileInfo(@RequestBody Map params) throws Exception {
        CheckParamsUtil.checkPaging(params);
        return Result.hintEnum(HintEnum.HINT_1000, demoService.queryFileInfo(params));
    }

    @PostMapping("certificateRecognition")
    public Result certificateRecognition(HttpServletRequest httpServletRequest) {
        return demoService.certificateRecognition(httpServletRequest);
    }

    @PostMapping("getJssdkSignature")
    public Result getJssdkSignature(@RequestParam Map params) throws Exception {
        return Result.hintEnum(HintEnum.HINT_1000, JssdkUtil.getSignature(params));
    }

    @GetMapping("getCookie")
    public Result getCookie(@CookieValue(value = "userName") String userName) {
        return Result.hintEnum(HintEnum.HINT_1000, userName);
    }

    @PostMapping("threadTest")
    public Result threadTest(@RequestBody Map params) throws Exception {
        return demoService.threadTest(params);
    }

    @PostMapping("convertPDF")
    public Result convertPDF(@RequestBody Map params) throws Exception {
        String base64Content = ConvertPDF.PDFToBase64(new File("E:\\OceanSoft\\Work\\云南出入境项目\\微警务接口资料new.pdf"));
        ConvertPDF.Base64ToPDF(base64Content, "E:/Temp/微警务接口资料.pdf");
        return Result.hintEnum(HintEnum.HINT_1000, true);
    }

    @GetMapping("queryUserInfo")
    public Result threadTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return Result.hintEnum(HintEnum.HINT_1000, demoService.queryUserInfo(null));
    }

    @RequestMapping("freemarker")
    public ModelAndView freemarker(Model model) {
        Map map = new HashMap() {{
            put("name", "freemarker模板");
            put("type", "加载ftl文件");
        }};
        model.addAttribute("params", map);
        return new ModelAndView("freemarker/index");
    }

    @RequestMapping("thymeleaf")
    public ModelAndView thymeleaf(Model model) {
        Map map = new HashMap() {{
            put("name", "thymeleaf模板");
            put("type", "加载html文件");
        }};
        model.addAttribute("params", map);
        return new ModelAndView("thymeleaf/index");
    }
}