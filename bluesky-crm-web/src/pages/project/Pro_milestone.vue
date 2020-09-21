<template>
    <div>
        <el-button @click="addmilestone()">添加里程碑</el-button>
        <el-table
            :data="tableData"
            border
            class="table"
            ref="multipleTable"
            header-cell-class-name="table-header"
            @selection-change="handleSelectionChange"
        >
            <el-table-column
                type="selection"
                width="55"
                align="center"
            ></el-table-column>
            <el-table-column label="序号" prop="id"></el-table-column>
            <el-table-column label="关联项目" prop="pro_id"></el-table-column>
            <el-table-column
                label="关联任务"
                prop="pro_task_id"
            ></el-table-column>
            <el-table-column label="名称" prop="name"></el-table-column>
            <el-table-column label="描述" prop="statement"></el-table-column>
            <el-table-column
                label="开始时间"
                prop="start_time"
            ></el-table-column>
            <el-table-column label="结束时间" prop="end_time"></el-table-column>
            <el-table-column label="负责人" prop="head"></el-table-column>
            <el-table-column label="状态" prop="status"></el-table-column>
            <el-table-column label="文件" prop="path"></el-table-column>
            <el-table-column label="操作" width="180" align="center">
                <template slot-scope="scope">
                    <el-button
                        type="text"
                        icon="el-icon-edit"
                        @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button
                    >
                    <el-button
                        type="text"
                        icon="el-icon-delete"
                        class="red"
                        @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button
                    >
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
    </div>
</template>

<script>
import { getpro, deletepro} from "@/api/project/milestone";
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
         handleDelete(index, row) {
            console.log(row.id);
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            }).then(() =>
                deletepro(row.id)
                    .then(res => {
                        this.tableData.splice(index, 1);
                        this.$ajaxAfter(res).then(() => {});
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                    })
            );
        },
        addmilestone() {
            this.$router.push({
                name: "add_milestone"
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
                    console.log(res.data.data.list);
                    this.tableData = res.data.data.list;
                    console.log(this.tableData);
                });
            })
            .catch(() => {
                this.$message.error("服务器出错！请稍后再试!");
            });
    }
};
</script>
