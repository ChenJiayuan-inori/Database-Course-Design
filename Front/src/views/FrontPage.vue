<template>
    <div class="body">
        <el-button class="logOut" @click="logOut" type="primary" round
            >Log Out</el-button
        >
        <img class="logo" src="../../public/img/logo.png" />
        <div class="table" id="table" v-loading="loading">
            <section class="header">
                <div @click="toListOrCollection">
                    <h1 id="transfer">Words List</h1>
                </div>
                <div class="input-group">
                    <input
                        type="search"
                        v-model="search"
                        placeholder="Search Data..."
                    />
                    <i class="el-icon-search" @click="searchBtn"></i>
                </div>
            </section>
            <section class="shell">
                <table>
                    <thead>
                        <tr>
                            <th id="word">word</th>
                            <th id="classes">classes</th>
                            <th id="type">type</th>
                            <th id="meaning">meaning</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            v-for="word in words"
                            :key="word.id"
                            @click="detailBtn(word.id)"
                        >
                            <td id="td">{{ word.word }}</td>
                            <td id="td" v-if="word.classes == 1">动词</td>
                            <td id="td" v-else-if="word.classes == 2">名词</td>
                            <td id="td" v-else>形容词</td>
                            <td id="td" v-if="word.type == 1">机械</td>
                            <td id="td" v-else-if="word.type == 2">电信</td>
                            <td id="td" v-else>软工</td>
                            <td id="td">{{ word.meaning }}</td>
                        </tr>
                    </tbody>
                </table>
            </section>
        </div>
        <div class="detail" v-if="showDetail">
            <div class="detail-head">
                <div>
                    <h1>{{ detail.word }}</h1>
                    <div>
                        <p v-if="detail.classes == 1">词性：动词</p>
                        <p v-else-if="detail.classes == 2">词性：名词</p>
                        <p v-else>词性：形容词</p>
                        <p v-if="detail.type == 1">分类：机械</p>
                        <p v-else-if="detail.type == 2">分类：电信</p>
                        <p v-else>分类：软工</p>
                    </div>
                </div>
                <i id="queriesTimes">已查询次数:{{ detail.queriesTimes }}</i>
                <i
                    id="isCollect"
                    class="el-icon-star-off"
                    @click="collect(detail.id)"
                ></i>
            </div>

            <div class="detail-body">
                <table>
                    <tr class="row">
                        <td class="column">meaning:</td>
                        <td class="detail-column">{{ detail.meaning }}</td>
                    </tr>
                    <tr class="row">
                        <td class="column">description:</td>
                        <td class="detail-column">{{ detail.description }}</td>
                    </tr>
                    <tr class="row">
                        <td class="column">example:</td>
                        <td class="detail-column">{{ detail.example }}</td>
                    </tr>
                </table>
            </div>

            <div class="detail-foot">
                <div class="closeBtn" @click="closeBtn">close</div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            time: "",
            search: "",
            showDetail: false,
            detail: {},
            words: [],
        };
    },

    methods: {
        loadInfo() {
            this.$axios.get("/front/info").then((res) => {
                console.log(res);
                this.words = res.data.data;

                //踩坑日记
                //emit调用返回的是一个this对象，拿不到值的，必须在父组件里定义回调函数，才能将值传到子组件中
                //这里定义回调函数将父组件中的id_flag传过来，用于判断是否为初次渲染，初次渲染时返回给组件当前列表第一的id
                //此后不再将列表第一的id返回，要想拿到新的detail必须点击列表具体项
                //此后每次只调用redir函数去重定位最后一次被选中的项，并同步到父组件中去，不再改变

                var is_flag;
                this.$emit("idFlagStatus", (isflag) => {
                    is_flag = isflag;
                });
                if (is_flag) {
                    console.log(is_flag);
                    this.$emit("defaultId", this.words[0].id);
                }
            });
        },

        searchBtn() {
            const user_id = String(window.localStorage.getItem("Token"));
            if (
                document.getElementById("transfer").innerHTML === "Words List"
            ) {
                this.$axios
                    .get("/front/search", {
                        params: {
                            search: this.search,
                        },
                    })
                    .then((res) => {
                        console.log(res);
                        this.words = res.data.data;
                        console.log(this.words);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            }
            else {
                this.$axios
                    .get("/front/searchCollection", {
                        params: {
                            user: user_id,
                            search: this.search,
                        },
                    })
                    .then((res) => {
                        console.log(res);
                        this.words = res.data.data;
                        console.log(this.words);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            }
        },

        detailBtn(id) {
            this.showDetail = true;
            const user_id = String(window.localStorage.getItem("Token"));
            this.$axios
                .get("/front/detail", {
                    params: {
                        id: id,
                    },
                })
                .then((res) => {
                    this.detail = res.data.data;

                    this.$axios
                        .get("/front/isCollected", {
                            params: {
                                wordId: id,
                                user: user_id,
                            },
                        })
                        .then((res) => {
                            console.log("检查收藏的结果为" + res.data.data);
                            if (res.data.data != null) {
                                document.getElementById("isCollect").className =
                                    "el-icon-star-on";
                            } else {
                                document.getElementById("isCollect").className =
                                    "el-icon-star-off";
                            }
                        });
                })
                .catch((error) => {
                    console.log(error);
                });
        },

        closeBtn() {
            this.showDetail = false;
        },

        collect(word_id) {
            if (
                document.getElementById("isCollect").className ===
                "el-icon-star-off"
            ) {
                var that = this;
                const user_id = String(window.localStorage.getItem("Token"));
                console.log("word_id是" + word_id);
                console.log("user_id是" + user_id);
                document.getElementById("isCollect").className =
                    "el-icon-star-on";
                this.$axios
                    .get("/front/addColletion", {
                        params: {
                            id: word_id,
                            user: user_id,
                        },
                    })
                    .then((res) => {
                        console.log(res);
                        that.$message({
                            type: "success",
                            message: "successfully collected!",
                        });
                        this.time = new Date().getTime();
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            } else {
                const user_id = String(window.localStorage.getItem("Token"));
                console.log("word_id是" + word_id);
                console.log("user_id是" + user_id);
                document.getElementById("isCollect").className =
                    "el-icon-star-off";
                this.$axios
                    .get("/front/deleteColletion", {
                        params: {
                            id: word_id,
                            user: user_id,
                        },
                    })
                    .then((res) => {
                        console.log(res);
                        that.$message({
                            type: "success",
                            message: "successfully discollected!",
                        });
                        this.time = new Date().getTime();
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            }
        },

        justInfo() {
            this.$notify({
                title: "提示",
                message:
                    "你可以通过点击标题的“Word List”或者“Collection”来切换词表和收藏夹",
                duration: 0,
            });
        },

        logOut() {
            this.$confirm(
                "Do you really want to exit? You must login again if you want back to this page",
                "Log Out",
                {
                    confirmButtonText: "Yes",
                    cancelButtonText: "No",
                    type: "warning",
                    center: true,
                }
            ).then(() => {
                localStorage.removeItem("Token");
                this.$router.push("/login");
                this.$message({
                    type: "success",
                    message: "You have exited.",
                });
            });
        },

        toListOrCollection() {
            if (
                document.getElementById("transfer").innerHTML === "Words List"
            ) {
                document.getElementById("transfer").innerHTML = "Collection";
                const user_id = String(window.localStorage.getItem("Token"));

                this.$axios
                    .get("/front/getCollection", {
                        params: {
                            user: user_id,
                        },
                    })
                    .then((res) => {
                        console.log("收藏夹的结果为" + res.data.data);
                        this.words = res.data.data;
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            } else {
                document.getElementById("transfer").innerHTML = "Words List";

                this.$axios.get("/front/info").then((res) => {
                    console.log("info的数据" + res.data.data);
                    this.words = res.data.data;
                });
            }
        },
    },

    mounted() {
        this.loadInfo();
        this.justInfo();
    },
};
</script>

<style src='../../public/css/base.css'  scoped></style>
<style src='../../public/css/frontpage.css' scoped></style>