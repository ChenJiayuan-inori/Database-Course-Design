<template>
    <div class="info-body">
        <div class="info-head">
            <table>
                <tr>
                    <td class="head-words">word</td>
                    <td class="head-classes">classes</td>
                    <td class="head-paraphrase">&nbsp;&nbsp;&nbsp;  paraphrase</td>
                </tr>
            </table>
            <input class="searchBtn" v-model="search" />
            <div class="el-icon-search" @click="searchBtn"></div>
        </div>
        <div class="info-table">
            <table>
                <tr v-for="word in words" :key="word.id" @click="redir(word.id)">
                    <td class="head-words">{{ word.word }}</td>
                    <td class="head-classes" v-if="word.classes == 1">动词</td>
                    <td class="head-classes" v-else-if="word.classes == 2">名词</td>
                    <td class="head-classes" v-else>形容词</td>
                    <td class="head-paraphrase">&nbsp;&nbsp;&nbsp;  {{ word.meaning }}</td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
export default {
    props: ['id', 'id_flag'],
    data() {
        return {
            search: "",
            words: [],
        };
    },
    methods: {
        loadInfo(){
            this.$axios.get("/backpage/info").then(res => {
                //ajax返回值赋值给 data中list
                console.log(res)
                this.words = res.data.data;


                //踩坑日记
                //emit调用返回的是一个this对象，拿不到值的，必须在父组件里定义回调函数，才能将值传到子组件中
                //这里定义回调函数将父组件中的id_flag传过来，用于判断是否为初次渲染，初次渲染时返回给组件当前列表第一的id
                //此后不再将列表第一的id返回，要想拿到新的detail必须点击列表具体项
                //此后每次只调用redir函数去重定位最后一次被选中的项，并同步到父组件中去，不再改变

                var is_flag
                this.$emit("idFlagStatus", isflag => {
                    is_flag = isflag;
                });
                if(is_flag){
                    console.log(is_flag)
                    this.$emit('defaultId', this.words[0].id);
                }
			})
        },

        searchBtn(){
            this.$axios.get("/backpage/search", {
                params: {
                    search : this.search
                }
            }).then(res =>{
                console.log(res)
                this.words = res.data.data
                console.log(this.words)
            }).catch(error => {
                console.log(error)
            })
        },
        
        redir(id){
            //看明白了，这是点击“（（具体某一行））”的时候，如果isFlag为false，说明没访问过
            //不是在一开始点detail页面的逻辑，这块逻辑要写在跳转detail的页面里才行
            this.$emit('defaultId', id);
            this.$emit('Details');
        },
    },
    mounted() {
        this.loadInfo();
    },

    created() {

    },
};
</script>

<style src='../../public/css/info.css' scoped></style>

