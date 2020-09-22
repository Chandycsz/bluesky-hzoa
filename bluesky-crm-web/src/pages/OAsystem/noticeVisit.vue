<template>
    <div>
        <a-card
            style="width:100%"
            :bordered="false"
            :tabList="tabListNoTitle"
            :activeTabKey="noTitleKey"
            @tabChange="key => handleTabChange(key, 'noTitleKey')"
        >
            <div v-if="show">
                <div class="button">
                    <a-radio-group v-model="status" @change="onChange">
                        <a-radio-button value="all">全部</a-radio-button>
                        <a-radio-button value="reviewed">待审核</a-radio-button>
                        <a-radio-button value="signed">待签定</a-radio-button>
                        <!-- <a-radio-button value="compeleted">待确认</a-radio-button>
                        <a-radio-button value="confirmed">已确认</a-radio-button>-->
                    </a-radio-group>
                    <!-- <a-input-search
                        placeholder="请输入任务描述"
                        enter-button="搜索"
                        @search="onSearch"
                        :isloading="isloading"
                        style="width: 272px;"
                    />-->
                </div>

                <a-table
                    :columns="columns"
                    :data-source="data"
                    bordered
                    :pagination="false"
                    :loading="isloading"
                    :rowKey="(record,index)=>{return index}"
                >
                    <div
                        :title="record.headline"
                        :style="{maxWidth: '100px',whiteSpace: 'nowrap',textOverflow: 'ellipsis',overflow: 'hidden', wordWrap: 'break-word', wordBreak: 'break-all' }"
                        slot="headline"
                        slot-scope="text, record"
                    >{{record.headline}}</div>
                    <template slot="operation" slot-scope="text, record">
                        <div class="editable-row-operations">
                            <a @click="() => Detail(record)">详情</a>
                            <a v-if="record.current==record.total" @click="() => Sign(record)">签定</a>
                        </div>
                    </template>
                </a-table>
            </div>
            <div v-else>
                <div class="button">
                    <a-radio-group v-model="status" @change="onChange">
                        <a-radio-button value="inPerformance">履行中</a-radio-button>
                        <a-radio-button value="relieve">解除</a-radio-button>
                        <a-radio-button value="expire">到期</a-radio-button>
                        <!-- <a-radio-button value="compeleted">待确认</a-radio-button>
                        <a-radio-button value="confirmed">已确认</a-radio-button>-->
                    </a-radio-group>
                    <!-- <a-input-search
                        placeholder="请输入任务描述"
                        enter-button="搜索"
                        @search="onSearch"
                        :isloading="isloading"
                        style="width: 272px;"
                    />-->
                </div>

                <a-table
                    :columns="Pcolumns"
                    :data-source="data"
                    bordered
                    :pagination="false"
                    :loading="isloading"
                    :rowKey="(record,index)=>{return index}"
                >
                    <div
                        :title="record.headline"
                        :style="{maxWidth: '100px',whiteSpace: 'nowrap',textOverflow: 'ellipsis',overflow: 'hidden', wordWrap: 'break-word', wordBreak: 'break-all' }"
                        slot="headline"
                        slot-scope="text, record"
                    >{{record.headline}}</div>
                    <template slot="operation" slot-scope="text, record">
                        <div class="editable-row-operations">
                            <a @click="() => Detail(record)">详情</a>
                            <a @click="() => EStop(record)">提前中止</a>
                            <a @click="() => TimeLine(record)">时间轴</a>
                        </div>
                    </template>
                </a-table>
            </div>
        </a-card>

        <a-modal :width="800" :visible="visible" @ok="handleOk" @cancel="handleCancel">
            <a-card style="margin-top: 24px" :bordered="false" title="合同信息">
                <a-descriptions>
                    <a-descriptions-item label="合同名称">{{record.name}}</a-descriptions-item>
                    <a-descriptions-item label="相对方">{{record.counterName}}</a-descriptions-item>
                    <a-descriptions-item label="合同有效日期">{{record.effectiveDate}}</a-descriptions-item>
                    <a-descriptions-item v-if="!show" label="签定日期">{{record.signDate}}</a-descriptions-item>
                    <a-descriptions-item label="申请人">{{record.creatorName}}</a-descriptions-item>
                    <a-descriptions-item label="状态">{{record.status}}</a-descriptions-item>
                    <a-descriptions-item label="金额">{{record.money}}</a-descriptions-item>
                    <a-descriptions-item label="转账方式">{{record.payway}}</a-descriptions-item>
                </a-descriptions>
            </a-card>
            <a-card style="margin-top: 24px" :bordered="false" title="进度管理信息" v-if="!show">
                <p class="pgcontent" v-for="item in PgmList" :key="item.index">
                    <b>管理人</b>
                    &nbsp;:&nbsp;{{item.manager}}&nbsp;&nbsp;
                    <b>管理内容</b>
                    &nbsp;:&nbsp;{{item.content}}&nbsp;&nbsp;
                    <b>时间范围</b>
                    &nbsp;:&nbsp;{{item.start_time}}~{{item.end_time}}
                </p>
            </a-card>
            <a-card style="margin-top: 24px" :bordered="false" title="付款管理信息" v-if="!show">
                <p class="pgcontent" v-for="item in TmList" :key="item.index">
                    <b>管理人</b>
                    &nbsp;:&nbsp;{{item.manager}}&nbsp;&nbsp;
                    <b>交易金额</b>
                    &nbsp;:&nbsp;{{item.money}}&nbsp;&nbsp;
                    <b>截止时间</b>
                    &nbsp;:&nbsp;{{item.deadline}}&nbsp;&nbsp;说明&nbsp;:&nbsp;{{item.content}}
                </p>
            </a-card>
            <a-card style="margin-top: 24px" :bordered="false" title="保证金管理信息" v-if="!show"></a-card>
            <a-card style="position:relative;" :bordered="false" title="审批进度" v-if="show">
                <br />

                <br />
            </a-card>
        </a-modal>

        <a-drawer
            title="时间轴"
            :width="600"
            :visible="Tlvisible"
            :body-style="{ paddingBottom: '80px' }"
            @close="onClose"
        >
            <a-form v-if="Tlvisible" :form="form">
                <a-form-item>
                    <div>
                        <!-- <div>任务流程</div> -->
                        <a-timeline>
                            <a-timeline-item
                                color="green"
                                v-for="item in timeline"
                                :key="item"
                            >{{item.time}}&nbsp;&nbsp;{{item.content}}</a-timeline-item>
                        </a-timeline>
                    </div>
                </a-form-item>
            </a-form>
        </a-drawer>
    </div>
