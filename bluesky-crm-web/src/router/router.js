import Vue from "vue";
import Router from "vue-router";
Vue.use(Router);

var router = new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [
        {
            path: "/",
            redirect: {
                name: "Login"
            },
            component: () => {
                return import("@/pages/Page");
            },
            children: [
                {
                    name: "IndexEnter",
                    path: "index",
                    title: "首页",
                    redirect: {
                        name: "SelectModule"
                    },
                    component: () => {
                        return import("@/pages/IndexEnter");
                    },
                    children: [
                        {
                            name: "SelectModule",
                            path: "selectModule",
                            meta: {
                                login: false,
                                title: "首页",
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/index/SelectModule");
                            }
                        },
                        {
                            name: "SearchList",
                            path: "searchList",
                            meta: {
                                login: true,
                                title: "信用查询",
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/index/SearchList");
                            }
                        }
                    ]
                },
                {
                    name: "XYPT",
                    path: "crm",
                    meta: {
                        title: "信用工作平台"
                    },
                    redirect: {
                        name: "XyptHome"
                    },
                    component: () => {
                        return import("@/pages/CrmEnter");
                    },
                    children: [
                        {
                            name: "XyptHome",
                            path: "xyptHome",
                            component: () => {
                                return import("@/pages/Home");
                            }
                        },
                        {
                            name: "sm_login_manage",
                            path: "sm_login_manage",
                            // meta: {
                            //     title: "登录页管理",
                            //     login: true,
                            //     authorize: false
                            // },
                            component: () => {
                                return import("@/pages/setting/LoginManage");
                            }
                        },
                        {
                            name: "pro_hinfo",
                            path: "pro_hinfo",
                            title: "项目修改历史信息",
                            component: () => {
                                return import("@/pages/project/PhInfo");
                            }
                        },
                        {
                            name: "add_proinfo",
                            path: "add_proinfo",
                            title: "新增项目信息",
                            component: () => {
                                return import("@/pages/project/AddPro_info");
                            }
                        },
                        {
                            name: "Proinfo",
                            path: "proinfo",
                            title: "项目信息",
                            // meta: {
                            //     title: "项目信息",
                            //     login: true,
                            //     authorize: false
                            // },
                            component: () => {
                                return import("@/pages/project/Project");
                            }
                        },
                        {
                            name: "sm_index_manage",
                            path: "sm_index_manage",
                            // meta: {
                            //     title: "首页管理",
                            //     login: true,
                            //     authorize: false
                            // },
                            component: () => {
                                return import("@/pages/setting/IndexManage");
                            }
                        },
                        {
                            name: "sm_menu_manage",
                            path: "sm_menu_manage",
                            // meta: {
                            //     title: "菜单栏管理",
                            //     login: true,
                            //     authorize: false
                            // },
                            component: () => {
                                return import("@/pages/setting/MenuManage");
                            }
                        },
                        {
                            name: "sm_work_manage",
                            path: "sm_work_manage",
                            // meta: {
                            //     title: "工作页管理",
                            //     login: true,
                            //     authorize: false
                            // },
                            component: () => {
                                return import("@/pages/setting/WorkManage");
                            }
                        },
                        {
                            name: "sm_copyright_manage",
                            path: "sm_copyright_manage",
                            meta: {
                                title: "版权信息管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/setting/CopyrightManage");
                            }
                        },
                        {
                            name: "om_region_manage",
                            path: "om_region_manage",
                            meta: {
                                title: "行政区管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/RegionManage");
                            }
                        },
                        {
                            name: "pro_manage",
                            path: "pro_manage",
                            title: "项目管理",
                            // meta: {
                            //     title: "行政区管理",
                            //     login: true,
                            //     authorize: false
                            // },
                            component: () => {
                                return import("@/pages/project/Project_Manager");
                            }
                        },
                        {
                            name: "pro_milestone",
                            path: "pro_milestone",
                            title: "项目信息",
                            // meta: {
                            //     title: "项目信息",
                            //     login: true,
                            //     authorize: false
                            // },
                            component: () => {
                                return import("@/pages/project/Pro_milestone");
                            }
                        },
                        {
                            name: "mis_peo_info",
                            path: "mis_peo_info",
                            title: "任务人员信息",
                            component: () => {
                                return import("@/pages/project/MispeoInfo");
                            }
                        },
                        {
                            name: "mission_info",
                            path: "mission_info",
                            title: "任务信息",
                            component: () => {
                                return import("@/pages/project/MissionInfo");
                            }
                        },
                        {
                            name: "add_milestone",
                            path: "add_milestone",
                            title: "新增项目信息",
                            component: () => {
                                return import("@/pages/project/Add_milestone");
                            }
                        },
                        {
                            name: "addmission",
                            path: "addmission",
                            title: "新增任务信息",
                            component: () => {
                                return import("@/pages/project/Add_mission");
                            }
                        },
                        {
                            name: "mil_hinfo",
                            path: "mil_hinfo",
                            title: "新增项目信息",
                            component: () => {
                                return import("@/pages/project/Pro_history_milestone");
                            }
                        },
                        {
                            name: "om_role_manage",
                            path: "om_role_manage",
                            meta: {
                                title: "角色管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/RoleManage");
                            }
                        },
                        {
                            name: "om_org_manage",
                            path: "om_org_manage",
                            meta: {
                                title: "单位管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/OrgManage");
                            }
                        },
                        {
                            name: "om_user_manage",
                            path: "om_user_manage",
                            meta: {
                                title: "用户管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/UserManage");
                            }
                        },
                        {
                            name: "om_auth_manage",
                            path: "om_auth_manage",
                            meta: {
                                title: "权限管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/AuthManage");
                            }
                        },
                        {
                            name: "om_personal_manage",
                            path: "om_personal_manage",
                            meta: {
                                title: "个人认证用户管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/PersonalManage");
                            }
                        },
                        {
                            name: "om_ent_manage",
                            path: "om_ent_manage",
                            meta: {
                                title: "企业认证用户管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/EntManage");
                            }
                        },
                        {
                            name: "om_api_manage",
                            path: "om_api_manage",
                            meta: {
                                title: "接口用户管理",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/ApiManage");
                            }
                        },
                        {
                            name: "om_log_manage",
                            path: "om_log_manage",
                            meta: {
                                title: "用户日志",
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/yhgl/LogManage");
                            }
                        },
                        {
                            name: "authority",
                            path: "authority",
                            meta: {
                                title: "没有权限，请联系管理员",
                                login: false,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/Authority.vue");
                            }
                        },
                        {
                            name: "new",
                            path: "new/page/*",
                            meta: {
                                login: true,
                                authorize: false
                            },
                            component: () => {
                                return import("@/pages/NewPage");
                            }
                        }
                    ]
                }
            ]
        },
        {
            name: "Login",
            path: "/login",
            meta: {
                title: "登录",
                login: false,
                authorize: false
            },
            component: () => {
                return import("@/pages/Login.vue");
            }
        },
        {
            name: "Err404",
            path: "*",
            meta: {
                title: "没有找到页面",
                login: false,
                authorize: false
            },
            component: () => {
                return import("@/Err404");
            }
        }
    ]
});

export default router;
