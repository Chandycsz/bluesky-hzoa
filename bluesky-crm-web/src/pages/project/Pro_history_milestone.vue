<template>
    <div>
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
            <el-table-column label="里程碑历史编号" prop="id"></el-table-column>
            <el-table-column label="里程碑编号" prop="mil_id"></el-table-column>
            <el-table-column label="修改前内容" prop="content_before"></el-table-column>
            <el-table-column label="修改后内容" prop="content_after"></el-table-column>
            <el-table-column label="修改时间" prop="update_time"></el-table-column>
        </el-table>
    </div>
</template>

<script>
// 调用接口获取后台项目历史信息
// eslint-disable-next-line no-unused-vars
import { gethistorypro } from "@/api/project/milestone";
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
        gethistorypro()
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
