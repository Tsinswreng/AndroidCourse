export class User{
	protected constructor(){}
	protected __init__(...args: Parameters<typeof User.new>){
		const z = this
		return z
	}

	static new(){
		const z = new this()
		z.__init__()
		return z
	}

	get This(){return User}

	protected _id:int
	get id(){return this._id}
	protected set id(v){this._id = v}

	protected _name:str
	get name(){return this._name}
	protected set name(v){this._name = v}
	

	protected _password:str
	get password(){return this._password}
	set password(v){this._password = v}
	

}
