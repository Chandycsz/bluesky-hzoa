<template>
    <div class="crm-main-cont" @click="cancelColor">
        <a-form-item
            label="工作页logo"
            :label-col="{ span: 3 }"
            :wrapper-col="{ span: 12 }"
        >
            <a-upload
                accept="image/png, image/jpeg"
                :customRequest="customRequest"
                name="workLogo"
                listType="picture-card"
                class="avatar-uploader"
                :showUploadList="false"
                :beforeUpload="beforeUpload"
                @change="handleChange"
            >
                <img :src="imageDomain + workLogo.value" alt="工作页logo" />
            </a-upload>
        </a-form-item>
        <a-form-item
            label="主题颜色"
            :label-col="{ span: 3 }"
            :wrapper-col="{ span: 3 }"
        >
            <span
                @click.stop="handleShowColor"
                class="color-box"
                :style="{ backgroundColor: workTheme.value }"
            >
                <span v-show="show" @click.stop="() => {}">
                    <Sketch
                        class="color-picker"
                        :value="workTheme.value"
                        @input="setWorkTheme"
                    />
                </span>
            </span>
        </a-form-item>
    </div>
</template>

<script>
import { imgUpdate, updateById, configPage } from "@/api/config";
import { Sketch } from "vue-color";
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";
export default {
    name: "workManage",
    data() {
        return {
            show: false,
            timer: undefined
        };
    },
    components: {
        Sketch: Sketch
    },

    computed: {
        ...mapState(["imageDomain"]),
        ...mapGetters(["workLogo", "workTheme"])
    },
    beforeRouteEnter(to, from, next) {
        configPage()
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        vm.setPage(res.data.data.pageSettingMap);
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
        ...mapMutations(["setThemeColor", "setPage"]),
        ...mapActions(["setPageAction"]),
        cancelColor() {
            this.show = false;
        },
        handleShowColor() {
            this.show = true;
        },
        setWorkTheme(value) {
            if (this.timer) {
                clearTimeout(this.timer);
            }
            this.timer = setTimeout(() => {
                this.timer = undefined;
                let rgba = value.rgba,
                    rgbaStr =
                        "rgb(" + rgba.r + "," + rgba.g + "," + rgba.b + ")";
                updateById({ id: this.workTheme.id, value: rgbaStr })
                    .then(res => {
                        this.$ajaxAfter(res).then(() => {
                            this.$message.success("修改成功！");
                            this.setThemeColor(rgbaStr);
                            window.less.modifyVars(
                                //更换主题颜色要这么写
                                {
                                    "@primary-color": this.workTheme.value,
                                    "@btn-primary-bg": this.workTheme.value,
                                    "@link-color": this.workTheme.value
                                }
                            );
                        });
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                    });
            }, 600);
        },
        //覆盖默认上传事件
        customRequest(file) {
            let formData = new FormData();
            formData.append("file", file.file);
            imgUpdate({ id: this.workLogo.id, formData })
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
                this.loading = false;
            }
        },
        //上传图片之前验证
        beforeUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.error("Image must smaller than 2MB!");
            }
            return isLt2M;
        }
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
.crm-main-cont {
    padding-top: 50px !important;
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
.vc-sketch {
    line-height: 18px;
}
</style>
