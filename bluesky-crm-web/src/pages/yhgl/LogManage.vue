<template>
    <div class="crm-main-cont">
        <a-tabs :defaultActiveKey="defaultActiveKey" @change="callback">
            <a-tab-pane tab="操作日志" key="operate">
                <a-form
                    class="ant-advanced-search-form"
                    :form="form"
                    @submit="handleSearch"
                >
                    <a-row>
                        <a-col :span="6">
                            <a-form-item
                                label="操作人"
                                :label-col="{ span: 5 }"
                                :wrapper-col="{ span: 16 }"
                            >
                                <a-input
                                    v-decorator="['accountName']"
                                    placeholder="请输入操作人"
                                />
                            </a-form-item>
                        </a-col>

                        <a-col :span="6">
                            <a-form-item
                                label="操作时间"
                                :label-col="{ span: 5 }"
                                :wrapper-col="{ span: 16 }"
                            >
                                <a-range-picker v-decorator="['times']" />
                            </a-form-item>
                        </a-col>
                        <a-col :span="5">
                            <a-button type="primary" html-type="submit">
                                查找
                            </a-button>
                            <a-button
                                :style="{ marginLeft: '8px' }"
                                @click="handleReset"
                            >
                                重置
                            </a-button>
                        </a-col>
                    </a-row>
                </a-form>
                <a-table
                    :columns="columns"
                    bordered
                    :dataSource="operateList"
                    :pagination="pagination"
                    @change="handleTableChange"
                    rowKey="id"
                >
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="登录日志" key="login" forceRender>
                <a-form
                    class="ant-advanced-search-form"
                    :form="form2"
                    @submit="handleSearchLogin"
                >
                    <a-row>
                        <a-col :span="5">
                            <a-form-item
                                label="用户姓名"
                                :label-col="{ span: 7 }"
                                :wrapper-col="{ span: 16 }"
                            >
                                <a-input
                                    v-decorator="['accountName']"
                                    placeholder="请输入姓名"
                                />
                            </a-form-item>
                        </a-col>

                        <a-col :span="5">
                            <a-form-item
                                label="操作类型"
                                :label-col="{ span: 8 }"
                                :wrapper-col="{ span: 12 }"
                            >
                                <a-select v-decorator="['type']" placeholder="">
                                    <a-select-option value="">
                                        全部
                                    </a-select-option>
                                    <a-select-option value="1">
                                        登录
                                    </a-select-option>
                                    <a-select-option value="2">
                                        注销
                                    </a-select-option>
                                </a-select>
                            </a-form-item>
                        </a-col>
                        <a-col :span="5">
                            <a-form-item
                                label="结果"
                                :label-col="{ span: 8 }"
                                :wrapper-col="{ span: 12 }"
                            >
                                <a-select
                                    v-decorator="['success']"
                                    placeholder=""
                                >
                                    <a-select-option value="">
                                        全部
                                    </a-select-option>
                                    <a-select-option value="1">
                                        成功
                                    </a-select-option>
                                    <a-select-option value="-1">
                                        失败
                                    </a-select-option>
                                </a-select>
                            </a-form-item>
                        </a-col>
                        <a-col :span="4">
                            <a-button type="primary" html-type="submit">
                                查找
                            </a-button>
                            <a-button
                                :style="{ marginLeft: '8px' }"
                                @click="handleResetLogin"
                            >
                                重置
                            </a-button>
                        </a-col>
                    </a-row>
                </a-form>
                <a-table
                    :columns="loginColumns"
                    bordered
                    :dataSource="loginList"
                    :pagination="loginPage"
                    rowKey="id"
                    @change="handleLoginChange"
                >
                    <template slot="type" slot-scope="text">
                        {{ text === 1 ? "登录" : "注销" }}
                    </template>
                    <template slot="success" slot-scope="text">
                        {{ text === 1 ? "成功" : "失败" }}
                    </template>
                </a-table>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
