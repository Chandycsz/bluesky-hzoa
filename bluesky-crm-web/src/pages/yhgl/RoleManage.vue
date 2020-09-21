<template>
    <div class="crm-main-cont">
        <h3 class="main-title">角色管理</h3>

        <div class="role-list-box" v-show="systemLevel === 1">
            <span class="role-title">省级角色</span>
            <ul class="role-list clearfix">
                <li :key="province.id" v-for="province in provinceList">
                    <a-input
                        :disabled="province.editStatus ? false : 'disabled'"
                        v-model="province.name"
                    ></a-input>
                    <a
                        class="edit-btn"
                        v-show="!province.editStatus"
                        href="javascript:;"
                        @click="handleEdit(province.id, 1)"
                        >编辑</a
                    >
                    <a
                        class="edit-btn"
                        v-show="!province.editStatus"
                        href="javascript:;"
                        @click="handleDelete(province.id, 1)"
                        >删除</a
                    >
                    <a
                        class="edit-btn"
                        v-show="province.editStatus"
                        href="javascript:;"
                        @click="handleUpdate(province.id, 1)"
                        >确定</a
                    >
                    <a
                        class="edit-btn"
                        v-show="province.editStatus"
                        href="javascript:;"
                        @click="handleCancel(province.id, 1)"
                        >取消</a
                    >
                </li>
                <li>
                    <a-input v-model="provinceText"></a-input>
                    <a
                        class="edit-btn"
                        href="javascript:;"
                        @click="handleAdd(1)"
                        >添加</a
                    >
                </li>
            </ul>
        </div>
        <div class="role-list-box" v-show="systemLevel < 3">
            <span class="role-title">市级角色</span>
            <ul class="role-list clearfix">
                <li :key="city.id" v-for="city in cityList">
                    <a-input
                        :disabled="city.editStatus ? false : 'disabled'"
                        v-model="city.name"
                    ></a-input>
                    <a
                        class="edit-btn"
                        v-show="!city.editStatus"
                        href="javascript:;"
                        @click="handleEdit(city.id, 2)"
                        >编辑</a
                    >
                    <a
                        class="edit-btn"
                        v-show="!city.editStatus"
                        href="javascript:;"
                        @click="handleDelete(city.id, 1)"
                        >删除</a
                    >
                    <a
                        class="edit-btn"
                        v-show="city.editStatus"
                        href="javascript:;"
                        @click="handleUpdate(city.id, 2)"
                        >确定</a
                    >
                    <a
                        class="edit-btn"
                        v-show="city.editStatus"
                        href="javascript:;"
                        @click="handleCancel(city.id, 2)"
                        >取消</a
                    >
                </li>
                <li>
                    <a-input v-model="cityText"></a-input>
                    <a
                        class="edit-btn"
                        href="javascript:;"
                        @click="handleAdd(2)"
                        >添加</a
                    >
                </li>
            </ul>
        </div>
        <div class="role-list-box">
            <span class="role-title">区级角色</span>
            <ul class="role-list clearfix">
                <li :key="district.id" v-for="district in districtList">
                    <a-input
                        :disabled="district.editStatus ? false : 'disabled'"
                        v-model="district.name"
                    ></a-input>
                    <a
                        class="edit-btn"
                        v-show="!district.editStatus"
                        href="javascript:;"
                        @click="handleEdit(district.id, 3)"
                        >编辑</a
                    >
                    <a
                        class="edit-btn"
                        v-show="!district.editStatus"
                        href="javascript:;"
                        @click="handleDelete(district.id, 1)"
                        >删除</a
                    >
                    <a
                        class="edit-btn"
                        v-show="district.editStatus"
                        href="javascript:;"
                        @click="handleUpdate(district.id, 3)"
                        >确定</a
                    >
                    <a
                        class="edit-btn"
                        v-show="district.editStatus"
                        href="javascript:;"
                        @click="handleCancel(district.id, 3)"
                        >取消</a
                    >
                </li>
                <li>
                    <a-input v-model="districtText"></a-input>
                    <a
                        class="edit-btn"
                        href="javascript:;"
                        @click="handleAdd(3)"
                        >添加</a
                    >
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import { getAll, addRole, deleteRole, updateRole } from "@/api/yhgl/role";

