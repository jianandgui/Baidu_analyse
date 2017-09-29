package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.ChartService;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostEventChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Created by lp-deepin on 17-5-20.
 */
@RestController
@RequestMapping("/event")
public class ChartController {
    private ChartService chartService;
    private SpecialPostEventChartService specialPostEventChartService;

    @Autowired
    public ChartController(ChartService chartService, SpecialPostEventChartService specialPostEventChartService) {
        this.specialPostEventChartService = specialPostEventChartService;
        this.chartService = chartService;
    }

    @GetMapping("/chart")
    public ResponseEntity<?> getChartPoints(@RequestParam(value = "source") String source,
                                            @RequestParam(value = "data") String dataTypeName,
                                            @RequestParam(value = "beginTime") String beginTime,
                                            @RequestParam(value = "endTime") String endTime,
                                            @RequestParam(value = "table") String table) {
        try {
            Map<String, List<ChartPoint>> result;
            result = chartService.getChartPoints(source, dataTypeName, beginTime, endTime, table);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/specialPostEventChart")
    public ResponseEntity<?> getSpecialPostEventChart(@RequestParam String url) throws IOException, NoEventException {
        try {
            List<ChartPoint> chartPointList = specialPostEventChartService.getChartPoints(url);
            return new ResponseEntity<Object>(chartPointList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<Object>(e.getMessage(), INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }


}
