import {
  Loading
} from 'element-ui';

let loadingCount = 0;
let loading;

const startLoading = () => {
  loading = Loading.service({
    lock: false,
    text: '加载中……',
    background: 'rgba(255, 255,255, 0.7)'
  });
};

const endLoading = () => {
  loading.close();
};

export const showLoading = (flag) => {
  if(!flag){
    if (loadingCount === 0) {
      startLoading();
    }
    loadingCount += 1;
  }
};

export const hideLoading = () => {
  if (loadingCount <= 0) {
    return;
  }
  loadingCount -= 1;
  if (loadingCount === 0) {
    endLoading();
  }
};