const columns = [
    {
        title: "操作步骤",
        dataIndex: "step",
        width: 100
    },
    {
        title: "操作编码",
        width: 100,
        dataIndex: "stepCode"
    },
    {
        title: "操作用户",
        dataIndex: "accountName",
        width: 120
    },
    {
        title: "操作ip",
        dataIndex: "ip",
        width: 100
    },
    {
        title: "操作时间",
        dataIndex: "createdAt",
        width: 120
    },
    {
        title: "说明",
        dataIndex: "memo"
    }
];
const loginColumns = [
    {
        title: "姓名",
        dataIndex: "accountName",
        width: 120
    },
    {
        title: "操作类型",
        dataIndex: "type",
        width: 100,
        scopedSlots: { customRender: "type" }
    },
    {
        title: "结果",
        dataIndex: "success",
        width: 100,
        scopedSlots: { customRender: "success" }
    },
    {
        title: "ip地址",
        dataIndex: "ip",
        width: 100
    },
    {
        title: "操作时间",
        dataIndex: "createdAt",
        width: 120
    },
    {
        title: "说明",
        dataIndex: "memo"
    }
];
import { getLoginLog, getOperateLog } from "@/api/yhgl/account";
export default {
    name: "LogManage",
    data() {
        return {
            columns,
            loginColumns,
            form: this.$form.createForm(this),
            form2: this.$form.createForm(this),
            defaultActiveKey: "operate",
            operateList: [],
            loginList: [],
            operateSearch: {
                accountName: "",
                timeStart: "",
                timeEnd: "",
                pageNum: 1
            },
            loginSearch: {
                mobile: "",
                accountName: "",
                type: "",
                success: "",
                pageNum: 1
            },
            pagination: {
                total: 1,
                current: 1,
                pageSize: 20
            },
            loginPage: {
                total: 0,
                current: 1,
                pageSize: 20
            }
        };
    },
    beforeRouteEnter(to, from, next) {
        getOperateLog({
            accountName: "",
            timeStart: "",
            timeEnd: "",
            pageNum: 1
        }).then(res => {
            if (res.data.success === "TRUE") {
                next(vm => {
                    vm.initData(res.data.data.page, "operate");
                });
            } else {
                next(vm => {
                    vm.$Progress.fail();
                });
            }
        });
    },
    methods: {
        initData(page, type) {
            let list = [];
            list = page.list.map(operate => {
                operate.createdAt = this.formatDate(
                    new Date(operate.createdAt)
                );
                return operate;
            });
            switch (type) {
                case "login":
                    this.loginList = list;
                    this.loginPage = {
                        total: page.total,
                        current: page.pageNum,
                        pageSize: page.pageSize
                    };
                    break;
                case "operate":
                    this.operateList = list;
                    this.pagination = {
                        total: page.total,
                        current: page.pageNum,
                        pageSize: page.pageSize
                    };
                    break;
            }
        },
        //dates 时间  type 代表是搜索时需要时分秒设置为00:00:00.展示时不手动设置,time代表第二个时间
        formatDate(dates, type, time) {
            let date = {
                    year: dates.getFullYear(),
                    month:
                        dates.getMonth() + 1 < 10
                            ? "0" + (dates.getMonth() + 1)
                            : dates.getMonth() + 1,
                    day:
                        dates.getDate() < 10
                            ? "0" + dates.getDate()
                            : dates.getDate(),
                    hour:
                        dates.getHours() < 10
                            ? "0" + dates.getHours()
                            : dates.getHours(),
                    minute:
                        dates.getMinutes() < 10
                            ? "0" + dates.getMinutes()
                            : dates.getMinutes(),
                    second:
                        dates.getSeconds() < 10
                            ? "0" + dates.getSeconds()
                            : dates.getSeconds()
                },
                times = "00:00:00";
            if (type) {
                if (time) {
                    times = "23:59:59";
                }
                return (
                    date.year + "-" + date.month + "-" + date.day + " " + times
                );
            } else {
                return (
                    date.year +
                    "-" +
                    date.month +
                    "-" +
                    date.day +
                    " " +
                    date.hour +
                    ":" +
                    date.minute +
                    ":" +
                    date.second
                );
            }
        },
        getOperateList() {
            getOperateLog(this.operateSearch)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.initData(res.data.data.page, "operate");
                        Object.assign(this.pagination, {
                            pageNum: this.operateSearch.pageNum || 1
                        });
                        document.documentElement.scrollTop = 0;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        getLoginList() {
            getLoginLog(this.loginSearch)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.initData(res.data.data.page, "login");
                        Object.assign(this.loginPage, {
                            pageNum: this.loginSearch.pageNum || 1
                        });
                        document.documentElement.scrollTop = 0;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        callback(selectKey) {
            switch (selectKey) {
                case "login":
                    this.getLoginList();
                    break;
                case "operate":
                    this.getOperateList();
                    break;
            }
        },
        handleSearch(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    this.operateSearch = Object.assign(this.operateSearch, {
                        accountName: values.accountName || "",
                        timeStart: values["times"]
                            ? this.formatDate(values["times"][0]["_d"], true)
                            : "",
                        timeEnd: values["times"]
                            ? this.formatDate(
                                  values["times"][1]["_d"],
                                  true,
                                  "end"
                              )
                            : "",
                        pageNum: 1
                    });
                    this.getOperateList(this.operateSearch);
                }
            });
        },
        handleReset() {
            this.operateSearch = Object.assign(this.operateSearch, {
                accountName: "",
                timeStart: "",
                timeEnd: "",
                pageNum: 1
            });
            this.form.resetFields();
            this.getOperateList(this.operateSearch);
        },
        handleSearchLogin(e) {
            e.preventDefault();
            this.form2.validateFields((err, values) => {
                if (!err) {
                    this.loginSearch = Object.assign(this.loginSearch, values, {
                        pageNum: 1
                    });
                    this.getLoginList(this.loginSearch);
                }
            });
        },
        handleResetLogin() {
            this.loginSearch = Object.assign(this.loginSearch, {
                mobile: "",
                accountName: "",
                type: "",
                success: "",
                pageNum: 1
            });
            this.form2.resetFields();
            this.getLoginList(this.loginSearch);
        },
        //点击行政区列表
        handleTableChange(pagination) {
            this.operateSearch.pageNum = pagination.current;
            this.getOperateList(this.operateSearch);
        },
        handleLoginChange(pagination) {
            this.loginSearch.pageNum = pagination.current;
            this.getLoginList(this.loginSearch);
        }
    },
    mounted() {
        this.form2.setFieldsValue({
            type: "",
            success: ""
        });
    }
};
</script>

<style scoped></style>
