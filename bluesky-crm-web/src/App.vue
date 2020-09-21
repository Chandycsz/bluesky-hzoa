<template>
    <a-locale-provider :locale="zh_CN">
        <div class="pages-container">
            <transition name="fade">
                <router-view />
            </transition>
            <vue-progress-bar></vue-progress-bar>
        </div>
    </a-locale-provider>
</template>

<script>
import zh_CN from "ant-design-vue/lib/locale-provider/zh_CN";
import { userStatus } from "@/api/user";
export default {
    name: "app",
    data() {
        return {
            zh_CN,
            show: false
        };
    },
    created() {
        this.$Progress.start();
    },
    mounted() {
        this.$Progress.finish();
        this.$router.beforeEach((to, from, next) => {
            this.$Progress.start();
            if (to.meta.login) {
                //需要登录的情况下判断是否有这个权限访问这个页面
                let status = false;
                if (to.meta.authorize) {
                    status = this.$store.state.loginMenus.some(menu => {
                        return to.name === "new" || menu === to.name;
                    });
                    if (!status) {
                        this.$message.error("您无权查看此页面").then(() => {
                            this.$router.push({ name: "Login" });
                        });
                        this.$Progress.finish();
                        return false;
                    }
                }

                userStatus().then(res => {
                    if (res.data.success === "TRUE" && res.data.data.account) {
                        if (to.meta.title) {
                            document.title = to.meta.title;
                        }
                        this.$store.commit("setAccount", res.data.data.account);
                        next();
                    } else {
                        this.$router.push({ name: "Login" });
                    }
                });
                this.$Progress.finish();
            } else {
                if (to.meta.title) {
                    document.title = to.meta.title;
                }
                this.$Progress.finish();
                next();
            }
        });
        this.$router.afterEach(() => {
            this.$Progress.finish();
        });
    }
};
</script>
<style scoped>
.pages-container {
    height: 100%;
    min-height: 600px;
}
</style>
