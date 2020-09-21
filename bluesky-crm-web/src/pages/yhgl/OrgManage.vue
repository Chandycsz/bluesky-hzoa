<template>
    <div class="crm-main-cont">
        <div
            class="clearfix"
            style="display: flex;justify-content: space-between;"
        >
            <a-tree
                class="fl org-tree"
                @select="onSelect"
                :selectedKeys="selectedKeys"
                :treeData="treeData"
            />

            <div class="fl org-main">
                <a-row>
                    <a-col :span="10">
                        <a-form-item
                            label="单位名称:"
                            :label-col="{ span: 5 }"
                            :wrapper-col="{ span: 16 }"
                        >
                            <a-input v-model="name" placeholder="单位名称" />
                        </a-form-item>
                    </a-col>

                    <a-col :span="10" class="org-btns">
                        <a-button type="primary" @click="handleSearch">
                            查找
                        </a-button>
                        <a-button @click="handleReset">
                            重置
                        </a-button>
                    </a-col>
                    <a-col :span="24">
                        <a-form-item>
                            <a-button type="primary" @click="onShow">
                                添加单位
                            </a-button>
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-table
                    @change="handleTableChange"
                    :columns="columns"
                    :dataSource="orgList"
                    bordered
                    :scroll="{ x: '1300px' }"
                    rowKey="id"
                    :pagination="pagination"
                >
                    <template slot="list" slot-scope="text, record">
                        <a @click="() => showAccount(record.id)">查看</a>
                    </template>
                    <template slot="status" slot-scope="text">
                        {{ text === 0 ? "启用" : "禁用" }}
                    </template>
                    <template slot="operation" slot-scope="text, record">
                        <div class="operation-btns">
                            <a
                                v-show="record.status === -5"
                                @click="() => start(record)"
                                >启用</a
                            >
                            <a
                                v-show="record.status === 0"
                                @click="() => stop(record)"
                                >停用</a
                            >
                            <a @click="() => handleChange(record)">修改</a>
                        </div>
                    </template>
                </a-table>
            </div>
        </div>

        <a-drawer
            :title="title"
            :width="450"
            @close="onClose"
            :visible="visible"
            :wrapStyle="{
                height: 'calc(100% - 108px)',
                overflow: 'auto',
                paddingBottom: '150px'
            }"
        >
            <a-form :form="form">
                <a-form-item label="顺序" v-bind="layoutStyle">
                    <a-input
                        type="number"
                        v-decorator="[
                            'sort',
                            {
                                rules: [
                                    { required: true, message: '请填写顺序!' }
                                ]
                            }
                        ]"
                    />
                </a-form-item>

                <a-form-item label="单位名称" v-bind="layoutStyle">
                    <a-input
                        v-decorator="[
                            'name',
                            {
                                rules: [
                                    {
                                        required: true,
                                        message: '请填写单位名称!'
                                    }
                                ]
                            }
                        ]"
                    />
                </a-form-item>

                <a-form-item label="单位简称" v-bind="layoutStyle">
                    <a-input
                        v-decorator="[
                            'fullName',
                            {
                                rules: [
                                    {
                                        required: true,
                                        message: '请填写单位简称!'
                                    }
                                ]
                            }
                        ]"
                    />
                </a-form-item>

                <a-form-item label="部门编码" v-bind="layoutStyle">
                    <a-input
                        v-decorator="[
                            'code',
                            {
                                rules: [
                                    {
                                        required: true,
                                        message: '请填写部门编码!'
                                    }
                                ]
                            }
                        ]"
                    />
                </a-form-item>
                <a-form-item label="统一社会信用代码" v-bind="layoutStyle">
                    <a-input v-decorator="['unifiedSocialCreditCode']" />
                </a-form-item>
                <a-form-item label="备注" v-bind="layoutStyle">
                    <textarea
                        cols="30"
                        rows="10"
                        class="memo"
                        v-decorator="['memo']"
                    ></textarea>
                </a-form-item>
                <div class="submit-btn">
                    <a-button @click="handleSubmit" type="primary"
                        >提交</a-button
                    >
                </div>
            </a-form>
        </a-drawer>

        <a-modal
            title="用户列表"
            v-model="visibleAccount"
            width="650px"
            @ok="
                () => {
                    this.visibleAccount = false;
                }
            "
            :cancelButtonProps="{ props: { disabled: true } }"
        >
            <a-table
                :columns="accountColumns"
                bordered
                :dataSource="accountList"
                :pagination="accountPage"
                :scroll="{ y: 300 }"
            >
                <template slot="status" slot-scope="text">
                    {{ text === 0 ? "启用" : "禁用" }}
                </template>
            </a-table>
        </a-modal>
    </div>
