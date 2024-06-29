import Tempus from '../util/Time'
import * as Row from './DbRows'
import { As } from '../util/Ut'
import { InstanceType_, KeyAsValue } from '../util/Type'

function assign(a,b){
	return Object.assign(a,b)
}


function keyAsValue<T extends kvobj>(obj:T){
	const ans = {}
	const keys = Object.keys(obj)
	for(const k of keys){
		ans[k] = k
	}
	return ans as KeyAsValue<T>
}




export class BaseInst<RowT extends Row.Row>{
	get Row(){return Row.Row}

	toRow():RowT{
		const z = this
		const ans = new z.Row()
		assign(ans, z)
		//@ts-ignore
		z.correctRow(ans)
		return ans as RowT
	}

	correctRow(row:RowT):RowT{
		return row
	}
	a():InstanceType_<typeof this.Row>{
		return void 0 as any
	}
}

export class BaseFactory<
	InstT extends BaseInst<Row.Row>
	, RowT extends Row.Row
>
{
	Inst:typeof BaseInst = BaseInst
	Row:typeof Row.Row = Row.Row
	//col = keyAsValue(neow this.Row()) as KeyAsValue<RowT>
	//繼承時 先初始化父類中 直接賦值ʹ字段
	col:KeyAsValue<RowT>
	protected constructor(){}
	protected __init__(){
		this.col = keyAsValue(new this.Row()) as KeyAsValue<RowT> //晚初始化
	}
	static new(){
		const z = new this()
		z.__init__()
		return z
	}
	new(prop:InstT):InstT{
		const z = this
		const ans = new z.Inst()
		assign(ans, prop)
		return ans as InstT
	}
	fromRow(row:RowT):InstT{
		const z = this
		let ans = new z.Inst()
		assign(ans, row)
		z.correctInst(ans as InstT)
		return ans as InstT
	}
	correctInst(inst:InstT):InstT{
		return inst
	}
}

class UserInst extends BaseInst<Row.User>{
	override get Row(){return Row.User}
	id:int
	name:str
	password:str
	ct:Tempus
	override correctRow(row: Row.User): Row.User {
		row.ct = Tempus.toUnixTime_mills(As(row.ct, Tempus))
		return row
	}
}

class UserFactory extends BaseFactory<UserInst, Row.User>{
	override Row = Row.User
	//@ts-ignore
	override Inst = UserInst
	override correctInst(inst: UserInst): UserInst {
		inst.ct = Tempus.new(As(inst.ct, 'number'))
		return inst
	}
}

export const User = UserFactory.new() as UserFactory
export type User = UserInst

//console.log(User)
// declare let user:User
// declare let userRow:Row.User
// userRow = user.a()

class ArticleInst extends BaseInst<Row.Article>{
	override get Row(){return Row.Article}
	id:int
	title:str
	content:str
	ct:Tempus
	override correctRow(row: Row.Article): Row.Article {
		row.ct = Tempus.toUnixTime_mills(As(row.ct, Tempus))
		return row
	}
}

class ArticleFactory extends BaseFactory<ArticleInst, Row.Article>{
	Row = Row.Article
	//@ts-ignore
	Inst = ArticleInst

	override correctInst(inst:ArticleInst){
		inst.ct = Tempus.new(As(inst.ct, 'number'))
		return inst
	}
}
export const Article = ArticleFactory.new() as ArticleFactory
export type Article = ArticleInst



class CommentInst extends BaseInst<Row.Comment>{
	get Row(){return Row.Comment}
	id:int
	user_id:int
	article_id:int
	text:str
	score:int
	ct:Tempus
	override correctRow(row: Row.Comment): Row.Comment {
		row.ct = Tempus.toUnixTime_mills(As(row.ct, Tempus))
		return row
	}
}

class CommentFactory extends BaseFactory<CommentInst, Row.Comment>{
	Row = Row.Comment
	//@ts-ignore
	Inst = CommentInst
	override correctInst(inst: CommentInst): CommentInst {
		inst.ct = Tempus.new(As(inst.ct, 'number'))
		return inst
	}
}

export const Comment = CommentFactory.new() as CommentFactory
export type Comment = CommentInst


