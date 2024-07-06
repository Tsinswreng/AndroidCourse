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
	 * 登录服务
	 * @param name 用户名
	 * @param pswd 密码
	 * @returns id
	 */
	async login(name:str, pswd:str){
		const z = this
		const got = await z.dbSrc.seekUserByName(name) // 通过用户名查询用户
		const user = got[0] //取用户简单对象数组的首元素
		//判断该用户是否为空。若为空则抛出错误
		if(user == void 0){
			throw new Error(`${name}\nno such user`)
		}
		//校验查询到的用户的密码与传入的密码是否匹配。若匹配则返回用户id
		if(user.password === pswd){
			//登錄成功
			return user.id+''
		}else{
			return "" // 若不匹配则返回空字符串
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


	/**
	 * 使用高德地图的天气API获取天气
	 * @returns 
	 */
	async fetchWeather(){
		//url、 ${weatherKey}为自己申请的key 、城市代码110101代表北京东城区
		const url = `https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=${weatherKey}`
		//console.log(url)
		//用内置的fetch函数发送请求
		const resp = await fetch(url)
		if (!resp.ok) {
			throw new Error(`HTTP error! status: ${resp.status}`);
		}
		const str = await resp.text() //返回响应的json字符串
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
