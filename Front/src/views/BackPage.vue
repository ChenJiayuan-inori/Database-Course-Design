<template>
    <div class="body">
        <div class="shell">
            <div class="logo"></div>
            <div class="terminal">
                <div class="terminal_head">
                    <img id="avater" src="../../public/head.png" />
                    <h id="name">einstein</h>
                </div>
                <div class="terminal_body">
                    <p class="btn" @click="info">Words Bank</p>
                    <p class="btn" @click="addwords">Add Words</p>
                    <p class="btn" @click="details">Details</p>
                    <p class="btn" @click="relax">Relax</p>
                    <p class="btn" @click="exit">Exit Sys</p>
                </div>
            </div>
            <div class="info">
                <component
                    :id_flag="id_flag"
                    :id="id"
                    :is="currentComponent"
                    :key="time"
                    @Details="details"
                    @defaultId="defaultId"
                    @idFlagStatus="idFlagStatus"
                    @Reset="Info"
                ></component>
            </div>
        </div>
    </div>
</template>

<script>
import AddWords from "../components/AddWords.vue";
import Details from "../components/Details.vue";
import Info from "../components/Info.vue";
import Relax from "../components/Relax.vue";

export default {
    data() {
        return {
            id_flag: true, //单独记录是否是第一次访问，true为第一次，访问详情页之前没有选择过单词的话默认返回当前info列表第一项的id，如果选择过了改为false,不再更新id
            id: 0,
            currentComponent: "Info",
            time: "", //点击时刷新组件用的
        };
    },
    components: {
        Info,
        AddWords,
        Details,
        Relax,
    },
    methods: {
        info() {
            this.currentComponent = "Info";
            this.time = new Date().getTime();
        },
        addwords() {
            this.currentComponent = "AddWords";
            this.time = new Date().getTime();
        },
        details() {
            this.currentComponent = "Details";
            this.time = new Date().getTime();
        },
        relax() {
            this.currentComponent = "Relax";
            this.time = new Date().getTime();
        },

        defaultId(id) {
            this.id = id;
        },

        //踩坑日记
        //emit调用返回的是一个this对象，拿不到值的，必须在父组件里定义回调函数，才能将值传到子组件中
        //这里定义回调函数将父组件中的id_flag传过来，用于判断是否为初次渲染，初次渲染时返回给组件当前列表第一的id
        //此后不再将列表第一的id返回，要想拿到新的detail必须点击列表具体项
        //此后每次只调用redir函数去重定位最后一次被选中的项，并同步到父组件中去，不再改变
        idFlagStatus(callback) {
            console.log(this.id_flag);
            console.log(this.id);
            if (this.id_flag == true) {
                this.id_flag = false;
                callback(true);
            } else callback(false);
        },

        exit() {
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
    },
    mounted() {},
};
</script>

<style src='../../public/css/base.css'  scoped></style>
<style src='../../public/css/backpage.css' scoped></style>


