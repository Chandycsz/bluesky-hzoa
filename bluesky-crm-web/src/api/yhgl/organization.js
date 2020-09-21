import axios from "axios";
import qs from "qs";

const api = "/api";

/**
 * 获取列表  分页
 * @param regionId  查询的省市区id
 * @param name      名称模糊查询
 * @param pageNum
 */
export function getPage({ regionId, name, pageNum }) {
    return axios.get(api + "/organization/page", {
        params: {
            regionId,
            name,
            pageNum
        }
    });
}

/**
 * 根据单位id查询下级单位
 * @param organizationId
 */
export function getListById(organizationId) {
    return axios.get(api + "/organization/" + organizationId + "/children");
}

/**
 * 获取列表  不分页
 * @param regionId  查询的省市区id
 * @param name      名称模糊查询
 */
export function getList({ regionId, name }) {
    return axios.get(api + "/organization/page", {
        params: {
            regionId,
            name
        }
    });
}

/**
 * 新增单位
 * @param code
 * @param name
 * @param sort
 * @param fullName
 * @param parentId                 上级部门id
 * @param regionId
 * @param unifiedSocialCreditCode  社会统一信用代码
 * @param memo                     备注
 * @param status                    0启用 -5禁用
 */
export function addOrg({
    code,
    name,
    sort,
    fullName,
    parentId,
    regionId,
    status,
    memo,
    unifiedSocialCreditCode
}) {
    return axios.post(
        api + "/organization/add",
        qs.stringify({
            code,
            name,
            sort,
            fullName,
            parentId,
            regionId,
            status,
            memo,
            unifiedSocialCreditCode
        })
    );
}

/**
 * 修改单位
 * @param id
 * @param code
 * @param name
 * @param sort
 * @param fullName
 * @param parentId
 * @param regionId
 * @param memo
 * @param unifiedSocialCreditCode
 */
export function updataOrg({
    id,
    code,
    name,
    sort,
    fullName,
    parentId,
    regionId,
    memo,
    unifiedSocialCreditCode
}) {
    return axios.put(
        api + "/organization/" + id + "/update",
        qs.stringify({
            code,
            name,
            sort,
            fullName,
            parentId,
            regionId,
            status,
            memo,
            unifiedSocialCreditCode
        })
    );
}

/**
 * 启用
 * @param id
 */
export function enableOrg(id) {
    return axios.post(api + "/organization/" + id + "/enable");
}
/**
 * 禁用
 * @param id
 */
export function disableOrg(id) {
    return axios.post(api + "/organization/" + id + "/disable");
}
