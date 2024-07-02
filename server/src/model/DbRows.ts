export class Row{

}

export class User extends Row{
	id:int
	name:str
	password:str
	ct:int
}


export class Article extends Row{
	id:int
	title:str
	author:str
	content:str
	ct:int
}

export class Comment extends Row{
	id:int
	article_id:int
	user_id:int
	text:str
	score:int
	ct:int
}



// class A{
// 	a:int
// 	b:str
// 	c:bool
// }

// function fn(prop:A){

// }

// fn({
// 	a:1
// 	,b:'1'
// 	,c:true
// })

// let objArr = [] as Article[]
// let arr:[str,str][] = [] //List<Pair<String, String>>
// for(const obj of objArr){
// 	let pair:[str,str] = [obj.title, obj.author]
// 	arr.push(pair)
// }