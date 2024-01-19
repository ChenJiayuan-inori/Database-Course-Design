<template>
    <div>
        <div class="add-words">
            <h1>Add Word</h1>
            <div class="add-words-head">
                <div class="word">
                    <h3 style="display=inline-block">Word:</h3>
                    <input
                        type="text"
                        id="word"
                        v-model="wordinfo.word"
                        placeholder="Input Word"
                    />
                </div>
                <div class="attribution">
                    <select
                        id="classes"
                        name="classes"
                        v-model="wordinfo.classes"
                    >
                        <option value="0">词性</option>
                        <option value="1">动词</option>
                        <option value="2">名词</option>
                        <option value="3">形容词</option>
                    </select>
                    <select id="types" name="types" v-model="wordinfo.type">
                        <option value="0">类别</option>
                        <option value="1">机械</option>
                        <option value="2">电信</option>
                        <option value="3">软工</option>
                    </select>
                    <p id="submitBtn" @click="submit">Submit</p>
                </div>
            </div>
            <div class="add-words-body">
                <div class="add-words-body-block">
                    <h3 style="display=inline-block">Meaning:</h3>
                    <input
                        type="text"
                        id="meaning"
                        v-model="wordinfo.meaning"
                        placeholder="Input Meaning"
                    />
                </div>
                <div class="add-words-body-block">
                    <h3 style="display=inline-block">Description:</h3>
                    <textarea
                        id="description"
                        v-model="wordinfo.description"
                        placeholder="Input Description"
                    />
                </div>
                <div class="add-words-body-block">
                    <h3 style="display=inline-block">Example:</h3>
                    <textarea
                        id="example"
                        v-model="wordinfo.example"
                        placeholder="Input Example"
                    />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            wordinfo: {
                word: null,
                classes: 0,
                type: 0,
                meaning: null,
                description: null,
                example: null,
            },
        };
    },

    methods: {
        submit() {
            console.log(this.wordinfo);
            const that = this

            if (
                this.wordinfo.word === null ||
                this.wordinfo.meaning === null ||
                this.wordinfo.description === null ||
                this.wordinfo.example === null
            ) {
                this.$message({
                    type: "error",
                    message: "填写不完整！",
                });
                return;
            }
            if (this.wordinfo.classes === 0) {
                this.$message({
                    type: "error",
                    message: "请选择词性",
                });
                return;
            }
            if (this.wordinfo.type === 0) {
                this.$message({
                    type: "error",
                    message: "请选择分类",
                });
                return;
            }

            this.$axios
                .post("/backpage/addword", this.$data.wordinfo)
                .then(function (res) {
                    if (res.data.code === 1) {
                        that.$message({
                            type: "success",
                            message: "Successfully Added!",
                        });
                    } else {
                        that.$message({
                            type: "error",
                            message: "Failed Adding!",
                        });
                    }
                    return;
                })
                .catch((error) => {
                    console.log(error);
                });
        },
    },
};
</script>

<style src='../../public/css/addwords.css' scoped></style>
<style src='../../public/css/base.css' scoped></style>