<template>
    <div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="项目名称">
                        <el-input v-model="form.projectname" style="width: 40%;"></el-input>
                    </el-form-item>
                    <el-form-item label="项目经理">
                        <el-select v-model="form.projectmanager" placeholder="请选择">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.label"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="参组成员">
                        <el-select v-model="form.member" multiple placeholder="请选择">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.label"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="开始日期">
                        <el-date-picker
                            v-model="form.starttime"
                            type="datetime"
                            placeholder="选择日期时间"
                            align="right"
                            :picker-options="pickerOptions"
                            value-format="yyyy-MM-dd HH:mm:ss"
                        ></el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-col :span="11">
                            <el-date-picker
                                type="date"
                                placeholder="选择日期"
                                v-model="form.date3"
                                value-format="yyyy-MM-dd"
                                style="width: 100%;"
                            ></el-date-picker>
                        </el-col>
                        <el-col class="line" :span="2">————</el-col>
                        <el-col :span="11">
                            <el-time-picker
                                placeholder="选择时间"
                                v-model="form.date4"
                                value-format="HH:mm:ss"
                                style="width: 100%;"
                            ></el-time-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="备注">
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
import { addpro } from "@/api/project/project";
export default {
    name: "baseform",
    data() {
        return {
            form: {
                projectname: "",
                projectmanager: "",
                member: [],
                starttime: "",
                date3: "",
                date4: ""
            },
            options: [
                {
                    value: "选项1",
                    label: "阿珍"
                },
                {
                    value: "选项2",
                    label: "双皮奶"
                },
                {
                    value: "选项3",
                    label: "蚵仔煎"
                },
                {
                    value: "选项4",
                    label: "龙须面"
                },
                {
                    value: "选项5",
                    label: "北京烤鸭"
                }
            ]
        };
    },
    methods: {
        onSubmit() {
            var values = {
                projectname: this.form.projectname,
                starttime: this.form.starttime,
                endtime: this.form.date3 + " " + this.form.date4,
                projectmanager: this.form.projectmanager,
                member: JSON.stringify(this.form.member),
                statement: this.form.statement
            };
            console.log(values.member);
            // let param = new URLSearchParams();
            // param.append("projectname", this.form.projectname);
            // param.append("starttime", this.form.date1 + " " + this.form.date2);
            // param.append("endtime", this.form.date3 + " " + this.form.date4);
            // param.append("projectmanager", this.form.projectmanager);
            // param.append("member", this.form.member);
            // param.append("statement", this.form.statement);
            addpro(values)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {});
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    }
    // this.$router.push("/add_proinfo");
};
</script>
