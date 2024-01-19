<template>
    <div class="mytest" id="test">
        <div class="shell">
            <h2 class="title">UserLogin</h2>
            <input
                type="text"
                id="username"
                v-model="user.username"
                placeholder="Username"
            />
            <input
                type="password"
                id="password"
                v-model="user.password"
                placeholder="Password"
            />
            <input
                type="submit"
                value="UserLogin"
                @click="login"
                id="loginBtn"
            />
            <div class="footer">
                <button id="Password" @click="transfer">管理员登录</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            token: [],
            user: {
                id: null,
                username: "",
                password: "",
                createTime: null,
                updateTime: null,
            },
            admin: {
                id: null,
                username: "",
                password: "",
                createTime: null,
                updateTime: null,
            },
        };
    },
    methods: {
        transfer: function () {
            var title = document.querySelector(".title");
            var loginBtn = document.getElementById("loginBtn");
            var passwordBtn = document.getElementById("Password");
            if (title.innerText === "UserLogin") {
                title.innerText = "AdminLogin";
                loginBtn.value = "AdminLogin";
                passwordBtn.innerText = "管理员登录";
                document.getElementById("username").value = "";
                document.getElementById("password").value = "";
                document.getElementById("username").placeholder =
                    "Admin Username";
                document.getElementById("password").placeholder =
                    "Admin Password";
            } else {
                title.innerText = "UserLogin";
                loginBtn.value = "UserLogin";
                passwordBtn.innerText = "用户登录";
                document.getElementById("username").placeholder = "Username";
                document.getElementById("password").placeholder = "Password";
            }
        },

        login: function () {
            const that = this;
            if (document.getElementById("loginBtn").value === "AdminLogin") {
                this.$axios
                    .post("/login/admin", this.$data.user)
                    .then(function (ret) {
                        console.log(ret.data);
                        if (ret.data.code == 1) {
                            const token = ret.data.data;
                            localStorage.setItem("Token", token);
                            that.$router.push("/backpage");
                            that.$message({
                                type: "success",
                                message: "Successfully Login!",
                            });
                        } else {
                            document.getElementById("username").value = "";
                            document.getElementById("password").value = "";
                            that.$message({
                                type: "error",
                                message:
                                    "Login with wrong username or password!",
                            });
                        }
                    });
            } else {
                this.$axios
                    .post("/login/user", this.$data.user)
                    .then(function (ret) {
                        console.log(ret.data);
                        if (ret.data.code == 1) {
                            const token = ret.data.data;
                            localStorage.setItem("Token", token);
                            that.$router.push("/front");
                            that.$message({
                                type: "success",
                                message: "Successfully Login!",
                            });
                        } else {
                            document.getElementById("username").value = "";
                            document.getElementById("password").value = "";
                            that.$message({
                                type: "error",
                                message:
                                    "Login with wrong username or password!",
                            });
                        }
                    });
            }
        },

        welcome() {
            this.$message({
                type: "success",
                message: "Step into the world of English",
                duration: 1800,
            });
        },
    },

    mounted() {
        this.welcome();
    },
};
</script>

<style src='../../public/css/base.css'  scoped></style>
<style src='../../public/css/login.css' scoped></style>
