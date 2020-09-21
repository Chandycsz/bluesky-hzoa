<template>
    <div class="crm-main-cont">
        <div class="type-box">
            <span>顶级系统行政区：</span>
            <a-radio-group v-model="type">
                <a-radio :value="1">省级</a-radio>
                <a-radio :value="2">市级</a-radio>
                <a-radio :value="3">区级</a-radio>
            </a-radio-group>
            <a-button type="primary" @click="handleUpdate">确定</a-button>
        </div>

        <a-row>
            <a-col :span="8">
                <div class="category-list">
                    <div
                        class="title"
                        :style="{ 'background-color': workTheme.value }"
                    >
                        省级类目
                    </div>
                    <a-spin :spinning="provinceSpin">
                        <div class="list">
                            <div class="empty" v-show="!provinceList.length">
                                无省级类目
                            </div>
                            <draggable
                                v-model="provinceList"
                                handle=".handle"
                                scrollSensitivity="100"
                                animation="150"
                            >
                                <div
                                    class="category"
                                    :class="
                                        provinceId === province.id
                                            ? 'active'
                                            : ''
                                    "
                                    v-for="province in provinceList"
                                    :key="province.id"
                                    @click="showCityCategory(province.id)"
                                >
                                    <span>{{ province.name }}</span>
                                    <a-dropdown class="action">
                                        <a class="ant-dropdown-link" href="#">
                                            编辑 <a-icon type="down" />
                                        </a>
                                        <a-menu slot="overlay">
                                            <a-menu-item
                                                @click="
                                                    showModal(
                                                        'province',
                                                        province
                                                    )
                                                "
                                            >
                                                <a href="javascript:">更名</a>
                                            </a-menu-item>
                                            <a-menu-item
                                                @click="
                                                    deleteCategory(
                                                        'province',
                                                        province.id
                                                    )
                                                "
                                            >
                                                <a href="javascript:">删除</a>
                                            </a-menu-item>
                                        </a-menu>
                                    </a-dropdown>
                                </div>
                            </draggable>
                        </div>
                    </a-spin>
                    <div class="action-bar">
                        <span>
                            <a-button
                                type="primary"
                                class="button"
                                @click="showModal('province')"
                                >添加省级</a-button
                            >
                        </span>
                    </div>
                </div>
            </a-col>
            <a-col :span="8">
                <div class="category-list" v-if="provinceId">
                    <div
                        class="title"
                        :style="{ 'background-color': workTheme.value }"
                    >
                        市级类目
                    </div>
                    <a-spin :spinning="citySpin">
                        <div class="list">
                            <div class="empty" v-show="!cityList.length">
                                无市级类目
                            </div>
                            <draggable
                                v-model="cityList"
                                handle=".handle"
                                scrollSensitivity="100"
                                animation="150"
                            >
                                <div
                                    class="category"
                                    :class="cityId === city.id ? 'active' : ''"
                                    v-for="city in cityList"
                                    :key="city.id"
                                    @click="showDistrictCategory(city.id)"
                                >
                                    <span>{{ city.name }}</span>
                                    <a-dropdown class="action">
                                        <a class="ant-dropdown-link" href="#">
                                            编辑 <a-icon type="down" />
                                        </a>
                                        <a-menu slot="overlay">
                                            <a-menu-item
                                                @click="showModal('city', city)"
                                            >
                                                <a href="javascript:">更名</a>
                                            </a-menu-item>
                                            <a-menu-item
                                                @click="
                                                    deleteCategory(
                                                        'city',
                                                        city.id
                                                    )
                                                "
                                            >
                                                <a href="javascript:">删除</a>
                                            </a-menu-item>
                                        </a-menu>
                                    </a-dropdown>
                                </div>
                            </draggable>
                        </div>
                    </a-spin>
                    <div class="action-bar">
                        <span>
                            <a-button
                                type="primary"
                                class="button"
                                @click="showModal('city')"
                                >添加市级</a-button
                            >
                        </span>
                    </div>
                </div>
            </a-col>
            <a-col :span="8">
                <div class="category-list" v-if="cityId">
                    <div
                        class="title"
                        :style="{ 'background-color': workTheme.value }"
                    >
                        区级类目
                    </div>
                    <a-spin :spinning="districtSpin">
                        <div class="list">
                            <div class="empty" v-show="!districtList.length">
                                无区级类目
                            </div>
                            <draggable
                                v-model="districtList"
                                handle=".handle"
                                scrollSensitivity="100"
                                animation="150"
                            >
                                <div
                                    class="category"
                                    v-for="district in districtList"
                                    :key="district.id"
                                >
                                    <span>{{ district.name }}</span>
                                    <a-dropdown class="action">
                                        <a class="ant-dropdown-link" href="#">
                                            编辑 <a-icon type="down" />
                                        </a>
                                        <a-menu slot="overlay">
                                            <a-menu-item
                                                @click="
                                                    showModal(
                                                        'district',
                                                        district
                                                    )
                                                "
                                            >
                                                <a href="javascript:">更名</a>
                                            </a-menu-item>
                                            <a-menu-item
                                                @click="
                                                    deleteCategory(
                                                        'district',
                                                        district.id
                                                    )
                                                "
                                            >
                                                <a href="javascript:">删除</a>
                                            </a-menu-item>
                                        </a-menu>
                                    </a-dropdown>
                                </div>
                            </draggable>
                        </div>
                    </a-spin>
                    <div class="action-bar">
                        <span>
                            <a-button
                                type="primary"
                                class="button"
                                @click="showModal('district')"
                                >添加区级</a-button
                            >
                        </span>
                    </div>
                </div>
            </a-col>
        </a-row>
        <a-modal
            :title="modal.title"
            :visible="modal.visible"
            @ok="handleOk"
            @cancel="handleCancel"
            :confirmLoading="confirmLoading"
        >
            <div class="modal-row">
                <a-form-item
                    :label="modal.label + '级名称'"
                    :label-col="{ span: 5 }"
                    :wrapper-col="{ span: 14 }"
                >
                    <a-input
                        class="add-input"
                        maxlength="20"
                        v-model.trim="modal.name"
                    />
                    <span class="c999 ml10">限20字</span>
                </a-form-item>
                <a-form-item
                    :label="modal.label + '级编码'"
                    :label-col="{ span: 5 }"
                    :wrapper-col="{ span: 14 }"
                >
                    <a-input
                        class="add-input"
                        maxlength="20"
                        v-model.trim="modal.code"
                    />
                </a-form-item>
            </div>
        </a-modal>
    </div>
