<template>
    <div class="crm-main-cont" @click="cancelColor">
        <div class="set-form">
            <a-form-item
                label="首页logo"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 12 }"
            >
                <a-upload
                    accept="image/png, image/jpeg"
                    :customRequest="customRequest"
                    name="indexHeadLogo"
                    listType="picture-card"
                    class="avatar-uploader"
                    :showUploadList="false"
                    :beforeUpload="beforeUpload"
                    @change="handleChange"
                >
                    <img
                        :src="imageDomain + indexHeadLogo.value"
                        alt="首页logo"
                    />
                </a-upload>
            </a-form-item>
            <a-form-item
                label="信用查询背景"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 12 }"
            >
                <a-upload
                    accept="image/png, image/jpeg"
                    :customRequest="customRequest"
                    name="indexQueryBg"
                    listType="picture-card"
                    class="avatar-uploader"
                    :showUploadList="false"
                    :beforeUpload="beforeUpload"
                    @change="handleChange"
                >
                    <img
                        :src="imageDomain + indexQueryBg.value"
                        alt="信用查询背景"
                    />
                </a-upload>
            </a-form-item>

            <a-form-item
                label="信用查询背景颜色"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 12 }"
            >
                <span
                    @click.stop="handleShow('search')"
                    class="color-box"
                    :style="{ backgroundColor: indexQueryBgColor.value }"
                >
                    <span v-show="searchShow" @click.stop="() => {}">
                        <Sketch
                            class="color-picker"
                            :value="indexQueryBgColor.value"
                            @input="setQueryBgColor"
                        />
                    </span>
                </span>
            </a-form-item>

            <a-form-item
                label="功能模块样式"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 19 }"
            >
                <a-radio-group
                    name="radioGroup"
                    :defaultValue="indexAppType.value"
                    v-model="indexAppType.value"
                    @change="setAppStyle"
                >
                    <a-radio :value="'1'">样式一</a-radio>
                    <a-radio :value="'2'">样式二</a-radio>
                </a-radio-group>
            </a-form-item>

            <a-form-item
                label="功能模块配置"
                :label-col="{ span: 3 }"
                :wrapper-col="{ span: 19 }"
            >
                <a-row v-if="indexAppType.value === '1'">
                    <a-col
                        :key="card.id"
                        :span="6"
                        v-for="card in menuSourceList"
                    >
                        <a-card
                            class="set-module"
                            :style="{
                                'background-color': card.backgroundColor
                            }"
                            :bordered="false"
                        >
                            <icon-font
                                @click="handleSet(card)"
                                class="edit-btn"
                                type="icon-bianji"
                            ></icon-font>
                            <icon-font
                                :type="card.icon"
                                :style="{ color: card.iconColor }"
                            ></icon-font>
                            <span
                                class="icon-title"
                                :style="{ color: card.color }"
                                >{{ card.name }}</span
                            >
                        </a-card>
                    </a-col>
                </a-row>

                <div v-else>
                    <div
                        class="card-style2 set-module"
                        :key="card.id"
                        v-for="card in menuSourceList"
                    >
                        <icon-font
                            @click="handleSet(card)"
                            class="edit-btn"
                            type="icon-bianji"
                        ></icon-font>
                        <div
                            class="icon-box"
                            :style="{
                                'background-color': card.backgroundColor
                            }"
                        >
                            <icon-font
                                :type="card.icon"
                                :style="{ color: card.iconColor }"
                            ></icon-font>
                        </div>
                        {{ card.name }}
                    </div>
                </div>
            </a-form-item>
        </div>

        <div class="1">
            <a-drawer
                :title="colors.name + '配置'"
                placement="right"
                :closable="false"
                @close="handleOk"
                :visible="visible"
                :width="500"
            >
                <a-form-item
                    label="背景颜色"
                    :label-col="{ span: 5 }"
                    :wrapper-col="{ span: 19 }"
                >
                    <span
                        @click.stop="handleShow('bg')"
                        class="color-box"
                        :style="{ backgroundColor: colors.backgroundColor }"
                    >
                        <span
                            v-show="bgShow"
                            @click.stop="cancelColor"
                            class="drawer-bg-span"
                        ></span>
                        <span v-show="bgShow" @click.stop="() => {}">
                            <Sketch
                                class="color-picker picker-top"
                                :value="colors.backgroundColor"
                                @input="setBackgroundColor"
                            />
                        </span>
                    </span>
                </a-form-item>

                <a-form-item
                    class="select-icon"
                    label="图标"
                    :label-col="{ span: 5 }"
                    :wrapper-col="{ span: 19 }"
                >
                    <icon-font
                        :key="icon"
                        @click="handleIcon(icon)"
                        v-for="icon in iconList"
                        :class="colors.icon === icon ? ' icon-active' : ''"
                        :type="icon"
                    ></icon-font>
                </a-form-item>

                <a-form-item
                    label="图标颜色"
                    :label-col="{ span: 5 }"
                    :wrapper-col="{ span: 19 }"
                    v-show="indexAppType.value === '1'"
                >
                    <span
                        @click.stop="handleShow('icon')"
                        class="color-box"
                        :style="{ backgroundColor: colors.iconColor }"
                    >
                        <span
                            v-show="iconShow"
                            @click.stop="cancelColor"
                            class="drawer-bg-span"
                        ></span>
                        <span v-show="iconShow" @click.stop="() => {}">
                            <Sketch
                                class="color-picker"
                                :value="colors.iconColor"
                                @input="setIconColor"
                            />
                        </span>
                    </span>
                </a-form-item>

                <a-form-item
                    label="文字颜色"
                    :label-col="{ span: 5 }"
                    :wrapper-col="{ span: 19 }"
                    v-show="indexAppType.value === '1'"
                >
                    <span
                        @click.stop="handleShow('font')"
                        class="color-box"
                        :style="{ backgroundColor: colors.color }"
                    >
                        <span
                            v-show="fontShow"
                            @click.stop="cancelColor"
                            class="drawer-bg-span"
                        ></span>
                        <span v-show="fontShow" @click.stop="() => {}">
                            <Sketch
                                class="color-picker"
                                :value="colors.color"
                                @input="setFontColor"
                            />
                        </span>
                    </span>
                </a-form-item>

                <a-form-item :wrapper-col="{ span: 19, offset: 5 }">
                    <a-button type="primary" size="large" @click="handleUpdate"
                        >修改</a-button
                    >
                </a-form-item>
            </a-drawer>
        </div>
    </div>
