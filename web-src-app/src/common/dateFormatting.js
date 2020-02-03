import main from '../main.js'
/* 日期格式化 */
export function formatting(date){
  var year = date.getFullYear();
  var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
  var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
  const time = year + '-' + month + '-' + day;
  return time;
};
/* 日期格式化年月日 */
export function formattingStr(date){
  var year = date.getFullYear();
  var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
  var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
  const time = year + '年' + month + '月' + day +'日';
  return time;
};

export function Toast(message){
  this.Toast({
    message: message,
    position: 'middle',
    duration: 50000,
    className: 'toast'
  });
}