</template>

<script>
import draggable from "vuedraggable";
import {
    getLevel,
    findChildren,
    addCategory,
    updateCategory,
    updateLevel,
    deleteCategory
} from "@/api/yhgl/regions";
export default {
    name: "RegionManage",
    components: {
        draggable
    },
    computed: {
        workTheme() {
            return this.$store.getters.workTheme;
        }
    },
    data() {
        return {
            type: "",
            modal: {
                id: "",
                title: "添加省份",
                labelName: "省级名称",
                labelCode: "省级编码",
                name: null,
                visible: false,
                confirmLoading: false,
                status: "add",
                code: "",
                parentId: 0
            },
            confirmLoading: false,
            level: "province", //默认省级
            provinceId: null,
            cityId: null,
            provinceSpin: false,
            citySpin: false,
            districtSpin: false,
            provinceList: [],
            cityList: [],
            districtList: []
        };
    },
    beforeRouteEnter(to, from, next) {
        //先获取列表，如果列表为空则不获取系统级别
        findChildren(0)
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        if (res.data.data.children.length) {
                            getLevel()
                                .then(getlevel => {
                                    vm.$ajaxAfter(getlevel).then(() => {
                                        vm.provinceList =
                                            res.data.data.children;
                                        vm.type =
                                            getlevel.data.data.systemLevel;
                                    });
                                })
                                .catch(() => {
                                    vm.$message.error(
                                        "服务器出错！请稍后再试!"
                                    );
                                    vm.$Progress.fail();
                                });
                        } else {
                            vm.provinceList = res.data.data.children;
                            
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
        /**
         * 添加或修改 弹窗
         * @param type      类型 省市区
         * @param category  传入的item，代表是修改
         */
        showModal(type, category) {
            let title,
                level = "省",
                parentId = 0,
                id = "",
                name = "",
                code = "",
                status = "add";
            this.modal.visible = true;
            this.level = type;

            switch (type) {
                case "province":
                    this.level = "province";
                    title = category ? "更名省级" : "添加省级";
                    break;
                case "city":
                    this.level = "city";
                    title = category ? "更名市级" : "添加市级";
                    level = "市";
                    parentId = this.provinceId;
                    break;
                case "district":
                    this.level = "district";
                    title = category ? "更名区级" : "添加区级";
                    level = "区";
                    parentId = this.cityId;
                    break;
            }
            if (category) {
                status = "edit";
                parentId = category.parentId;
                name = category.name;
                id = category.id;
                code = category.code;
            }
            this.modal = {
                ...this.modal,
                id: id,
                visible: true,
                name: name,
                code: code,
                title: title,
                label: level,
                status: status,
                parentId: parentId
            };
        },
        //设置系统级别，当没有数据时，不可保存
        handleUpdate() {
            if (this.provinceList.length) {
                updateLevel(this.type)
                    .then(res => {
                        if (res.data.success === "TRUE") {
                            this.$message.success("设置成功!");
                        } else {
                            this.$message.error(res.data.message);
                        }
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                    });
            } else {
                this.$message.error("请先设置省级类目再进行设置系统级别！");
            }
        },
        //弹窗点击确定，按状态进行操作
        handleOk() {
            if (!this.modal.name) {
                this.$message.error("请填写" + this.modal.label + "名称！");
                return;
            }
            if (!this.modal.code) {
                this.$message.error("请填写" + this.modal.label + "编码！");
                return;
            }
            this.confirmLoading = true;
            if (this.modal.status === "add") {
                this.addCategory();
            } else {
                this.editCategory();
            }
        },
        addCategory() {
            addCategory(this.modal).then(res => {
                this.$ajaxAfter(res)
                    .then(() => {
                        this.$message.success("添加成功！");
                        this.getList(this.modal.parentId);
                        this.confirmLoading = false;
                        this.modal.visible = false;
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                        this.confirmLoading = false;
                    });
            });
        },
        editCategory() {
            updateCategory(this.modal)
                .then(res => {
                    this.$ajaxAfter(res)
                        .then(() => {
                            this.$message.success("修改成功！");
                            this.getList(this.modal.parentId);
                            this.confirmLoading = false;
                            this.modal.visible = false;
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                            this.confirmLoading = false;
                        });
                })
                .catch(() => {
                    this.confirmLoading = false;
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        getList(id) {
            this.confirmLoading = true;
            findChildren(id)
                .then(res => {
                    if (res.data.success === "TRUE") {
                        this.confirmLoading = false;
                        switch (this.level) {
                            case "province":
                                this.provinceList = res.data.data.children;
                                break;
                            case "city":
                                this.citySpin = false;
                                this.cityList = res.data.data.children;
                                this.districtList = [];
                                break;
                            case "district":
                                this.districtSpin = false;
                                this.districtList = res.data.data.children;
                                break;
                        }
                    } else {
                        this.confirmLoading = false;
                        this.$message.error(res.data.message);
                    }
                })
                .catch(() => {
                    this.confirmLoading = false;
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        handleCancel() {
            this.modal.visible = false;
        },
        showCityCategory(categoryId) {
            this.citySpin = true;
            this.level = "city";
            this.provinceId = categoryId;
            this.getList(this.provinceId);
        },
        showDistrictCategory(categoryId) {
            this.districtSpin = true;
            this.level = "district";
            this.cityId = categoryId;
            this.getList(this.cityId);
        },
        deleteCategory(level, categoryId) {
            let vm = this,
                levelName =
                    level === "province"
                        ? "省级"
                        : level === "city"
                        ? "市级"
                        : "区级";
            this.$Modal.confirm({
                title: "删除",
                content: "确定删除该" + levelName + "吗？",
                centered: true,
                onOk() {
                    return new Promise(resolve => {
                        deleteCategory(categoryId).then(res => {
                            resolve();
                            if (res.data.success === "TRUE") {
                                vm.$message.success("删除成功！");
                                switch (level) {
                                    case "province":
                                        vm.level = "province";
                                        vm.getList(0);
                                        break;
                                    case "city":
                                        vm.showCityCategory(vm.provinceId);
                                        break;
                                    case "district":
                                        vm.showDistrictCategory(vm.cityId);
                                        break;
                                }
                            } else {
                                vm.$message.error(res.data.message);
                            }
                        });
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
            padding: 10px;
            border-bottom: 1px solid #eee;
            cursor: pointer;
            .action {
                float: right;
            }
            .icon-move {
                font-size: 12px;
                margin-right: 10px;
                cursor: move;
            }
        }
        .active {
            background: #e6f7ff;
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
        }
    }
}
.add-input {
    width: 200px;
    margin-right: 20px;
}
</style>
