const path = require("path");
const AntDesignThemePlugin = require("antd-theme-webpack-plugin");

const env = process.env.NODE_ENV;
let target = "";

switch (env) {
    case "development":
        target = {
            api: "http://localhost:2608"
        };
        break;
    case "production":
        target = {
            api: "http://192.168.1.29"
        };
        break;
}
module.exports = {
    devServer: {
        host: "localhost",
        open: true,
        proxy: {
            "/api/*": {
                target: target.api,
                changeOrigin: true,
                pathRewrite: { "^/api": "" }
            }
        }
    },
    configureWebpack: {
        performance: {
            hints: false
        },
        plugins: [
            new AntDesignThemePlugin({
                antDir: path.join(__dirname, "./node_modules/ant-design-vue"), //antd包位置
                stylesDir: "./src/assets/theme", //指定皮肤文件夹
                varFile: "", //自己设置默认的主题色
                indexFileName: "./public/index.html",
                mainLessFile: "",
                outputFilePath: path.join(
                    __dirname,
                    "./src/assets/theme/color.less"
                ), //输出到什么地方
                themeVariables: [
                    //这里写要改变的主题变量
                    "@primary-color",
                    "@btn-primary-bg"
                ],
                generateOnce: false
            })
        ]
    },
    css: {
        loaderOptions: {
            less: {
                modifyVars: {
                    "primary-color": "#246df8",
                    "link-color": "#4378fa",
                    "border-radius-base": "2px",
                    "font-size-base": "12px",
                    "text-color": "#333",
                    "text-color-secondary": "#666",
                    "body-background": "#fff",
                    "link-hover-decoration": "underline"
                },
                javascriptEnabled: true
            }
        }
    }
};
