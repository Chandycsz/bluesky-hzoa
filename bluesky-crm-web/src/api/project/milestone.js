import axios from "axios";
import qs from "qs";
const api = "/api";

//获取后台页面
export function getpro() {
    return axios.get(api + "/program_milestone/list");
}
export function gethistorypro() {
    return axios.get(api + "/program_milestone_history/list");
}
export function addpro({
    projectname,
    starttime,
    endtime,
    member,
    projectmanager,
    statement
}) {
    return axios.post(api + "/program_milestone/update",
        qs.stringify({
            projectname,
            starttime,
            endtime,
            member,
            projectmanager,
            statement
        }));
}
export function deletepro(id) {
    return axios.post(api + "/program_milestone/delete",
        qs.stringify({
            id
        }));
}


export function findAllmileidname() {
    return axios.get(api + "/program_milestone/listmidname");
}