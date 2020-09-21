import axios from "axios";
import qs from "qs";

const api = "/api";

/**
 * 查询省市区列表
 * @param parentId 传父id ,如果是省则传0
 */
export function findChildren(parentId) {
    return axios.get(api + "/regions/find_children", {
        params: {
            parentId
        }
    });
}

/**
 * 创建省市区
 * @param parentId 父级区域的id,省级为顶级，parentId传null
 * @param name 区域名称
 * @param code 行政区域代码
 */
export function addCategory({ parentId, name, code }) {
    return axios.post(
        api + "/regions/add",
        qs.stringify({
            parentId,
            name,
            code
        })
    );
}

/**
 * 更新省市区指定id数据
 * @param id
 * @param name
 * @param code
 */
export function updateCategory({ id, name, code }) {
    return axios.put(
        api + "/regions/" + id + "/update",
        qs.stringify({
            id,
            name,
            code
        })
    );
}

/**
 * 删除省市区指定id数据
 * @param id
 */
export function deleteCategory(id) {
    return axios.put(api + "/regions/" + id + "/delete");
}

/**
 * 获取系统级别
 */
export function getLevel() {
    return axios.get(api + "/regions/get_system_level");
}

/**
 * 更新系统级别
 * @param newLevel      int类型 1省级2市级3区级
 */
export function updateLevel(newLevel) {
    return axios.put(
        api + "/regions/update_system_level",
        qs.stringify({
            newLevel
        })
    );
}

/**
 * 获取行政区树状结构
 */
export function getAllTree(underSystemLevel) {
    return axios.get(api + "/regions/all_tree_under_level", {
        params: {
            underSystemLevel: underSystemLevel || false
        }
    });
}
