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
            <el-table-column label="项目编号" prop="pro_id"></el-table-column>
            <el-table-column label="修改前内容" prop="content_before"></el-table-column>
            <el-table-column label="修改后内容" prop="content_after"></el-table-column>
            <el-table-column label="修改时间" prop="update_time"></el-table-column>
        </el-table>
    </div>
</template>

<script>
import { getphinfo, getphinfobyid } from "@/api/project/project";
export default {
    data() {
        return {
            tableData: []
        };
    },
    methods: {},
    mounted() {
        if (localStorage.getItem("cur_pro_id") == null) {
            getphinfo()
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
        } else {
            var pro_id = localStorage.getItem("cur_pro_id");
            getphinfobyid(pro_id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        console.log("返回表格值" + res.data.data.list);
                        this.tableData = res.data.data.list;
                        localStorage.removeItem("cur_pro_id");
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    }
};
</script>
