package com.loyer.example.service.ipml;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loyer.example.entity.Result;
import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import com.loyer.example.persistence.mysql.MysqlDemoMapper;
import com.loyer.example.persistence.oracle.OracleDemoMapper;
import com.loyer.example.persistence.postgresql.PostgresqlDemoMapper;
import com.loyer.example.service.DemoService;
import com.loyer.example.utils.CertificateRecognitionUtil;
import com.loyer.example.utils.CheckDBLinkUtil;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kuangq
 * @projectName example
 * @title DemoServiceIpml
 * @description 测试ServiceIpml
 * @date 2019-08-01 10:59
 */
@Service
public class DemoServiceIpml implements DemoService {

    @Resource
    PostgresqlDemoMapper postgresqlDemoMapper;

    @Resource
    MysqlDemoMapper mysqlDemoMapper;

    @Resource
    OracleDemoMapper oracleDemoMapper;

    @Override
    public Result queryDataBase() throws Exception {
        Map resultMap = new HashMap() {{
            put("postgresql", CheckDBLinkUtil.postgresqlDataSource() ? postgresqlDemoMapper.queryDataBase() : "链接失败");
            put("mysql", CheckDBLinkUtil.mysqlDataSource() ? mysqlDemoMapper.queryDataBase() : "链接失败");
            put("oracle", CheckDBLinkUtil.oracleDataSource() ? oracleDemoMapper.queryDataBase() : "链接失败");
        }};
        return Result.hintEnum(HintEnum.HINT_1000, resultMap);
    }

    @Override
    public Result upload(HttpServletRequest httpServletRequest) {
        Map resultMap = new HashMap();
        try {
            resultMap.put("data", new HashMap() {{
                httpServletRequest.getParameterMap().forEach((key, value) -> {
                    put(key, ((String[]) value)[0]);
                });
            }});
            Collection<Part> parts = httpServletRequest.getParts();
            Iterator<Part> iterator = parts.iterator();
            while (iterator.hasNext()) {
                Part part = iterator.next();
                if (part.getContentType() != null) {
                    byte[] byteArray = IOUtils.toByteArray(part.getInputStream());
                    resultMap.put("base64", Base64.getEncoder().encodeToString(byteArray));
                    resultMap.put("size", part.getSize());
                    resultMap.put("type", part.getContentType());
                    resultMap.put("name", part.getSubmittedFileName().substring(0, part.getSubmittedFileName().lastIndexOf(".")));
                }
            }
            return Result.hintEnum(HintEnum.HINT_1000, resultMap);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1010, e.getMessage());
        }
    }

    @Override
    public Result saveFile(List<Map> params) throws Exception {
        return Result.hintEnum(HintEnum.HINT_1000, postgresqlDemoMapper.saveFile(params));
    }

    @Override
    public Result deleteFile(Map params) throws Exception {
        return Result.hintEnum(HintEnum.HINT_1000, postgresqlDemoMapper.deleteFile(params));
    }

    @Override
    public Result queryFileInfo(Map params) throws Exception {
        int pageNum = Integer.parseInt(params.get("pageNum").toString());
        int pageSize = Integer.parseInt(params.get("pageSize").toString());
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(postgresqlDemoMapper.queryFileInfo(params));
        Map result = new HashMap() {{
            put("total", pageInfo.getTotal());
            put("pages", pageInfo.getPages());
            put("list", pageInfo.getList());
        }};
        return Result.hintEnum(HintEnum.HINT_1000, result);
    }

    @Override
    public Result certificateRecognition(HttpServletRequest httpServletRequest) {
        JSONObject jsonObject = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            httpServletRequest.getParameterMap().forEach((key, value) -> {
                if ("fileCode".equals(key)) {
                    stringBuffer.append(((String[]) value)[0]);
                }
            });
            Collection<Part> parts = httpServletRequest.getParts();
            Iterator<Part> iterator = parts.iterator();
            while (iterator.hasNext()) {
                Part part = iterator.next();
                if (part.getContentType() != null) {
                    jsonObject = CertificateRecognitionUtil.certificateRecognition(stringBuffer.toString(), part.getInputStream());
                }
            }
            return Result.hintEnum(HintEnum.HINT_1000, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(HintEnum.HINT_1017, e.getMessage());
        }
    }

    @Override
    public Result threadTest(Map params) throws Exception {
        ThreadTest threadTest = new ThreadTest();
        int nThreads = Integer.parseInt(params.get("nThreads").toString());
        ExecutorService exe = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < nThreads; i++) {
            params.put("index", i);
            ThreadTest thread = new ThreadTest(threadTest, i, params);
            exe.execute(thread);
        }
        exe.shutdown();
        while (true) {
            if (exe.isTerminated()) {
                break;
            }
            Thread.sleep(1000);
        }
        return Result.hintEnum(HintEnum.HINT_1000, threadTest.getResult());
    }

    public class ThreadTest implements Runnable {

        private ThreadTest threadTest;
        private int index;
        private Map params;
        private Map result;

        public Map getResult() {
            return result;
        }

        public void setResult(Map result) {
            this.result = result;
        }

        public ThreadTest() {
        }

        public ThreadTest(ThreadTest threadTest, int index, Map params) {
            this.threadTest = threadTest;
            this.index = index;
            this.params = params;
        }

        @Override
        public void run() {
            try {
                int millis = new Random().nextInt(10) * 1000;
                Thread.sleep(millis);
                if (threadTest.getResult() == null) {
                    threadTest.setResult(new HashMap());
                }
                threadTest.getResult().put(index, String.format("线程等待时长：%s", millis));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Result queryUserInfo(Map params) throws Exception {
        return Result.hintEnum(HintEnum.HINT_1000, mysqlDemoMapper.queryUserInfo(params));
    }
}