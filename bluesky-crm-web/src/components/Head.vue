<template>
    <div
        id="crmHead"
        class="clearfix"
        v-if="workTheme"
        :style="{ 'background-color': workTheme.value }"
    >
        <div class="fr clearfix nav-right">
            <span style="font-size: 14px">
                <a-icon type="user" />
                {{ storeAccount.name }}
            </span>
            <a class="nav-right-item" href="javascript:;" @click="out">
                退出
            </a>
        </div>
    </div>
</template>

<script>
import { loginOut } from "@/api/user";
import { mapGetters } from "vuex";
export default {
    name: "CrmHead",
    computed: {
        ...mapGetters(["workTheme", "workLogo", "storeAccount"])
    },
    methods: {
        back() {
            this.$router.push({
                name: "SelectModule"
            });
        },
        out() {
            loginOut().then(res => {
                this.$ajaxAfter(res).then(() => {
                    this.$router.replace({
                        name: "Login"
                    });
                });
            });
        }
    }
};
</script>

<style scoped>
#crmHead {
    height: 60px;
    line-height: 60px;
    overflow: hidden;
    position: relative;
    background-color: #1890ff;
    padding: 0 20px;
    color: #fff;
}
.nav-right a {
    margin: 0 10px;
    color: #fff;
    font-size: 14px;
}
.login-img {
    max-height: 40px;
}
</style>
