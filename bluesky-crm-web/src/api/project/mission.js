import axios from "axios";
import qs from "qs";
const api = "/api";

//获取后台页面
export function getmissioninfo() {
    return axios.get(api + "/mis/getmissioninfo");
}

export function UpdateConnection({ id, pro_id, mile_id }) {
    return axios.post(
        api + "/mis/Updateconnection",
        qs.stringify({
            id,
            pro_id,
            mile_id
        })
    );

}

export function AddMission({
    headline,
    priority,
    type,
    statement,
    deadline
}) {
    return axios.post(api + "/mis/addMission", qs.stringify({
        headline,
        priority,
        type,
        statement,
        deadline
    }));
}

export function getmispeoinfo() {
    return axios.get(api + "/mis/getmispeoinfo");
}
