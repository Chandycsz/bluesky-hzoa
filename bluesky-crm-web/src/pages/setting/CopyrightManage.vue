<template>
    <div class="crm-main-cont">
        <a-form :form="form" @submit="submitOrg">
            <a-form-item
                label="主办单位"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 7 }"
            >
                <a-input
                    placeholder="请填写主办单位"
                    v-decorator="[
                        'value',
                        {
                            rules: [
                                { required: true, message: '请填写主办单位!' }
                            ]
                        }
                    ]"
                />
                <a-button type="primary" html-type="submit">
                    修改
                </a-button>
            </a-form-item>
        </a-form>

        <a-form :form="form2" @submit="submitSup">
            <a-form-item
                label="技术支持单位"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 7 }"
            >
                <a-input
                    placeholder="请填写技术支持单位"
                    v-decorator="[
                        'value',
                        {
                            rules: [
                                {
                                    required: true,
                                    message: '请填写技术支持单位!'
                                }
                            ]
                        }
                    ]"
                />
                <a-button type="primary" html-type="submit">
                    修改
                </a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
import { updateById, configPage } from "@/api/config";
import { mapGetters } from "vuex";
export default {
    name: "CopyrightManage",
    computed: {
        ...mapGetters(["footOrganizer", "footSupport"])
    },
    data() {
        return {
            form: this.$form.createForm(this),
            form2: this.$form.createForm(this)
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
        submitOrg(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    this.setData(this.footOrganizer.id, values.value);
                }
            });
        },
        submitSup(e) {
            e.preventDefault();
            this.form2.validateFields((err, values) => {
                if (!err) {
                    this.setData(this.footSupport.id, values.value);
                }
            });
        },
        setData(id, value) {
            updateById({ id, value })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("修改成功！");
                        this.$store.commit("setFoot", { id, value });
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    },
    mounted() {
        this.form.setFieldsValue({
            value: this.footOrganizer.value
        });
        this.form2.setFieldsValue({
            value: this.footSupport.value
        });
    }
};
</script>

<style scoped>
.crm-main-cont {
    padding-top: 50px !important;
}
.ant-input {
    width: 200px;
    margin-right: 20px;
}
</style>
