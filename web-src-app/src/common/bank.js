/**
 * 下载文件方法
 * @param {String} fileUrl [文件地址]
 * @param {String} fileName [文件名称(展示)]
 */
export function downloadFile(fileUrl, fileName,isGeneratedFile) {
  var elemIF = document.createElement('iframe');
	  if(isGeneratedFile){
      // 系统生成的文件
      elemIF.src = fileUrl;
	  }else{
      // 上传的文件
      elemIF.src = '/commonModule/downloadFile?filePathName=' + fileUrl + '&realFileName='+fileName;
	  }
      elemIF.style.display = 'none';
      document.body.appendChild(elemIF);
}

/**
 * 下载文件方法
 * @param {String} array [目标列表]
 * @param {String} func [匿名函数例:function(item){
                      return[item.questionId]
                      }(其中questionId为需要分组的字段)]
 */
export function groupBy(array, func) {
  const obj = [];
  const groups = {};
  array.forEach(function (key) {
    const group = JSON.stringify(func(key));
    groups[group] = groups[group] || [];
    groups[group].push(key);
  });
  return Object.keys(groups).map(function (group) {
    return {
      Key:JSON.parse(group)[0],
      dataList:groups[group]
    }
  });
}


/*-----------------------------流程相关-----------------------------------*/
const procNodeList = [{
                              node:'SubmitVerify',
                              name:'提交审核',
                              worker:''
                            },{
                              node:'ManagerVerify',
                              name:'一级审核',
                              worker:'部门经理'
                            },{
                              node:'QualityVerify',
                              name:'二级审核',
                              worker:'质控组员'
                            },{
                              node:'GenerManagerVerify',
                              name:'三级审核',
                              worker:'质控副总'
                            },{
                              node:'Signer',
                              name:'签发',
                              worker:'签发人'
                            },{
                              node:'SubmitArchive',
                              name:'提交归档',
                              worker:'招标部组长'
                            },{
                              node:'ArchiveVerify',
                              name:'归档审批',
                              worker:'质检部组员'
                            },{
                              node:'Archive',
                              name:'归档',
                              worker:'行政经理'
                            }]


/**
 * 得到下一节点
 * @param {String} node [节点]
 */
export function getNextStepNode(node) {
  for (var i = procNodeList.length - 1; i >= 0; i--) {
    if (procNodeList[i].node==node) {
      if ((i+1)<procNodeList.length) {
        return procNodeList[i+1].node
      }
    }
  }
}

/**
 * 得到当前节点名称
 * @param {String} node [节点]
 */
export function getStepName(node) {
  for (var i = procNodeList.length - 1; i >= 0; i--) {
    if (procNodeList[i].node==node) {
      return procNodeList[i].name
    }
  }
}


/**
 * 得到当前节点负责人
 * @param {String} node [节点]
 */
export function getStepWorker(node) {
  for (var i = procNodeList.length - 1; i >= 0; i--) {
    if (procNodeList[i].node==node) {
      return procNodeList[i].worker
    }
  }
}


/**
 * 得到当前节点序号
 * @param {String} node [节点]
 */
export function getStepNum(node) {
  for (var i = procNodeList.length - 1; i >= 0; i--) {
    if (procNodeList[i].node==node) {
      return i
    }
  }
}



/*----------------------------------部门ID-----------------------------------------*/
/*高管部门ID*/
export const seniorManagerDeptId = 'e18edd98-9fbd-11e9-a036-0e411fd5cf91'
/*市场部ID*/
export const marketDeptId = '593df92d-fb21-442a-b279-a8b821bbc0d4'
/*造价部ID*/
export const manufactureDeptId = 'db5e76f9-9fbd-11e9-a036-0e411fd5cf91'
/*招标部ID*/
export const tenderDeptId = 'dea3cd5d-9fbd-11e9-a036-0e411fd5cf91'
/*监理部ID*/
export const superviseDeptId = 'eb1f8c91-960d-4717-84ee-cc3b8137ccd3'
