<template>
    <div class="crm-main-cont">
        <a-tabs :defaultActiveKey="defaultActiveKey" @change="callback">
            <a-tab-pane tab="省级角色" key="1" :pagination="false">
                <a-table
                    :columns="columns"
                    :dataSource="provinceList"
                    rowKey="id"
                    bordered
                >
                    <template slot="auth" slot-scope="text">
                        <div
                            class="auth-box clearfix"
                            :key="auth.id"
                            v-for="auth in text"
                        >
                            <div class="fl mr25">{{ auth.name }} -</div>
                            <div class="fl">
                                <div
                                    :key="childAuth.id"
                                    v-for="childAuth in auth.childMenuSourceList"
                                >
                                    <div class="fl mr25">
                                        {{ childAuth.name }} -
                                    </div>

                                    <div
                                        :key="secAuth.id"
                                        class="fl"
                                        v-for="(secAuth,
                                        secIndex) in childAuth.operationList"
                                    >
                                        {{ secAuth.operationName }}
                                        {{
                                            secIndex &lt;
                                            childAuth.operationList.length - 1
                                                ? "、"
                                                : ""
                                        }}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                    <template slot="operation" slot-scope="text, record">
                        <div class="editable-row-operations">
                            <a @click="() => showAuth(record)">修改</a>
                        </div>
                    </template>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="市级角色" key="2" forceRender :pagination="false">
                <a-table
                    :columns="columns"
                    :dataSource="cityList"
                    rowKey="id"
                    bordered
                >
                    <template slot="auth" slot-scope="text">
                        <div
                            class="auth-box clearfix"
                            :key="auth.id"
                            v-for="auth in text"
                        >
                            <div class="fl mr25">{{ auth.name }} -</div>
                            <div class="fl">
                                <div
                                    :key="childAuth.id"
                                    v-for="childAuth in auth.childMenuSourceList"
                                >
                                    <div class="fl mr25">
                                        {{ childAuth.name }} -
                                    </div>

                                    <div
                                        :key="secAuth.id"
                                        class="fl"
                                        v-for="(secAuth,
                                        secIndex) in childAuth.operationList"
                                    >
                                        {{ secAuth.operationName }}
                                        {{
                                            secIndex &lt; childAuth.operationList.length - 1
                                                ? "、"
                                                : ""
                                        }}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                    <template slot="operation" slot-scope="text, record">
                        <div class="editable-row-operations">
                            <a @click="() => showAuth(record)">修改</a>
                        </div>
                    </template>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="区级角色" key="3">
                <a-table
                    :columns="columns"
                    :dataSource="districtList"
                    rowKey="id"
                    bordered
                    :pagination="false"
                >
                    <template slot="auth" slot-scope="text">
                        <div
                            class="auth-box clearfix"
                            :key="auth.id"
                            v-for="auth in text"
                        >
                            <div class="fl mr25">{{ auth.name }} -</div>
                            <div class="fl">
                                <div
                                    :key="childAuth.id"
                                    v-for="childAuth in auth.childMenuSourceList"
                                >
                                    <div class="fl mr25">
                                        {{ childAuth.name }} -
                                    </div>

                                    <div
                                        class="fl"
                                        :key="secAuth.id"
                                        v-for="(secAuth,
                                        secIndex) in childAuth.operationList"
                                    >
                                        {{ secAuth.operationName }}
                                        {{
                                            secIndex &lt;
                                            childAuth.operationList.length - 1
                                                ? "、"
                                                : ""
                                        }}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                    <template slot="operation" slot-scope="text, record">
                        <div class="editable-row-operations">
                            <a @click="() => showAuth(record)">修改</a>
                        </div>
                    </template>
                </a-table>
            </a-tab-pane>
        </a-tabs>

        <a-modal
            title="权限列表"
            v-model="authVisible"
            @ok="updateAuth"
            @cancel="cancelAuth"
        >
            <ul>
                <li class="account-info">
                    <label class="auto-label" for="">角色名称：</label>
                    <span class="f14">{{ editAuth.name }}</span>
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
                                    :key="authTwo.id"
                                    class="menu-two-item"
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
import { getListByLevel } from "@/api/yhgl/role";
import { getAuthDetail, authSave } from "@/api/yhgl/role";
import { mapGetters } from "vuex";

const columns = [
    {
        title: "角色名称",
        dataIndex: "name",
        width: 250,
        scopedSlots: { customRender: "name" }
    },
    {
        title: "权限",
        className: "auth",
        dataIndex: "auth",
        scopedSlots: { customRender: "auth" }
    },
    {
        title: "操作",
        width: 150,
        dataIndex: "operation",
        scopedSlots: { customRender: "operation" }
    }
];

export default {
    name: "AuthManage",
    data() {
        return {
            columns,
            defaultActiveKey: 1,
            key: 1,
            provinceList: [],
            cityList: [],
            districtList: [],
            editItem: "",
            authVisible: false,
            authAccount: "",
            editAuth: "",
            authList: [],
            operationIdList: [],
            operationCodeList: []
        };
    },
    computed: {
        ...mapGetters(["storeAccount"])
    },
    beforeRouteEnter(to, from, next) {
        getListByLevel(1)
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        res.data.data.roleList.map(auth => {
                            let arr = [];
                            arr = res.data.data.roleAuthorityDetailMap[auth.id];
                            arr && Object.assign(auth, { auth: arr });
                        });
                        vm.provinceList = res.data.data.roleList;
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
        callback(key) {
            this.key = key;
            getListByLevel(key)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        res.data.data.roleList.map(auth => {
                            let arr = [];
                            arr = res.data.data.roleAuthorityDetailMap[auth.id];
                            arr && Object.assign(auth, { auth: arr });
                        });
                        switch (parseInt(key)) {
                            case 1:
                                this.provinceList = res.data.data.roleList;
                                break;
                            case 2:
                                this.cityList = res.data.data.roleList;
                                break;
                            case 3:
                                this.districtList = res.data.data.roleList;
                                break;
                        }
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        showAuth(auth) {
            let account = this.storeAccount;
            if (account.roleType === 1 && auth.id === account.id) {
                this.$message.error(
                    "系统管理员不可设置权限，已默认获取所有权限！"
                );
            } else {
                this.editAuth = auth;
                getAuthDetail(auth.id)
                    .then(res => {
                        this.$ajaxAfter(res).then(() => {
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
                id: this.editAuth.id,
                accountAuthorityList: formData
            })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("权限修改成功！");
                        this.callback(this.key);
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

<style scoped>
.f14 {
    font-size: 14px;
}
.auth-box {
    display: flex;
    align-items: center;
    line-height: 30px;
    border-bottom: 1px solid #e8e8e8;
    padding-bottom: 10px;
    margin: 10px 0;
}
.auth-box:last-of-type {
    padding-bottom: 0;
    border-bottom: none;
}
.mr25 {
    margin-right: 25px;
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
