/* 整数 */
export const integer = (rule, value, callback) => {
  if(!value){
    callback();
  } else if ((Number(value)) && (value) % 1 === 0) {
    callback();
  } else {
    return callback(new Error('请输入整数！'));
  }
};
/* 正数 */
export const positiveNumber = (rule, value, callback) => {
  if(!value){
    callback();
  } else if ((Number(value)) && value > 0) {
    callback();
  } else {
    return callback(new Error('请输入正数！'));
  }
};
/* 百分比 */
export const percentage = (rule, value, callback) => {
  if(!value){
    callback();
  } else if ((Number(value)) && value >= 0 && value<=100) {
    callback();
  } else {
    return callback(new Error('请输入正确比例！'));
  }
};
/* 大写字母 */
export const capital = (rule, value, callback) => {
  var reg = /^[A-Z]+$/;
  if(!value){
    callback();
  } else if(reg.test(value)){
    callback();
  }else{
    return callback(new Error('请输入大写字母'));
  }
};
/* 固定电话 */
export const fixation = (rule, value, callback) => {
  var reg = /0\d{2,3}-\d{7,8}/;
  if(!value){
    callback();
  } else if(reg.test(value)){
    callback();
  }else{
    return callback(new Error('请输入正确的固定电话号码'));
  }
};
/* 手机号码 */
export const tel = (rule, value, callback) => {
  var reg = /^1(3|4|5|6|7|8|9)\d{9}$/;
  if(!value){
    callback();
  } else if(reg.test(value)){
    callback();
  }else{
    return callback(new Error('请输入正确的手机号码'));
  }
};
/* 金额 */
export const moneyCheck = (rule, value, callback) => {
  var reg = /^(([1-9][0-9]{0,9})|(([0]\.\d{1,2}|[1-9][0-9]{0,9}\.\d{1,2})))$/
  if (!value) {
    callback();
  }else if(reg.test(value)){
    callback();
  }else{
    return callback(new Error('请输入正确的金额(整数不超过10位，小数不超过2位)'));
  }
};

/* 数字字母符号 */
export const checkNumAlphaSymbol = (rule, value, callback) => {
  let reg = /^[\da-zA-Z!@#$%^&*]*$/;
  if (!value) {
    callback();
  }else if(reg.test(value)){
    callback();
  }else{
    return callback(new Error('只能包含字母数字或符号'));
  }
};

/* 百分比 */
export const checkPercentage = (rule, value, callback) => {
  let reg = /(^\d+\.?\d{0,2}%$)|(^\d+\.?\d{0,2}$)/;
  if (!value) {
    callback();
  }else if(reg.test(value)){
    callback();
  }else{
    return callback(new Error('请输入正确的百分比'));
  }
};


