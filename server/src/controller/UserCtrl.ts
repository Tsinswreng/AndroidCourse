import { UserSvc } from "../service/UserSvc"
import express from 'express'

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
	
	async initRouter(){
		const z = this
		z.router.post('/login', (req,res)=>{
			res.send('114')
		})
		z.router.get('/test', (req,res)=>{
			res.send('test')
		})
	}
}
