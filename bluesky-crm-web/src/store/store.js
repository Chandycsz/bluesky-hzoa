import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);
import { configPage, configMenu } from "@/api/config";

function getData(datas, type) {
    return datas.filter(sets => {
        return sets.type === type;
    })[0];
}
const store = {
    getters: {
        loginLogo: state => {
            return getData(state.pageSettingMap.login, "logo");
        },
        loginBg: state => {
            return getData(state.pageSettingMap.login, "background");
        },
        loginBgColor: state => {
            return getData(state.pageSettingMap.login, "background_color");
        },
        loginType: state => {
            return getData(state.pageSettingMap.login, "login_type");
        },
        workLogo: state => {
            return getData(state.pageSettingMap.work, "logo");
        },
        workTheme: state => {
            return getData(state.pageSettingMap.work, "theme_color");
        },
        indexHeadLogo: state => {
            return getData(state.pageSettingMap.index, "logo");
        },
        indexQueryBg: state => {
            return getData(state.pageSettingMap.index, "query_background");
        },
        indexAppType: state => {
            return getData(state.pageSettingMap.index, "app_style_type");
        },
        indexQueryBgColor: state => {
            return getData(
                state.pageSettingMap.index,
                "query_background_color"
            );
        },
        footOrganizer: state => {
            return getData(state.pageSettingMap.copyRight, "organizer");
        },
        footSupport: state => {
            return getData(state.pageSettingMap.copyRight, "support");
        },
        defaultConfig: state => {
            return getData(state.pageSettingMap.default, "upload_dir");
        },
        systemName: state => {
            return getData(state.pageSettingMap.default, "system_name");
        },
        storeAccount: state => {
            return state.account;
        }
    },
    state: {
        appInit: false,
        account: {
            id: "",
            name: "",
            nikeName: ""
        },
        imageDomain: "/img/",
        pageSettingMap: {
            copyRight: [],
            default: [],
            index: [],
            login: [],
            work: []
        },
        menuSourceList: [],
        loginMenus: []
    },
    mutations: {
        setAccount(state, props) {
            Object.assign(state.account, props);
        },
        clearAccount(state) {
            state.account = {
                id: "",
                name: "",
                nikeName: ""
            };
        },
        setAppInit(state) {
            state.appInit = true;
        },
        setPage: function(state, props) {
            Object.assign(state.pageSettingMap, props);
        },
        //作用： 当用户手动跳转路径，但是权限不足时判断使用的数据
        setMenu: function(state, props) {
            state.menuSourceList = props;
            let menus = [];
            props.map(menu => {
                menu.childMenuSourceList &&
                    menu.childMenuSourceList.map(twoMenu => {
                        menus.push(twoMenu.code);
                    });
            });
            state.loginMenus = menus;
        },
        setFoot: function(state, props) {
            if (state.pageSettingMap.copyRight.length) {
                state.pageSettingMap.copyRight.map(item => {
                    if (item.id === props.id) {
                        item.value = props.value;
                    }
                });
            }
        },
        setThemeColor(state, color) {
            state.pageSettingMap.work.map(item => {
                if (item.type === "theme_color") {
                    Object.assign(item, { value: color });
                }
            });
        },
        setQueryColor(state, color) {
            state.pageSettingMap.index.map(item => {
                if (item.type === "query_background_color") {
                    Object.assign(item, { value: color });
                }
            });
        }
    },
    actions: {
        setMenuAction({ commit }) {
            return new Promise(resolve => {
                configMenu().then(res => {
                    if (res.data.success === "TRUE") {
                        commit("setMenu", res.data.data.menuSourceList);
                        resolve();
                    }
                });
            });
        },
        setPageAction({ commit }) {
            return new Promise(resolve => {
                configPage().then(res => {
                    if (res.data.success === "TRUE") {
                        commit("setPage", res.data.data.pageSettingMap);
                        resolve();
                    }
                });
            });
        },
        setConfig({ commit }, props) {
            return new Promise(resolve => {
                commit("setPage", props);
                resolve();
            });
        }
    }
};

export default new Vuex.Store(store);
