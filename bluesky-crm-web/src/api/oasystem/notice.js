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

export function ListNoticeByCId({ CId, status }) {
    return axios.post(api + "/notice/ListNoticeByCId", qs.stringify({ CId, status })
    );
}


export function DeleteNoticeById(id) {
    return axios.post(api + "/notice/DeleteNoticeById", qs.stringify({ id })
    );
}

export function getNoticeByAId(AId) {
    return axios.post(api + "/notice/getNoticeByAId", qs.stringify({ AId })
    );
}


export function visitNotice({ id, account_id }) {
    return axios.post(api + "/notice/visitNotice", qs.stringify({ id, account_id })
    );
}

export function ListReviewedNotice(account_id) {
    return axios.post(api + "/notice/ListReviewedNotice", qs.stringify({ account_id }))

}

export function addComments({ NId, choice,review,AId }) {
    return axios.post(api + "/notice/addComments", qs.stringify({ NId, choice,review,AId })
    );
}