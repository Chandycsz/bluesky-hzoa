<template>
    <div>
        <router-view></router-view>
    </div>
</template>

<script>
export default {
    name: "Page",
    beforeRouteEnter(to, from, next) {
        next(vm => {
            if (to.meta.login && !vm.$store.state.account.id) {
                vm.$message.error("您未登录或登录超时！").then(() => {
                    vm.$router.replace({
                        name: "Login"
                    });
                });
            }
        });
    }
};
</script>

<style scoped lang="less">
@theme: #246df8;
.fl {
    float: left;
}
.fr {
    float: right;
}
.inline-block {
    display: inline-block;
    vertical-align: middle;
}
.transition {
    transition: all 0.3s ease;
}
.logOut {
    margin-left: 20px;
}
.head {
    height: 106px;
    width: 1080px;
    margin: 0 auto;
    .logo {
        margin-top: 31px;
        cursor: pointer;
        .fl;
    }
    .nav-bar {
        margin-left: 100px;
        font-size: 16px;
        .fl;
        .nav {
            position: relative;
            text-decoration: none;
            line-height: 106px;
            height: 106px;
            display: inline-block;
            margin-right: 60px;
            padding: 0 4px;
            &:after {
                content: "";
                display: inline-block;
                position: absolute;
                bottom: 0;
                left: 50%;
                width: 0;
                height: 3px;
                .transition;
            }
            &:hover {
                &:after {
                    background-color: @theme;
                    width: 100%;
                    left: 0;
                }
            }
        }
        .router-link-active {
            font-weight: bold;
            &:after {
                background-color: @theme;
                width: 100%;
                left: 0;
            }
        }
    }
    .action-bar {
        font-size: 14px;
        height: 36px;
        margin-top: 35px;
        .fr;
        .button {
            width: 100px;
            height: 36px;
            line-height: 36px;
            .inline-block;
            text-align: center;
            margin-left: 30px;
            text-decoration: none;
        }
        .action {
            .inline-block;
            color: @theme;
        }
        .user {
            max-width: 60px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .line {
            .inline-block;
            margin: 0 24px;
            width: 1px;
            height: 14px;
            background: #dcdfe1;
        }
    }
}
</style>
