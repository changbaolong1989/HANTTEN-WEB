import Vue from 'vue'
import Router from 'vue-router'
/* 登录路由 */
import Login from '@/components/login/m-login'
// 一级路由
import MPage from '@/components/page/m-page'
// 二级路由
import MWork from '@/components/work/index' /* 工作台 */
import MItem from '@/components/item/m-item' /* 项目 */
import MProCfg from '@/components/process/pro-cfg' /* 工作流配置 */
import MBazaarItemList from '@/components/bazaar/item/m-bazaar-itemList' /* 市场部项目列表 */
import MReport from '@/components/report/index' /* 数据报表 */
import MHNotice from '@/components/head/m-head-notice' /* 公告中心 */
import MHInform from '@/components/head/m-head-inform' /* 通告中心 */
import MHSkill from '@/components/head/m-head-skill' /* 技术标准 */
import MPersonalCenter from '@/components/head/personalCenter/m-personalCenter' /* 个人中心 */
import MContractInformation from '@/components/contractInformation/index' /* 合同信息 */
import MWholeProcessCost from '@/components/wholeProcessCost/index' /* 全过程造价 */


/* 公司设置路由开始 */
import MHSInform from '@/components/head/setting/mh-set-inform' /* 公司信息 */
import MHSCollaborate from '@/components/head/setting/mh-set-collaborate' /* 合作公司 */
import MHSRole from '@/components/head/setting/mh-set-role' /* 角色管理 */
import MHSPowerConfig from '@/components/head/setting/powerConfig/index' /*权限管理 */
import MHSMember from '@/components/head/setting/mh-set-member' /* 员工管理 */
import MHSNotice from '@/components/head/setting/mh-set-notice' /*公司设置  >  公告中心 */
import MHSSkill from '@/components/head/setting/mh-set-skill' /* 技术标准 */
import MHSOrganization from '@/components/head/setting/mh-set-organization' /* 组织架构 */
import MHSExplain from '@/components/head/setting/mh-set-explain' /* 工作步骤说明设置 */
/* 公司设置路由结束 */
// 三级路由
import MWorkDefault from '@/components/work/default/index' /* 工作台默认页面 */
import MWorkHoursList from '@/components/work/default/hoursList' /* 工时列表 */
import MWorkleaseChapter from '@/components/work/pleaseChapter/index' /* 请章 */
import MWorkContract from '@/components/work/contract/index' /* 合同视图 */
import MCostOf from '@/components/costOf/m-costOf' /* 造价部视图 */

import MWorkContractAside from '@/components/work/contract/aside' /* 通用(左侧菜单部分) */
import MWorkContractTenderTaskAside from '@/components/work/contract/asideTenderTask' /* 市场部详情(左侧菜单部分) */
import MCostAside from '@/components/costOf/cost-Aside' /* 造价部详情(左侧菜单部分) */
import MCostRight from '@/components/costOf/cost-Right' /* 造价部详情(右侧部分) */

import MCostDefault from '@/components/costOf/default/index' /* 造价部详情默认(中间部分) */
import MCostIndex from '@/components/costOf/cost-details/cost-index' /* 造价部详情A1-A9(中间部分) */
import MPrepareCostIndex from '@/components/costOf/Prepare/cost-index' /* 造价部详情A2-A3-A7(中间部分) */
import MCostOfCollectingData from '@/components/costOf/collectingData/index' /* 造价部详情A4(中间部分) */
import MCostOfDrawUpAWorkPlan from '@/components/costOf/drawUpAWorkPlan/index' /* 造价部详情A5(中间部分) */
import MCostOfModifyThePerfect from '@/components/costOf/modifyThePerfect/index' /* 造价部详情A6(中间部分) */
import MCostOfSiteVisit from '@/components/costOf/siteVisit/index' /* 造价部详情A8(中间部分) */
import MCostOfArchives from '@/components/costOf/archives/index' /* 造价部详情A10(中间部分) */
import MCostOfTender from '@/components/costOf/tender/index' /* 招标部详情A7(中间部分) */
import MCostOfBids from '@/components/costOf/bids/index' /* 招标部详情A8(中间部分) */

import MWorkContractFoundItem from '@/components/work/contract/foundItem1/index' /* 项目详情(中间部分) */

import MWorkContractTenderTask from '@/components/work/contract/tenderTask/index' /* 市场部详情(中间部分) */
import MWorkContractTenderTaskR from '@/components/work/contract/tenderTask/right' /* 市场部详情(右侧部分) */
import MContractInformationDefault from '@/components/contractInformation/default/index' /* 合同信息 */

import MWholeProcessCostProjectList from '@/components/wholeProcessCost/projectList/index' /* 全过程造价项目列表 */
import MWholeProcessCostProjectDetails from '@/components/wholeProcessCost/projectDetails/index' /* 全过程造价项目详情 */



/* 通用组件 */
import four from '@/components/404/404' /* 404页面 */
import NoData from '@/components/NoData/index' /* 暂无数据页面 */

