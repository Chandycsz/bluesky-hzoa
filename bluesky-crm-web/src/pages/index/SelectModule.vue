<template>
    <div class="select-module-box">
        <div
            class="search-box"
            :style="{
                background: indexQueryBgColor.value
            }"
        >
            <div
                class="search-box-bg"
                :style="{
                    background:
                        'url(' + imageDomain + indexQueryBg.value + ') 0 50%'
                }"
            >
                <div class="search-main wrap search-btn-radius">
                    <p class="search-tab">信用查询</p>
                    <a-col :span="14">
                        <a-input-search
                            :style="{ backgroundColor: workTheme.value }"
                            size="large"
                            placeholder="请输入信用信息关键字"
                            @search="searchKey"
                            enterButton
                            v-model="keywords"
                        />
                    </a-col>
                </div>
            </div>
        </div>
        <div class="wrap container-main">
            <a-row v-if="indexAppType.value === '1'">
                <a-col :key="card.id" :span="6" v-for="card in menuSourceList">
                    <a-card
                        :style="{ 'background-color': card.backgroundColor }"
                        :bordered="false"
                    >
                        <router-link
                            v-if="card.systemMenu"
                            :to="{
                                name: card.childMenuSourceList[0].code,
                                params: {
                                    key: card.childMenuSourceList[0].code
                                }
                            }"
                        >
                            <icon-font
                                :type="card.icon"
                                :style="{ color: card.iconColor }"
                            ></icon-font>
                            <span
                                class="icon-title"
                                :style="{ color: card.color }"
                                >{{ card.name }}</span
                            >
                        </router-link>

                        <router-link
                            v-else
                            :to="{
                                path:
                                    '/crm/' +
                                    card.childMenuSourceList[0].code,
                                query: { id: card.childMenuSourceList[0].id }
                            }"
                        >
                            <icon-font
                                :type="card.icon"
                                :style="{ color: card.iconColor }"
                            ></icon-font>
                            <span
                                class="icon-title"
                                :style="{ color: card.color }"
                                >{{ card.name }}</span
                            >
                        </router-link>
                    </a-card>
                </a-col>
            </a-row>

            <ul class="clearfix" v-else>
                <li
                    class="fl"
                    :key="card.id"
                    :span="6"
                    v-for="card in menuSourceList"
                >
                    <router-link
                        class="card-style2"
                        v-if="card.systemMenu"
                        :to="{
                            name: card.childMenuSourceList[0].code,
                            params: {
                                key: card.childMenuSourceList[0].code
                            }
                        }"
                    >
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
                    </router-link>

                    <router-link
                        v-else
                        class="card-style2"
                        :to="{
                            path:
                                '/crm/' +
                                card.childMenuSourceList[0].code,
                            query: { id: card.childMenuSourceList[0].id }
                        }"
                    >
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
                    </router-link>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
    name: "SelectModule",
    computed: {
        ...mapState(["imageDomain", "menuSourceList"]),
        ...mapGetters([
            "indexQueryBg",
            "indexQueryBgColor",
            "workTheme",
            "indexAppType"
        ])
    },
    data() {
        return {
            keywords: "",
            searchBg:
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554884702455&di=108c7022a9505614d5aef9669ea9f9a8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F038b61856dd5df532f875520f69cc2e.jpg"
        };
    },
    methods: {
        selectType(type) {
            this.type = type;
        },
        searchKey() {
            if (this.keywords) {
                this.$router.push({
                    name: "SearchList",
                    query: {
                        keywords: this.keywords
                    }
                });
            }
        }
    }
};
</script>

<style scoped lang="less">
.select-module-box {
    background-color: #f6f8fa;
}
.search-box {
    height: 120px;
}
.search-box-bg {
    width: 1200px;
    height: 100%;
    margin: 0 auto;
}
.search-main {
    padding-top: 25px;
}
.search-tab {
    font-size: 14px;
    color: #fff;
    height: 34px;
    padding-bottom: 14px;

    .active {
        color: #ff0000;
    }
}

.container-main {
    padding: 30px 0 50px;
    min-height: 460px;
}

.ant-card {
    margin-bottom: 25px;
    text-align: center;
    width: 265px;
    border-radius: 5px;
    padding: 10px 0;

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

    &:hover {
        -moz-box-shadow: 2px 2px 5px #eee;
        -webkit-box-shadow: 2px 2px 5px #eee;
        box-shadow: 2px 2px 5px #eee;

        .anticon {
            transform: scale(1.2);
        }
    }
}
.anticon {
    font-size: 24px;
    display: block;
    margin-bottom: 7px;
    color: #666;
}
.anticon-search {
    font-size: 14px;
}
.icon-title {
    font-size: 14px;
}
.card-style2 {
    width: 155px;
    height: 185px;
    padding-top: 25px;
    background-color: #f0f0f0;
    margin-right: 30px;
    margin-bottom: 30px;
    font-size: 16px;
    color: #333;
    text-align: center;
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
