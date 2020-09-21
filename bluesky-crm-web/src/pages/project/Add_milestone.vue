<template>
    <div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="关联项目">
                        <el-input v-model="form.studentname" style="width: 50%;"></el-input>
                    </el-form-item>
                    <el-form-item label="关联任务">
                        <el-input v-model="form.buildingname" style="width: 50%;"></el-input>
                    </el-form-item>
                    <el-form-item label="名称">
                        <el-select v-model="form.type" placeholder="请选择">
                            <el-option label="新里程碑" value="新里程碑"></el-option>
                            <el-option label="就里程碑" value="就里程碑"></el-option>
                            <el-option label="老里程碑" value="老里程碑"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input v-model="form.buildingname" style="width: 50%;"></el-input>
                    </el-form-item>
                    <el-form-item label="开始时间">
                        <el-date-picker
                            v-model="form.starttime"
                            type="datetime"
                            placeholder="选择日期时间"
                            align="right"
                            :picker-options="pickerOptions"
                        ></el-date-picker>
                        <!-- <el-col :span="11">
                            <el-date-picker
                                type="date"
                                placeholder="选择日期"
                                v-model="form.date1"
                                value-format="yyyy-MM-dd"
                                style="width: 50%;"
                            ></el-date-picker>
                        </el-col>
                        <el-col class="line" :span="2">-——</el-col>
                        <el-col :span="11">
                            <el-time-picker
                                placeholder="选择时间"
                                v-model="form.date2"
                                style="width: 100%;"
                            ></el-time-picker>
                        </el-col>-->
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-col :span="11">
                            <el-date-picker
                                type="date"
                                placeholder="选择日期"
                                v-model="form.date1"
                                value-format="yyyy-MM-dd"
                                style="width: 50%;"
                            ></el-date-picker>
                        </el-col>
                        <el-col class="line" :span="2">-——</el-col>
                        <el-col :span="11">
                            <el-time-picker
                                placeholder="选择时间"
                                v-model="form.date2"
                                style="width: 100%;"
                            ></el-time-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="负责人">
                        <el-input type="textarea" rows="5" v-model="form.statement"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "baseform",
    data() {
        return {
            form: {
                buildingname: "",
                studentname: "",
                date1: "",
                date2: "",
                type: "",
                textarea: ""
            }
        };
    },
    methods: {
        onSubmit() {
            // var d = this.form.date2;
            // d = d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
            // let param = new URLSearchParams();
            // param.append("type", this.form.type);
            // param.append("time", this.form.date1 + " " + d);
            // param.append("buildingname", this.form.buildingname);
            // param.append("studentname", this.form.studentname);
            // param.append("statement", this.form.statement);
            // this.axios({
            //     method: "post",
            //     url: "http://localhost:8080/SpringMybatisDemo/adddminfo",
            //     data: param
            // }).then(res => {
            //     this.$confirm("添加成功", "是否继续添加记录", {
            //         confirmButtonText: "是",
            //         cancelButtonText: "否",
            //         type: "warning"
            //     })
            //         .then(() => {
            //             this.$router.push("/adddminfo");
            //         })
            //         .catch(() => {
            //             this.$router.push("/mainpage");
            //         });
            // });
        }
    }
};
</script>