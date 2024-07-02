import * as Row  from "@src/model/DbRows"
import { ArticleSvc } from "../service/ArticleSvc"
import express, { text } from 'express'
import * as Mod from '../model/Models'

export class ArticleCtrl{
	protected constructor(){}
	protected async __Init__(...args: Parameters<typeof ArticleCtrl.New>){
		const z = this
		z.svc = await ArticleSvc.New()
		z.initRouter()
		return z
	}

	static async New(){
		const z = new this()
		z.__Init__()
		return z
	}

	get This(){return ArticleCtrl}

	protected _svc:ArticleSvc
	get svc(){return this._svc}
	protected set svc(v){this._svc = v}

	protected _router = express.Router()
	get router(){return this._router}
	protected set router(v){this._router = v}
	
	initRouter(){
		const z = this
		z.router.get('/allArticle', async(req, res)=>{
			const ans = await z.svc.getAllArticle()
			const json = JSON.stringify(ans)
			res.status(200).send(json)
		})

		z.router.post('/addComment', async(req,res)=>{
			try {
				const body = req.body as Row.Comment
				//console.log(body)
				const user_id = body.user_id
				const article_id = body.article_id
				const score = body.score
				const text = body.text
				await z.svc.comment(article_id, user_id, score, text)
				res.status(200).send('')
			} catch (err) {
				console.error(err)
				res.status(400).send('failed')
			}
		})

	}

}
