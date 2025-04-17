package com.xz.schoolnavinfo.controller;


import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.data.entity.Stuff;
import com.xz.schoolnavinfo.service.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stuff")
public class StuffController {
    @Autowired
    private StuffService stuffService;

    @PostMapping("/createStuff")
    public Result createStuff(@RequestBody Stuff stuff) {
        if (stuff.getAddress() == null ||
            stuff.getDesc() == null ||
            stuff.getLocation() == null) {

            return Result.fail("参数错误");
        }
        if (stuffService.createStuff(stuff)) {
            return Result.success("操作成功");
        }
        return Result.fail("操作失败");
    }

    @PostMapping("/deleteById")
    public Result deleteById(@RequestBody String id) {
        if (stuffService.deleteStuffById(id.replaceAll("^\"|\"$", ""))) {
            return Result.success("操作成功");
        } else {
            return Result.fail("操作失败");
        }
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody String id) {
        boolean res = stuffService.updateStatus(id.replaceAll("^\"|\"$", ""));
        if (res) {
            return Result.success("操作成功");
        } else {
            return Result.fail("操作失败");
        }
    }

    @GetMapping("/get")
    public Result getStuffList() {
        return Result.data(stuffService.getStuffList());
    }

    @PostMapping("/getById")
    public Result getStuffById(@RequestBody String id) {
        return Result.data(stuffService.getStuffById(id.replaceAll("^\"|\"$", "")));
    }
}
