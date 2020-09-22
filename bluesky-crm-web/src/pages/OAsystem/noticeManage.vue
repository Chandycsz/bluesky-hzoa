<template>
    <div>
        <a-card style="width:100%" :bordered="false">
            <div>
                <a-button class="button" icon="plus" type="primary" @click="Submit">提交申请</a-button>
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
                            <a @click="() => Detail(record)">详情</a>
                            <a v-if="record.current==record.total" @click="() => Sign(record)">签定</a>
                        </div>
                    </template>
                </a-table>
            </div>
        </a-card>
    </div>
</template>

<script>
import { ListNoticeByCId } from "@/api/oasystem/notice";
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
            submitvisible: false,
            status: "reviewing",
            columns,
            data: [],
            isloading: false
        };
    },
    mounted() {
        this.ListNoticeByCId();
    },
    methods: {
        disabledDate(current) {
            return current < new Date().getTime() - 10 * 24 * 60 * 3600;
        },
        Submit() {
            // this.submitvisible = true;
            this.$router.push({ name: "notice_add" });
        },
        ggOk() {
            this.submitvisible = false;
        },
        ggCancel() {
            this.submitvisible = false;
        },
        onChange(e) {
            if (e.target.value == "Reviewing") {
                this.status = "all";
            }
        },
        ListNoticeByCId() {
            var CId = this.$store.state.account.id;
            ListNoticeByCId(CId)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.data = res.data.data.list;
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
