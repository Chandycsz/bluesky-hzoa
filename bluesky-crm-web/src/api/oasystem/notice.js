import axios from "axios";
import qs from "qs";
const api = "/api";

export function AddNotice(formData) {
    return axios({
        url: api + "/notice/AddNotice",
        method: "post",
        data: formData,
        headers: { "Content-Type": "application/x-www-form-urlencoded" }
    });
}

export function ListNoticeByCId(CId) {
    return axios.post(api + "/notice/ListNoticeByCId", qs.stringify({ CId })
    );
}