</template>

<script>
import { getAllTree } from "@/api/yhgl/regions";
import {
    getPage,
    addOrg,
    updataOrg,
    enableOrg,
    disableOrg
} from "@/api/yhgl/organization";
import { getAccountList } from "@/api/yhgl/account";
const columns = [
    {
        width: 150,
        title: "名称",
        dataIndex: "name",
        fixed: "left",
        scopedSlots: { customRender: "name" }
    },
    {
        title: "简称",
        dataIndex: "fullName",
        scopedSlots: { customRender: "fullName" }
    },
    {
        title: "部门编码",
        dataIndex: "code",
        scopedSlots: { customRender: "code" }
    },
    {
        title: "上级部门",
        dataIndex: "org",
        scopedSlots: { customRender: "org" }
    },
    {
        title: "统一社会信用代码",
        dataIndex: "unifiedSocialCreditCode",
        scopedSlots: { customRender: "unifiedSocialCreditCode" }
    },
    {
        title: "状态",
        dataIndex: "status",
        scopedSlots: { customRender: "status" }
    },
    {
        title: "用户列表",
        width: 100,
        scopedSlots: { customRender: "list" }
    },
    {
        title: "序号",
        dataIndex: "sort",
        width: 50,
        scopedSlots: { customRender: "sort" }
    },
    {
        title: "备注",
        dataIndex: "memo",
        width: 120,
        scopedSlots: { customRender: "memo" }
    },
    {
        title: "操作",
        width: 140,
        dataIndex: "operation",
        scopedSlots: { customRender: "operation" }
    }
];
const accountColumns = [
    {
        title: "姓名",
        width: 100,
        dataIndex: "name",
        scopedSlots: { customRender: "name" }
    },
    {
        title: "手机号",
        width: 130,
        dataIndex: "mobile",
        scopedSlots: { customRender: "mobile" }
    },
    {
        title: "单位",
        width: 130,
        dataIndex: "organizationName",
        scopedSlots: { customRender: "organizationName" }
    },
    {
        title: "角色",
        width: 150,
        dataIndex: "roleName",
        scopedSlots: { customRender: "roleName" }
    },
    {
        title: "状态",
        width: 100,
        dataIndex: "status",
        scopedSlots: { customRender: "status" }
    }
];
export default {
    name: "OrgManage",
    data() {
        return {
            columns,
            accountColumns,
            name: "",
            form: this.$form.createForm(this),
            selectedKeys: [],
            autoExpandParent: true,
            treeData: [],
            orgList: [],
            accountList: [],
            onLoadData: false,
            visible: false,
            visibleAccount: false,
            editOrg: "",
            title: "新增单位",
            layoutStyle: {
                labelCol: {
                    span: 9
                },
                wrapperCol: {
                    span: 13
                }
            },
            pagination: {
                total: 0,
                current: 1,
                pageSize: 20
            },
            accountPage: {
                total: 0,
                current: 1
            }
        };
    },
    beforeRouteEnter(to, from, next) {
        getAllTree()
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        if (res.data.data.allRegionTreeUnderLevel.length) {
                            vm.treeData = vm.setTree(
                                res.data.data.allRegionTreeUnderLevel
                            );
                            vm.selectedKeys = [
                                res.data.data.allRegionTreeUnderLevel[0].id
                            ];
                            vm.getOrgList({
                                regionId:
                                    res.data.data.allRegionTreeUnderLevel[0].id
                            });
                        } else {
                            vm.$message.error("请先创建行政区！").then(() => {
                                vm.$router.push({
                                    name: "om_region_manage"
                                });
                            });
                        }
                    });
                });
            })
            .catch(() => {
                next(vm => {
                    vm.$message.error("服务器出错！请稍后再试!");
                    vm.$Progress.fail();
                });
            });
    },
    methods: {
        //递归树状数据
        setTree(trees) {
            trees.map(tree => {
                Object.assign(tree, {
                    title: tree.name,
                    key: tree.id,
                    isLeaf: tree.leaf
                });
                if (tree.children) {
                    this.setTree(tree.children);
                }
            });
            return trees;
        },
        //获取单位列表数据
        getOrgList({ regionId, name = "", pageNum = "" }) {
            getPage({ regionId, name, pageNum })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.pagination = {
                            current: res.data.data.organizations.pageNum,
                            total: res.data.data.organizations.total,
                            pageSize: res.data.data.organizations.pageSize
                        };
                        this.orgList = res.data.data.organizations.list;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        onSelect(selectedKeys) {
            if (selectedKeys.length) {
                this.selectedKeys = selectedKeys;
                if (selectedKeys !== this.selectedKeys[0]) {
                    this.getOrgList({ regionId: this.selectedKeys[0] });
                }
            }
        },
        //搜索
        handleSearch() {
            this.getOrgList({
                regionId: this.selectedKeys[0],
                name: this.name
            });
        },
        //重置
        handleReset() {
            if (this.name) {
                this.name = "";
                this.getOrgList({ regionId: this.selectedKeys[0] });
            }
        },
        onShow() {
            this.visible = true;
            this.editOrg = "";
            this.title = "新增单位";
        },
        onClose() {
            this.visible = false;
        },
        //提交修改或添加
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    if (this.editOrg.id) {
                        Object.assign(values, {
                            id: this.editOrg.id,
                            regionId: this.editOrg.regionId,
                            parentId: this.editOrg.parentId
                        });
                        this.updateDataOrg(values);
                    } else {
                        //默认启用是0
                        values.regionId = this.selectedKeys[0];
                        values.parentId = "";
                        values.status = 0;
                        this.addDataOrg(values);
                    }
                }
            });
        },
        //修改
        updateDataOrg(values) {
            updataOrg(values)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("修改成功");
                        this.getOrgList({ regionId: this.selectedKeys[0] });
                        this.form.resetFields();
                        this.onClose();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        //添加
        addDataOrg(values) {
            addOrg(values)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("新增成功");
                        this.getOrgList({ regionId: this.selectedKeys[0] });
                        this.form.resetFields();
                        this.onClose();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        //查看用户列表
        showAccount(organizationId) {
            getAccountList({ organizationId })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.visibleAccount = true;
                        this.accountList = res.data.data.page.list;
                        this.accountPage = {
                            total: res.data.data.page.total,
                            current: res.data.data.page.pageNum
                        };
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        //点击行政区列表
        handleTableChange(pagination) {
            this.getOrgList({
                regionId: this.selectedKeys[0],
                name: this.name,
                pageNum: pagination.current
            });
        },
        handleChange(org) {
            this.visible = true;
            this.editOrg = org;
            this.title = "单位修改：" + org.name;
            setTimeout(() => {
                this.form.setFieldsValue({
                    sort: parseInt(org.sort),
                    name: org.name,
                    fullName: org.fullName,
                    code: org.code,
                    memo: org.memo || "",
                    unifiedSocialCreditCode: org.unifiedSocialCreditCode || ""
                });
            }, 0);
        },
        //启用
        start(org) {
            enableOrg(org.id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("启用成功！");
                        this.$set(org, "status", 0);
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        //停用
        stop(org) {
            disableOrg(org.id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("停用成功！");
                        this.$set(org, "status", -5);
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    }
};
</script>

<style>
.org-main .ant-form-item-label {
    text-align: left;
}
</style>
<style scoped lang="less">
.org-tree {
    min-width: 150px;
    flex-shrink: 0;
}
.org-main {
    width: 80%;
    margin-left: 30px;
}
.org-btns button {
    margin-right: 15px;
}
.operation-btns a {
    margin-right: 10px;
}
.memo {
    width: 100%;
    resize: none;
    height: 100px;
    padding: 10px;
    border: 1px solid rgb(217, 217, 217);
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
}
.submit-btn {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e9e9e9;
    padding: 10px 16px;
    background: #fff;
    text-align: right;
}
</style>