</template>

<script>
const columns = [
    {
        title: "合同名称",
        width: 120,
        dataIndex: "name"
    },
    {
        title: "相对方",
        width: 200,
        dataIndex: "counterName"
    },
    {
        title: "状态",
        width: 150,
        dataIndex: "status"
    },

    {
        title: "申请人",
        width: 150,
        dataIndex: "creatorName"
    },
    {
        title: "操作",
        key: "operation",
        width: 150,
        scopedSlots: { customRender: "operation" }
    }
];

const Pcolumns = [
    {
        title: "合同名称",
        width: 120,
        dataIndex: "name"
    },
    {
        title: "相对方",
        width: 200,
        dataIndex: "counterName"
    },
    {
        title: "状态",
        width: 150,
        dataIndex: "status"
    },
    {
        title: "操作",
        key: "operation",
        width: 150,
        scopedSlots: { customRender: "operation" }
    }
];



export function sortByKey(array, key) {
    return array.sort(function(a, b) {
        var x = a[key]; //如果要从大到小,把x,y互换就好
        var y = b[key];
        return x < y ? -1 : x > y ? 1 : 0;
    });
}
export function compare(property) {
    return function(a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value1 - value2;
    };
}
export default {
    data() {
        return {
            status: "all",
            columns,
            Pcolumns,

            data: [],
            isloading: false,

            visible: false,
            record: [],
            tabListNoTitle: [
                {
                    key: "create",
                    tab: "合同签署"
                },
                {
                    key: "sign",
                    tab: "合同履行"
                }
            ],
            steps: [
                {
                    title: "上传文件"
                },
                {
                    title: "合同属性"
                },
                {
                    title: "进度管理"
                },
                {
                    title: "付款管理"
                },
                {
                    title: "履约保证金"
                }
            ],
            noTitleKey: "create",
            show: true,
            form: this.$form.createForm(this),
            uploadvisible: false,
            title: "上传扫描件",
            layoutStyle: {
                labelCol: {
                    span: 7
                },
                wrapperCol: {
                    span: 16
                }
            },
            current: "",
            PgmList: [], //进度管理
            TmList: [], //交易金额管理
            contract: [],
            Tlvisible: false,
            timeline: [],
            stepsList: [],
            test: []
        };
    },
    mounted() {
        if (this.$route.params.status != undefined) {
            this.status = this.$route.params.status;
            if (
                this.status == "inPerformance" ||
                this.status == "relieve" ||
                this.status == "expire"
            ) {
                // this.noTitleKey = "sign";
                this.handleTabChange("sign", "noTitleKey");
                return;
            }

            if (
                this.status == "all" ||
                this.status == "reviewed" ||
                this.status == "signed"
            ) {
                // this.handleTabChange("sign", "noTitleKey")
                this.listContractByCId(0);
            }
        }
    },
    methods: {
        onClose() {
            this.Tlvisible = false;
        },
        TimeLine(record) {
            this.Tlvisible = true;
            getTimeLine(record.id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.timeline = res.data.data.list;
                        console.log(this.timeline);
                        console.log(sortByKey(this.timeline, "time"));
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        handleback() {
            this.uploadvisible = true;
        },
        Sign(record) {
            this.$router.push({
                name: "contract_sign",
                params: record
            });
        },
        handleOk() {
            this.uploadvisible = false;
            this.visible = false;
        },
        handleCancel() {
            this.visible = false;
            this.test = [];
        },
        Detail(record) {
            this.current = parseInt(record.current) - 1;
            this.visible = true;
            this.record = record;
            this.contract = record;
            listPGMbyCId(record.id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.PgmList = res.data.data.list;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
            listTradeMBycId(record.id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.TmList = res.data.data.list;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });

            listStepsList(record.groupId)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.stepsList = res.data.data.list;
                        let header = { title: "提交合同" };
                        let ending = { title: "完成" };
                        let step = {};
                        this.test = [];
                        for (let i = 0; i < res.data.data.list.length; i++) {
                            step = {
                                title: res.data.data.list[i].headline,
                                description: res.data.data.list[i].managerName,
                                managerId: res.data.data.list[i].managerId,
                                serialNumber:
                                    res.data.data.list[i].serialNumber,
                                id: res.data.data.list[i].id
                            };
                            this.test.push(step);
                        }
                        this.test.sort(compare("serialNumber"));
                        this.test.unshift(header);
                        this.test.push(ending);
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        onChange(e) {
            if (e.target.value == "all") {
                this.status = "all";
                if (this.noTitleKey == "create") {
                    this.listContractByCId(0);
                } else {
                    this.listContractByCId(1);
                }
                return;
            }
            if (e.target.value == "reviewed") {
                this.status = "reviewed";
                if (this.noTitleKey == "create") {
                    this.listContractByCId(0);
                } else {
                    this.listContractByCId(1);
                }
                return;
            }
            if (e.target.value == "signed") {
                this.status = "signed";
                if (this.noTitleKey == "create") {
                    this.listContractByCId(0);
                } else {
                    this.listContractByCId(1);
                }
                return;
            }
            if (e.target.value == "inPerformance") {
                this.status = "inPerformance";
                this.listContractByCId(1);
            }
            // if (e.target.value == "compeleted") {
            //     this.isloading = true;
            //     this.listmission();
            // }
            // if (e.target.value == "confirmed") {
            //     this.isloading = true;
            //     this.listmission();
            // }
        },

        listContractByCId(type) {
            var values = {
                creatorId: this.$store.state.account.id,
                status: this.status,
                type: type
            };
            this.isloading = true;
            listContractByCId(values)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.isloading = false;
                        this.data = res.data.data.list;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        handleTabChange(key, type) {
            this[type] = key;
            if (key == "create") {
                this.show = true;
                this.status = "all";
                this.listContractByCId(0);
            }
            if (key == "sign") {
                this.show = false;
                this.status = "inPerformance";
                this.listContractByCId(1);
            }
        }
    }
    // watch: {
    //     current: {
    //         handler(current) {
    //             if (current == 0) {
    //                 this.title = "上传合同扫描件";
    //                 return;
    //             }
    //             if (current == 1) {
    //                 this.title = "合同签定要素填写";
    //                 return;
    //             }
    //             if (current == 2) {
    //                 this.title = "进度设定";
    //                 return;
    //             }
    //             if (current == 3) {
    //                 this.title = "付款设定";
    //                 return;
    //             }
    //             if (current == 4) {
    //                 this.title = "履约保证金设定";
    //                 return;
    //             }
    //         },
    //         immediate: true
    //     }
    // }
};
</script>

<style scoped lang="less">
.button {
    margin-bottom: 10px;
}
.editable-row-operations a {
    margin-right: 10px;
}
.steps {
    // width: 80%;

    margin-bottom: 10px;
}
.pgcontent {
    margin-top: 20px;
    font-size: 12px;
    // line-height: 32px;
}
</style>
