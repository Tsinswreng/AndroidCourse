import { InstanceType_, KeyMirror } from '../util/Type'
import * as Row from '../model/DbRows'
import * as Mod from '../model/Models'
import mysql from 'mysql';
import { MysqlPromise } from '../MySqlPromise';

class Tbl<FactT extends Mod.BaseFactory<any, any>>{
	protected constructor(){}
	protected __init__(...args: Parameters<typeof Tbl.new>){
		const z = this
		z.name = args[0]
		//@ts-ignore
		z.factory = args[1]
		z._col = z.factory.col
		return z
	}

	static new<FactT>(name:str, factory:FactT){
		//@ts-ignore
		const z = new this<FactT>()
		z.__init__(name, factory)
		return z
	}

	//get This(){return Tbl}
	protected _name:str
	get name(){return this._name}
	protected set name(v){this._name = v}

	protected _factory:FactT
	get factory(){return this._factory}
	protected set factory(v){this._factory = v}
	
	protected _col:FactT['col']
	get col(){return this._col}
	protected set col(v){this._col = v}
	
}

const TBL = Tbl.new.bind(Tbl)
class Tbls{
	user = TBL('user', Mod.User)
	article = TBL('article', Mod.Article)
	comment = TBL('comment', Mod.Comment)
}

const tbls = new Tbls()



class InitSql{
	protected constructor(){}
	protected __init__(...args: Parameters<typeof InitSql.new>){
		const z = this
		return z
	}

	static new(){
		const z = new this()
		z.__init__()
		return z
	}

	get This(){return InitSql}
	protected _tbls = tbls
	get tbls(){return this._tbls}
	protected set tbls(v){this._tbls = v}

	sql_mkAllTbls(){
		const z = this
		const ans = [
			z.mkTbl_user()
			,z.mkTbl_article()
			,z.mkTbl_comment()
		]
		return ans
	}
	
	mkTbl_user(){
		const z = this
		const tbl = z.tbls.user //user表对象
		const c = tbl.col //列名
		const ans = 
`CREATE TABLE IF NOT EXISTS ${tbl.name}(
	${c.id} INT AUTO_INCREMENT PRIMARY KEY
	,${c.name} VARCHAR(255) UNIQUE
	,${c.password} VARCHAR(255)
	,${c.ct} BIGINT
)`
		return ans
	}

	mkTbl_article(){
		const z = this
		const tbl = z.tbls.article
		const c = tbl.col
		const ans = 
`CREATE TABLE IF NOT EXISTS ${tbl.name}(
	${c.id} INT AUTO_INCREMENT PRIMARY KEY
	,${c.title} VARCHAR(255)
	,${c.author} VARCHAR(255)
	,${c.content} TEXT
	,${c.ct} BIGINT
)`
		return ans
	}


	mkTbl_comment(){
		const z = this
		const tbl = z.tbls.comment
		const c = tbl.col
		const ans = 
`CREATE TABLE IF NOT EXISTS ${tbl.name}(
	${c.id} INT AUTO_INCREMENT PRIMARY KEY
	,${c.user_id} INT
	,${c.article_id} INT
	,${c.ct} BIGINT
	,${c.text} TEXT
	,${c.score} INT
	,FOREIGN KEY (${c.user_id}) REFERENCES ${z.tbls.user.name}(${z.tbls.user.col.id})
	,FOREIGN KEY (${c.article_id}) REFERENCES ${z.tbls.article.name}(${z.tbls.article.col.id})
)`
		return ans
	}
}



export class DbSrc{
	protected constructor(){}
	protected __init__(...args: Parameters<typeof DbSrc.new>){
		const z = this
		z.db = args[0]
		return z
	}

	static new(db:mysql.Connection){
		const z = new this()
		z.__init__(db)
		return z
	}

	get This(){return DbSrc}

	protected _db:mysql.Connection
	get db(){return this._db}
	protected set db(v){this._db = v}

	protected _initSql = InitSql.new()
	protected get initSql(){return this._initSql}
	protected set initSql(v){this._initSql = v}
	
	protected _tbls = tbls
	get tbls(){return this._tbls}
	protected set tbls(v){this._tbls = v}
	

	async initSchema(){
		const z = this
		const sqls = z.initSql.sql_mkAllTbls()
		for(const sql of sqls){
			await MysqlPromise.query(z.db, sql)
		}
		return true
	}



	/**
	 * 添加用户
	 * @param inst 用户的程序模型实例
	 * @returns 
	 */
	async addUser(inst:Mod.User){
		const z = this
		const tbl = z.tbls.user // 用户表对象
		const c = tbl.col //表中各列的列名
		const sql = 
`INSERT INTO ${tbl.name} (${c.name}, ${c.password}, ${c.ct}) VALUES(?,?,?)` //插入sql，用问号作参数化查询，防止sql注入攻击
		const row = inst.toRow() //程序模型实例转简单对象
		const params = [row.name, row.password, row.ct] // 查询参数
		return await MysqlPromise.query(z.db, sql, params) //执行sql并传递参数
	}

	/**
	 * 通过用户名查询用户，返回简单用户对象数组。
	 * 实际上，由于name有unique约束，故数组中最多只有一个元素。
	 * @param name 用户名
	 * @returns 
	 */
	async seekUserByName(name:str){
		const z = this
		const tbl = z.tbls.user //用户表对象
		const c = tbl.col //列名
		const sql = 
`SELECT * FROM ${tbl.name} WHERE ${c.name} = ?` //查询sql
		const params = [name] //查询参数
		const [row, ] = await MysqlPromise.query<Row.User[]>(z.db, sql, params) // 执行sql
		return row //返回查询结果
	}

	async addArticle(inst:Mod.Article){
		const z = this
		const tbl = z.tbls.article
		const c = tbl.col
		const sql = 
`INSERT INTO ${tbl.name} (${c.title}, ${c.author}, ${c.content}, ${c.ct}) VALUES(?,?,?,?)`
		const row = inst.toRow()
		const params = [row.title, row.author, row.content, row.ct]
		return await MysqlPromise.query(z.db, sql, params)
	}

	async addComment(inst:Mod.Comment){
		const z = this
		const tbl = z.tbls.comment
		const c = tbl.col
		const sql = 
`INSERT INTO ${tbl.name} (${c.article_id}, ${c.user_id}, ${c.score}, ${c.text}, ${c.ct})
VALUES (?,?,?,?,?)`
		const r = inst.toRow()
		const params = [r.article_id, r.user_id, r.score, r.text, r.ct]
		return await MysqlPromise.query(z.db, sql, params)
	}

	/**
	 * 帶正文
	 * @returns 
	 */
	async getAllArticle(){
		const z = this
		const tbl = z. tbls.article
		const sql = 
`SELECT * FROM ${tbl.name}`
		const [ans ] = await MysqlPromise.query<Row.Article[]>(z.db, sql)
		return ans
	}

	async seekCommentByArticleId(id:int|str){
		const z = this
		const tbl = z.tbls.comment
		const sql = 
`SELECT * FROM ${tbl.name} WHERE ${tbl.col.article_id}=?`
		//console.log(sql)//t
		const params = id
		//@ts-ignore
		const [ans] = await MysqlPromise.query<Row.Comment[]>(z.db, sql, params)
		return ans
	}




	// /**
	//  * 不帶正文
	//  */
	// async getAllArticleInfo(){

	// }

	// async seekArticleById(){

	// }

}
