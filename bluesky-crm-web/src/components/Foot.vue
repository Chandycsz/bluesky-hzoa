<template>
    <div id="foot">
        主办单位：{{ footOrganizer.value }}
        <span class="support">技术支持：{{ footSupport.value }}</span>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
    name: "Foot",
    computed: {
        ...mapGetters(["footOrganizer", "footSupport"])
    }
};
</script>

<style scoped>
#foot {
    border-top: 1px solid #eee;
    text-align: center;
    line-height: 60px;
}
#foot .support {
    margin-left: 20px;
}
</style>
