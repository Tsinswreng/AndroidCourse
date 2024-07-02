import Tempus from "@src/util/Time";
import { mysqlConneOpt, dbPromise } from "../ENV";
import { DbErr, MysqlPromise } from "../MySqlPromise";
import { DbSrc } from "../db/DbSrc";
import * as Mod from '../model/Models'

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

	async comment(articleId:int, userId:int, score:int, text:str){
		const z = this
		const inst = Mod.Comment.new({
			id: NaN
			,user_id: userId
			,article_id: articleId
			,score: score
			,text: text
			,ct: Tempus.new()
		})
		await z.dbSrc.addComment(inst)
		return true
	}
	
}
