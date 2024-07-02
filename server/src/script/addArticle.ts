import { mysqlConneOpt } from "../ENV";
import { DbErr, MysqlPromise } from "../MySqlPromise";
import { DbSrc } from "../db/DbSrc";
import * as fs from 'fs'
import * as Mod from '../model/Models'
import Tempus from "../util/Time";
const title = '兰停集序'
const author = '王羲之'
async function main(){
	try {
		const db = await MysqlPromise.connect(mysqlConneOpt)
		const dbSrc = DbSrc.new(db)
		const content = fs.readFileSync('./content.txt', {encoding: 'utf-8'})
		const inst = Mod.Article.new({
			id: NaN
			,content: content
			,title: title
			,ct: Tempus.new()
			,author: author
		})
		const ans = await dbSrc.addArticle(inst)
		//console.log(ans, 'ans')
	} catch (err) {
		if(err instanceof DbErr){
			console.error(err)
			console.error(err.sql)
		}
		throw err
	}finally{
		process.exit()
	}
}

main().catch(e=>{console.error(-1)})