Vue.use(Router)

let routes = [{
    path: '/',
    component: Login
  },
  {
    path: '/page',
    component: MPage,
    redirect: '/page/NoData',
    children: [{
        path: "item",
        component: MItem,
        meta: {
          navDisplay: ['项目']
        }
      },
      {
        path: "processConfig",
        component: MProCfg,
        meta: {
          navDisplay: ['工作流配置']
        },
      },
      {
        path: "four",
        component: four,
        meta: {
          navDisplay: ['404']
        },
      },
      {
        path: "NoData",
        component: NoData,
        meta: {
          navDisplay: ['暂无数据']
        },
      },
      {
        path: "work",
        component: MWork,
        children: [{
          path: "default",
          component: MWorkDefault,
          meta: {
            navDisplay:['工作台']
          }
        }, {
          path: "hoursList",
          component: MWorkHoursList,
          meta: {
            navDisplay: ['工作台','工时列表']
          }
        }, {
          path: "pleaseChapter",
          component: MWorkleaseChapter,
          meta: {
            navDisplay: ['工作台','请章']
          }
        }, {
          path: "contract",
          component: MWorkContract,
          children: [{
            path: "/page/item/contract/FoundItem",
            components: {
              'aside': MWorkContractAside,
              'center': MWorkContractFoundItem
            },
            meta: {
              navDisplay: ['项目','项目详情']
            }
          },{
            path: "FoundItem",
            components: {
              'aside': MWorkContractAside,
              'center': MWorkContractFoundItem
            },
            meta: {
              navDisplay: ['工作台','项目详情']
            }
          },{
            path: "/page/item/contract/tenderTask",
            components: {
              'aside': MWorkContractTenderTaskAside,
              'center': MWorkContractTenderTask,
              'right': MWorkContractTenderTaskR
            },
            meta: {
              navDisplay: ['项目','市场部详情']
            }
          }, {
            path: "tenderTask",
            components: {
              'aside': MWorkContractTenderTaskAside,
              'center': MWorkContractTenderTask,
              'right': MWorkContractTenderTaskR
            },
            meta: {
              navDisplay: ['工作台','市场部详情']
            }
          }]
        }]
      },
      {
        path: "wholeProcessCost",
        component: MWholeProcessCost,
        children: [{
          path: "projectList",
          component: MWholeProcessCostProjectList,
          meta: {
            navDisplay: ['全过程造价','项目列表']
          }
        },{
          path: "projectDetails",
          component: MWholeProcessCostProjectDetails,
          meta: {
            navDisplay: ['全过程造价','项目详情']
          }
        }]
      },
      {
        path: "report",
        component: MReport,
        meta: {
          navDisplay: ['数据报表']
        }
      },
      {
        path: "contractInformation",
        component: MContractInformation,
        children: [{
          path: "",
          component: MContractInformationDefault,
          meta: {
            navDisplay: ['合同信息']
          }
        }]
      },
      {
        path: "notice",
        component: MHNotice,
        meta: {
          navDisplay: ["公告中心"]
        }
      },
      {
        path: "skill",
        component: MHSkill,
        meta: {
          navDisplay: ["技术标准"]
        }
      },
      {
        path: "inform",
        component: MHInform,
        meta: {
          navDisplay: ["通知中心"]
        }
      },
      {
        path: "person",
        component: MPersonalCenter,
        meta: {
          navDisplay: ["个人中心"]
        }
      },
      {
        path: "set-inform",
        component: MHSInform,
        meta: {
          navDisplay: ["公司信息"]
        }
      },
      {
        path: "set-collaborate",
        component: MHSCollaborate,
        meta: {
          navDisplay: ["合作公司"]
        }
      },
      {
        path: "set-role",
        component: MHSRole,
        meta: {
          navDisplay: ["角色管理"]
        }
      },
      {
        path: "set-PowerConfig",
        component: MHSPowerConfig,
        meta: {
          navDisplay: ["权限管理"]
        }
      },
      {
        path: "set-member",
        component: MHSMember,
        meta: {
          navDisplay: ["员工管理"]
        }
      },
      {
        path: "set-notice",
        component: MHSNotice,
        meta: {
          navDisplay: ["公告中心"]
        }
      },
      {
        path: "set-skill",
        component: MHSSkill,
        meta: {
          navDisplay: ["技术标准"]
        }
      },
      {
        path: "set-organization",
        component: MHSOrganization,
        meta: {
          navDisplay: ["组织架构"]
        }
      },
      {
        path: "set-explain",
        component: MHSExplain,
        meta: {
          navDisplay: ["工作步骤说明设置"]
        }
      }
    ]
  },
  {
    path: "*",
    redirect: '/'
  }
];

let router = new Router({
  routes,
  mode: "history"
})

router.beforeEach((to, from, next) => {
  router.current = to;
  if('/noJump'!=to.redirectedFrom)next();
})

export default router;
