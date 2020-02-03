package com.hd.test;

import application.Application;
import com.alibaba.fastjson.JSONObject;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.StringToolUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WorkFlowCommon {

    @Resource
    private CommonActivitiService commonActivitiService;

    /**
     * 测试部署
     */
    @Test
    public void deploy() {
        JSONObject o = new JSONObject();
        o.put("请章流程", "processes/SealApplyProcess.zip");
        o.put("投标流程", "processes/Tender.zip");
        o.put("创建项目流程", "processes/CreateProject.zip");
        o.put("编撰合同流程", "processes/WriteContract.zip");
        o.put("任务_重新编辑流程", "processes/Task_Reedit.zip");
        o.put("任务_A3审批流程", "processes/Task_A3.zip");
        o.put("任务_成果文件审批流程", "processes/Task.zip");
        o.put("请款流程", "processes/CashApply.zip");
        o.put("归档流程", "processes/Archive.zip");
        this.excDeploy(o);
    }

    private void excDeploy(JSONObject o) {
        for (String k : o.keySet()) {
            String v = StringToolUtils.convertNullObjectToString(o.get(k));
            commonActivitiService.excDeploy(v, k);
        }
    }

}

