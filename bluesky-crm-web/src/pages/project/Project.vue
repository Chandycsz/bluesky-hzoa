<template>
    <div>
        <el-button icon="el-icon-plus" type="primary" @click="addproject()">添加项目</el-button>
        <!-- <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
        >
            <el-menu-item index="1">处理中心</el-menu-item>
            <el-submenu index="2">
                <template slot="title">我的工作台</template>
                <el-menu-item index="2-1">选项1</el-menu-item>
                <el-menu-item index="2-2">选项2</el-menu-item>
                <el-menu-item index="2-3">选项3</el-menu-item>
                <el-submenu index="2-4">
                    <template slot="title">选项4</template>
                    <el-menu-item index="2-4-1">选项1</el-menu-item>
                    <el-menu-item index="2-4-2">选项2</el-menu-item>
                    <el-menu-item index="2-4-3">选项3</el-menu-item>
                </el-submenu>
            </el-submenu>
            <el-menu-item index="3" disabled>消息中心</el-menu-item>
            <el-menu-item index="4">
                <a href="https://www.ele.me" target="_blank">订单管理</a>
            </el-menu-item>
        </el-menu>-->
        <el-table
            :data="tableData"
            border
            height="400"
            class="table"
            ref="multipleTable"
            header-cell-class-name="table-header"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column label="项目编号" prop="pro_code" width="100" fixed></el-table-column>
            <el-table-column label="项目名称" prop="pro_name" width="100"></el-table-column>
            <el-table-column label="项目经理" prop="pro_manager" width="100"></el-table-column>
            <el-table-column label="项目成员" prop="pro_member"></el-table-column>
            <el-table-column label="项目开始时间" prop="pro_start_time" width="200"></el-table-column>
            <el-table-column label="项目结束时间" prop="pro_end_time" width="200"></el-table-column>
            <el-table-column label="备注" prop="statement" width="150"></el-table-column>
            <el-table-column label="操作" width="180" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button
                        type="text"
                        icon="el-icon-edit"
                        @click="handleEdit(scope.$index, scope.row)"
                    >编辑</el-button>
                    <el-button
                        type="text"
                        icon="el-icon-delete"
                        class="red"
                        @click="handleDelete(scope.$index, scope.row)"
                    >删除</el-button>
                    <el-button
                        type="text"
                        icon="el-icon-edit"
                        @click="handlehInfo(scope.$index, scope.row)"
                    >历史修改记录</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="edittable" :model="edittable" label-width="70px">
                <el-form-item label="项目编号">
                    <el-input v-model="edittable.pro_code" disabled></el-input>
                </el-form-item>
                <el-form-item label="项目名称">
                    <el-input v-model="edittable.pro_name"></el-input>
                </el-form-item>
                <el-form-item label="开始时间">
                    <el-date-picker
                        v-model="edittable.pro_start_time"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        :picker-options="pickerOptions"
                        value-format="yyyy-MM-dd HH:mm:ss"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-date-picker
                        v-model="edittable.pro_end_time"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        :picker-options="pickerOptions"
                        value-format="yyyy-MM-dd HH:mm:ss"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="项目经理">
                    <el-input v-model="edittable.pro_manager"></el-input>
                </el-form-item>
                <el-form-item label="参与人员">
                    <el-input v-model="edittable.pro_member"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="edittable.statement"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { getpro, deletepro, editpro } from "@/api/project/project";
export default {
    data() {
        return {
            tableData: [],
            multipleSelection: [],
            editVisible: false,
            edittable: [],
            idx: -1
        };
    },
    methods: {
        handleEdit(index, row) {
            this.idx = index;
            this.edittable = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$set(this.tableData, this.idx, this.edittable);
            console.log(this.edittable.pro_start_time);
            var values = {
                pro_code: this.edittable.pro_code,
                projectname: this.edittable.pro_name,
                starttime: this.edittable.pro_start_time,
                endtime: this.edittable.pro_end_time,
                projectmanager: this.edittable.pro_manager,
                member: this.edittable.pro_member,
                statement: this.edittable.statement
            };
            editpro(values)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("修改成功!");
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        handleDelete(index, row) {
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            }).then(() =>
                deletepro(row.pro_code)
                    .then(res => {
                        this.tableData.splice(index, 1);
                        this.$ajaxAfter(res).then(() => {});
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                    })
            );
        },
        handlehInfo(index, row) {
            localStorage.setItem("cur_pro_id", row.pro_id);
            this.$router.push({
                name: "pro_hinfo"
            });
        },
        addproject() {
            this.$router.push({
                name: "add_proinfo"
            });
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        }
    },
    mounted() {
        getpro()
            .then(res => {
                this.$ajaxAfter(res).then(() => {
                    this.tableData = res.data.data.list;
                });
            })
            .catch(() => {
                this.$message.error("服务器出错！请稍后再试!");
            });
    }
};
</script>
<style>
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
</style>