import express from 'express'
import bodyParser from "body-parser";
import { UserCtrl } from './controller/UserCtrl';
import Tempus from './util/Time';
import { ArticleCtrl } from './controller/ArticleCtrl';

export class Server{
	protected constructor(){}
	protected async __Init__(...args: Parameters<typeof Server.New>){
		const z = this
		return z
	}

	static async New(){
		const z = new this()
		await z.__Init__()
		return z
	}

	get This(){return Server}

	protected _app = express()
	get app(){return this._app}
	protected set app(v){this._app = v}

	protected _port = 3000
	get port(){return this._port}
	set port(v){this._port = v}

	/**
	 * 初始化路由
	 */
	async initRoutes(){
		const z = this
		// 获取毫秒级unix时间戳，测试服务能否正常运作
		z.app.get('/unixMills', (req, res)=>{
			res.send(Tempus.toISO8601(Tempus.new()))
		})

		const userCtrl = await UserCtrl.New()//用户控制器
		z.app.use('/user', userCtrl.router);//将用户控制器的路由挂载到/user下
		z.app.use('/article', (await ArticleCtrl.New()).router) //将文章控制器的路由挂载到/article下
	}

	initUse(){
		const z = this

		//const VocaServer = z
		z.app.use((req:any, res:any, next:any)=>{
			res.header("Access-Control-Allow-Origin", "*");
			res.header("Access-Control-Allow-Methods", "*");
			res.header("Access-Control-Allow-Headers", "*");
			next()
		})
		//z.app.use(express.static('./out/frontend/dist'));
		z.app.use(express.json());
		z.app.use(bodyParser.json({limit:'64MB'}))
	}

	async start(){
		const z = this
		z.initUse()
		await z.initRoutes()
		z.app.listen(z.port, ()=>{
			console.log(z.port)
		})
	}

}
