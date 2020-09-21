import axios from "axios";
import qs from "qs";
const api = "/api";

/**
 * 获取角色管理列表
 */
export function getAll() {
    return axios.get(api + "/role/all");
}

/**
 * 添加角色
 * @param name
 * @param level  1省级 2市级 3区级
 */
export function addRole({ name, level }) {
    return axios.post(
        api + "/role/add",
        qs.stringify({
            name,
            level
        })
    );
}

/**
 * 删除角色
 * @param id
 */
export function deleteRole(id) {
    return axios.put(api + "/role/" + id + "/delete");
}

/**
 * 更新角色
 * @param id
 * @param name
 */
export function updateRole({ id, name }) {
    return axios.put(
        api + "/role/" + id + "/update_name",
        qs.stringify({
            id,
            name
        })
    );
}

/**
 * 根据等级查角色
 * @param level
 */
export function getListByLevel(level) {
    return axios.get(api + "/role/auth/level", {
        params: {
            level
        }
    });
}

/**
 * 获取用户权限信息
 * @param id
 */
export function getAuthDetail(id) {
    return axios.get(api + "/role/auth/" + id + "/detail");
}

/**
 *  用户修改权限
 * @param id
 * @param accountAuthorityList
 */
export function authSave({ id, accountAuthorityList }) {
    return axios.post(api + "/role/auth/" + id + "/save", accountAuthorityList);
}
