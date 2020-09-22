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
export default {
    data() {
        return {};
    },
    mounted() {}
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
