import { mysqlConneOpt, dbPromise } from "../ENV";
import { DbErr, MysqlPromise } from "../MySqlPromise";
import { DbSrc } from "../db/DbSrc";

export class ArticleSvc{
	protected constructor(){}
	protected async __Init__(...args: Parameters<typeof ArticleSvc.New>){
		const z = this
		const db = await dbPromise
		z.dbSrc = DbSrc.new(db)
		return z
	}

	static async New(){
		const z = new this()
		await z.__Init__()
		return z
	}

	get This(){return ArticleSvc}

	protected _dbSrc:DbSrc
	get dbSrc(){return this._dbSrc}
	protected set dbSrc(v){this._dbSrc = v}

	async getAllArticle(){
		const z = this
		return await z.dbSrc.getAllArticle()
	}
	
}
