import axios from "axios";
import qs from "qs";
const api = "/api";

/**
 * 获取页面配置
 */
export function configPage() {
    return axios.get(api + "/config/page");
}

/**
 * 获取菜单配置
 */
export function configMenu() {
    return axios.get(api + "/config/menu");
}

/**
 * 查找菜单
 * @param parentId
 */
export function menuByParentId(parentId) {
    return axios.get(api + "/setting/menu/find", {
        params: {
            parentId
        }
    });
}

/**
 * 根据id查找菜单
 * @param parentId
 */
export function menuById(id) {
    return axios.get(api + "/config/menu/" + id);
}

/**
 * 修改上传路径
 * @param value
 */
export function defaultPath({ value }) {
    return axios.post(
        api + "/setting/page/default/dir/update",
        qs.stringify({
            value
        })
    );
}

/**
 * 上传图片
 * @param id
 * @param file
 */
export function imgUpdate({ id, formData }) {
    return axios({
        url: api + "/setting/page/" + id + "/file/update",
        method: "post",
        data: formData,
        headers: { "Content-Type": "application/x-www-form-urlencoded" }
    });
}

/**
 * 更新页面配置内容 ，只要id跟value
 * @param id
 * @param value
 */
export function updateById({ id, value }) {
    return axios.post(
        api + "/setting/page/" + id + "/update",
        qs.stringify({
            value
        })
    );
}

/**
 * 设置模块样式
 * @param id
 * @param backgroundColor   模块背景色
 * @param color             模块内文字颜色
 * @param icon              模块icon名称
 * @param iconColor         模块icon颜色
 */
export function setMenuModule({ id, backgroundColor, color, icon, iconColor }) {
    return axios.post(api + "/setting/menu/operation/setting", {
        id,
        backgroundColor,
        color,
        icon,
        iconColor
    });
}

/**
 * 根据id查菜单操作项
 * @param menuId
 */
export function getMenuById(menuId) {
    return axios.get(api + "/setting/menu/operation/find", {
        params: {
            menuId
        }
    });
}

/**
 * 修改菜单
 * @param id
 * @param name
 * @param url
 */
export function updateMenu({ id, name, url }) {
    return axios.post(
        api + "/setting/menu/" + id + "/update",
        qs.stringify({ name, url })
    );
}

/**
 * 更新操作项名称
 * @param id name
 */
export function updateOperation({ id, operationName }) {
    return axios.post(
        api + "/setting/menu/operation/" + id + "/update",
        qs.stringify({ name: operationName })
    );
}

/**
 * 菜单排序
 * @param ids
 */
export function menuSort(ids) {
    return axios.post(api + "/setting/menu/sort", qs.stringify({ ids }));
}

/**
 * 添加一级菜单
 * @param code
 * @param name
 * @param parentId
 * @param seq
 * @param url
 */
export function addMenu({ code, name, parentId, seq, url }) {
    return axios.post(api + "/setting/menu/save", {
        code,
        name,
        parentId,
        seq,
        url
    });
}

/**
 * 删除菜单
 * @param id
 */
export function deleteMenu(id) {
    return axios.post(api + "/setting/menu/" + id + "/delete");
}

/**
 * 添加操作项
 * @param operationName
 * @param operation
 * @param menuId
 */
export function addOperation({ operationName, operation, menuId }) {
    return axios.post(
        api + "/setting/menu/operation/save",
        qs.stringify({
            operationName,
            operation,
            menuId
        })
    );
}

/**
 * 删除操作权限
 * @param id
 */
export function deleteOperation({ id, menuId }) {
    return axios.post(
        api + "/setting/menu/operation/" + id + "/delete",
        qs.stringify({
            menuId
        })
    );
}
