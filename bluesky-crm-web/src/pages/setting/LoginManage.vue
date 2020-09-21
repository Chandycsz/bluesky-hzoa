<template>
    <div class="crm-main-cont" @click="cancelColor">
        <a-form :form="form" @submit="pathUpdate">
            <a-form-item
                label="上传图片路径"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 12 }"
            >
                <a-input
                    :style="{ width: '268px' }"
                    v-decorator="[
                        'value',
                        {
                            rules: [
                                {
                                    required: true,
                                    message: '上传图片路径不能为空!'
                                }
                            ]
                        }
                    ]"
                />
                <a-button type="primary" html-type="submit" class="submit-btn">
                    修改
                </a-button>
            </a-form-item>
        </a-form>
        <a-form :form="systemNameForm" @submit="systemNameUpdate">
            <a-form-item
                label="系统名称"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 12 }"
            >
                <a-input
                    :style="{ width: '268px' }"
                    v-decorator="[
                        'value',
                        {
                            rules: [
                                { required: true, message: '系统名称不能为空!' }
                            ]
                        }
                    ]"
                />
                <a-button type="primary" html-type="submit" class="submit-btn">
                    修改
                </a-button>
            </a-form-item>
        </a-form>
        <a-form-item
            label="登录背景颜色"
            :label-col="{ span: 3 }"
            :wrapper-col="{ span: 12 }"
        >
            <span
                @click.stop="handleShow"
                class="color-box"
                :style="{ backgroundColor: loginBgColor.value }"
            >
                <span v-show="colorShow" @click.stop="() => {}">
                    <Sketch
                        class="color-picker"
                        :value="loginBgColor.value"
                        @input="setLoginBgColor"
                    />
                </span>
            </span>
        </a-form-item>
        <a-form-item
            label="登录页logo"
            :label-col="{ span: 3 }"
            :wrapper-col="{ span: 12 }"
        >
            <a-upload
                accept="image/png, image/jpeg"
                :customRequest="customRequest"
                name="loginLogo"
                listType="picture-card"
                class="avatar-uploader"
                :showUploadList="false"
                :beforeUpload="beforeUpload"
                @change="handleChange"
            >
                <img
                    v-if="loginLogo.value"
                    :src="imageDomain + loginLogo.value"
                    alt="登录页logo"
                />
            </a-upload>
        </a-form-item>
        <a-form-item
            label="登录页背景"
            :label-col="{ span: 3 }"
            :wrapper-col="{ span: 12 }"
        >
            <a-upload
                accept="image/png, image/jpeg"
                :customRequest="customRequest"
                name="loginBg"
                listType="picture-card"
                class="avatar-uploader"
                :showUploadList="false"
                :beforeUpload="beforeUpload"
                @change="handleChange"
            >
                <img :src="imageDomain + loginBg.value" alt="登录页背景" />
            </a-upload>
        </a-form-item>
        <a-form :form="form2" @submit="loginTypeUpdate">
            <a-form-item
                label="登录方式"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 15 }"
            >
                <a-checkbox-group
                    v-decorator="[
                        'value',
                        { rules: [{ validator: loginTypeRule }] }
                    ]"
                >
                    <a-checkbox value="captcha">验证码登录</a-checkbox>
                    <a-checkbox value="password">密码登录</a-checkbox>
                    <a-checkbox value="wechat">微信登录</a-checkbox>
                </a-checkbox-group>
                <a-button type="primary" html-type="submit" class="submit-btn">
                    修改
                </a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
import { defaultPath, imgUpdate, updateById, configPage } from "@/api/config";
import { Sketch } from "vue-color";
import { mapGetters, mapState, mapActions } from "vuex";
export default {
    name: "LoginMange",
    components: {
        Sketch
    },
    computed: {
        ...mapState(["imageDomain"]),
        ...mapGetters([
            "defaultConfig",
            "systemName",
            "loginLogo",
            "loginType",
            "loginBg",
            "loginBgColor"
        ])
    },
    data() {
        return {
            form: this.$form.createForm(this),
            form2: this.$form.createForm(this),
            systemNameForm: this.$form.createForm(this),
            colorShow: false,
            timer: undefined
        };
    },
    beforeRouteEnter(to, from, next) {
        configPage()
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        vm.$store.commit(
                            "setPage",
                            res.data.data.pageSettingMap
                        );
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
        ...mapActions(["setPageAction"]),
        cancelColor() {
            this.colorShow = false;
        },
        handleShow() {
            this.colorShow = true;
        },
        //覆盖默认上传事件
        customRequest(file) {
            let formData = new FormData(),
                id = this.loginBg.id;
            formData.append("file", file.file);
            if (file.filename === "loginLogo") {
                id = this.loginLogo.id;
            }
            imgUpdate({ id: id, formData })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("上传成功！");
                        this.setPageAction();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        //切换图片
        handleChange(info) {
            if (info.file.status === "uploading") {
                this.loading = true;
                return;
            }
            if (info.file.status === "done") {
                if (info.file.status === "uploading") {
                    this.loading = true;
                    return;
                }
                if (info.file.status === "done") {
                    this.loading = false;
                }
            }
        },
        //上传图片之前验证
        beforeUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.error("Image must smaller than 2MB!");
            }
            return isLt2M;
        },
        //设置上传路径
        pathUpdate(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    defaultPath(values)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("修改成功！");
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        //系统名称
        systemNameUpdate(e) {
            e.preventDefault();
            this.systemNameForm.validateFields((err, values) => {
                if (!err) {
                    Object.assign(values, { id: this.systemName.id });
                    updateById(values)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("修改成功！");
                                this.setPageAction();
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        //登录方式验证规则
        loginTypeRule(rule, value, callback) {
            if (!value.length) {
                callback("请选择登录方式！");
            }
            if (
                value.indexOf("password") === -1 &&
                value.indexOf("captcha") === -1
            ) {
                callback("密码登录或者验证码登录，至少选择一种登录方式！");
            }
            callback();
        },
        //修改登录方式
        loginTypeUpdate(e) {
            e.preventDefault();
            this.form2.validateFields((err, values) => {
                if (!err) {
                    values.value = values.value.join(",");
                    Object.assign(values, { id: this.loginType.id });
                    updateById(values)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                this.$message.success("修改成功！");
                                this.setPageAction();
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        //设置背景颜色
        setLoginBgColor(value) {
            if (this.timer) {
                clearTimeout(this.timer);
            }
            this.timer = setTimeout(() => {
                this.timer = undefined;
                let rgbStr =
                    "rgb(" +
                    value.rgba.r +
                    "," +
                    value.rgba.g +
                    "," +
                    value.rgba.b +
                    ")";
                updateById({ id: this.loginBgColor.id, value: rgbStr })
                    .then(res => {
                        this.$ajaxAfter(res).then(() => {
                            this.$message.success("修改成功！");
                            this.setPageAction();
                        });
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                    });
            }, 600);
        }
    },
    mounted() {
        this.form.setFieldsValue({
            value: this.defaultConfig.value
        });
        this.form2.setFieldsValue({
            value: this.loginType.value.split(",")
        });
        this.systemNameForm.setFieldsValue({
            value: this.systemName.value
        });
    }
};
</script>

<style scoped>
.submit-btn {
    margin-left: 20px;
}
.crm-main-cont img {
    max-width: 330px;
}
.color-box {
    width: 20px;
    height: 20px;
    display: inline-block;
    border: 1px solid #e8e8e8;
    vertical-align: top;
    margin-top: 9px;
    position: relative;
}
.color-picker {
    position: absolute;
    z-index: 11111;
    top: -150px;
    left: 30px;
}
</style>
