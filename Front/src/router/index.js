import Vue from 'vue'
import VueRouter from 'vue-router'
import { jwtDecode } from "jwt-decode"


Vue.use(VueRouter)

const routes = [
	{
		path: '/login',
		name: 'login',
		component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
	},
	{
		path: '/',
		name: 'index',
		redirect: '/login'
	},
	{
		path: '/backpage',
		name: 'backpage',
		component: () => import('../views/BackPage.vue')
	},
	{
		path: '/front',
		name: 'front',
		component: () => import('../views/FrontPage.vue')
	}
]


const router = new VueRouter({
	routes
})

/*路由守卫   根据登录获得token*/

router.beforeEach((to, from, next) => {

	const isLogin = localStorage.Token ? true : false;
	console.log("是否登录:" + isLogin)

	if (to.path === "/login" || to.path === "/") {
		next();
		return;
	}
	else {
		if (isLogin) {
			try {
				const token = jwtDecode(localStorage.getItem('Token'));
				console.log(token)


				if (to.path === "/backpage") {
					if (token.identify === 'user') {
						console.log("Forbidden with no promission!")
						this.$message({
                            type: "error",
                            message: "Forbidden with no promission!",
                        });
						next('/login')
					}
					const expire = token.exp * 1000;
					const now = new Date().getTime();
					if (now > expire) {
						// 如果令牌已过期，则删除localStorage中的token并重新登录或提示用户重新登录
						localStorage.removeItem('Token');
						this.$message({
                            type: "error",
                            message: "Your identify has been passed. Please login again!",
                        });
						next('/login');
					} else {
						//如果令牌未过期，则将token保存到localStorage中，并继续访问该路由
						localStorage.setItem('Token', localStorage.getItem('Token'));
						next()
					}
				}
				else {
					const expire = token.exp * 1000;
					const now = new Date().getTime();
					if (now > expire) {
						// 如果令牌已过期，则删除localStorage中的token并重新登录或提示用户重新登录
						localStorage.removeItem('Token');
						alert('您的登录已过期，请重新登录。');
						next('/login');
					} else {
						//如果令牌未过期，则将token保存到localStorage中，并继续访问该路由
						localStorage.setItem('Token', localStorage.getItem('Token'));
						next()
					}
				}
			}
			catch (error) {
				console.log(error);
				next('/login');
			}
		}
		else {
			next('/login');
		}
	}
});


export default router
