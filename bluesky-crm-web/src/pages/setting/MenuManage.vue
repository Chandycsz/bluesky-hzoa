<template>
    <div class="crm-main-cont">
        <a-row>
            <a-col :span="8">
                <div class="category-list">
                    <div
                        class="title"
                        :style="{ backgroundColor: workTheme.value }"
                    >
                        菜单资源
                    </div>
                    <a-spin :spinning="menuSpin">
                        <div class="list">
                            <div class="empty" v-show="!menuSourceList.length">
                                无菜单数据
                            </div>
                            <draggable
                                v-model="menuSourceList"
                                handle=".handle"
                                scrollSensitivity="100"
                                animation="150"
                                :sort="menuSort"
                            >
                                <div
                                    class="category"
                                    :class="menuId === menu.id ? 'active' : ''"
                                    v-for="menu in menuSourceList"
                                    :key="menu.id"
                                    @click="showTwoMenu(menu.id)"
                                >
                                    <div
                                        class="category-cont"
                                        v-show="!menu.editStatus"
                                    >
                                        <icon-font
                                            class="icon-move handle"
                                            v-show="menuSort"
                                            type="icon-move"
                                        ></icon-font>
                                        {{ menu.name }}
                                    </div>
                                    <div
                                        class="category-cont"
                                        v-show="menu.editStatus"
                                    >
                                        <icon-font
                                            class="icon-move handle"
                                            v-show="menuSort"
                                            type="icon-move"
                                        ></icon-font>
                                        <a-input v-model="menu.name"></a-input>
                                    </div>

                                    <div
                                        class="group-btns"
                                        :style="{ 'text-align': 'right' }"
                                        :class="
                                            menu.editStatus ? 'active-edit' : ''
                                        "
                                    >
                                        <a
                                            v-show="
                                                menu.editStatus &&
                                                    !menu.systemMenu
                                            "
                                            @click.stop="handleDeleteMenu(menu)"
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            删除
                                        </a>
                                        <a
                                            v-show="menu.editStatus"
                                            @click.stop="
                                                editMenuName(menu, 'menu')
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            修改
                                        </a>
                                        <a
                                            v-show="menu.editStatus"
                                            @click.stop="
                                                cancelMenuName(menu, 'menu')
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            取消
                                        </a>
                                        <a
                                            v-show="!menu.editStatus"
                                            @click.stop="setMenuName(menu)"
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            编辑
                                        </a>
                                    </div>
                                </div>
                            </draggable>
                        </div>
                    </a-spin>
                    <div class="action-bar">
                        <span v-show="!menuSort">
                            <a-button
                                type="primary"
                                class="button"
                                @click="showModal('menu')"
                                >添加菜单</a-button
                            >
                        </span>
                        <span v-show="!menuSort">
                            <a-button
                                type="primary"
                                class="button"
                                @click="sortHandler('menu')"
                                >排序</a-button
                            >
                        </span>
                        <span v-show="menuSort">
                            <a-button
                                type="primary"
                                class="button"
                                @click="completeSort('menu')"
                                >完成</a-button
                            >
                            <a-button
                                type="primary"
                                class="button"
                                @click="cancelSort('menu')"
                                >取消</a-button
                            >
                        </span>
                    </div>
                </div>
            </a-col>
            <a-col :span="8">
                <div class="category-list" v-if="menuId">
                    <div
                        class="title"
                        :style="{ backgroundColor: workTheme.value }"
                    >
                        子菜单
                    </div>
                    <a-spin :spinning="twoMenuSpin">
                        <div class="list two-menu-list">
                            <div class="empty" v-show="!twoMenuList.length">
                                无子级菜单
                            </div>
                            <draggable
                                v-model="twoMenuList"
                                handle=".handle"
                                scrollSensitivity="100"
                                animation="150"
                                :sort="twoMenuSort"
                            >
                                <div
                                    class="category"
                                    :class="
                                        twoMenuId === twoMenu.id ? 'active' : ''
                                    "
                                    v-for="twoMenu in twoMenuList"
                                    :key="twoMenu.id"
                                    @click="showOperation(twoMenu.id)"
                                >
                                    <div
                                        class="category-cont"
                                        v-show="!twoMenu.editStatus"
                                    >
                                        <icon-font
                                            class="icon-move handle"
                                            v-show="twoMenuSort"
                                            type="icon-move"
                                        ></icon-font>
                                        {{ twoMenu.name }}
                                    </div>
                                    <div
                                        class="category-cont"
                                        v-show="twoMenu.editStatus"
                                    >
                                        <icon-font
                                            class="icon-move handle"
                                            v-show="twoMenuSort"
                                            type="icon-move"
                                        ></icon-font>
                                        <span>{{ twoMenu.name }}</span>
                                    </div>

                                    <div
                                        class="group-btns"
                                        :style="{ 'text-align': 'right' }"
                                        :class="
                                            twoMenu.editStatus
                                                ? 'active-edit'
                                                : ''
                                        "
                                    >
                                        <a
                                            v-show="
                                                twoMenu.editStatus &&
                                                    !twoMenu.systemMenu
                                            "
                                            @click.stop="
                                                handleDeleteMenu(twoMenu)
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            删除
                                        </a>
                                        <a
                                            v-show="twoMenu.editStatus"
                                            @click.stop="
                                                editMenuName(twoMenu, 'twoMenu')
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            修改
                                        </a>
                                        <a
                                            v-show="twoMenu.editStatus"
                                            @click.stop="
                                                cancelMenuName(
                                                    twoMenu,
                                                    'twoMenu'
                                                )
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            取消
                                        </a>
                                        <a
                                            v-show="!twoMenu.editStatus"
                                            @click.stop="setMenuName(twoMenu)"
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            编辑
                                        </a>
                                    </div>
                                </div>
                            </draggable>
                        </div>
                    </a-spin>
                    <div class="action-bar">
                        <span v-show="!twoMenuSort">
                            <a-button
                                type="primary"
                                class="button"
                                @click="showModal('twoMenu')"
                                >添加菜单</a-button
                            >
                        </span>
                        <span v-show="!twoMenuSort">
                            <a-button
                                type="primary"
                                class="button"
                                @click="sortHandler('twoMenu')"
                                >排序</a-button
                            >
                        </span>
                        <span v-show="twoMenuSort">
                            <a-button
                                type="primary"
                                class="button"
                                @click="completeSort('twoMenu')"
                                >完成</a-button
                            >
                            <a-button
                                type="primary"
                                class="button"
                                @click="cancelSort('twoMenu')"
                                >取消</a-button
                            >
                        </span>
                    </div>
                </div>
            </a-col>
            <a-col :span="8">
                <div class="category-list" v-if="twoMenuId">
                    <div
                        class="title"
                        :style="{ backgroundColor: workTheme.value }"
                    >
                        操作
                    </div>
                    <a-spin :spinning="operationSpin">
                        <div class="list">
                            <div class="empty" v-show="!operationList.length">
                                无操作权限
                            </div>
                            <draggable
                                v-model="operationList"
                                handle=".handle"
                                scrollSensitivity="100"
                                animation="150"
                            >
                                <div
                                    class="category"
                                    v-for="operation in operationList"
                                    :key="operation.id"
                                >
                                    <div
                                        class="category-cont"
                                        v-show="!operation.editStatus"
                                    >
                                        {{ operation.operationName }}
                                    </div>
                                    <div
                                        class="category-cont"
                                        v-show="operation.editStatus"
                                    >
                                        <a-input
                                            v-model="operation.operationName"
                                        ></a-input>
                                    </div>

                                    <div
                                        class="group-btns"
                                        :style="{ 'text-align': 'right' }"
                                        :class="
                                            operation.editStatus
                                                ? 'active-edit'
                                                : ''
                                        "
                                    >
                                        <a
                                            v-show="
                                                operation.editStatus &&
                                                    !operation.systemMenu
                                            "
                                            @click.stop="
                                                handleDeleteOpe(operation)
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            删除
                                        </a>
                                        <a
                                            v-show="operation.editStatus"
                                            @click="
                                                editMenuName(
                                                    operation,
                                                    'operation'
                                                )
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            修改
                                        </a>
                                        <a
                                            v-show="operation.editStatus"
                                            @click="
                                                cancelMenuName(
                                                    operation,
                                                    'operation'
                                                )
                                            "
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            取消
                                        </a>
                                        <a
                                            v-show="!operation.editStatus"
                                            @click="setMenuName(operation)"
                                            class="ant-dropdown-link"
                                            href="#"
                                        >
                                            编辑
                                        </a>
                                    </div>
                                </div>
                            </draggable>
                        </div>
                    </a-spin>
                    <div class="action-bar">
                        <span>
                            <a-button
                                type="primary"
                                class="button"
                                @click="showOpeModal"
                                >添加操作权限</a-button
                            >
                        </span>
                    </div>
                </div>
            </a-col>
        </a-row>

        <a-modal
            :title="editItem.id ? '修改菜单' : '添加菜单'"
            v-model="visible"
            @ok="handleAddMenu"
            @cancel="handleCancel"
        >
            <a-form :form="form" layout="vertical">
                <a-row :gutter="16">
                    <a-col :span="12">
                        <a-form-item label="菜单名">
                            <a-input
                                v-decorator="[
                                    'name',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请输入菜单名'
                                            }
                                        ]
                                    }
                                ]"
                                placeholder="请输入菜单名"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col v-show="!editItem.id" :span="12">
                        <a-form-item label="菜单编码">
                            <a-input
                                v-decorator="[
                                    'code',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请输入菜单编码'
                                            }
                                        ]
                                    }
                                ]"
                                placeholder="请输入菜单编码"
                            />
                        </a-form-item>
                    </a-col>
                </a-row>
                <a-row :gutter="16" v-show="showUrl">
                    <a-col :span="16">
                        <a-form-item label="跳转链接">
                            <a-input
                                v-decorator="['url']"
                                style="width: 100%"
                                placeholder="请输入跳转链接"
                            />
                        </a-form-item>
                    </a-col>
                </a-row>
            </a-form>
        </a-modal>

        <a-modal
            title="添加操作权限"
            v-model="opeVisible"
            @ok="handleAddOpe"
            @cancel="handleCancel"
        >
            <a-form :form="form2" layout="vertical">
                <a-row :gutter="16">
                    <a-col :span="12">
                        <a-form-item label="操作项名称">
                            <a-input
                                v-decorator="[
                                    'operationName',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请输入操作项名称'
                                            }
                                        ]
                                    }
                                ]"
                                placeholder="请输入操作项名称"
                            />
                        </a-form-item>
                    </a-col>
                    <a-col :span="12">
                        <a-form-item label="操作项编码">
                            <a-input
                                v-decorator="[
                                    'operation',
                                    {
                                        rules: [
                                            {
                                                required: true,
                                                message: '请输入操作项编码'
                                            }
                                        ]
                                    }
                                ]"
                                placeholder="请输入操作项编码"
                            />
                        </a-form-item>
                    </a-col>
                </a-row>
            </a-form>
        </a-modal>
    </div>
