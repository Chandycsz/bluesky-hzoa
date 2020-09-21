import axios from "axios";
import qs from "qs";
const api = "/api";

//获取后台页面
export function getpro() {
    return axios.get(api + "/program_information/list");
}

export function addpro({
    projectname,
    starttime,
    endtime,
    member,
    projectmanager,
    statement
}) {
    return axios.post(api + "/program_information/addProgram",
        qs.stringify({
            projectname,
            starttime,
            endtime,
            member,
            projectmanager,
            statement
        }));
}

export function deletepro(pro_code) {
    return axios.post(api + "/program_information/deleteProgram",
        qs.stringify({
            pro_code
        }));
}

export function editpro({
    pro_code,
    projectname,
    starttime,
    endtime,
    member,
    projectmanager,
    statement
}) {
    return axios.post(api + "/program_information/editProgram",
        qs.stringify({
            pro_code,
            projectname,
            starttime,
            endtime,
            member,
            projectmanager,
            statement
        }));
}

//获取后台页面
export function getphinfobyid(pro_id) {
    return axios.get(api + "/program_information/listhInfobypid", {
        params: {
            pro_id
        }
    })
}

//获取后台页面
export function getphinfo() {
    return axios.get(api + "/program_information/listhInfo");
}

export function findChildren(parentId) {
    return axios.get(api + "/program_information/findchildren", {
        params: {
            parentId
        }
    });
}
/**
 * 获取系统级别
 */
export function getLevel() {
    return axios.get(api + "/program_information/getlevel");
}


export function addCategory({ parentId, name }) {
    // console.log(parentId, name);
    return axios.post(
        api + "/program_information/addpro",
        qs.stringify({
            parentId,
            name
        })
    );
}

/**
 * 更新省市区指定id数据
 * @param id
 * @param name
 * @param code
 */
export function updateCategory({ id, name }) {
    return axios.put(
        api + "/program_information/" + id + "/update",
        qs.stringify({
            id,
            name

        })
    );
}

/**
 * 删除省市区指定id数据
 * @param id
 */
export function deleteCategory(id) {
    return axios.put(api + "/program_information/" + id + "/delete");
}


export function findAllProgramnamecode() {
    return axios.get(api + "/program_information/listpidname");
}