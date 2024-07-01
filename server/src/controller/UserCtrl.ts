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
		z.router.post('/login', async(req,res)=>{
			try {
				const name = req.body['name']
				const pswd = req.body['pswd']
				if(!z.isNonNullStr(name, pswd)){
					res.status(403).send('login failed')
					return 
				}
				const ans = await z.svc.login(name, pswd)
				if(ans === ""){
					res.status(403).send('login failed')
					return
				}
				res.status(200).send(ans)
				return
			} catch (error) {
				console.error(error)
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
				const str = await z.svc.fetchWeather()
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
