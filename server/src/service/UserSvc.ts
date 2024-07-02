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
		z.preloadWeatherJson().then()
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

	protected _weather:str = ""
	get weather(){return this._weather}
	protected set weather(v){this._weather = v}
	

	/**
	 * 
	 * @param name 
	 * @param pswd 
	 * @returns id
	 */
	async login(name:str, pswd:str){
		const z = this
		const got = await z.dbSrc.seekUserByName(name)
		const user = got[0]
		if(user.password === pswd){
			//登錄成功
			return user.id+''
		}else{
			return ""
		}
	}

	async preloadWeatherJson(){
		const z = this
		for(let i = 0; i < 20; i++){
			try {
				const got = await z.fetchWeather()
				if(got != void 0 && got.length > 0){
					z.weather = got
					console.log('load weather ok')
					break
				}
			} catch (err) {
				continue
			}
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
