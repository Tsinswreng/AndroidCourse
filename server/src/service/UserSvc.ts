import { mysqlConneOpt } from "../ENV";
import { DbErr, MysqlPromise } from "../MySqlPromise";
import { DbSrc } from "../db/DbSrc";

export class UserSvc{
	protected constructor(){}
	protected async __Init__(...args: Parameters<typeof UserSvc.New>){
		const z = this
		const db = await MysqlPromise.connect(mysqlConneOpt)
		const dbSrc = DbSrc.new(db)
		z.dbSrc = dbSrc
		return z
	}

	static async New(){
		const z = new this()
		await z.__Init__()
		return z
	}

	//get This(){return UserCtrl}
	protected _dbSrc:DbSrc
	get dbSrc(){return this._dbSrc}
	protected set dbSrc(v){this._dbSrc = v}

	async login(name:str, pswd:str){
		const z = this
		// const name = req.body??['name']
		// const pwsd = req.body??['pwsd']
		// console.log(name)
		// console.log(pwsd)
		// console.log(typeof name)
		const got = await z.dbSrc.seekUserByName(name)
		const user = got[0]
		if(user.password === pswd){
			//登錄成功
			return true
		}else{
			return false
		}
	}

}
