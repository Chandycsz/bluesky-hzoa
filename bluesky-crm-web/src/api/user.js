import axios from "axios";
import qs from "qs";
const api = "/api";

/**
 * 用户名密码登录方式
 * @param userName
 * @param password
 */
export function accountLogin({ userName, password }) {
    return axios.post(
        api + "/login/user_name",
        qs.stringify({
            userName,
            password
        })
    );
}

/**
 * 判断用户是否登录
 */
export function userStatus() {
    return axios.get(api + "/current_account");
}

/**
 * 用户退出
 */
export function loginOut() {
    return axios.get(api + "/logout");
}
