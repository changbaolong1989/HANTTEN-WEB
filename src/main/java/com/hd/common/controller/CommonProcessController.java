package com.hd.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.hd.base.controller.BaseController;
import com.hd.common.service.CommonActivitiService;
import com.hd.common.util.MessageConstant;
import com.hd.common.util.SpringUtils;
import com.hd.common.util.StringToolUtils;
import com.hd.manager.vo.HTProcessVO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程相关通用Controller
 */
@Controller
@CrossOrigin
public class CommonProcessController extends BaseController {

    /**
     * log对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CommonProcessController.class);

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private CommonActivitiService commonActivitiService;

    @Value("${system-param.flow-diagram.is-cross}")
    private boolean isCross;

    @Value("${system-param.flow-diagram.cross-url}")
    private String crossUrl;

    /**
     * 流程部署
     *
     * @param paramVo HTProcessVO
     * @param file    文件
     * @return 结果JSON
     */
    @RequestMapping("/process/deploy")
    @ResponseBody
    public JSONObject deployProcesses(HTProcessVO paramVo, MultipartFile file) {
        JSONObject resultJson;
        String fileName = file.getOriginalFilename();
        String deployName = paramVo.getDeployName();
        Deployment deployment;
        try {
            InputStream inputStream = file.getInputStream();
            //文件后缀名
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                //部署zip格式流程
                deployment = repositoryService.createDeployment().name(deployName).addZipInputStream(zipInputStream).deploy();
            } else {
                //部署bpmn格式流程
                deployment = repositoryService.createDeployment().name(deployName).addInputStream(fileName, inputStream).deploy();
            }
            String deploymentId = deployment.getId();
            //查询当前部署的最高版本
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).orderByProcessDefinitionVersion().desc().list().get(0);
            resultJson = new JSONObject(setReturnMessageMap(SUCCESS, SpringUtils.getMessage(MessageConstant.MSG_INFO_PROCESS_DEPLOY_0001)));
            logger.info("流程" + deployName + "部署成功！当前部署ID为：" + deploymentId + "。当前版本号为：" + processDefinition.getVersion() + "。");
        } catch (Exception e) {
            resultJson = new JSONObject(setReturnMessageMap(FAILURE, SpringUtils.getMessage(MessageConstant.MSG_INFO_PROCESS_DEPLOY_0002)));
            e.printStackTrace();
        }
        return resultJson;
    }

    /**
     * 查看流程图
     */
    @RequestMapping("/process/getFlowDiagram")
    @ResponseBody
    public void getFlowDiagram(HttpServletRequest request, HttpServletResponse response) {
        String processInstId = request.getParameter("processInstId");
        commonActivitiService.getFlowDiagram(response, processInstId);
    }

    /**
     * 获取跨域路径
     */
    @RequestMapping("/process/getFlowDiagramUrl")
    @ResponseBody
    public String getFlowDiagramUrl(HttpServletRequest request, HttpServletResponse response) {
        return isCross ? crossUrl : "";
    }


    /**
     * 流程部署
     */
    @RequestMapping("admin/process/deploy")
    @ResponseBody
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

/*    @RequestMapping("/testDeploy")
    public void testDeploy(){
        //processEngine = ProcessEngines.getDefaultProcessEngine();
        //RepositoryService repositoryService = processEngine.getRepositoryService();
        //System.out.println(repositoryService);
        //System.out.println(this.repositoryService);
        String fileName = "processes/SealApplyProcess.zip";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        repositoryService.createDeployment().name("请章流程").addZipInputStream(zipInputStream).excDeploy();
    }*/

}
