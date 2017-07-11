package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.service.ReportService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by LLPP on 2017/6/19.
 */
@RequestMapping("/event")
@RestController
//@PreAuthorize("hasAnyRole('ADMIN','VIP')")
public class ReportController {
    private ReportService reportService;
    private FreeMarkerConfigurer freeMarkerConfigurer;

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
            , @PathVariable int issue){
        Template template = null;

        Map<String , Object> reportDataMap = null;

        try {
            reportDataMap = reportService.getReportDataMap(year,issue);
            template = freeMarkerConfigurer.getConfiguration().getTemplate("template.ftl");
            //文件类型
            response.setHeader("content-Type", "application/msword");
            // 下载文件的名称 "西南石油大学yyyy年m-m月舆情月报.doc"
            String fileName = "西南石油大学" + year + "年" + issue + "-" + (issue+1) + "月舆情月报.doc";
            //解决文件名乱码问题
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            //将数据写入模板文件，写入流
            template.process(reportDataMap, new OutputStreamWriter(response.getOutputStream()));
        } catch (BaseException e) {
            response.setContentType("text/plain;charset=UTF-8");
            try {
                response.setStatus(e.getStatus().value());
                response.getWriter().write( e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            response.reset();
            response.setContentType("text/plain;charset=UTF-8");
            response.setStatus(500);
            try {
                response.getWriter().write( e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
