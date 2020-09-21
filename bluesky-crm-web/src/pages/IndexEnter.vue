<template>
    <div id="indexEnter">
        <Header />
        <router-view></router-view>
        <Foot />
    </div>
</template>

<script>
import Header from "@/components/Head";
import Foot from "@/components/Foot";
export default {
    name: "IndexEnter",
    components: {
        Header,
        Foot
    }
};
</script>

<style scoped></style>
