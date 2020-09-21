require("@babel/polyfill");
import Vue from "vue";
import App from "./App.vue";
import router from "@/router/router";
import store from "@/store/store";
import VueProgressBar from "vue-progressbar";
import axios from "axios";
import { userStatus } from "@/api/user";
import { configPage, configMenu } from "@/api/config";
import "./plugins/AntDesign";
Vue.config.productionTip = false;
import "ant-design-vue/dist/antd.less";
import "@/assets/global.css";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)
const progressBarConfig = {
    autoFinish: false,
    failedColor: "red" // 失败显示的颜色
};
import { Icon } from "ant-design-vue";
const IconFont = Icon.createFromIconfontCN({
    scriptUrl: import("@/assets/js/iconfont") // 在 iconfont.cn 上生成
});

Vue.use(VueProgressBar, progressBarConfig);
Vue.component("icon-font", IconFont);

router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    if (!store.state.appInit) {
        axios
            .all([configPage(), configMenu(), userStatus()])
            .then(
                axios.spread((configPageData, configMenuData, userStatus) => {
                    store.commit(
                        "setMenu",
                        configMenuData.data.data.menuSourceList
                    );

                    store
                        .dispatch(
                            "setConfig",
                            configPageData.data.data.pageSettingMap
                        )
                        .then(() => {
                            store.commit("setAppInit");
                        });

                    store.commit("setAccount", userStatus.data.data.account);
                    // if (to.meta.login) {
                    //     //需要登录的情况下判断是否有这个权限访问这个页面
                    //     let status = false;
                    //     if (to.meta.authorize) {
                    //         status = store.state.loginMenus.some(menu => {
                    //             return to.name === "new" || menu === to.name;
                    //         });
                    //         if (!status) {
                    //             router.push({ name: "Login" });
                    //             return false;
                    //         }
                    //     }
                    // }
                    window.less
                        .modifyVars(
                            //更换主题颜色要这么写
                            {
                                "@primary-color": store.getters.workTheme.value,
                                "@btn-primary-bg":
                                    store.getters.workTheme.value,
                                "@link-color": store.getters.workTheme.value
                            }
                        )
                        .then(() => {
                            next();
                        });
                })
            )
            .catch(err => {
                console.log(err);
            });
    } else {
        next();
    }
});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");

Vue.prototype.$ajaxAfter = function (res) {
    return new Promise((resolve, reject) => {
        if (res.data.success === "TRUE") {
            resolve();
        } else if (res.data.success === "UNLOGIN") {
            this.$message.error("您未登录或登录超时!").then(() => {
                this.$router.push({
                    name: "Login"
                });
            });
        } else if (res.data.success === "FORBIDDEN") {
            this.$router.push({
                name: "authority"
            });
        } else {
            this.$message.error(res.data.message);
            reject();
        }
    });
};
