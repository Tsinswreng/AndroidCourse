import { weatherKey } from "@src/ENV"
import { UserSvc } from "../service/UserSvc"
import express from 'express'
import { Weather } from "@src/model/Weather"

export class UserCtrl{
	protected constructor(){}
	protected async __Init__(...args: Parameters<typeof UserCtrl.New>){
		const z = this
		z.svc = await UserSvc.New()
		await z.initRouter()
		return z
	}

	static async New(){
		const z = new this()
		await z.__Init__()
		return z
	}

	//get This(){return UserCtrl}
	protected _svc:UserSvc
	get svc(){return this._svc}
	protected set svc(v){this._svc = v}
	
	protected _router = express.Router()
	get router(){return this._router}
	protected set router(v){this._router = v}

	isNonNullStr(...strs:any[]){
		for(const str of strs){
			if(typeof str !== 'string' || str.length === 0){
				return false
			}
		}
		return true
	}
	
	async initRouter(){
		const z = this

		/**
		 * 登录接口
		 * url: <挂载点>/login
		 * 请求方法:post
		 */
		z.router.post('/login', async(req,res)=>{
			try {
				//从请求体中解析请求参数 、包括用户名和密码
				const name = req.body['name']
				const pswd = req.body['pswd']
				// 判空
				if(!z.isNonNullStr(name, pswd)){
					// 状态码403, 返回登录失败字符串
					res.status(403).send('login failed')
					return 
				}
				// 调用服务层的登录函数，获取用户id
				const ans = await z.svc.login(name, pswd)
				// 检验登录是否成功
				if(ans === ""){
					res.status(403).send('login failed')
					return
				}
				// 登录成功，状态码200, 返回用户id
				res.status(200).send(ans)
				return
			} catch (error) {
				// 出现错误，登录失败
				res.status(400).send('login failed')
				console.error(error) //打印错误信息到控制台
			}
		})

		/** 註冊並登錄 */
		z.router.post('/signUp', async(req,res)=>{
			try {
				const name = req.body['name']
				const pswd = req.body['pswd']
				// console.log(name)
				// console.log(pswd) //t
				if(!z.isNonNullStr(name, pswd)){
					res.status(403).send('signUp failed')
					return 
				}

				const signAns = await z.svc.signUp(name, pswd)
				if(!signAns){
					res.status(403).send('signUp failed')
					return
				}
				const ans = await z.svc.login(name, pswd)
				if(ans === ""){
					res.status(403).send('signUp failed')
					return
				}
				res.status(200).send(ans)
				return
			} catch (error) {
				console.error(error)
				res.status(400).send('failed')
			}

		})

		//https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=
		//用魔法會導致連接超時
		z.router.get('/weather', async(req,res)=>{
			try {
				const str = await z.svc.fetchWeather()
				res.send(str)
			} catch (err) {
				console.error(err)
				res.status(500).send('failed')
			}
		})


		z.router.get('/fmtWeather', async(req,res)=>{
			try {
				let str:str
				if(z.svc.weather !== ""){
					str = z.svc.weather
				}else{
					str = await z.svc.fetchWeather()
				}
				const inst = JSON.parse(str) as Weather
				const ans = z.svc.fmtWeather(inst)
				res.status(200).send(ans)
			} catch (err) {
				console.error(err)
				res.status(500).send('')
			}
		})



		z.router.get('/test', (req,res)=>{
			res.send('test')
		})
	}
}
