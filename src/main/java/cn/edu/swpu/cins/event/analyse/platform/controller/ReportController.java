package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.service.ReportService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Created by LLPP on 2017/6/19.
 */
@RequestMapping("/event")
@RestController
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
        //文件类型
        response.setHeader("content-Type", "application/msword");
        // 下载文件的名称
        response.setHeader("Content-Disposition", "attachment;filename=" + year + "-" + issue +".doc");
        Template template = null;
        Map<String , Object> reportDataMap = null;

        try {
            reportDataMap = reportService.getReportDataMap(year,issue);
        } catch (BaseException e) {
            e.printStackTrace();
        }

        try {
            template = freeMarkerConfigurer.getConfiguration().getTemplate("word-template.ftl");
            template.process(reportDataMap, new OutputStreamWriter(response.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
