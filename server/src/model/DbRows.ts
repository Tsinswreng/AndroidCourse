export class Row{

}

/**
 * 用户
 */
export class User extends Row{
	id:int // INT AUTO_INCREMENT PRIMARY_KEY
	name:str // 用戶名 VARCHAR(255) NOT NULL UNIQUE
	password:str // 密码 VARCHAR(255) NOT NULL
	ct:int // 创建时间 BIGINT NOT NULL
}


/**
 * 文章
 */
export class Article extends Row{
	id:int // INT AUTO_INCREMENT PRIMARY_KEY
	title:str // 标题 VARCHAR(255)
	author:str // 作者 VARCHAR(255)
	content:str // 内容 TEXT
	ct:int // 创建时间 BIGINT NOT NULL
}

/**
 * 评论
 */
export class Comment extends Row{
	id:int // INT AUTO_INCREMENT PRIMARY_KEY
	article_id:int // 文章id INT FOREIGN KEY REFERENCES Article(id)
	user_id:int // 用户id INT FOREIGN KEY REFERENCES User(id)
	text:str // 评论内容 VARCHAR(1024)
	score:int // 评分 INT
	ct:int // 创建时间 BIGINT
}

export class Shoucang extends Row{
	id:int
	article_id:int
	user_id:int
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