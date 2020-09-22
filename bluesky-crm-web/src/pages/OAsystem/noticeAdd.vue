<template>
    <div>
        <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
            <a-form @submit="handleSubmit" :form="form">
                <a-form-item
                    label="标题"
                    :labelCol="{lg: {span: 7}, sm: {span: 7}}"
                    :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
                >
                    <a-input
                        v-decorator="[
              'headline',
              {rules: [{ required: true, message: '请输入标题' }]}
            ]"
                        placeholder="设置该公告的标题"
                    />
                </a-form-item>
                <a-form-item
                    label="失效时间"
                    :labelCol="{lg: {span: 7}, sm: {span: 7}}"
                    :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
                >
                    <a-date-picker
                        v-decorator="[
                  'failureTime',
                  {
                    rules: [{ required: true, message: '请选择失效时间' }],
                  },
                ]"
                        format="YY-MM-DD HH:mm:ss "
                        placeholder="请选择失效日期"
                        style="width:100%"
                        show-time
                        :disabled-date="disabledDate"
                    />
                </a-form-item>
                <a-form-item
                    label="公告内容"
                    :labelCol="{lg: {span: 7}, sm: {span: 7}}"
                    :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
                >
                    <a-textarea
                        rows="4"
                        placeholder="请输入公告内容"
                        v-decorator="[
              'content',
              {rules: [{ required: true, message: '请输入公告内容' }]}
            ]"
                    />
                </a-form-item>
                <a-form-item
                    label="上传附件"
                    :labelCol="{lg: {span: 7}, sm: {span: 7}}"
                    :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
                >
                    <a-upload
                        :file-list="fileList"
                        :remove="handleRemove"
                        :before-upload="beforeUpload"
                        v-decorator="[
                  'file',
                  {
                    rules:[{validator:this.uploadRules}] 
                  },
                ]"
                    >
                        <a-button :disabled="fileList.length==1">
                            <a-icon type="upload" />选择文件
                        </a-button>
                    </a-upload>
                </a-form-item>
                <a-form-item
                    label="发送对象"
                    :labelCol="{lg: {span: 7}, sm: {span: 7}}"
                    :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
                    :required="false"
                >
                    <a-radio-group v-decorator="['target', { initialValue: 1 }]">
                        <a-radio :value="1">所有用户</a-radio>
                        <a-radio :value="2">部分公开</a-radio>
                    </a-radio-group>
                    <a-form-item v-show="form.getFieldValue('target') === 2">
                        <a-select mode="multiple">
                            <a-select-option value="4">多选添加</a-select-option>
                        </a-select>
                    </a-form-item>
                </a-form-item>
                <a-form-item :wrapperCol="{ span: 24 }" style="text-align: center">
                    <a-button htmlType="submit" type="primary">提交</a-button>
                    <a-button style="margin-left: 8px">保存</a-button>
                </a-form-item>
            </a-form>
        </a-card>
    </div>
</template>

<script>
import { AddNotice } from "@/api/oasystem/notice";
export default {
    data() {
        return {
            form: this.$form.createForm(this),
            fileList: []
        };
    },
    mounted() {
        console.log(AddNotice);
    },
    methods: {
        handleSubmit(e) {
            e.preventDefault();
            let formData = new FormData();
            if (this.fileList.length != 0) {
                formData.append("file", this.fileList[0]);
            }
            this.form.validateFields((err, values) => {
                if (!err) {
                    formData.append("headline", values.headline);
                    formData.append("failureTime", values.failureTime);
                    formData.append("content", values.content);
                    formData.append("target", values.target);
                    formData.append("creatorId", this.$store.state.account.id);
                    AddNotice(formData)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("提交成功!");
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        disabledDate(current) {
            return current < new Date().getTime() - 10 * 24 * 60 * 3600;
        },
        uploadRules(rule, val, callback) {
            // if (this.fileList.length == 0) {
            //     callback("请上传至少一张凭证图片！");
            // }
            callback();
        },
        beforeUpload(file) {
            const sizeOk = file.size < 1024 * 300;
            const typeOk = file.type === "image";
            this.fileList = [...this.fileList, file];
            return false;
        },
        handleRemove(file) {
            const index = this.fileList.indexOf(file);
            const newFileList = this.fileList.slice();
            newFileList.splice(index, 1);
            this.fileList = newFileList;
        }
    }
};
</script>
