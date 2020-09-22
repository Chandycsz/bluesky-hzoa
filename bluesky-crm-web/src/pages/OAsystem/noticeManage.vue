<template>
    <div>
        <a-card style="width:100%" :bordered="false">
            <div>
                <a-button class="button" icon="plus" type="primary" @click="submitAdd">提交申请</a-button>
            </div>
            <div>
                <div>
                    <a-radio-group v-model="status" @change="onChange">
                        <a-radio-button value="Reviewing">审核中</a-radio-button>
                        <a-radio-button value="Passed">已通过</a-radio-button>
                        <a-radio-button value="Returned">被退回</a-radio-button>
                        <a-radio-button value="Invalid">已失效</a-radio-button>
                    </a-radio-group>
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
                            <a @click="() => Edit(record)">修改</a>
                            <a @click="() => Delete(record)">删除</a>
                            <a v-if="record.status===0" @click="() => handleSubmit(record)">提交申请</a>
                        </div>
                    </template>
                </a-table>
            </div>
        </a-card>
    </div>
</template>

<script>
import { ListNoticeByCId, DeleteNoticeById } from "@/api/oasystem/notice";
const columns = [
    {
        title: "标题",
        width: 120,
        dataIndex: "headline"
    },
    {
        title: "内容",
        width: 200,
        dataIndex: "content"
    },
    {
        title: "失效时间",
        width: 150,
        dataIndex: "failureTime"
    },
    {
        title: "发起人",
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
export default {
    data() {
        return {
            submitvisible: false,
            status: "Reviewing",
            columns,
            data: [],
            isloading: false,
            editvisible: false
        };
    },
    mounted() {
        this.ListNoticeByCId();
    },
    methods: {
        Edit(record) {
            this.editvisible = true;
        },
        disabledDate(current) {
            return current < new Date().getTime() - 10 * 24 * 60 * 3600;
        },
        submitAdd() {
            // this.submitvisible = true;
            this.$router.push({ name: "notice_add" });
        },
        Delete(record) {
            this.isloading = true;
            var id = record.id;
            DeleteNoticeById(id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.data = this.data.filter(item => item.id !== id);
                        this.$message.success("删除成功！");
                        this.isloading = false;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                    this.isloading = false;
                });
        },
        ggOk() {
            this.submitvisible = false;
        },
        ggCancel() {
            this.submitvisible = false;
        },
        onChange(e) {
            if (e.target.value == "Reviewing") this.status = "Reviewing";
            else if (e.target.value == "Passed") this.status = "Passed";
            else if (e.target.value == "Returned") this.status = "Returned";
            else if (e.target.value == "Invalid") this.status = "Invalid";
            this.ListNoticeByCId();
        },
        ListNoticeByCId() {
            this.isloading = true;
            var value = {
                CId: this.$store.state.account.id,
                status: this.status
            };
            ListNoticeByCId(value)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.data = res.data.data.list;
                        this.isloading = false;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    }
};
</script>

<style scoped lang="less">
.button {
    // padding: 10px;
    margin-bottom: 10px;
    // float: left;
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
