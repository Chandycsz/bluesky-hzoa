import axios from "axios";
import qs from "qs";
const api = "/api";

/** 条件查询用户
 *  @param name 姓名
 *  @param mobile 手机号
 *  @param organizationId 单位id
 *  @param roleId 角色id
 *  @param status 状态
 *  @param  pageNum
 */
export function getAccountList({
    name,
    mobile,
    organizationId,
    roleId,
    status,
    pageNum
}) {
    return axios.get(api + "/account/list", {
        params: {
            name,
            mobile,
            organizationId,
            roleId,
            status,
            pageNum
        }
    });
}

/**
 * 添加用户
 * @param name
 * @param password
 * @param passwordConfirm
 * @param mobile
 * @param roleId
 * @param organizationId
 * @param department
 * @param idCard
 * @param sort
 * @returns {AxiosPromise<any>}
 */
export function addAccount({
    name,
    password,
    passwordConfirm,
    mobile,
    roleId,
    organizationId,
    department,
    idCard,
    sort
}) {
    return axios.post(
        api + "/account/add",
        qs.stringify({
            name,
            password,
            passwordConfirm,
            mobile,
            roleId,
            organizationId,
            department,
            idCard,
            sort
        })
    );
}

/**
 * 修改用户
 * @param id
 * @param name
 * @param mobile
 * @param department
 * @param idCard
 * @param sort
 * @returns {AxiosPromise<any>}
 */
export function updateAccount({ id, name, mobile, department, idCard, sort }) {
    return axios.post(
        api + "/account/" + id + "/update",
        qs.stringify({
            name,
            mobile,
            department,
            idCard,
            sort
        })
    );
}

/**
 * 启用用户
 * @param ids
 */
export function enableAccount(ids) {
    return axios.post(
        api + "/account/enable",
        qs.stringify({
            idsStr: ids
        })
    );
}

/**
 * 禁用用户
 * @param ids
 */
export function disableAccount(ids) {
    return axios.post(
        api + "/account/disable",
        qs.stringify({
            idsStr: ids
        })
    );
}

/**
 * 获取用户登录日志
 * @param mobile
 * @param accountName
 * @param type
 * @param success
 * @param pageNum
 */
export function getLoginLog({ accountName, type, success, pageNum }) {
    return axios.get(api + "/get_login_log", {
        params: {
            accountName,
            type,
            success,
            pageNum
        }
    });
}

/**
 * 获取用户操作日志
 * @param accountName
 * @param timeStart
 * @param timeEnd
 * @param pageNum
 */
export function getOperateLog({ accountName, timeStart, timeEnd, pageNum }) {
    return axios.get(api + "/get_operate_log", {
        params: {
            accountName,
            timeStart,
            timeEnd,
            pageNum
        }
    });
}

/**
 * 获取用户权限信息
 * @param id
 */
export function getAuthDetail(id) {
    return axios.get(api + "/account/auth/" + id + "/edit");
}

/**
 *  用户修改权限
 * @param id
 * @param accountAuthorityList
 */
export function authSave({ id, accountAuthorityList }) {
    return axios.post(
        api + "/account/auth/" + id + "/save",
        accountAuthorityList
    );
}