</template>

<script>
import draggable from "vuedraggable";
import {
    getMenuById,
    updateMenu,
    updateOperation,
    menuSort,
    addMenu,
    deleteMenu,
    menuByParentId,
    addOperation,
    deleteOperation
} from "@/api/config";
import { mapGetters, mapActions } from "vuex";
export default {
    name: "MenuManage",
    components: {
        draggable
    },
    computed: {
        ...mapGetters(["workTheme"])
    },
    data() {
        return {
            form: this.$form.createForm(this),
            form2: this.$form.createForm(this),
            type: 1,
            confirmLoading: false,
            level: "province", //默认省级
            parenId: "",
            menuId: null,
            twoMenuId: null,
            menuSpin: false,
            twoMenuSpin: false,
            operationSpin: false,
            menuSourceList: [],
            oldMenuList: [],
            twoMenuList: [],
            oldTwoMenuList: [],
            operationList: [],
            oldOperationList: [],
            menuSort: false,
            twoMenuSort: false,
            operationSort: false,
            editItem: {
                id: ""
            }, //弹窗标题 ： 无id是添加菜单 ，有id是修改菜单
            visible: false,
            opeVisible: false,
            showUrl: true
        };
    },
    beforeRouteEnter(to, from, next) {
        menuByParentId(0)
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        vm.menuSourceList = res.data.data.menuSourceList;
                        vm.oldMenuList = JSON.parse(
                            JSON.stringify(res.data.data.menuSourceList)
                        );
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
        ...mapActions(["setMenuAction"]),
        setMenuName(menu) {
            this.$set(menu, "editStatus", true);
        },
        cancelMenuName(menu, type) {
            this.$set(menu, "editStatus", false);

            switch (type) {
                case "menu":
                    this.oldMenuList.map(oldMenu => {
                        if (oldMenu.id === menu.id) {
                            this.$set(menu, "name", oldMenu.name);
                            return false;
                        }
                    });
                    break;
                case "twoMenu":
                    this.oldTwoMenuList.map(oldTwoMenu => {
                        if (oldTwoMenu.id === menu.id) {
                            this.$set(menu, "name", oldTwoMenu.name);
                            return false;
                        }
                    });
                    break;
                case "operation":
                    this.oldOperationList.map(oldOpe => {
                        if (oldOpe.id === menu.id) {
                            this.$set(
                                menu,
                                "operationName",
                                oldOpe.operationName
                            );
                            return false;
                        }
                    });
                    break;
            }
        },
        editMenuName(menu, type) {
            switch (type) {
                case "menu":
                    this.setUpdateMenu(menu);
                    break;
                case "twoMenu":
                    Object.assign(this.editItem, menu);
                    this.showUrl = true;
                    this.visible = true;
                    setTimeout(() => {
                        this.form.setFieldsValue({
                            name: menu.name,
                            code: menu.code,
                            url: menu.url
                        });
                    }, 0);
                    break;
                case "operation":
                    this.setUpdateOpe(menu);
                    break;
            }
        },
        setUpdateOpe(menu) {
            updateOperation(menu)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("修改成功");
                        Object.assign(menu, { editStatus: false });
                        this.setMenuAction();
                    });
                })
                .catch(() => {
                    this.confirmLoading = false;
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        setUpdateMenu(menu) {
            updateMenu(menu)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("修改成功");
                        Object.assign(menu, { editStatus: false });
                        this.addMenuBefore(menu.parentId);
                        this.setMenuAction();
                    });
                })
                .catch(() => {
                    this.confirmLoading = false;
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        handleDeleteMenu(menu) {
            let vm = this;
            this.$Modal.confirm({
                title: "删除菜单",
                content: "是否要删除此菜单?",
                okText: "确认",
                okType: "danger",
                cancelText: "取消",
                onOk() {
                    deleteMenu(menu.id)
                        .then(res => {
                            vm.$ajaxAfter(res).then(() => {
                                vm.$message.success("删除成功！");
                                vm.addMenuBefore(menu.parentId);
                                if (menu.parentId === 0) {
                                    if (menu.id === vm.menuId) {
                                        vm.twoMenuList = [];
                                        vm.menuId = "";
                                        vm.twoMenuId = "";
                                        vm.operationList = [];
                                    }
                                } else {
                                    if (menu.id === vm.twoMenuId) {
                                        vm.twoMenuId = "";
                                        vm.operationList = [];
                                    }
                                }
                            });
                        })
                        .catch(() => {
                            vm.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        showTwoMenu(menuId) {
            this.twoMenuSpin = true;
            this.menuId = menuId;

            menuByParentId(menuId)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.twoMenuList = res.data.data.menuSourceList;
                        this.oldTwoMenuList = JSON.parse(
                            JSON.stringify(res.data.data.menuSourceList)
                        );
                        this.twoMenuSpin = false;
                        this.operationList = [];
                        this.twoMenuId = "";
                    });
                })
                .catch(() => {
                    this.twoMenuSpin = false;
                    this.menuId = "";
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        showOperation(twoMenuId) {
            this.operationSpin = true;
            this.twoMenuId = twoMenuId;
            getMenuById(twoMenuId)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.operationList = res.data.data.operationList;
                        this.oldOperationList = JSON.parse(
                            JSON.stringify(res.data.data.operationList)
                        );
                    });
                    this.operationSpin = false;
                })
                .catch(() => {
                    this.operationSpin = false;
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        sortHandler(type) {
            switch (type) {
                case "menu":
                    this.menuSort = true;
                    break;
                case "twoMenu":
                    this.twoMenuSort = true;
                    break;
                case "operation":
                    this.operationSort = true;
                    break;
            }
        },
        completeSort(type) {
            let arr = [];
            switch (type) {
                case "menu":
                    this.menuSourceList.map(menu => {
                        arr.push(menu.id);
                    });
                    break;
                case "twoMenu":
                    this.twoMenuList.map(menu => {
                        arr.push(menu.id);
                    });
                    break;
            }
            menuSort(arr.join(","))
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("排序成功！");
                        switch (type) {
                            case "menu":
                                this.menuSort = false;
                                break;
                            case "twoMenu":
                                this.twoMenuSort = false;
                                break;
                        }
                        this.setMenuAction();
                    });
                })
                .catch(() => {
                    this.operationSpin = false;
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        cancelSort(type) {
            switch (type) {
                case "menu":
                    this.menuSourceList = JSON.parse(
                        JSON.stringify(this.oldMenuList)
                    );
                    this.menuSort = false;
                    break;
                case "twoMenu":
                    this.twoMenuList = JSON.parse(
                        JSON.stringify(this.oldTwoMenuList)
                    );
                    this.twoMenuSort = false;
                    break;
            }
        },
        showModal(type) {
            this.visible = true;
            this.editItem = {
                id: ""
            };
            switch (type) {
                case "menu":
                    this.parentId = 0;
                    this.showUrl = false;
                    break;
                case "twoMenu":
                    this.parentId = this.menuId;
                    this.showUrl = true;
                    break;
            }
        },
        handleCancel() {
            this.form.resetFields();
            this.visible = false;
        },
        handleAddMenu(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    if (this.editItem.id) {
                        values.id = this.editItem.id;
                        updateMenu(values)
                            .then(res => {
                                this.$ajaxAfter(res).then(() => {
                                    this.$message.success("修改菜单成功！");
                                    this.form.resetFields();
                                    this.addMenuBefore(this.editItem.parentId);
                                });
                                this.visible = false;
                            })
                            .catch(() => {
                                this.$message.error("服务器出错！请稍后再试!");
                            });
                    } else {
                        Object.assign(values, {
                            parentId: this.parentId,
                            seq: this.menuSourceList.length
                        });
                        addMenu(values)
                            .then(res => {
                                this.$ajaxAfter(res).then(() => {
                                    this.$message.success("添加菜单成功！");
                                    this.visible = false;
                                    this.form.resetFields();
                                    this.addMenuBefore(this.parentId);
                                });
                            })
                            .catch(() => {
                                this.$message.error("服务器出错！请稍后再试!");
                            });
                    }
                }
            });
        },
        addMenuBefore(id) {
            menuByParentId(id)
                .then(res2 => {
                    this.$ajaxAfter(res2).then(() => {
                        if (id > 0) {
                            this.twoMenuList = res2.data.data.menuSourceList;
                            this.oldTwoMenuList = JSON.parse(
                                JSON.stringify(res2.data.data.menuSourceList)
                            );
                            this.setMenuAction();
                        } else {
                            this.menuSourceList = res2.data.data.menuSourceList;
                            this.oldMenuList = JSON.parse(
                                JSON.stringify(res2.data.data.menuSourceList)
                            );
                        }
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        showOpeModal() {
            this.opeVisible = true;
        },
        handleAddOpe(e) {
            e.preventDefault();
            this.form2.validateFields((err, values) => {
                if (!err) {
                    values.menuId = this.twoMenuId;
                    addOperation(values)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("添加成功！");
                                this.opeVisible = false;
                                this.form2.resetFields();
                                this.showOperation(this.twoMenuId);
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        handleDeleteOpe(operation) {
            let vm = this;
            this.$Modal.confirm({
                title: "删除操作项",
                content: "是否要删除此操作权限?",
                okText: "确认",
                okType: "danger",
                cancelText: "取消",
                onOk() {
                    deleteOperation({
                        id: operation.id,
                        menuId: vm.twoMenuId
                    })
                        .then(res => {
                            vm.$ajaxAfter(res).then(() => {
                                vm.$message.success("删除成功！");
                                vm.showOperation(vm.twoMenuId);
                            });
                        })
                        .catch(() => {
                            vm.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        }
    }
};
</script>

<style scoped lang="less">
.type-box {
    margin-bottom: 30px;
}

.category-list {
    border: 1px solid #ddd;
    margin-right: 20px;

    .title {
        padding-left: 10px;
        line-height: 40px;
        font-size: 14px;
        color: #fff;
    }

    .list {
        height: 60vh;
        overflow: auto;

        .empty {
            text-align: center;
            padding: 30px 0;
        }

        .category {
            border-bottom: 1px solid #eee;
            cursor: pointer;
            display: flex;
            justify-content: space-between;

            .action {
                float: right;
            }

            .icon-move {
                font-size: 12px;
                margin-right: 10px;
                cursor: move;
            }

            .ant-input {
                width: 200px;
            }

            .category-cont {
                padding: 10px;
                width: 60%;
            }
        }

        .active {
            background: #f3f3f3;

            span {
                font-weight: bold;
            }
        }
    }

    .action-bar {
        border-top: 1px solid #ddd;
        padding: 10px 0;
        text-align: center;

        .button {
            min-width: 120px;
            width: 30%;
            margin: 0 10px;
        }
    }
}

.group-btns {
    width: 70px;
}

.ant-dropdown-link {
    padding: 10px;
    display: inline-block;
    vertical-align: top;
}

.active-edit {
    flex-shrink: 0;

    .ant-dropdown-link {
        line-height: 32px;
    }

    &.group-btns {
        width: 140px;
        flex-shrink: 0;
    }
}
.two-menu-list .active-edit .ant-dropdown-link {
    line-height: 1;
}
</style>
