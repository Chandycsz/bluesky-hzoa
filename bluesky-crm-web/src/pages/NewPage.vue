<template>
    <div class="crm-main-cont">
        <iframe v-if="url" :src="url" frameborder="0"></iframe>
    </div>
</template>

<script>
import { menuById } from "@/api/config";
export default {
    name: "NewPage",
    data() {
        return {
            url: ""
        };
    },
    watch: {
        $route(to) {
            menuById(to.query.id)
                .then(res => {
                    this.$ajaxAfter(res).then(() => {
                        this.url = res.data.data.menuSource.url;
                        document.title = res.data.data.menuSource.name;
                    });
                })
                .catch(() => {
                    this.$message.error("服务器出错！请稍后再试!");
                });
        }
    },
    beforeRouteEnter(to, from, next) {
        menuById(to.query.id)
            .then(res => {
                next(vm => {
                    vm.$ajaxAfter(res).then(() => {
                        vm.url = res.data.data.menuSource.url;
                        document.title = res.data.data.menuSource.name;
                    });
                });
            })
            .catch(() => {
                next(vm => {
                    vm.$message.error("服务器出错！请稍后再试!");
                    vm.$Progress.fail();
                });
            });
    }
};
</script>

<style scoped>
iframe {
    width: 100%;
    height: 80vh;
    min-height: 500px;
    max-height: 900px;
}
</style>