export default {
    name: "RoleManage",
    data() {
        return {
            provinceList: [],
            oldProvinceList: [],
            cityList: [],
            oldCityList: [],
            districtList: [],
            oldDistrictList: [],
            provinceText: "",
            cityText: "",
            districtText: "",
            systemLevel: 1
        };
    },
    beforeRouteEnter(to, from, next) {
        getAll()
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        vm.setData(res);
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
        getData() {
            getAll()
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.setData(res);
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        setData(res) {
            this.systemLevel = res.data.data.systemLevel;
            if (res.data.data.result[1]) {
                this.provinceList = res.data.data.result[1];
                this.oldProvinceList = JSON.parse(
                    JSON.stringify(res.data.data.result[1])
                );
            }

            if (res.data.data.result[2]) {
                this.cityList = res.data.data.result[2];
                this.oldCityList = JSON.parse(
                    JSON.stringify(res.data.data.result[2])
                );
            }

            if (res.data.data.result[3]) {
                this.districtList = res.data.data.result[3];
                this.oldDistrictList = JSON.parse(
                    JSON.stringify(res.data.data.result[3])
                );
            }
        },
        /**
         * 注意这里的level 1省级 2市级 3区级  以下都是一样的
         * @param level
         */
        handleAdd(level) {
            let name;

            switch (level) {
                case 1:
                    name = this.provinceText;
                    break;
                case 2:
                    name = this.cityText;
                    break;
                case 3:
                    name = this.districtText;
                    break;
            }
            if (!name) {
                this.$message.error("请填写后再进行添加！");
                return false;
            }
            addRole({
                name,
                level
            })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("添加成功！");
                        this.addSuccess(res.data.data.id, level);
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        addSuccess(id, level) {
            let list = [];
            switch (level) {
                case 1:
                    this.provinceList.push({ id: id, name: this.provinceText });
                    list = JSON.parse(JSON.stringify(this.provinceList));
                    this.oldProvinceList = list;
                    this.provinceText = "";
                    break;
                case 2:
                    this.cityList.push({ id: id, name: this.cityText });
                    list = JSON.parse(JSON.stringify(this.cityList));
                    this.oldCityList = list;
                    this.cityText = "";
                    break;
                case 3:
                    this.districtList.push({ id: id, name: this.districtText });
                    list = JSON.parse(JSON.stringify(this.districtList));
                    this.oldDistrictList = list;
                    this.districtText = "";
                    break;
            }
        },
        handleEdit(id, level) {
            let list = [];

            switch (level) {
                case 1:
                    list = [...this.provinceList];
                    break;
                case 2:
                    list = [...this.cityList];
                    break;
                case 3:
                    list = [...this.districtList];
                    break;
            }
            list.map((category, index) => {
                if (id === category.id) {
                    this.$set(list[index], "editStatus", true);
                    return false;
                }
            });
        },
        handleCancel(id, level) {
            let oldList = [],
                list = [];
            switch (level) {
                case 1:
                    oldList = [...this.oldProvinceList];
                    list = this.provinceList;
                    break;
                case 2:
                    oldList = [...this.oldCityList];
                    list = this.cityList;
                    break;
                case 3:
                    oldList = [...this.oldDistrictList];
                    list = this.districtList;
                    break;
            }
            oldList.map((category, index) => {
                if (id === category.id) {
                    this.$set(list, index, Object.assign({}, category));
                    return false;
                }
            });
        },
        handleDelete(id) {
            deleteRole(id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("删除成功！");
                        this.getData();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        handleUpdate(id, level) {
            let list = [];
            switch (level) {
                case 1:
                    list = [...this.provinceList];
                    break;
                case 2:
                    list = [...this.cityList];
                    break;
                case 3:
                    list = [...this.districtList];
                    break;
            }

            list.map(category => {
                if (id === category.id) {
                    updateRole({ id: category.id, name: category.name })
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("修改成功！");
                                this.getData();
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                    return false;
                }
            });
        }
    }
};
</script>

<style scoped lang="less">
.main-title {
    font-size: 14px;
    border-bottom: 1px solid #ccc;
    padding-bottom: 10px;
    margin-bottom: 30px;
}
.role-list-box {
    display: flex;
    margin-bottom: 30px;
    line-height: 32px;

    .role-title {
        font-size: 14px;
        margin-right: 30px;
    }
    .role-list {
        width: 80%;
        li {
            width: 23%;
            display: flex;
            float: left;
            margin-right: 2%;
            margin-bottom: 20px;
            padding: 0 10px;
        }
        .edit-btn {
            flex-shrink: 0;
            margin-left: 10px;
        }
    }
}
</style>
