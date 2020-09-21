<template>
    <div>
        <el-button icon="el-icon-plus" type="primary" @click="addmission()">添加任务</el-button>
        <el-button icon="el-icon-plus" type="primary" @click="connect()">关联任务</el-button>
        <el-table
            :data="tableData"
            border
            class="table"
            height="400"
            ref="multipleTable"
            header-cell-class-name="table-header"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column label="序号" prop="id" fixed></el-table-column>
            <el-table-column label="关联项目" prop="pro_id" fixed ></el-table-column>
            <el-table-column label="关联里程碑" prop="mile_id" fixed></el-table-column>
            <el-table-column label="标题" prop="headline"></el-table-column>
            <el-table-column label="优先级" prop="priority" width="100"></el-table-column>
            <el-table-column label="类型" prop="type" width="100"></el-table-column>
            <el-table-column label="描述" prop="statement"></el-table-column>
            <el-table-column label="状态" prop="status"></el-table-column>
            <el-table-column label="截至日期" prop="deadline" width="100"></el-table-column>
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
                </template>
            </el-table-column>
        </el-table>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="edittable" :model="edittable" label-width="70px">
                <el-form-item label="关联项目">
                    <el-input v-model="edittable.pro_id"></el-input>
                </el-form-item>
                <el-form-item label="关联任务">
                    <el-input v-model="edittable.pro_task_id"></el-input>
                </el-form-item>
                <el-form-item label="名称">
                    <el-input v-model="edittable.name"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="edittable.statement"></el-input>
                </el-form-item>
                <el-form-item label="开始时间">
                    <el-input v-model="edittable.start_time"></el-input>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-input v-model="edittable.end_time"></el-input>
                </el-form-item>
                <el-form-item label="负责人">
                    <el-input v-model="edittable.head"></el-input>
                </el-form-item>
                <el-form-item label="文件">
                    <el-input v-model="edittable.path"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 关联弹出框 -->
        <el-dialog title="编辑" :visible.sync="ctableVisible" width="50%">
            <el-form ref="ctable" :model="ctable" label-width="100px">
                <el-form-item label="关联项目">
                    <el-select v-model="ctable.pro_id" placeholder="请选择">
                        <el-option
                            v-for="item in program"
                            :key="item.pro_id"
                            :label="item.pro_id "
                            :value="item.pro_id"
                        >
                            <span style="float: left">{{item.pro_id }}</span>
                            <span
                                style="float: right; color: #8492a6; font-size: 13px"
                            >{{ item.pro_name}}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="关联里程碑">
                    <el-select v-model="ctable.mile_id" placeholder="请选择">
                        <el-option
                            v-for="item in mile"
                            :key="item.id"
                            :label="item.id "
                            :value="item.id"
                        >
                            <span style="float: left">{{item.id }}</span>
                            <span
                                style="float: right; color: #8492a6; font-size: 13px"
                            >{{ item.name}}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="ctableVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveConnect">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { getmissioninfo, UpdateConnection } from "@/api/project/mission";
import { findAllProgramnamecode } from "@/api/project/project";
import { findAllmileidname } from "@/api/project/milestone";

export default {
    data() {
        return {
            tableData: [],
            multipleSelection: [],
            editVisible: false,
            edittable: [],
            idx: -1,
            ctable: [],
            ctableVisible: false,
            program: [],
            mile: []
        };
    },
    methods: {
        handleEdit(index, row) {
            this.idx = index;
            this.edittable = row;
            this.editVisible = true;
        },
        //  handleDelete(index, row) {
        //     console.log(row.id);
        //     this.$confirm("确定要删除吗？", "提示", {
        //         type: "warning"
        //     }).then(() =>
        //         deletepro(row.id)
        //             .then(res => {
        //                 this.tableData.splice(index, 1);
        //                 this.$ajaxAfter(res).then(() => {});
        //             })
        //             .catch(() => {
        //                 this.$message.error("服务器出错！请稍后再试!");
        //             })
        //     );
        // },
        addmission() {
            this.$router.push({
                name: "addmission"
            });
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        connect() {
            if (this.multipleSelection.length < 1) {
                this.$message.error("请先选中一条任务!");
            } else if (this.multipleSelection.length > 1) {
                this.$message.error("请取消多选!");
            } else {
                console.log(this.multipleSelection);
                this.ctableVisible = true;
            }
        },
        saveConnect() {
            this.ctableVisible = false;
            // this.$set(
            //     this.tableData,
            //     this.multipleSelection[0].id -1,
            //     this.multipleSelection[0]
            // );
            //  Object.assign(this.tableData, this.edittable);
            var values = {
                id: this.multipleSelection[0].id,
                pro_id: this.ctable.pro_id,
                mile_id: this.ctable.mile_id
            };
            console.log(values);
            UpdateConnection(values)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("关联成功!");

                        this.list();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        list() {
            getmissioninfo()
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        console.log("res" + res.data.data.list);
                        this.tableData = res.data.data.list;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    },
    mounted() {
        this.list();
        findAllProgramnamecode()
            .then(res => {
                this.$ajaxAfter(res).then(() => {
                    this.program = res.data.data.list;
                });
            })
            .catch(() => {
                this.$message.error("服务器出错！请稍后再试!");
            });

        findAllmileidname()
            .then(res => {
                this.$ajaxAfter(res).then(() => {
                    this.mile = res.data.data.list;
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
