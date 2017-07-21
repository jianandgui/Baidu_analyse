package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataEnum;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartTypeEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.OperationFailureException;
import cn.edu.swpu.cins.event.analyse.platform.exception.UserException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.ReportService;
import cn.edu.swpu.cins.event.analyse.platform.utility.chart.generator.impl.ChartGeneratorImpl;
import freemarker.template.Template;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LLPP on 2017/6/19.
 */
@RequestMapping("/event")
@RestController
public class ReportController {
    private ReportService reportService;
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private ChartGeneratorImpl chartGenerator;

    @Autowired
    public ReportController(ReportService reportService
            , FreeMarkerConfigurer freeMarkerConfigurer) {
        this.reportService = reportService;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @GetMapping("/report/{year}/{issue}")
    public void getReport(HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable int year
            , @PathVariable int issue
            , @RequestParam("permission") String permission
            , HttpSession httpSession) {
        Template template = null;
        Map<String, Object> reportDataMap = null;

        try {
            //check whether the session has the downloading permision
            String storedPermission = (String) httpSession.getAttribute("permission");
            if (storedPermission == null || !storedPermission.equals(permission)) {
                throw new UserException("权限不足", HttpStatus.FORBIDDEN);
            }

            reportDataMap = reportService.getReportDataMap(year, issue);
            template = freeMarkerConfigurer.getConfiguration().getTemplate("template.ftl");
            //文件类型
            response.setHeader("content-Type", "application/msword");
            // 下载文件的名称 "西南石油大学yyyy年m-m月舆情月报.doc"
            String fileName = "西南石油大学" + year + "年" + issue + "-" + (issue + 1) + "月舆情月报.doc";
            //解决文件名乱码问题
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            //将数据写入模板文件，写入流
            template.process(reportDataMap, new OutputStreamWriter(bao));
            response.getOutputStream().write(bao.toByteArray());
        } catch (BaseException e) {
            response.setContentType("text/plain;charset=UTF-8");
            try {
                response.setStatus(e.getStatus().value());
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.reset();
            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(500);
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','VIP')")
    @GetMapping("/report/permission")
    public ResponseEntity<?> permission(HttpSession session) {
        session.setMaxInactiveInterval(600);
        String permission = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        session.setAttribute("permission", permission);
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }
}
