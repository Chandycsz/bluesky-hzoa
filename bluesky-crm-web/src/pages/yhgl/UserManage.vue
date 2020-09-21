<template>
    <div class="crm-main-cont">
        <div class="search-box">
            <a-form :form="formSearch">
                <a-form-item label="姓名" v-bind="layoutStyle2">
                    <a-input v-decorator="['name']" />
                </a-form-item>
                <a-form-item label="手机号" v-bind="layoutStyle2">
                    <a-input v-decorator="['mobile']" />
                </a-form-item>
                <a-form-item label="行政区" v-bind="layoutStyle2">
                    <a-cascader
                        :options="treeData"
                        changeOnSelect
                        @change="hanlderTree"
                        placeholder="请先选择行政区"
                    />
                </a-form-item>
                <a-form-item label="单位" v-bind="layoutStyle2">
                    <a-select
                        v-decorator="['organizationId']"
                        placeholder="先选行政区，再选单位"
                    >
                        <a-select-option
                            :key="org.id"
                            v-for="org in searchData.orgList"
                            :value="org.id"
                            >{{ org.name }}</a-select-option
                        >
                    </a-select>
                </a-form-item>
                <a-form-item label="用户类型" v-bind="layoutStyle2">
                    <a-select
                        v-decorator="['roleId']"
                        placeholder="先选行政区，再选用户类型"
                    >
                        <a-select-option
                            :key="org.id"
                            v-for="org in searchData.roleList"
                            :value="org.id"
                            >{{ org.name }}</a-select-option
                        >
                    </a-select>
                </a-form-item>
                <a-form-item label="状态" v-bind="layoutStyle2">
                    <a-select v-decorator="['status']">
                        <a-select-option value="">
                            全部
                        </a-select-option>
                        <a-select-option :value="0">
                            启用
                        </a-select-option>
                        <a-select-option :value="-5">
                            禁用
                        </a-select-option>
                    </a-select>
                </a-form-item>
            </a-form>
        </div>
        <div class="btns-group">
            <a-button type="primary" @click="handleSearch">
                搜索
            </a-button>
            <a-button @click="handleReset">
                重置
            </a-button>
            <a-button @click="addAccount" type="primary">
                添加用户
            </a-button>
        </div>

        <a-table
            class="account-list"
            :columns="columns"
            :dataSource="accountList"
            @change="handleTableChange"
            bordered
            rowKey="id"
            :pagination="pagination"
        >
            <template slot="status" slot-scope="text">
                {{ text === 0 ? "启用" : "禁用" }}
            </template>
            <template slot="auth" slot-scope="text, record">
                <a @click="() => showAuth(record)">查看</a>
            </template>
            <template slot="operation" slot-scope="text, record">
                <div class="editable-row-operations">
                    <a @click="() => edit(record)">修改</a>
                    <a
                        v-show="record.status === -5"
                        @click="() => start(record.id)"
                        >启用</a
                    >
                    <a
                        v-show="record.status === 0"
                        @click="() => stop(record.id)"
                        >禁用</a
                    >
                </div>
            </template>
        </a-table>

        <a-drawer
            :title="title"
            :width="650"
            @close="onClose"
            :visible="visible"
            :wrapStyle="{ height: 'calc(100% - 108px)', overflow: 'auto' }"
        >
            <a-form v-if="!editStatus && visible" :form="form">
                <a-row>
                    <a-col :span="12">
                        <a-form-item label="姓名" v-bind="layoutStyle">
                            <a-input
                                v-decorator="[
                                    'name',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写名称!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="手机号" v-bind="layoutStyle">
                            <a-input
                                v-decorator="[
                                    'mobile',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写手机号!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="密码" v-bind="layoutStyle">
                            <a-input
                                type="password"
                                v-decorator="[
                                    'password',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写密码!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="确认密码" v-bind="layoutStyle">
                            <a-input
                                type="password"
                                v-decorator="[
                                    'passwordConfirm',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写二次确认密码!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>

                    <a-col :span="12">
                        <a-form-item label="行政区" v-bind="layoutStyle">
                            <a-cascader
                                changeOnSelect
                                v-decorator="[
                                    'regionId',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请先选择行政区!'
                                            }
                                        ]
                                    }
                                ]"
                                :options="treeData"
                                @change="hanlderTreeAdd"
                                placeholder="请选择选行政区"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="单位" v-bind="layoutStyle">
                            <a-select
                                v-decorator="[
                                    'organizationId',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请选择单位!'
                                            }
                                        ]
                                    }
                                ]"
                                placeholder="先选行政区，再选单位"
                            >
                                <a-select-option
                                    :key="org.id"
                                    v-for="org in formData.orgList"
                                    :value="org.id"
                                    >{{ org.name }}</a-select-option
                                >
                            </a-select>
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="用户类型" v-bind="layoutStyle">
                            <a-select
                                placeholder="先选行政区，再选用户类型"
                                v-decorator="[
                                    'roleId',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请选择单位!'
                                            }
                                        ]
                                    }
                                ]"
                            >
                                <a-select-option
                                    :key="role.id"
                                    v-for="role in formData.roleList"
                                    :value="role.id"
                                    >{{ role.name }}</a-select-option
                                >
                            </a-select>
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="顺序" v-bind="layoutStyle">
                            <a-input
                                type="number"
                                min="1"
                                v-decorator="[
                                    'sort',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写顺序!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="用户部门" v-bind="layoutStyle">
                            <a-input v-decorator="['department']" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="身份证号" v-bind="layoutStyle">
                            <a-input
                                v-decorator="[
                                    'idCard',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写身份证号!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                </a-row>
                <div class="submit-btn">
                    <a-button @click="handleSubmit" type="primary"
                        >提交</a-button
                    >
                </div>
            </a-form>
            <a-form v-if="editStatus && visible" :form="form2">
                <a-row>
                    <a-col :span="12">
                        <a-form-item label="姓名" v-bind="layoutStyle">
                            <a-input
                                v-decorator="[
                                    'name',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写名称!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="手机号" v-bind="layoutStyle">
                            <a-input
                                v-decorator="[
                                    'mobile',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写手机号!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>

                    <a-col :span="12">
                        <a-form-item label="顺序" v-bind="layoutStyle">
                            <a-input
                                type="number"
                                min="1"
                                v-decorator="[
                                    'sort',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写顺序!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="用户部门" v-bind="layoutStyle">
                            <a-input v-decorator="['department']" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="身份证号" v-bind="layoutStyle">
                            <a-input
                                v-decorator="[
                                    'idCard',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请填写身份证号!'
                                            }
                                        ]
                                    }
                                ]"
                            />
                        </a-form-item>
                    </a-col>
                </a-row>
                <div class="submit-btn">
                    <a-button @click="updateAccount" type="primary"
                        >修改</a-button
                    >
                </div>
            </a-form>
        </a-drawer>

        <a-modal
            title="权限列表"
            v-model="authVisible"
            @ok="updateAuth"
            @cancel="cancelAuth"
        >
            <ul>
                <li class="account-info">
                    <label class="auto-label" for="">用户：</label>
                    <span class="f14">{{ authAccount.name }}</span>
                </li>
                <li class="account-info">
                    <label class="auto-label" for="">部门：</label>
                    <span class="f14">{{ authAccount.organizationName }}</span>
                </li>
                <li class="account-info">
                    <label class="auto-label" for="">角色：</label>
                    <span class="f14">{{ authAccount.roleName }}</span>
                </li>
                <li class="account-info">
                    <label class="auto-label" for="">权限列表：</label>
                    <span class="f14" v-show="!authList.length">无</span>
                    <ul v-show="authList.length">
                        <li
                            :key="auth.id"
                            v-for="auth in authList"
                            class="auth-list"
                        >
                            <a-checkbox
                                :checked="auth.checked"
                                class="menu-top"
                                @change="setChecked(auth.id, 'menu')"
                                >{{ auth.name }}</a-checkbox
                            >

                            <div>
                                <div
                                    class="menu-two-item"
                                    :key="authTwo.id"
                                    v-for="authTwo in auth.childMenuSourceList"
                                >
                                    <a-checkbox
                                        :checked="authTwo.checked"
                                        class="menu-two-top"
                                        @change="
                                            setChecked(authTwo.id, 'twoMenu')
                                        "
                                        >{{ authTwo.name }}</a-checkbox
                                    >

                                    <span>
                                        <span
                                            class="operation-item"
                                            :key="authMain.id"
                                            v-for="authMain in authTwo.operationList"
                                        >
                                            <a-checkbox
                                                :checked="authMain.checked"
                                                @change="
                                                    changeAuth(authMain.id)
                                                "
                                                >{{
                                                    authMain.operationName
                                                }}</a-checkbox
                                            >
                                        </span>
                                    </span>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </a-modal>
    </div>
