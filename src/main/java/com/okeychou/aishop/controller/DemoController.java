package com.okeychou.aishop.controller;

import com.okeychou.aishop.common.exception.BusinessException;
import com.okeychou.aishop.common.result.Result;
import com.okeychou.aishop.common.result.ResultCode;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    // 测试1:正常成功,带数据
    @GetMapping("/ping")
    public Result<Map<String, String>> ping() {
        return Result.success(Map.of("service", "ai-shopping-server",
                "status", "alive"));
    }

    // 测试2:业务异常(主动抛出)
    @GetMapping("/error-demo")
    public Result<Void> errorDemo() {
        throw new BusinessException(ResultCode.NOT_FOUND);
    }


}
