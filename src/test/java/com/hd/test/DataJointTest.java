package com.hd.test;

import application.Application;
import com.hd.data.HTDataJoint;
import com.hd.data.dao.OldDataJointPropertyMapper;
import com.hd.data.dao.OldProjectInfoEntityMapper;
import com.hd.data.dao.ToDoArchiveMapper;
import com.hd.data.entity.OldDataJointProperty;
import com.hd.data.entity.ToDoArchive;
import com.hd.manager.service.HTContractService;
import com.hd.manager.vo.HTContractVO;
import com.hd.manager.vo.HTProjectVO;
import com.hd.manager.vo.HTUserVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DataJointTest {

    private static final Logger logger = LoggerFactory.getLogger(HTDataJoint.class);

    @Resource
    private OldProjectInfoEntityMapper oldProjectInfoEntityMapper;

    @Resource
    private OldDataJointPropertyMapper dataJointPropertyMapper;

    @Resource
    private HTDataJoint dataJoint;

    @Resource
    private HTContractService contractService;

    @Resource
    private ToDoArchiveMapper toDoArchiveMapper;

    //副总职位级别
    private static final String JOB_LEVEL_FU_MANAGER = "10";
    //旧系统部门
    private static final String TABLE_NAME = "old_project_info_replenish_1199";
    //    private static final String OLD_DEPT_ID = "8";
    private static final String OLD_DEPT_ID = "6";
    //    private static final String OLD_DEPT_NAME = "造价部";
    private static final String OLD_DEPT_NAME = "招标代理部";

    //开始数据对接标识
    private static final String DATA_JOINT_PROPERTY = "startImport";
    private static final String START_DATA_JOINT = "true";
    private static final String END_DATA_JOINT = "false";

    //休眠时长
    private static final long SLEEP_TIME = 10000;

    //private static final String TASK_KEY = "SubmitVerify";

    private HTProjectVO projectVO = new HTProjectVO();

    @Test
    public void joint() {
        //Map<String, OldProjectInfoEntity> projectMap = new HashMap<>();
        //List<OldProjectInfoEntity> list = oldProjectInfoEntityMapper.selectProjectInfo(TABLE_NAME, JOB_LEVEL_FU_MANAGER, OLD_DEPT_ID);
        //--   AllotManager   SubmitVerify
        //List<OldProjectInfoEntity> list = oldProjectInfoEntityMapper.selectToDoArchiveProjectInfo(TASK_KEY, JOB_LEVEL_FU_MANAGER, OLD_DEPT_ID);
        //logger.info("数据筛选开始：一共有" + list.size() + "条项目合同编号输入。");

/*        for (String num : projectContractNum) {
            for (OldProjectInfoEntity oldProjectInfoEntity : list) {
                if (StringUtils.isNotBlank(oldProjectInfoEntity.getRemark()) && oldProjectInfoEntity.getRemark().contains(num)) {
                    if (projectMap.containsKey(num)) {//包含重复
                        logger.info("数据筛选程序发现有重复数据，项目合同号为：" + num);
                        projectMap.remove(num);
                    } else {
                        projectMap.put(num, oldProjectInfoEntity);
                    }
                }
            }
        }*/

/*        for (OldProjectInfoEntity oldProjectInfoEntity : list) {
            String projectContractNum = oldProjectInfoEntity.getProjectContractNum();
            if (projectMap.containsKey(projectContractNum)) {//包含重复
                logger.info("数据筛选程序发现有重复数据，项目合同号为：" + projectContractNum);
                projectMap.remove(projectContractNum);
            } else {
                projectMap.put(projectContractNum, oldProjectInfoEntity);
            }
        }*/

        //logger.info("数据筛选完成：排除重复或不满足条件的数据后，剩余" + projectMap.size() + "条数据待对接");

        synchronized (this) {
            for (; ; ) {
                logger.info("是否继续进行数据对接？ 如果需要继续，请修改《old_data_joint_property》表的值为true");
                OldDataJointProperty dataJointProperty = dataJointPropertyMapper.selectByPrimaryKey(DATA_JOINT_PROPERTY);
                if (START_DATA_JOINT.equalsIgnoreCase(dataJointProperty.getValue())) {
                    break;
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        logger.info("数据对接进行中………………");

        OldDataJointProperty dataJointProperty = new OldDataJointProperty();
        dataJointProperty.setName(DATA_JOINT_PROPERTY);
        dataJointProperty.setValue(END_DATA_JOINT);
        dataJointPropertyMapper.updateByPrimaryKey(dataJointProperty);

        try {
            //dataJoint.handle(projectMap, projectVO, OLD_DEPT_ID, OLD_DEPT_NAME);


            List<ToDoArchive> list = toDoArchiveMapper.selectByExample(null);
            System.out.println("一共：" + list.size());
            for (ToDoArchive toDoArchive : list) {
                HTContractVO contractVO = new HTContractVO();
                HTUserVO user = new HTUserVO();
                user.setLoginUserId("0091dbf2-bfe4-11e9-b448-64006a07233f");
                user.setUserName("王利");
                contractVO.setLoginUserInfo(user);
                contractVO.setContractId(toDoArchive.getContractId());
                contractService.excContractSubmitArchive(contractVO);

            }


            //1100-1399处理到归档，重复数据处理
/*            if(!list.isEmpty() && projectMap.isEmpty()){
                String projectContractNum = list.get(0).getProjectContractNum();
                projectMap.put(projectContractNum, list.get(0));
            }*/

            //dataJoint.handle(projectMap, TASK_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("数据对接完成………………");
    }

    @Before
    public void set() {
        //-----------------------------projectVO----------------------------------//
        HTUserVO userVO = new HTUserVO();
        userVO.setLoginUserId("0091dbf2-bfe4-11e9-b448-64006a07231f");
        projectVO.setLoginUserInfo(userVO);
        projectVO.setCompanyId("913dfd30-e04c-11e9-842c-64006a07180f");
        //-----------------------------projectVO----------------------------------//
    }

}