</template>

<script>
import axios from "axios";
import { getAllTree } from "@/api/yhgl/regions";
import { getList } from "@/api/yhgl/organization";
import {
    getAccountList,
    addAccount,
    enableAccount,
    disableAccount,
    updateAccount,
    getAuthDetail,
    authSave
} from "@/api/yhgl/account";
import { getAll } from "@/api/yhgl/role";

const columns = [
    {
        title: "姓名",
        dataIndex: "name",
        scopedSlots: { customRender: "name" }
    },
    {
        title: "手机号",
        dataIndex: "mobile",
        scopedSlots: { customRender: "mobile" }
    },
    {
        title: "单位",
        dataIndex: "organizationName",
        scopedSlots: { customRender: "organizationName" }
    },
    {
        title: "角色",
        dataIndex: "roleName",
        scopedSlots: { customRender: "roleName" }
    },
    {
        title: "状态",
        dataIndex: "status",
        scopedSlots: { customRender: "status" }
    },
    {
        title: "权限列表",
        dataIndex: "auth",
        scopedSlots: { customRender: "auth" }
    },
    {
        title: "操作",
        dataIndex: "operation",
        scopedSlots: { customRender: "operation" }
    }
];
export default {
    name: "UserManage",
    data() {
        return {
            columns,
            form: this.$form.createForm(this),
            form2: this.$form.createForm(this),
            formSearch: this.$form.createForm(this),
            editStatus: false,
            visible: false,
            title: "添加用户",
            layoutStyle: {
                labelCol: {
                    span: 7
                },
                wrapperCol: {
                    span: 16
                }
            },
            layoutStyle2: {
                labelCol: {
                    span: 7
                },
                wrapperCol: {
                    span: 17
                }
            },
            treeData: [],
            searchData: {
                orgList: [],
                roleList: []
            },
            formData: {
                orgList: [],
                roleList: []
            },
            accountList: [],
            search: {
                name: "",
                mobile: "",
                roleId: "",
                organizationId: "",
                status: "",
                pageNum: ""
            },
            pagination: {
                total: 40,
                current: 1,
                pageSize: 20
            },
            editItem: "",
            authVisible: false,
            authAccount: "",
            editAuthId: "",
            authList: [],
            operationIdList: [],
            operationCodeList: []
        };
    },
    beforeRouteEnter(to, from, next) {
        axios
            .all([getAllTree(true), getAccountList({})])
            .then(
                axios.spread(trees => {
                    next(vm => {
                        vm.$ajaxAfter(trees).then(() => {
                            vm.treeData = vm.setTree(
                                trees.data.data.allRegionTreeUnderLevel
                            );
                            vm.initAccountList();
                        });
                    });
                })
            )
            .catch(() => {
                next(vm => {
                    vm.$message.error("服务器出错！请稍后再试!");
                    vm.$Progress.fail();
                });
            });
    },
    mounted() {
        this.formSearch.setFieldsValue({
            status: ""
        });
    },
    methods: {
        //递归树状数据
        setTree(trees) {
            trees.map(tree => {
                Object.assign(tree, {
                    label: tree.name,
                    value: tree.id,
                    isLeaf: false
                });
                if (tree.children) {
                    this.setTree(tree.children);
                } else {
                    delete tree.children;
                }
            });
            return trees;
        },
        //点击搜索
        handleSearch() {
            let values = this.formSearch.getFieldsValue();
            Object.assign(this.search, {
                name: values.name || "",
                mobile: values.mobile || "",
                organizationId: values.organizationId || "",
                roleId: values.roleId || "",
                status: values.status,
                pageNum: 1
            });
            this.initAccountList();
        },
        //点击重置
        handleReset() {
            Object.assign(this.search, {
                name: "",
                mobile: "",
                organizationId: "",
                roleId: "",
                status: "",
                pageNum: 1
            });
            this.initAccountList();
        },
        //初始化用户数据
        initAccountList() {
            getAccountList(this.search)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.accountList = res.data.data.page.list;
                        Object.assign(this.pagination, {
                            total: res.data.data.page.total,
                            pageSize: res.data.data.page.pageSize,
                            pageNum: res.data.data.page.pageNum
                        });
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        addAccount() {
            this.title = "添加用户";
            this.editStatus = false;
            this.visible = true;
        },
        //获取单位列表数据
        onClose() {
            this.visible = false;
        },
        //提交修改或添加
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    if (values.password !== values.passwordConfirm) {
                        this.$message.error("2次密码输入不一致！");
                        return;
                    }

                    addAccount(values)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("创建用户成功！");
                                this.setAccount();
                                this.visible = false;
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        setAccount() {
            this.editStatus = false;
            getAccountList({ ...this.search })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.accountList = res.data.data.page.list;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        handleTableChange(page) {
            this.search.pageNum = page.current;
            this.initAccountList();
        },
        hanlderTree(value, region) {
            if (value.length) {
                this.getOrgList(value);
                this.getRoleList(region[region.length - 1].level);
            }
        },
        getOrgList(value, type) {
            getList({ regionId: value[value.length - 1] })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        if (res.data.data.organizations.list.length) {
                            if (type === "form") {
                                this.formData.orgList =
                                    res.data.data.organizations.list;
                            } else {
                                this.searchData.orgList =
                                    res.data.data.organizations.list;
                            }
                        }
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        getRoleList(level, type) {
            getAll()
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        if (type === "form") {
                            this.formData.roleList =
                                res.data.data.result[level];
                        } else {
                            this.searchData.roleList =
                                res.data.data.result[level];
                        }
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        //添加时选择行政区
        hanlderTreeAdd(value, region) {
            this.formData.orgList = [];
            this.formData.roleList = [];
            this.form.setFieldsValue({
                roleId: [],
                organizationId: []
            });
            if (value.length) {
                this.getOrgList(value, "form");
                this.getRoleList(region[region.length - 1].level, "form");
            }
        },
        edit(account) {
            this.editItem = account;
            this.title = account.name;
            this.visible = true;
            this.editStatus = true;
            setTimeout(() => {
                this.form2.setFieldsValue({
                    name: account.name,
                    mobile: account.mobile,
                    sort: account.sort || "",
                    department: account.department || "",
                    idCard: account.idCard || ""
                });
            }, 0);
        },
        updateAccount(e) {
            e.preventDefault();
            this.form2.validateFields((err, values) => {
                if (!err) {
                    values.id = this.editItem.id;
                    updateAccount(values)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("修改成功！");
                                this.setAccount();
                                this.visible = false;
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        start(ids) {
            enableAccount(ids)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("启用成功！");
                        this.setAccount();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        stop(ids) {
            disableAccount(ids)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("禁用成功！");
                        this.setAccount();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        showAuth(auth) {
            let account = this.$store.state.account;
            if (account.roleType === 1 && auth.id === account.id) {
                this.$message.error(
                    "系统管理员不可设置权限，已默认获取所有权限！"
                );
            } else {
                this.editAuthId = auth.id;
                getAuthDetail(auth.id)
                    .then(res => {
                        this.$ajaxAfter(res).then(() => {
                            this.authAccount = res.data.data.account;
                            this.authList = this.getChecked(
                                res.data.data.menuSourceList,
                                res.data.data.operationIdList
                            );
                            this.authVisible = true;
                        });
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                    });
            }
        },
        changeAuth(value) {
            let index = this.operationIdList.indexOf(value);
            if (index > -1) {
                this.operationIdList.splice(index, 1);
            } else {
                this.operationIdList.push(value);
            }

            this.getChecked(this.authList);
        },
        getChecked(menus, operationIdList) {
            let menuArr,
                idsArr = [],
                operationCodeList = [];
            operationIdList = operationIdList || this.operationIdList;
            menuArr = menus.map(menu => {
                let menuNum = 0;
                menu.checked = false;
                menu.childMenuSourceList.map(twoMenu => {
                    let twoMenuNum = 0;
                    twoMenu.checked = false;
                    twoMenu.operationList.map(opeAuth => {
                        opeAuth.checked = false;
                        if (operationIdList.indexOf(opeAuth.id) > -1) {
                            opeAuth.checked = true;
                            idsArr.push(opeAuth.id);
                            operationCodeList.push(opeAuth.operation);
                            twoMenuNum += 1;
                        }
                    });
                    if (twoMenuNum === twoMenu.operationList.length) {
                        twoMenu.checked = true;
                        menuNum += 1;
                    }
                });

                if (menuNum === menu.childMenuSourceList.length) {
                    menu.checked = true;
                }
                this.operationIdList = idsArr;
                this.operationCodeList = operationCodeList;
                return menu;
            });
            return menuArr;
        },
        setChecked(id, type) {
            switch (type) {
                case "menu":
                    this.authList.map(menu => {
                        if (menu.id === id) {
                            Object.assign(menu, { checked: !menu.checked });
                            menu.childMenuSourceList.map(twoMenu => {
                                Object.assign(twoMenu, {
                                    checked: menu.checked
                                });

                                twoMenu.operationList.map(opeAuth => {
                                    Object.assign(opeAuth, {
                                        checked: menu.checked
                                    });
                                    let index = this.operationIdList.indexOf(
                                        opeAuth.id
                                    );
                                    if (menu.checked) {
                                        if (index < 0) {
                                            this.operationIdList.push(
                                                opeAuth.id
                                            );
                                            this.operationCodeList.push(
                                                opeAuth.operation
                                            );
                                        }
                                    } else {
                                        if (index > -1) {
                                            this.operationIdList.splice(
                                                index,
                                                1
                                            );
                                            this.operationCodeList.splice(
                                                index,
                                                1
                                            );
                                        }
                                    }
                                });
                            });
                        }
                    });
                    break;
                case "twoMenu":
                    this.authList.map(menu => {
                        let menuNum = 0;
                        menu.childMenuSourceList.map(twoMenu => {
                            if (twoMenu.id === id) {
                                Object.assign(twoMenu, {
                                    checked: !twoMenu.checked
                                });
                                if (twoMenu.checked) {
                                    menuNum += 1;
                                } else {
                                    Object.assign(menu, { checked: false });
                                }

                                twoMenu.operationList.map(opeAuth => {
                                    Object.assign(opeAuth, {
                                        checked: twoMenu.checked
                                    });
                                    let index = this.operationIdList.indexOf(
                                        opeAuth.id
                                    );
                                    if (twoMenu.checked) {
                                        if (index < 0) {
                                            this.operationIdList.push(
                                                opeAuth.id
                                            );
                                            this.operationCodeList.push(
                                                opeAuth.operation
                                            );
                                        }
                                    } else {
                                        if (index > -1) {
                                            this.operationIdList.splice(
                                                index,
                                                1
                                            );
                                            this.operationCodeList.splice(
                                                index,
                                                1
                                            );
                                        }
                                    }
                                });
                            } else {
                                if (twoMenu.checked === true) {
                                    menuNum += 1;
                                }
                            }
                        });
                        if (menuNum === menu.childMenuSourceList.length) {
                            Object.assign(menu, { checked: true });
                        }
                    });
                    break;
            }
        },
        updateAuth() {
            let formData = [];
            this.operationIdList.map((id, index) => {
                formData.push({
                    operationId: id,
                    operation: this.operationCodeList[index]
                });
            });
            authSave({
                id: this.editAuthId,
                accountAuthorityList: formData
            })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("权限修改成功！");
                        this.authVisible = false;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        cancelAuth() {
            this.authVisible = false;
        }
    }
};
</script>

<style>
.search-box .ant-form-item-label {
    width: 60px;
    text-align: left;
}
</style>
<style scoped lang="less">
.f14 {
    font-size: 14px;
}
.search-box {
    .ant-form-item {
        display: inline-block;
        min-width: 280px;
    }

    .ant-select {
        width: 100%;
    }
}

.btns-group button {
    margin-right: 10px;
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

.ant-row > div {
    height: 63px;
}
.account-list {
    margin-top: 30px;
}
.editable-row-operations a {
    margin-right: 10px;
}
.account-info {
    margin-bottom: 10px;
}
.auto-label {
    font-size: 14px;
}
.auth-list {
    display: flex;
    align-items: center;
    border-bottom: 1px solid #e8e8e8;
    padding: 10px 0;
}
.menu-top {
    flex-shrink: 0;
    width: 120px;
}
.menu-two-item {
    margin: 10px 0;
}

.menu-two-item {
    margin: 10px 0;
    display: flex;
    align-items: center;
}
.menu-two-top {
    width: 110px;
    flex-shrink: 0;
}
.operation-item {
    line-height: 28px;
}
</style>
