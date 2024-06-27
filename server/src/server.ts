//@ts-check
import express from 'express'
import bodyParser from "body-parser";

export class Server{
	protected constructor(){}
	protected __init__(...args: Parameters<typeof Server.new>){
		const z = this
		return z
	}

	static new(){
		const z = new this()
		z.__init__()
		return z
	}

	get This(){return Server}

	protected _app = express()
	get app(){return this._app}
	protected set app(v){this._app = v}

	protected _port = 3000
	get port(){return this._port}
	set port(v){this._port = v}
	
	initRoutes(){
		const z = this
		z.app.get('/', (req, res)=>{
			res.send('<h1>114514</h1>')
		})
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
		z.app.use(bodyParser.json({limit:'64MB'}))
	}

	async start(){
		const z = this
		z.initUse()
		z.initRoutes()
		z.app.listen(z.port, ()=>{
			console.log(z.port)
		})
	}

}
