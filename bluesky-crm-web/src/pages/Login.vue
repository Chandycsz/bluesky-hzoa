<template>
    <div id="login">
        <header class="login-header">
            <div class="logo-div">
                <!-- <img src="../assets/images/logo.png" alt class="logo" /> -->
            </div>
        </header>
        <div class="container" :style="themeStyle">
            <div class="banner">
                <img v-if="loginBg" :src="imageDomain + loginBg.value" alt />
            </div>
            <div class="wrap">
                <div class="login-box">
                    <div class="login-tab" :class="isCodeLogin && isPhoneLogin ? '' : 'left-tab'">
                        <template v-if="isCodeLogin && isPhoneLogin">
                            <a
                                href="javascript:"
                                @click="handleLoginType(1)"
                                :class="types === 1 ? '' : 'notActive'"
                            >验证码登录</a>
                            <a
                                href="javascript:"
                                @click="handleLoginType(2)"
                                :class="types === 2 ? '' : 'notActive'"
                            >密码登录</a>
                        </template>
                        <template v-else>
                            <span class="login-tab-title">
                                {{
                                isCodeLogin ? "验证码" : "帐号密码"
                                }}登录
                            </span>
                        </template>
                    </div>
                    <a-form class="a-form" :form="form" @submit="handleSubmit" hideRequiredMark>
                        <template v-if="isCodeLogin && types === 1">
                            <a-form-item
                                label="手机号"
                                :label-col="{ span: 4 }"
                                :wrapper-col="{ span: 20 }"
                            >
                                <a-input
                                    maxlength="11"
                                    v-decorator="[
                                        'mobile',
                                        {
                                            rules: [
                                                {
                                                    required: true,
                                                    message: '请输入手机号!'
                                                }
                                            ]
                                        }
                                    ]"
                                />
                            </a-form-item>
                            <a-form-item
                                label="验证码"
                                :label-col="{ span: 4 }"
                                :wrapper-col="{ span: 20 }"
                                class="code-input"
                            >
                                <a-input
                                    v-decorator="[
                                        'code',
                                        {
                                            rules: [
                                                {
                                                    required: true,
                                                    message: '请输入验证码!'
                                                }
                                            ]
                                        }
                                    ]"
                                />
                                <a-button class="code">发送验证码</a-button>
                            </a-form-item>
                        </template>
                        <template v-if="isPhoneLogin && types === 2">
                            <a-form-item
                                label="用户名"
                                :label-col="{ span: 4 }"
                                :wrapper-col="{ span: 20 }"
                            >
                                <a-input
                                    maxlength="11"
                                    v-decorator="[
                                        'userName',
                                        {
                                            rules: [
                                                {
                                                    required: true,
                                                    message: '请输入用户名!'
                                                }
                                            ]
                                        }
                                    ]"
                                />
                            </a-form-item>
                            <a-form-item
                                label="密码"
                                :label-col="{ span: 4 }"
                                :wrapper-col="{ span: 20 }"
                            >
                                <a-input
                                    type="password"
                                    v-decorator="[
                                        'password',
                                        {
                                            rules: [
                                                {
                                                    required: true,
                                                    message: '请输入密码!'
                                                }
                                            ]
                                        }
                                    ]"
                                />
                            </a-form-item>
                        </template>
                        <a-form-item class="login-btn">
                            <a-button type="primary" block size="large" html-type="submit">登录</a-button>
                        </a-form-item>
                        <a-form-item v-if="wechatStatus">
                            <div class="other-login">
                                <span class="fl wechat-btn">
                                    <a target="_blank" :href="wechatAuthUrl">
                                        <img
                                            width="20px"
                                            style="margin-right: 10px"
                                            src="https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1554684117&di=13237979735c29ef467a631f9847c8a7&src=http://hbimg.b0.upaiyun.com/f89a35d2617c0c2b83f9c6149021b383cd6b977730fa-Al3vpM_fw658"
                                            alt
                                        />
                                    </a>
                                    <a target="_blank" :href="wechatAuthUrl">微信登录</a>
                                </span>
                            </div>
                        </a-form-item>
                    </a-form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { accountLogin } from "@/api/user";
import { mapState, mapGetters } from "vuex";
export default {
    name: "Login",
    computed: {
        ...mapState(["imageDomain"]),
        ...mapGetters(["loginLogo", "loginBg", "loginBgColor", "loginType"]),
        isCodeLogin() {
            return new RegExp("captcha").test(this.loginType.value);
        },
        types() {
            return new RegExp("captcha").test(this.loginType.value) ? 1 : 2;
        },
        isPhoneLogin() {
            return new RegExp("password").test(this.loginType.value);
        },
        wechatStatus: function() {
            return new RegExp("wechat").test(this.loginType.value);
        },
        themeStyle: function() {
            return {
                "background-color": this.loginBgColor.value
            };
        }
    },
    data() {
        return {
            form: this.$form.createForm(this),
            wechatAuthUrl: ""
        };
    },
    beforeRouteEnter(to, from, next) {
        next(vm => {
            document.title = vm.$store.getters.systemName.value;
        });
    },
    methods: {
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFields((err, values) => {
                if (!err) {
                    accountLogin(values)
                        .then(res => {
                            this.$ajaxAfter(res).then(() => {
                                localStorage.setItem(
                                    "account",
                                    JSON.stringify(res.data.data.account)
                                );
                                this.$store
                                    .dispatch("setMenuAction")
                                    .then(() => {
                                        this.$router.push({
                                            name: "notice_manage"
                                        });
                                    });
                            });
                        })
                        .catch(() => {
                            this.$message.error("服务器出错！请稍后再试!");
                        });
                }
            });
        },
        /**
         * 切换登录类型
         * @param type
         */
        handleLoginType: function(type) {
            this.types = type;
        }
    }
};
</script>
<style>
.ant-form-explain {
    text-align: left;
    line-height: 1.75;
}

.ant-form-item-with-help {
    margin-bottom: 5px;
}
#login .ant-form-item-label {
    text-align: left;
}
</style>

<style scoped lang="less">
#login {
    height: 100%;
}
.logo-img {
    text-align: center;
    padding-top: 50px;
}
.login-tab {
    text-align: center;
    padding-bottom: 35px;

    &.left-tab {
        text-align: left;
        .login-tab-title {
            font-size: 16px;
            margin: 0 35px;
        }
    }

    a {
        margin: 0 21px;
        font-size: 16px;
        text-decoration: none;

        &.notActive {
            color: #333;
        }
    }
}

.code {
    width: 100px;
}

.code-input {
    input {
        width: 170px;
        margin-right: 20px;
    }
}

input {
    transition-duration: 0ms;
}

.login-box {
    width: 420px;
    padding: 45px 0 0;
    height: 390px;
    border: 1px solid #e5e7e9;
    border-radius: 10px;
    position: absolute;
    top: 180px;
    right: 10%;
    background-color: #fff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.login-btn {
    padding-top: 15px;
}

.login-title {
    font-size: 22px;
    text-align: center;
}
.a-form {
    width: 350px;
    margin: 0 35px;
}
.login-header {
    height: 80px;
    line-height: 80px;
    /*padding: 0 50px;*/
    background-color: #fff;

    .logo-div {
        width: 1200px;
        margin: auto;
    }

    img {
        height: 45px;
    }
}
.container {
    overflow: hidden;
    height: 100%;
    background-color: #ffffff;
}
.banner {
    height: 600px;
    position: relative;
    width: 1200px;
    margin: 0 auto;

    img {
        position: absolute;
        top: 0;
        left: 50%;
        transform: translateX(-50%);
    }
}
</style>
