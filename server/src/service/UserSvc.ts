import Tempus from "@src/util/Time";
import { mysqlConneOpt, dbPromise, weatherKey } from "../ENV";
import { DbErr, MysqlPromise } from "../MySqlPromise";
import { DbSrc } from "../db/DbSrc";
import * as Mod from '../model/Models'
import { Weather } from "@src/model/Weather";

export class UserSvc{
	protected constructor(){}
	protected async __Init__(...args: Parameters<typeof UserSvc.New>){
		const z = this
		const db = await dbPromise
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
			return user.password
		}else{
			return ""
		}
	}

	async signUp(name:str, pswd:str){
		const z = this
		const inst = Mod.User.new({
			id: NaN
			,name
			,password:pswd
			,ct: Tempus.new()
		})
		await z.dbSrc.addUser(inst)
		return true
	}

	async fetchWeather(){
		const url = `https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=${weatherKey}`
		//console.log(url)
		const resp = await fetch(url)
		if (!resp.ok) {
			throw new Error(`HTTP error! status: ${resp.status}`);
		}
		const str = await resp.text()
		return str
	}

	fmtWeather(weather:Weather){
		const l = weather.lives[0]
		if(l == void 0){
			return ""
		}
		const space = ' '
		const sb = [] as str[]
		sb.push(
			//l.province, l.city, space
			l.weather, space, l.temperature_float,'°'
			,space, '风力:',l.windpower
		)
		return sb.join('')
	}

}
