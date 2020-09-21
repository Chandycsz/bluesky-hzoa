<template>
    <div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="标题">
                        <el-input v-model="form.headline" style="width: 40%;"></el-input>
                    </el-form-item>
                    <el-form-item label="优先级">
                        <el-select v-model="form.priority" placeholder="请选择">
                            <el-option label="第一优先级" value="1"></el-option>
                            <el-option label="第二优先级" value="2"></el-option>
                            <el-option label="第三优先级" value="3"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="类型">
                        <el-select v-model="form.type" placeholder="请选择">
                            <el-option label="后端" value="1"></el-option>
                            <el-option label="前端" value="2"></el-option>
                            <el-option label="美工" value="3"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="截止日期">
                        <el-date-picker
                            v-model="form.deadline"
                            type="datetime"
                            placeholder="选择日期时间"
                            align="right"
                            :picker-options="pickerOptions"
                            value-format="yyyy-MM-dd HH:mm:ss"
                        ></el-date-picker>
                    </el-form-item>
                    <el-form-item label="说明">
                        <el-input type="textarea" rows="5" v-model="form.statement"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="cancel">提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
import { AddMission } from "@/api/project/mission";
export default {
    name: "baseform",
    data() {
        return {
            form: {
                headline: "",
                priority: "",
                type: "",
                statement: "",
                deadline: ""
            },

            textarea: ""
        };
    },
    methods: {
        onSubmit() {
            var values = {
                headline: this.form.headline,
                priority: this.form.priority,
                type: this.form.type,
                statement: this.form.statement,
                deadline: this.form.deadline
            };
            console.log(this.form.deadline);
            AddMission(values)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("添加完成!");
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    }
    // this.$router.push("/add_proinfo");
};
</script>
