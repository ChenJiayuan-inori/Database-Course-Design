<template>
    <div class="details-body">
        <div class="details">
            <div class="detail-head">
                <div>
                    <h1>{{ detail.word }}</h1>
                    <div>
                        <!-- <p>词性:{{ detail.classes }}</p>
                        <p>类别:{{ detail.type }}</p> -->
                        <p v-if="detail.classes == 1">词性：动词</p>
                        <p v-else-if="detail.classes == 2">词性：名词</p>
                        <p v-else>词性：形容词</p>
                        <p v-if="detail.type == 1">分类：机械</p>
                        <p v-else-if="detail.type == 2">分类：电信</p>
                        <p v-else>分类：软工</p>
                    </div>
                </div>
                <i id="queriesTimes">已查询次数:{{ detail.queriesTimes }}</i>
                <!-- <i id="isCollect" class="el-icon-star-off" @click="collect"></i> -->
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
                <!-- <div class="closeBtn" @click="closeBtn"></div> -->
                <el-button class="closeBtn" type="text" @click="dialogFormVisible = true">edit</el-button>
                <el-button class="closeBtn" type="text" @click="deleteWord">delete</el-button>
            </div>
        </div>

        <el-dialog title="Update Word" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="Word" :label-width="formLabelWidth">
                    <el-input
                        v-model="form.word"
                        auto-complete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="Meaning" :label-width="formLabelWidth">
                    <el-input
                        v-model="form.meaning"
                        auto-complete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="Description" :label-width="formLabelWidth">
                    <el-input
                        v-model="form.description"
                        type="textarea"
                        :rows="2"
                        placeholder="请输入内容"
                        auto-complete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="Example" :label-width="formLabelWidth">
                    <el-input
                        type="textarea"
                        :rows="2"
                        placeholder="请输入内容"
                        v-model="form.example"
                        auto-complete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="词性" :label-width="formLabelWidth">
                    <el-select
                        v-model="form.classes"
                        placeholder="请选择词性"
                    >
                        <el-option label="动词" value="1"></el-option>
                        <el-option label="名词" value="2"></el-option>
                        <el-option label="形容词" value="3"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="分类" :label-width="formLabelWidth">
                    <el-select
                        v-model="form.type"
                        placeholder="请选择分类"
                    >
                        <el-option label="机械" value="1"></el-option>
                        <el-option label="电信" value="2"></el-option>
                        <el-option label="软工" value="3"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm"
                    >确 定</el-button
                >
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    //接收info组件中传来的参数，点击跳转单词详情页面，根据id查询
    props: ["id"],

    data() {
        return {
            time: "",
            targetId: this.id,
            detail: {},
            temp: [],
            dialogFormVisible: false,
            form: {
                id: this.id,
                word: null,
                meaning: null,
                description: null,
                example: null,
                classes: null,
                type: null,
            },
            formLabelWidth: "120px",
        };
    },

    methods: {
        initDetail() {
            this.$axios
                .get("/backpage/selectById", {
                    params: {
                        id: this.targetId,
                    },
                })
                .then((res) => {
                    console.log(res);
                    this.detail = res.data.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        },

        deleteWord() {
            var reset_id;
            var that = this;
            this.$confirm("Do you really want to delete this word?", {
                confirmButtonText: "Yes",
                cancelButtonText: "No",
                type: "warning",
                center: true,
            })
                .then(() => {
                    //踩坑日志，这里一开始只是使用异步请求发送信息，但是没有考虑emit和异步请求完成的前后
                    //导致一个问题，emit调用的父组件方法先被执行了，异步请求才拿到后端的响应数据
                    //要注意两个异步请求的执行顺序，将第二个放到then之后执行，且调用emit时要使用nextTick进行控制
                    //这样完成本次的请求处理
                    this.$axios
                        .delete("/backpage/deleteById", {
                            params: {
                                id: this.targetId,
                            },
                        })
                        .then((res) => {
                            console.log(res.data);
                            console.log("已经执行了删除");
                            that.$message({
                                type: "success",
                                message: "Successfully Delete!",
                            });
                            this.$nextTick(() => {
                                this.$axios
                                    .get("/backpage/info")
                                    .then((res) => {
                                        this.temp = res.data.data;
                                        reset_id = this.temp[0].id;
                                        console.log("reset的id为" + reset_id);
                                        this.$nextTick(() => {
                                            this.$emit("defaultId", reset_id);
                                            console.log("已经执行了reset");
                                            this.$emit("Reset");
                                            console.log("已经执行完毕");
                                        });
                                    });
                            });
                        })
                        .catch((error) => {
                            console.log(error);
                        });
                })
                .catch((error) => {
                    console.log(error);
                });
        },

        update() {
            this.$msgbox({
                title: "Update Word",
                message: h(
                    "div",
                    {
                        attrs: {
                            class: "el-textarea",
                        },
                    },
                    [
                        h("textarea", {
                            attrs: {
                                class: "el-textarea__inner",
                                autocomplete: "off",
                                rows: 4,
                            },
                            value: _this.commentContent,
                        }),
                    ]
                ),
                showCancelButton: true,
                confirmButtonText: "确定",
                cancelButtonText: "取消",
            });
        },

        submitForm() {
            this.dialogFormVisible = false
            this.$axios.post("/backpage/update", this.form).then(res => {
                console.log(res);
                this.$emit("Details");
            }).catch(error => {
                console.log(error);
            })
        }
    },

    mounted() {
        this.initDetail();
    },
};
</script>

<style src='../../public/css/details.css' scoped></style>