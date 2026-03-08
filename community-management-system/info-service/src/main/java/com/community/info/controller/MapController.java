package com.community.info.controller;

import com.community.common.result.ResponseResult;
import com.community.info.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/locations")
    public ResponseResult<?> getLocations(@RequestParam String type, 
                                        @RequestParam Double latitude, 
                                        @RequestParam Double longitude) {
        return mapService.getLocations(type, latitude, longitude);
    }
}