</template>

<script>
import { imgUpdate, setMenuModule, configPage, updateById } from "@/api/config";
import { Sketch } from "vue-color";
import { mapState, mapGetters, mapActions, mapMutations } from "vuex";
export default {
    name: "IndexManage",
    computed: {
        ...mapState(["imageDomain", "menuSourceList"]),
        ...mapGetters([
            "indexHeadLogo",
            "indexQueryBg",
            "indexQueryBgColor",
            "indexAppType"
        ])
    },
    components: {
        Sketch: Sketch
    },
    data() {
        return {
            iconList: [
                "icon-bianji",
                "icon-tool",
                "icon-jiangli",
                "icon-jiangli1",
                "icon-jishijihechaxun",
                "icon-dangan1",
                "icon-pingjia",
                "icon-gongju",
                "icon-dangan"
            ],
            visible: false,
            logoUrl: "http://www.jhcredit.gov.cn:80/sso//static/image/nw02.png",
            bgUrl:
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554884702455&di=108c7022a9505614d5aef9669ea9f9a8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F038b61856dd5df532f875520f69cc2e.jpg",
            colors: {
                id: "",
                name: "",
                icon: "",
                backgroundColor: "",
                iconColor: "",
                color: ""
            },
            searchShow: false,
            bgShow: false,
            iconShow: false,
            fontShow: false,
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
        ...mapMutations(["setQueryColor"]),
        ...mapActions(["setMenuAction", "setPageAction"]),
        cancelColor() {
            this.searchShow = false;
            this.bgShow = false;
            this.iconShow = false;
            this.fontShow = false;
        },
        handleShow(type) {
            switch (type) {
                case "search":
                    this.searchShow = true;
                    break;
                case "bg":
                    this.bgShow = true;
                    break;
                case "icon":
                    this.iconShow = true;
                    break;
                case "font":
                    this.fontShow = true;
                    break;
            }
        },
        setBackgroundColor(value) {
            if (this.timer) {
                clearTimeout(this.timer);
            }
            this.timer = setTimeout(() => {
                this.colors.backgroundColor = this.getRgb(value.rgba);
            }, 600);
        },
        setIconColor(value) {
            this.timer = setTimeout(() => {
                this.colors.iconColor = this.getRgb(value.rgba);
            }, 600);
        },
        setFontColor(value) {
            this.timer = setTimeout(() => {
                this.colors.color = this.getRgb(value.rgba);
            }, 600);
        },
        //点击编辑模块设置
        handleSet(module) {
            this.visible = true;
            this.colors.id = module.id;
            this.colors.name = module.name || "";
            this.colors.icon = module.icon || "";
            this.colors.backgroundColor = module.backgroundColor || "";
            this.colors.iconColor = module.iconColor || "";
            this.colors.color = module.color || "";
        },
        handleUpdate() {
            let obj = {
                ...this.colors
            };
            setMenuModule(obj)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("修改成功！");
                        this.setMenuAction();
                        this.handleOk();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        },
        //设置时选择icon
        handleIcon(icon) {
            this.colors.icon = icon;
        },
        showModal() {
            this.visible = true;
        },
        handleOk() {
            this.visible = false;
        },
        //上传图片
        customRequest(file) {
            let formData = new FormData(),
                id = this.indexQueryBg.id;
            formData.append("file", file.file);
            if (file.filename === "indexHeadLogo") {
                id = this.indexHeadLogo.id;
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
        handleChange(info) {
            if (info.file.status === "uploading") {
                this.loading = true;
                return;
            }
            if (info.file.status === "done") {
                this.loading = false;
            }
        },
        beforeUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.error("Image must smaller than 2MB!");
            }
            return isLt2M;
        },
        //设置背景颜色
        setQueryBgColor(value) {
            if (this.timer) {
                clearTimeout(this.timer);
            }
            this.timer = setTimeout(() => {
                this.timer = undefined;
                let rgbStr = this.getRgb(value.rgba);
                updateById({ id: this.indexQueryBgColor.id, value: rgbStr })
                    .then(res => {
                        this.$ajaxAfter(res).then(() => {
                            this.$message.success("修改成功！");
                            this.setQueryColor(rgbStr);
                        });
                    })
                    .catch(() => {
                        this.$message.error("服务器出错！请稍后再试!");
                    });
            }, 600);
        },
        getRgb(rgb) {
            return "rgb(" + rgb.r + "," + rgb.g + "," + rgb.b + ")";
        },
        setAppStyle() {
            updateById({
                id: this.indexAppType.id,
                value: this.indexAppType.value
            })
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.$message.success("切换成功！");
                        this.setPageAction();
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    }
};
</script>

<style scoped lang="less">
.submit-btn {
    width: 100px;
}
.crm-main-cont img {
    max-width: 330px;
}
.ant-card {
    margin-bottom: 25px;
    text-align: center;
    width: 90%;
    border-radius: 5px;
    padding: 10px 0;
    position: relative;
    border: 1px solid #e8e8e8;

    .anticon {
        font-size: 40px;
        width: 50px;
        height: 50px;
        margin: 0 auto 15px;
        display: block;
        transition: 0.4s;

        &:before {
            line-height: 1;
        }
        &.icon-gongju {
            position: relative;
            left: -3px;
        }
    }
}
.set-module {
    .edit-btn {
        font-size: 24px;
        position: absolute;
        right: 10px;
        top: 10px;
        width: 24px;
        height: 24px;
        opacity: 0;
        cursor: pointer;
    }
    &:hover {
        .edit-btn {
            opacity: 1;
        }
    }
}
.icon-title {
    font-size: 14px;
}
.select-icon {
    max-height: 250px;
    overflow-y: auto;

    .anticon {
        font-size: 30px;
        line-height: 30px;
        padding: 10px;
        border: 1px solid #ccc;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
        margin: 0 10px 10px 0;
        display: inline-block;
        cursor: pointer;
        transition: 0.3s;

        &:hover,
        &.icon-active {
            color: #1890ff;
            border: 1px solid #1890ff;
        }
    }
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
.drawer-bg-span {
    position: fixed;
    display: block;
    z-index: 1;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
}
.picker-top {
    top: 0;
}
.vc-sketch {
    line-height: 18px;
}
.card-style2 {
    width: 155px;
    height: 185px;
    padding-top: 25px;
    background-color: #f0f0f0;
    margin-right: 30px;
    font-size: 16px;
    color: #333;
    text-align: center;
    position: relative;
    display: inline-block;

    &:hover .icon-box {
        transform: scale(1);
    }

    .icon-box {
        transition: 0.3s;
        width: 85px;
        height: 85px;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        border-radius: 50%;
        margin: 0 auto 15px;
        transform: scale(0.9);

        .anticon {
            font-size: 40px;
            padding-top: 22px;
        }
    }
}
</style>
