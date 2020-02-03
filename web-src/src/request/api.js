import {
  get,
  post
} from './http'
import ElementUI from 'element-ui'
import context from '@/main.js'
export const stateDialog = (type, message) => {
  context.$message({
    center: true,
    message: message,
    type: type
  });
}
export const apiAddress = (url, parameter) => post(url, parameter);
export const apiAddressGet = (url, parameter) => get(url, parameter);

