<template>
    <div>
        <div class="pages-header">
            <CrmHead />
        </div>
        <a-layout id="components-layout-demo-top-side-2">
            <a-layout>
                <a-layout-sider
                    class="slider-menu"
                    :trigger="null"
                    v-model="collapsed"
                >
                    <div
                        v-if="workTheme"
                        class="shrink"
                        :class="collapsed ? 'shrink-now' : ''"
                        @click="toggleCollapsed"
                        :style="{ color: workTheme.value }"
                    >
                        <a-icon
                            :type="collapsed ? 'double-right' : 'double-left'"
                        />
                    </div>
                    <div class="left-menu">
                        <a-menu
                            :inlineCollapsed="collapsed"
                            mode="inline"
                            class="menu-box"
                            :defaultOpenKeys="defaultOpenKeys"
                            :selectedKeys="selectedKeys"
                        >
                            <a-sub-menu
                                v-for="menu in menuSourceList"
                                :key="menu.id"
                            >
                                <span slot="title">
                                    <icon-font
                                        :type="menu.icon"
                                        style="font-size: 14px;"
                                    ></icon-font>
                                    <span>{{ menu.name }}</span></span
                                >
                                <a-menu-item
                                    :key="twoMenu.code"
                                    v-for="twoMenu in menu.childMenuSourceList"
                                >
                                    <router-link
                                        v-if="twoMenu.systemMenu"
                                        :to="{
                                            name: twoMenu.code,
                                            params: { key: twoMenu.code }
                                        }"
                                        >{{ twoMenu.name }}</router-link
                                    >
                                    <router-link
                                        v-else
                                        :to="{
                                            path:
                                                '/crm/new/page/' + twoMenu.code,
                                            query: { id: twoMenu.id }
                                        }"
                                        >{{ twoMenu.name }}</router-link
                                    >
                                </a-menu-item>
                            </a-sub-menu>
                        </a-menu>
                    </div>
                </a-layout-sider>
                <a-layout
                    class="main-container"
                    :class="collapsed ? 'closeMenu' : ''"
                >
                    <a-breadcrumb class="breadcrumb-box">
                        <a-breadcrumb-item
                            ><router-link :to="{ name: 'XyptHome' }"
                                >首页</router-link
                            ></a-breadcrumb-item
                        >
                        <a-breadcrumb-item v-show="menuNav">{{
                            menuNav
                        }}</a-breadcrumb-item>
                        <a-breadcrumb-item v-show="selectNav">{{
                            selectNav
                        }}</a-breadcrumb-item>
                    </a-breadcrumb>
                    <div class="crm-main">
                        <router-view></router-view>
                    </div>
                    <footer class="crm-footer">
                        版权所有：{{ footSupport.value }}
                    </footer>
                </a-layout>
            </a-layout>
        </a-layout>
    </div>
</template>

<script>
import CrmHead from "@/components/Crm/CrmHead";
import { mapGetters, mapState } from "vuex";
export default {
    name: "CrmEnter",
    components: {
        CrmHead
    },
    computed: {
        ...mapState(["menuSourceList"]),
        ...mapGetters(["footSupport", "workTheme"])
    },
    data() {
        return {
            collapsed: false,
            defaultOpenKeys: [],
            selectedKeys: [],
            menuNav: "",
            selectNav: ""
        };
    },
    watch: {
        $route(to) {
            this.defaultOpenKeys = [];
            this.initSlider(this, to);
        }
    },
    beforeRouteEnter(to, from, next) {
        next(vm => {
            vm.initSlider(vm, to);
        });
    },
    methods: {
        /**
         * 初始化时配置菜单选择
         */
        initSlider(vm, to) {
            let sub,
                menuNav,
                selectNav,
                key = to.params.key || to.params.pathMatch || to.name;
            vm.menuSourceList.map(menu => {
                menu.childMenuSourceList &&
                    menu.childMenuSourceList.filter(menu2 => {
                        if (menu2.code === key) {
                            sub = menu.id;
                            menuNav = menu.name;
                            selectNav = menu2.name;
                            return false;
                        }
                    });
            });

            if (sub) {
                vm.menuNav = menuNav;
                vm.selectNav = selectNav;
                vm.defaultOpenKeys.push(sub);
                vm.selectedKeys.splice(0, 1, sub);
                vm.selectedKeys.splice(1, 1, key);
            } else {
                vm.selectedKeys = [];
                vm.menuNav = undefined;
                vm.selectNav = undefined;
            }
        },
        /**
         *  切换菜单展开关闭
         */
        toggleCollapsed() {
            this.collapsed = !this.collapsed;
        }
    }
};
</script>

<style scoped lang="less">
.pages-header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 999;
}

.main-container {
    min-width: 960px;
    min-height: 90%;
    overflow: hidden;
    padding: 60px 24px;
    margin-left: 200px;
    position: relative;

    &.closeMenu {
        margin-left: 80px;
    }
}
.slider-menu {
    width: 200px;
    height: 100%;
    overflow-x: hidden;
    overflow-y: auto;
    position: fixed;
    left: 0;
    top: 60px;
    background: #fff;
}

.shrink {
    position: fixed;
    bottom: 0;
    width: 194px;
    cursor: pointer;
    height: 40px;
    line-height: 40px;
    text-align: center;
    background-color: #fff;
    z-index: 99;
    &.shrink-now {
        padding-left: 0;
        width: 80px;
    }
}
.main-container,
.slider-menu,
.shrink {
    transition: 150ms cubic-bezier(0.645, 0.045, 0.355, 1);
}

.breadcrumb-box {
    margin: 16px 0;
}

.crm-main {
    .crm-main-cont {
        padding: 25px 40px;
        min-height: 500px;
        background-color: #fff;
    }
}
.crm-footer {
    text-align: center;
    width: 100%;
    position: absolute;
    left: 0;
    bottom: 20px;
}
.menu-box {
    height: 100%;
    border: 0;
    padding-bottom: 100px;
}
.ant-menu-submenu-title .iconfont {
    margin-right: 10px;
}
</style>
