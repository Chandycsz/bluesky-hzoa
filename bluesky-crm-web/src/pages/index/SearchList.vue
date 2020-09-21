<template>
    <div id="searchList">
        <div
            class="search-box"
            :style="{ background: indexQueryBgColor.value }"
        >
            <div
                class="search-box-bg"
                :style="{
                    background:
                        'url(' + imageDomain + indexQueryBg.value + ') 0 50%'
                }"
            >
                <div class="wrap search-btn-radius">
                    <a-col :span="14">
                        <a-input-search
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
        <div class="wrap">
            <div class="search-main">
                <div class="search-tabs">
                    <a href="javascript:;" class="active">全部（123）</a>
                    <a href="javascript:;">企业（3）</a>
                    <a href="javascript:;">自然人（21）</a>
                    <a href="javascript:;">社会组织（1）</a>
                    <a href="javascript:;">事业单位（0）</a>
                    <a href="javascript:;">政府机构（110）</a>
                </div>
                <div class="search-content">
                    <ul>
                        <li>
                            <router-link to="/123">
                                123123
                            </router-link>
                        </li>
                        <li>
                            <router-link to="/123">
                                测试搜索测试搜索测试搜索测试搜索测试搜索
                            </router-link>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
export default {
    name: "SearchList",
    computed: {
        ...mapGetters(["indexQueryBg", "indexQueryBgColor"]),
        ...mapState(["imageDomain"])
    },
    data() {
        return {
            keywords: "",
            searchBg:
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554884702455&di=108c7022a9505614d5aef9669ea9f9a8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F038b61856dd5df532f875520f69cc2e.jpg"
        };
    },
    beforeRouteEnter(to, from, next) {
        next(vm => {
            vm.keywords = to.query.keywords;
        });
    },
    methods: {
        searchKey() {}
    }
};
</script>

<style scoped lang="less">
#searchList {
    padding-bottom: 50px;
    background-color: #f6f8fa;
    height: 80vh;
    min-height: 550px;
}

.ant-input-affix-wrapper {
    margin-top: 40px;
}
.search-box {
    height: 120px;
    border-bottom: 1px solid #d7d7d7;
}
.search-box-bg {
    width: 1200px;
    height: 100%;
    margin: 0 auto;
}
.search-tabs {
    line-height: 50px;
    border-bottom: 1px solid #d7d7d7;
    margin-bottom: 15px;

    a {
        margin-right: 25px;
        position: relative;
        display: inline-block;
        text-decoration: none;

        &:after {
            content: "";
            position: absolute;
            height: 1px;
            width: 0;
            left: 50%;
            bottom: 0;
            opacity: 0;
            transition: 0.3s;
        }

        &.active，&:hover {
            text-decoration: none;
        }
        &.active:after,
        &:hover:after {
            opacity: 1;
            left: 0;
            width: 100%;
        }
    }
}

.search-main {
    min-height: 350px;
    margin-top: 30px;
    padding: 0 25px 50px;
    background-color: #fff;
    border-radius: 5px;
    -moz-box-shadow: 2px 2px 5px #f1f1f1;
    -webkit-box-shadow: 2px 2px 5px #f1f1f1;
    box-shadow: 2px 2px 5px #f1f1f1;

    li {
        line-height: 40px;
        border-bottom: 1px solid #d7d7d7;
    }
}
</style>
