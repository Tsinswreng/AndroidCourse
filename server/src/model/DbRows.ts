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
