package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.model.view.SpecialEventChart;
import cn.edu.swpu.cins.event.analyse.platform.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lp-deepin on 17-5-20.
 */
@RestController
@RequestMapping("/event")
public class ChartController {
    private ChartService chartService;

    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @PostMapping("/chart")
    public ResponseEntity<?> getChartPoints(@RequestBody SpecialEventChart specialEventChart) {
        try {
            Map<String, List<ChartPoint>> result;
            result = chartService.getChartPoints(specialEventChart.getSource(),
                    specialEventChart.getDataTypeName(),
                    specialEventChart.getBeginTime(),
                    specialEventChart.getEndTime(),
                    specialEventChart.getTable(),
                    specialEventChart.getIds());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}
