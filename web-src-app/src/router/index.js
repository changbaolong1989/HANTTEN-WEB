import Vue from 'vue'
import Router from 'vue-router'

import Mlogin from '@/components/login/m-login' /* 登录 */
import MApp from '@/components/app/index' /* 默认首页 */
import MworkBench from '@/components/work/workBench' /* 工作台 */
import MProjectList from '@/components/item/projectList' /* 项目列表 */
import Mreport from '@/components/report/index' /* 数据报表 */
import MContractInformation from '@/components/contractInformation/index' /* 合同信息 */
import MContractInformationDefault from '@/components/contractInformation/default/index' /* 合同信息默认展示 */
import MoverallProcess from '@/components/overallProcess/index' /*全过程 */
import MdialogTable from '@/components/report/performance/dialogTable' /* 员工扣分 */

/* 左中右 */
/* 合同 */
import MfoundItemIndex from '@/components/work/contract/foundItem1/index' /*合同中间 */
/* 市场 */
import MbazaarItemIndex from '@/components/work/contract/bazaarItem/index' /*市场中间 */
/* 全过程 */
import MprojectDetails from '@/components/overallProcess/projectDetails/index' /*全过程中间 */

/* 更多操作 */
import MAuditOpinion from '@/components/more/sublevel/auditOpinion' /* 审核意见 */
import MManHour from '@/components/more/sublevel/manHour' /* 我的工时 */
import MWorkingHours from '@/components/more/sublevel/workingHours' /* 工时列表 */
import MWorkingRemark from '@/components/more/sublevel/working/workingRemark' /* 工时列表备注 */
import MPleaseChapter from '@/components/work/pleaseChapter/index' /* 请章记录 */
import MPleaseApplyChapter from '@/components/work/pleaseChapter/pleaseApplyChapter' /* 请章申请 */
import MModifyProject from '@/components/work/contract/contractAside/modifyProject' /* 修改合同项目 */

import MCreateProject from '@/components/item/itemList/createProject' /* 创建市场项目 */
import MCreateContract from '@/components/item/itemList/createContract' /* 创建合同项目 */
import MContractDetails from '@/components/work/contractDetails/contractDetails' /* 项目详情 */

Vue.use(Router)

export default new Router({
  routes: [{ /* 主页面 */
      path: '/app',
      name: 'app',
      component: MApp,
      meta: {
        showFooter: false
      },
      redirect: '/app/login',
      children: [{ /* 登录 */
          path: 'login',
          name: 'login',
          component: Mlogin,
        }, { /* 工作台 */
          path: 'workbench',
          name: 'workbench',
          component: MworkBench,
          meta: {
            showFooter: true
          },
        },
        { /* 项目 */
          path: 'projectList',
          name: 'projectList',
          component: MProjectList,
          meta: {
            showFooter: true
          },
        },
        { /* 数据报表 */
          path: 'dialogTable',
          name: 'dialogTable',
          component: MdialogTable,
          meta: {
            showFooter: true
          },
        },
        { /* 合同信息 */
          path: 'contractInformation',
          name: 'contractInformation',
          component: MContractInformation,
          meta: {
            showFooter: true
          },
          children: [{
            path: "default",
            component: MContractInformationDefault,
            meta: {
              showFooter: true
            }
          }]
        },
        { /* 全过程 */
          path: 'overallProcess',
          name: 'overallProcess',
          component: MoverallProcess,
          meta: {
            showFooter: true
          },
        },
        { /* 员工扣分 */
          path: 'report',
          name: 'report',
          component: Mreport,
          meta: {
            showFooter: true
          },
        },
        { /* 项目详情 */
          path: 'mcontractDetails',
          name: 'mcontractDetails',
          component: MContractDetails,
        },
        { /* 创建市场项目 */
          path: 'createProject',
          name: 'createProject',
          component: MCreateProject,
        },
        { /* 创建合同项目 */
          path: 'createContract',
          name: 'createContract',
          component: MCreateContract,
        },
        { /* 审核意见 */
          path: 'auditOpinion',
          name: 'auditOpinion',
          component: MAuditOpinion,
        },
        { /* 我的工时 */
          path: 'manHour',
          name: 'manHour',
          component: MManHour,
        },
        { /* 工时列表 */
          path: 'workingHours',
          name: 'workingHours',
          component: MWorkingHours,
        },
        { /* 工时列表备注 */
          path: 'workingRemark',
          name: 'workingRemark',
          component: MWorkingRemark,
        },
        { /* 请章记录 */
          path: 'pleaseChapter',
          name: 'pleaseChapter',
          component: MPleaseChapter,
        },
        { /* 请章申请*/
          path: 'pleaseApplyChapter',
          name: 'pleaseApplyChapter',
          component: MPleaseApplyChapter,
        },
        { /* 合同中间 */
          path: 'foundItem',
          name: 'foundItem',
          component: MfoundItemIndex,
        },
        { /* 市场中间 */
          path: 'bazaarItemIndex',
          name: 'bazaarItemIndex',
          component: MbazaarItemIndex,
        },
        { /* 全过程中间 */
          path: 'projectDetails',
          name: 'projectDetails',
          component: MprojectDetails,
        },
        { /* 修改合同项目 */
          path: 'modifyProject',
          name: 'modifyProject',
          component: MModifyProject,
        }
      ]
    },
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y:0 }
   },
  mode: 'history'
})
