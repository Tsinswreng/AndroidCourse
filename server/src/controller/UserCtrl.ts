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
		z.router.post('/login', async(req,res)=>{
			try {
				const name = req.body['name']
				const pswd = req.body['pswd']
				if(typeof name !== 'string' || typeof pswd !== 'string'){
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


		z.router.get('/test', (req,res)=>{
			res.send('test')
		})
	}
}
